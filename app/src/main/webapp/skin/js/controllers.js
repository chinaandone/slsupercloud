/**
 * Created by mckay on 16-1-21.
 * ui.router配置
 */
'use strict';

define(function (require) {
    var app = require('./app');

    app.controller('indexCtrl',indexCtrl)
        .controller('loginCtrl',loginCtrl)
        .controller('accountUser',accountUser)
        .controller('menuCtrl',menuCtrl)
        .controller('cleverActivity',cleverActivity)
        .controller('cleverVideo',cleverVideo)
        .controller('cleverAdvert',cleverAdvert)
        .controller('cleverMaterial',cleverMaterial)
        .controller('libraryActivity',libraryActivity)
        .controller('libraryVideo',libraryVideo)
        .controller('libraryAdvert',libraryAdvert)
        .controller('libraryMaterial',libraryMaterial)
        .controller('tablePhone',tablePhone)
        .controller('tableWatch',tableWatch)
        .controller('marketManage',marketManage)
        .controller('manageBrand',manageBrand)
        .controller('manageStore',manageStore)
        .controller('manageTables',manageTables)
        .controller('manageType',manageType)
        .controller('chartsCalls',chartsCalls)
        .controller('chartsReview',chartsReview)
        .controller('monitor',monitor);

    //loginCtrl.$inject = ['$rootScope','$scope','auth','$state','info'];

    function indexCtrl(){
    }

    //登陆
    function loginCtrl($rootScope,$scope,auth,$state,info){
        var vm = $scope.vm = {
            loginId:'',
            password:'',
            login:function(){
                auth.get(this.loginId,this.password).then(function(req){
                    if(req.code ===1 && !req.success){
                        info.error(req.message);
                    }else{
                        var userInfo = req.data;
                        localStorage.userinfo = JSON.stringify(userInfo);
                        $rootScope.C.boxDisplay = true;
                        if(userInfo.roleType == 'BRANDUSER' || userInfo.roleType == 'CLIENTUSER'){
                            auth.brand(userInfo.clientId);
                            auth.store(userInfo.orgId);
                        }
                        $state.go('index');
                    }
                })
            }
        };
    }

    //小超人-菜单
    function menuCtrl($scope,list,baseData,superMenu,base,auth,info,ngDialog,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({shopId:auth.store(),queryKey:vm.search_val || '',iDisplayStart:($scope.currentPage - 1)*base.page_length},'menu').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    $scope.pageCount = $filter('pageNum')(vm.count);
                });
            },
            edit:function(index){
                ngDialog.open({
                    template: 'editMenu',
                    controller: ['$scope','view', function($scope,view) {
                        var vm = $scope.vm = {
                            search_val:'',
                            view:view || {},
                            title:(view?'修改':'添加'),
                            scanCode:view?view.scanCode:'',
                            active:view?$filter('bool')(view.status,true):true,
                            save:function(){
                                var data = {
                                    id:this.view.id || '',
                                    menuId:parseInt(this.view.menuId) || '',
                                    subMenuId:parseInt(this.view.subMenuId) || '',
                                    scanCode:parseInt(this.view.scanCode),
                                    name:this.view.name,
                                    price:parseInt(this.view.price),
                                    photo:this.view.photo || '',
                                    status:$filter('bool')(vm.active)
                                };
                                if(view){
                                    baseData.update($filter('apiData')(data),'menu').then(function(req){
                                        req.success?ngDialog.closeAll('修改成功!'):info.error(req.message);
                                    });
                                }else{
                                    baseData.save($filter('apiData')(data),'menu').then(function(req){
                                        req.success?ngDialog.closeAll('添加成功!'):info.error(req.message);
                                    });
                                }
                            },
                            menuImg:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.view.photo = res.data.qiniuPath;
                                }
                            },
                            del_img:function(){
                                this.view.photo = '';
                            }
                        };
                        //vm.view.status = 0;
                    }],
                    resolve: {
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            },
            search:function(){
                vm.get();
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };

        //上传菜单
        $scope.menuUP = function(type){
            superMenu.save(type).then(function(req){
                if(req.success){
                    vm.get();
                    info.error('导入菜单成功！');
                }else{
                    info.error('导入菜单失败！');
                }
            });
        };
    }

    //推送-轮播木爷活动
    function cleverActivity($scope,list,baseData,base,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'rollPublish').then(function(data){
                    vm.list = data.aaData;
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    var l = vm.list[index];
                    baseData.delete({rollMainId:l.rollMainId,title:l.title,publishTime:l.publishTime},'rollPublish').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //推送---木爷视频
    function cleverVideo($scope,list,baseData,base,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'videoPublish').then(function(data){
                    vm.list = data.aaData;
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    var v = vm.list[index], data = {
                        videoPublishId:(v.videoPublishId?v.videoPublishId:''),
                        businessId: v.businessId,
                        description:v.description,
                        publishTime:v.publishTime
                    };
                    baseData.delete(data,'videoPublish').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //推送---木爷内嵌广告
    function cleverAdvert($scope,list,baseData,base,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'advertMain').then(function(data){
                    vm.list = data.aaData;
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    var data = {title:vm.list[index].title,created:vm.list[index].created};
                    baseData.delete(data,'advertMain').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //推送---素材推送
    function cleverMaterial($scope,list,baseData,base,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'materialPublish').then(function(data){
                    vm.list = data.aaData;
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    var data = {description:vm.list[index].description,publishTime:vm.list[index].publishTime,materialBusinessId:vm.list[index].materialBusinessId};
                    baseData.delete(data,'materialPublish').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //资源---木爷活动[本店活动]
    function libraryActivity($scope,$state,list,base,baseData,info,ngDialog,$filter){
        var vm = $scope.vm = {
            list:list.aaData,
            view:'',
            isStore:$state.current.data.store?1:'',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length,isStore:vm.isStore},'rollMain').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                var tView = type?{title:vm.list[index].title, descriptionText:vm.list[index].descriptionText}:'';
                ngDialog.open({
                    template: 'addActivity',
                    controller: ['$scope','view','isStore',function($scope,view,isStore) {
                        var vm = $scope.vm = {
                            isStore:isStore,
                            title:(view?'修改':'添加'),
                            view:view,
                            pic:view?view:'',
                            picMore:view?view.rollDetailList:[],
                            minDate:$filter('dateFormat')(new Date(),true),
                            activityImg:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.pic = res.data;
                                }
                            },
                            activityImgMore:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.picMore=vm.picMore.concat(res.data);
                                }
                            },
                            save:function(){
                                if(vm.pic && vm.view.title && vm.view.descriptionText && vm.picMore.length>0){
                                    var data = {
                                        pictrueId:vm.pic.pictrueId,
                                        orderSeq:1,
                                        startTime:'',
                                        endTime:'',
                                        title:vm.view.title,
                                        description:vm.view.descriptionText,
                                        rollDetailViewList:vm.picMore
                                    };

                                    if(vm.isStore) {
                                        var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';

                                        if (startTime && endTime && endTime > startTime) {
                                            data.startTime = startTime;
                                            data.endTime = endTime;
                                        } else {
                                            info.error('下架时间必须大于上架时间！');
                                            return false
                                        }
                                    }

                                    if(view.rollMainId){
                                        data.rollMainId = view.rollMainId;
                                        baseData.update({rollMainViewJson:JSON.stringify(data)},'rollMain').then(function(req){
                                            req.success?ngDialog.closeAll('修改成功！'):info.error(req.message?req.message:'保存失败，请稍后再试！');
                                        });
                                    }else{
                                        baseData.save({rollMainViewJson:JSON.stringify(data)},'rollMain').then(function(req){
                                            req.success?ngDialog.closeAll('保存成功！'):info.error(req.message?req.message:'保存失败，请稍后再试！');
                                        });
                                    }
                                }else{
                                    info.error('请完整填写内容！')
                                }
                            },
                            picMoreRemove:function(id,index){
                                if(id!=undefined){
                                    baseData.delete({rollDetailId:id},'rollDetail').then(function(req){
                                        req.success?vm.picMore.splice(index,1):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                    });
                                }else{
                                    vm.picMore.splice(index,1)
                                }
                            }
                        };
                        if(vm.view && vm.isStore){
                            vm.startTime = $filter('dateFormat')(vm.view.startTime,true);
                            vm.endTime = $filter('dateFormat')(vm.view.endTime,true);
                        }
                    }],
                    resolve: {
                        view:function(){
                            return type?vm.list[index]:'';
                        },
                        isStore:function(){
                            return vm.isStore;
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res === '$closeButton') {
                            if(type){
                                vm.list[index].title = tView.title;
                                vm.list[index].descriptionText = tView.descriptionText;
                            }
                        }else{
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            },
            remove:function(id,index){
                if(confirm("确认删除?")){
                    baseData.delete({rollMainId:id},'rollMain').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            },
            push:function(index){
                ngDialog.open({
                    template: 'pushActivity',
                    controller: ['$scope','view',function($scope,view) {
                        var pm = $scope.pm = {
                            view:view,
                            minDate:$filter('dateFormat')(new Date(),true),
                            save:function(){
                                var startTime = new Date(pm.startTime).getTime() || '',endTime = new Date(pm.endTime).getTime() || '';
                                if(startTime && endTime && endTime>startTime){
                                    var data = [{
                                        rollMainId:pm.view.rollMainId,
                                        orderSeq:1,
                                        startTime:startTime,
                                        endTime:endTime,
                                        title:pm.view.title
                                    }];
                                    baseData.save({value:JSON.stringify(data)},'rollPublish').then(function(req){
                                        req.success?ngDialog.closeAll('推送成功！'):ngDialog.closeAll(req.message);
                                    });
                                }else{
                                    info.error('下架时间必须大于上架时间！');
                                }
                            }
                        };
                    }],
                    resolve: {
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.list[index].push = true;
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };

    }

    //资源---木爷视频[本店视频]
    function libraryVideo($scope,list,$state,base,baseData,ngDialog,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            isStore:$state.current.data.store?1:'',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length,isStore:vm.isStore},'videoBusiness').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                var tView = type?{description:vm.list[index].description}:'';
                ngDialog.open({
                    template: 'addVideo',
                    controller: ['$scope','view','isStore',function($scope,view,isStore) {

                        var vm = $scope.vm = {
                            isStore:isStore,
                            title:(view.businessId?'修改':'添加'),
                            view:view,
                            pic:view?view:'',
                            minDate:$filter('dateFormat')(new Date(),true),
                            video:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.pic = res.data;
                                }
                            },
                            save:function(){
                                if(vm.pic.videoId && vm.view.description){
                                    var data = {"videoId":vm.pic.videoId,"kind":vm.view.kind,"timeStart":"","timeEnd":"","description":vm.view.description};
                                    if(vm.isStore){
                                        var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';
                                        if (startTime && endTime && endTime > startTime) {
                                            data.timeStart = startTime;
                                            data.timeEnd = endTime;
                                        } else {
                                            info.error('下架时间必须大于上架时间！');
                                            return false
                                        }
                                    }

                                    if(vm.view.businessId){
                                        data.businessId = vm.view.businessId;
                                        baseData.update(data,'videoBusiness').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message);
                                        });
                                    }else{
                                        baseData.save(data,'videoBusiness').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message);
                                        });
                                    }
                                }else{
                                    info.error('请完整填写内容！');
                                }
                            }
                        };

                        if(vm.view && vm.isStore){
                            vm.startTime = $filter('dateFormat')(vm.view.timeStart,true);
                            vm.endTime = $filter('dateFormat')(vm.view.timeEnd,true);
                        }
                    }],
                    resolve: {
                        view:function(){
                            return type?vm.list[index]:{};
                        },
                        isStore:function(){
                            return vm.isStore;
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res === '$closeButton') {
                            if(type){
                                vm.list[index].description = tView.description;
                            }
                        }else{
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    baseData.delete({businessId:vm.list[index].businessId},'videoBusiness').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            },
            push:function(index){
                ngDialog.open({
                    template: 'pushVideo',
                    controller: ['$scope','view',function($scope,view) {
                        var vm = $scope.vm = {
                            view:view,
                            minDate:$filter('dateFormat')(new Date(),true),
                            save:function(){
                                var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';

                                if(startTime && endTime && endTime>startTime){
                                    var data = [{
                                        businessId:vm.view.businessId,
                                        orderSeq:1,
                                        kind:vm.view.kind,
                                        timeStart:startTime,
                                        timeEnd:endTime,
                                        description:vm.view.description,
                                        playFreq:vm.view.playFreq
                                    }];

                                    baseData.save({value:JSON.stringify(data)},'videoPublish').then(function(req){
                                        req.success?ngDialog.closeAll('发布成功！'):ngDialog.closeAll(req.message);

                                    });
                                }else{
                                    info.error('下架时间必须大于上架时间！');
                                }
                            }
                        };
                    }],
                    resolve: {
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.list[index].push = true;
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //资源---木爷内嵌广告
    function libraryAdvert($scope,list,base,baseData,ngDialog,info,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length,isStore:vm.isStore},'advertisement').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                var tView = type?{title:vm.list[index].title, description:vm.list[index].description}:'';
                ngDialog.open({
                    template: 'addAdvert',
                    controller: ['$scope','view',function($scope,view) {
                        var vm = $scope.vm = {
                            title:(view?'修改':'添加'),
                            view:view,
                            pic:view?view:'',
                            advertImg:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.pic = res.data;
                                }
                            },
                            save:function(){
                                if(vm.view.title && vm.pic.pictrueId && vm.view.description){
                                    var data = {
                                        title:vm.view.title,
                                        pictrueId:vm.pic.pictrueId,
                                        description:vm.view.description
                                    };
                                    if(vm.view.advertisementId){
                                        data.advertisementId = vm.view.advertisementId;
                                        baseData.update(data,'advertisement').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                        });
                                    }else{
                                        baseData.save(data,'advertisement').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                        });
                                    }
                                }else{
                                    info.error('请完整填写内容！')
                                }
                            }
                        };
                    }],
                    resolve: {
                        view:function(){
                            return type?vm.list[index]:'';
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res === '$closeButton') {
                            if(type){
                                vm.list[index].title = tView.title;
                                vm.list[index].description = tView.description;
                            }
                        }else{
                            info.error(res);
                            vm.get();
                        }

                    }
                });
            },
            remove:function(id,index){
                if(confirm("确认删除?")){
                    baseData.delete({advertisementId:id},'advertisement').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            },
            push:function(index){
                ngDialog.open({
                    template: 'pushAdvert',
                    controller: ['$scope','view',function($scope,view) {
                        var vm = $scope.vm = {
                            view:view,
                            minDate:$filter('dateFormat')(new Date(),true),
                            save:function(){
                                var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';
                                if(startTime && endTime && endTime>startTime){
                                    baseData.save({advertisementId:vm.view.advertisementId, orderSeq:1, timeStart:startTime, timeEnd:endTime},'advertMain').then(function(req){
                                        req.success?ngDialog.closeAll('发布成功！'):ngDialog.closeAll(req.message)
                                    });
                                }else{
                                    info.error('下架时间必须大于上架时间！');
                                }
                            }
                        };
                    }],
                    resolve: {
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.list[index].push = true;
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }


    //资源---素材资源
    function libraryMaterial($scope,list,$state,base,baseData,ngDialog,info,$filter){
        var vm = $scope.vm = {
            list:list.aaData,
            view: '',
            isStore:$state.current.data.store?1:'',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length,isStore:vm.isStore},'materialBusiness').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                var tView = type?{description:vm.list[index].description}:'';
                ngDialog.open({
                    template: 'addMaterial',
                    controller: ['$scope','view','isStore',function($scope,view,isStore) {
                        //控制器 逻辑代码
                        var vm = $scope.vm = {
                            isStore:isStore,
                            title:(view.materialBusinessId?'修改':'添加'),
                            view:view,
                            pic:view?view:'',
                            active:view?$filter('bool')(view.active,true):true,
                            minDate:$filter('dateFormat')(new Date(),true),
                            material:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.pic = res.data;
                                }
                            },
                            save:function(){
                                if(vm.pic.materialId && vm.view.description){
                                    var data = {"materialId":vm.pic.materialId,"kind":vm.view.kind,"timeStart":"","timeEnd":"","description":vm.view.description,"active":$filter('bool')(vm.active),"packageName":vm.view.packageName|| '',"version":vm.view.version|| ''};
                                    if(vm.isStore){
                                        var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';
                                        if (startTime && endTime && endTime > startTime) {
                                            data.timeStart = startTime;
                                            data.timeEnd = endTime;
                                        } else {
                                            info.error('下架时间必须大于上架时间！');
                                            return false
                                        }
                                    }

                                    if(vm.view.materialBusinessId){
                                        data.materialBusinessId = vm.view.materialBusinessId;
                                        baseData.update(data,'materialBusiness').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message);
                                        });
                                    }else{
                                        baseData.save(data,'materialBusiness').then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message);
                                        });
                                    }
                                }else{
                                    info.error('请完整填写内容！');
                                }
                            }
                        };

                        if(vm.view && vm.isStore){
                            vm.startTime = $filter('dateFormat')(vm.view.timeStart,true);
                            vm.endTime = $filter('dateFormat')(vm.view.timeEnd,true);
                        }
                    }],
                    resolve:{
                        view:function(){
                            return type?vm.list[index]:{};
                        },
                        isStore:function(){
                            return vm.isStore;
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res === '$closeButton') {
                            if(type){
                                vm.list[index].title = tView.title;
                                vm.list[index].descriptionText = tView.descriptionText;
                            }
                        }else{
                            info.error(res);
                            vm.get();
                        }

                    }
                });
            },
            remove:function(index){
                if(confirm("确认删除?")){
                    baseData.delete({materialBusinessId:vm.list[index].materialBusinessId},'materialBusiness').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                        }
                    });
                }
            },
            push:function(index){
                ngDialog.open({
                    template: 'pushMaterial',
                    controller: ['$scope','view',function($scope,view) {
                        var vm = $scope.vm = {
                            view:view,
                            minDate:$filter('dateFormat')(new Date(),true),
                            save:function(){
                                var startTime = new Date(vm.startTime).getTime() || '',endTime = new Date(vm.endTime).getTime() || '';

                                if(startTime && endTime && endTime>startTime){
                                    var data = [{
                                        materialBusinessId:vm.view.materialBusinessId,
                                        orderSeq:1,
                                        kind:1,
                                        timeStart:startTime,
                                        timeEnd:endTime,
                                        description:vm.view.description
                                    }];

                                    baseData.save({value:JSON.stringify(data)},'materialPublish').then(function(req){
                                        req.success?ngDialog.closeAll('发布成功！'):ngDialog.closeAll(req.message);

                                    });
                                }else{
                                    info.error('下架时间必须大于上架时间！');
                                }
                            }
                        };
                    }],
                    resolve: {
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.list[index].push = true;
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //手机号设置
    function tablePhone($scope,base,list,baseData,info,ngDialog,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'tablePhone').then(function(data){
                    vm.list = data.aaData;
                });
            },
            syncs:function(index){
                var v = vm.list[index];
                baseData.save({tablePhoneId:v.tablePhoneId || '',tableId:v.tableId,phone:v.phone || ''},'tablePhone').then(function(req){
                    req.success?info.error('同步成功！'):info.error(req.message);
                    vm.get();
                });
            },
            edit:function(index){
                var phone = vm.list[index].phone;
                ngDialog.open({
                    template: 'editTablePhone',
                    controller: ['$scope','view', function($scope,view) {
                        var nm = $scope.nm = {
                            view:view,
                            save:function(){
                                var phones = this.view.phone.split(",");
                                var validResult = true;
                                angular.forEach(phones,function(data,index,array){
                                    //data等价于array[index]
                                    if(!(/^(13[0-9]|14[0|1|2|3|4|5|6|7]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(data)) || data.length !== 11){
                                        validResult = false;
                                        return false;
                                    }
                                });
                                if(validResult){
                                    baseData.update({tablePhoneId:this.view.tablePhoneId || '',phone:this.view.phone},'tablePhone').then(function(req){
                                        if(req.success){
                                            info.error(req.message);
                                            ngDialog.closeAll();
                                        }else{
                                            info.error(req.message);
                                        }
                                    });
                                }else{
                                    info.error('请输入准确的手机号！');
                                }
                            }
                        };
                    }],
                    resolve:{
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res == '$closeButton') {
                            $scope.$apply(function() {
                                vm.list[index].phone = phone;
                            });
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //手环设置
    function tableWatch($scope,base,list,baseData,info,ngDialog,$filter){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'tableWatch').then(function(data){
                    vm.list = data.aaData;
                });
            },
            syncs:function(index){
                var v = vm.list[index];
                baseData.save({tableWatchId:v.tableWatchId || '',tableId:v.tableId,watchadd:v.watchadd|| '',dongleadd:v.dongleadd|| '',mac_address:v.mac_address || ''},'tableWatch').then(function(req){
                    req.success?info.error('同步成功！'):info.error(req.message);
                    vm.get();
                });
            },
            edit:function(index){
                var phone = vm.list[index].phone;
                var watchadd = vm.list[index].watchadd;
                var dongleadd = vm.list[index].dongleadd;
                var mac_address = vm.list[index].mac_address;
                ngDialog.open({
                    template: 'editTableWath',
                    controller: ['$scope','view', function($scope,view) {
                        var nm = $scope.nm = {
                            view:view,
                            save:function(){
                                var watchadd = this.view.watchadd;
                                var dongleadd = this.view.dongleadd;
                                var mac_address = this.view.mac_address;
                                var validResult = true;
                                // angular.forEach(phones,function(data,index,array){
                                //     //data等价于array[index]
                                //     if(!(/^(13[0-9]|14[0|1|2|3|4|5|6|7]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(data)) || data.length !== 11){
                                //         validResult = false;
                                //         return false;
                                //     }
                                // });
                                if(validResult){
                                    baseData.update({tableWatchId:this.view.tableWatchId || '',watchadd:this.view.watchadd||'',dongleadd:this.view.dongleadd||'',mac_address:this.view.mac_address},'tableWatch').then(function(req){
                                        if(req.success){
                                            info.error(req.message);
                                            ngDialog.closeAll();
                                        }else{
                                            info.error(req.message);
                                        }
                                    });
                                }else{
                                    info.error('请输入正确的手环地址范围和DongleMac地址！');
                                }
                            }
                        };
                    }],
                    resolve:{
                        view:function(){
                            return vm.list[index];
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res == '$closeButton') {
                            $scope.$apply(function() {
                                vm.list[index].phone = phone;
                                vm.list[index].watchadd = watchadd;
                                vm.list[index].dongleadd = dongleadd;
                                vm.list[index].mac_address = mac_address;
                            });
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //推广管理
    function marketManage($scope,$state,list,base,baseData,info,ngDialog,$filter,auth){
        var vm = $scope.vm = {
            list:list.aaData,
            view:'',
            count:list.itotalRecords,
            where:{},
            get:function(){
                baseData.query({iDisplayAll:0,iDisplayStart:($scope.currentPage - 1)*base.page_length,company:vm.where.company?vm.where.company:''},'market').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                //if(!auth.store() && index == undefined){//修改时不弹提示，新增时必须先选店铺
                //    info.error('请先选择要推广的店铺！');
                //    return false;
                //}
                var tView = type?{company:vm.list[index].company, brand:vm.list[index].brand,videoName:vm.list[index].videoName}:'';
                ngDialog.open({
                    template: 'addMarket',
                    controller: ['$scope','view',function($scope,view) {
                        var vm = $scope.vm = {
                            title:(view?'修改':'添加'),
                            view:view,
                            picMore:view?[].concat(view.monitors):[],
                            minDate:$filter('dateFormat')(new Date(),true),
                            activityImgMore:function(progress,res,qn){
                                $scope.progress = qn?progress/2:(progress>0?progress/2+50:0);
                                if(res){
                                    vm.picMore=vm.picMore.concat(res.data);
                                }
                            },
                            save:function(){
                                if(vm.view.company && vm.view.brand && vm.view.videoName){
                                    var data = {
                                        company:vm.view.company,
                                        brand:vm.view.brand,
                                        videoName:vm.view.videoName,
                                        mediaForm:vm.view.mediaForm,
                                        adLength:vm.view.adLength,
                                        playFreq:vm.view.playFreq,
                                        circle:vm.view.circle,
                                        timeStart:'',
                                        timeEnd:'',
                                        monitors:vm.picMore
                                    };

                                    var timeStart = new Date(vm.timeStart).getTime() || '',timeEnd = new Date(vm.timeEnd).getTime() || '';

                                    if (timeStart && timeEnd && timeEnd > timeStart) {
                                        data.timeStart = timeStart;
                                        data.timeEnd = timeEnd;
                                    } else {
                                        info.error('下画时间必须大于上画时间！');
                                        return false
                                    }

                                    if(view.marketId){
                                        data.marketId = view.marketId;
                                        baseData.update({dataJson:JSON.stringify(data)},'market').then(function(req){
                                            req.success?ngDialog.closeAll('修改成功！'):info.error(req.message?req.message:'保存失败，请稍后再试！');
                                        });
                                    }else{
                                        baseData.save({dataJson:JSON.stringify(data)},'market').then(function(req){
                                            req.success?ngDialog.closeAll('保存成功！'):info.error(req.message?req.message:'保存失败，请稍后再试！');
                                        });
                                    }
                                }else{
                                    info.error('请完整填写内容！')
                                }
                            },
                            picMoreRemove:function(index){
                                vm.picMore.splice(index,1)
                            }
                        };
                        if(vm.view){
                            vm.timeStart = $filter('dateFormat')(vm.view.timeStart,true);//true：年月日；false:年月日时分秒
                            vm.timeEnd = $filter('dateFormat')(vm.view.timeEnd,true);//true：年月日；false:年月日时分秒
                        }
                    }],
                    resolve: {
                        view:function(){
                            return type?vm.list[index]:'';
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res === '$closeButton') {//点击关闭按钮后执行
                            if(type){
                                vm.list[index].company = tView.company;
                                vm.list[index].brand = tView.brand;
                                vm.list[index].videoName = tView.videoName;
                            }
                        }else{//提交成功后执行
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            },
            remove:function(id,index){
                if(confirm("确认删除?")){
                    baseData.delete({marketId:id},'market').then(function(req){
                        if(req.success){
                            info.error('删除成功！');
                            vm.list.splice(index,1);
                            vm.count--;
                            $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
                            //mk的分页器会监听pageCount的变化，每次变化会触发$scope.onPageChange()函数，所以只要加这一句就可以达到删除数据分页跟着变化
                        }
                    });
                }
            },
            search:function(){
                vm.get();
            },
            tableToExcel : function(table, name) {
                if(!vm.where.company || vm.where.company.length == 0){
                    info.error('请输入“发布公司名称”以便导出！');
                    return false;
                }
                //去后台取出所有数据
                baseData.query({iDisplayAll:1,company:vm.where.company?vm.where.company:''},'market').then(function(data){
                    var tableHTML='<div class="rating_search"><!--cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc -->' +
                        '<label>发布公司:'+vm.where.company+'</label><br><label>操作日期:'+ vm.Format("yyyy/MM/dd",new Date()) +'</label></div><table><thead><tr><th>发布品牌</th><th>视频名称</th><th>媒体形式</th><th>广告长度</th><th>日频次</th><th>商圈</th><th>品牌</th><th>门店</th><th>终端数量</th><th>上画时间</th><th>下画时间</th><th>监控照片</th></tr></thead><tbody>';
                    if(data.aaData && data.aaData.length > 0){
                        angular.forEach(data.aaData, function (indexData, index, array) {
                            //indexData等价于array[index]
                            tableHTML+='<tr style="height: 100px">';
                            tableHTML+='<td>'+indexData.brand + '</td>';
                            tableHTML+='<td>'+indexData.videoName + '</td>';
                            tableHTML+='<td>'+indexData.mediaFormName + '</td>';
                            tableHTML+='<td>'+indexData.adLengthName + '</td>';
                            tableHTML+='<td>'+indexData.playFreqName + '</td>';
                            tableHTML+='<td>'+indexData.circle + '</td>';
                            tableHTML+='<td>'+indexData.clientName + '</td>';
                            tableHTML+='<td>'+indexData.orgName + '</td>';
                            tableHTML+='<td>'+indexData.deviceCount + '</td>';
                            tableHTML+='<td>'+$filter('dateFormat')(indexData.timeStart,true) + '</td>';
                            tableHTML+='<td>'+$filter('dateFormat')(indexData.timeEnd,true) + '</td>';
                            tableHTML+='<td>';
                            if(indexData.monitors && indexData.monitors.length > 0){
                                angular.forEach(indexData.monitors, function (indexData2, index2, array2) {
                                    tableHTML+=('<img src="'+ $filter('myeeUrlImg')(indexData2.qiniuPath) +'">');
                                });
                            }
                            else{
                                tableHTML+=' ';
                            };
                            tableHTML+='</td>';
                            tableHTML+='</tr>';
                        });
                    }
                    tableHTML+=('</tbody></table>');

                    var uri = 'data:application/vnd.ms-excel;base64,'
                        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
                        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
                        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
                    //if (!table.nodeType) table = document.getElementById(table)
                    //var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
                    var ctx = {worksheet: name || '木爷推广管理数据', table: tableHTML};
                    window.location.href = uri + base64(format(template, ctx));
                });

            },
            Format : function (fmt,date) {
                var o = {
                    "M+": date.getMonth() + 1, //月份
                    "d+": date.getDate(), //日
                    "h+": date.getHours(), //小时
                    "m+": date.getMinutes(), //分
                    "s+": date.getSeconds(), //秒
                    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                    "S": date.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //品牌管理
    function manageBrand($rootScope,$scope,list,$filter,baseData,base,ngDialog,info){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'client').then(function(data){
                    vm.list = data.aaData;
                    if(vm.count != data.itotalRecords){
                        vm.count = data.itotalRecords;
                        $rootScope.C.brands.unshift(vm.list[0]);
                    }
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                ngDialog.open({
                    template: 'addBrand',
                    controller: ['$scope','view', function($scope,view) {
                        var vm = $scope.vm = {
                            title:view?'修改':'添加',
                            view:view?view:'',
                            save:function(){
                                var data = {clientId:this.view.clientId?this.view.clientId:'',name:this.view.name,description:this.view.description};
                                if(view){
                                    baseData.update(data,'client').then(function(req){
                                        req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                    });
                                }else {
                                    baseData.save(data,'client').then(function (req) {
                                        req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                    });
                                }
                            }
                        };
                    }],
                    resolve: {
                        view: function() {
                            return type === 'edit'?vm.list[index]:'';
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //店铺管理
    function manageStore($rootScope,$scope,list,$filter,baseData,base,ngDialog,info,auth){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({orgId:'',iDisplayStart:($scope.currentPage - 1)*base.page_length},'org').then(function(data){
                    vm.list = data.aaData;
                    if(vm.count != data.itotalRecords){
                        vm.count = data.itotalRecords;
                        auth.brand() && $rootScope.C.orgs.unshift(vm.list[0]);
                    }

                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                ngDialog.open({
                    template: 'addStore',
                    controller: ['$scope','view','brands', function($scope,view,brands) {
                        var vm = $scope.vm = {
                            title:view?'修改':'添加',
                            view:view || '',
                            brands:brands || '',
                            clientId:view.clientId?view.clientId:0,
                            save:function(){
                                if(vm.clientId){
                                    var data = {
                                        orgId:this.view.orgId?this.view.orgId:'',
                                        clientId:vm.clientId,
                                        name:this.view.name,
                                        description:this.view.description,
                                        phone:this.view.phone,
                                        fax:this.view.fax
                                    };
                                    if(view){
                                        baseData.update(data,'org',true).then(function(req){
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                        });
                                    }else {
                                        baseData.save(data,'org',true).then(function (req) {
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                        });
                                    }
                                }else{
                                    info.error('请先选择添加的品牌！');
                                    return false;
                                }
                            }
                        };
                    }],
                    resolve: {
                        view: function() {
                            return type === 'edit'?vm.list[index]:'';
                        },
                        brands:function(){
                            return $rootScope.C.brands;
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton'){
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //餐桌管理
    function manageTables($scope,list,$filter,baseData,base,ngDialog,auth,info){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'table').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                if(auth.brand()){
                    if(auth.store() || type === 'edit'){
                        ngDialog.open({
                            template: 'addManageTables',
                            controller: ['$scope','view','list', function($scope,view,list){
                                var vm = $scope.vm = {
                                    title:view?'修改':'添加',
                                    tableType:list.aaData || '',
                                    tableTypeId:view.tableTypeId || 0,
                                    view:view?view:'',
                                    clientId:view.clientId?view.clientId:0,
                                    save:function(){
                                        if(this.view.scanCode && this.view.scanCode>200){
                                            info.error('桌子码只能是0-200之间的数字！');
                                            return false;
                                        }
                                        if(this.tableTypeId){
                                            var data = {
                                                tableId:this.view.tableId?this.view.tableId:'',
                                                name:this.view.name,
                                                scanCode:this.view.scanCode || '',
                                                erpId:this.view.erpId || '',
                                                description:this.view.description,
                                                seatAdded:this.view.seatAdded,
                                                clientId:auth.brand(),
                                                orgId:auth.store(),
                                                tableTypeId:this.tableTypeId
                                            };
                                            if(view){
                                                baseData.update(data,'table').then(function(req){
                                                    req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                                });
                                            }else {
                                                baseData.save(data,'table').then(function (req) {
                                                    req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                                });
                                            }
                                        }else{
                                            info.error('请先添加餐桌类型！');
                                        }
                                    }
                                };
                            }],
                            resolve: {
                                view:function(){
                                    return type === 'edit'?vm.list[index]:'';
                                },
                                list:function(baseData){
                                    return baseData.query({iDisplayAll:1},'tableType');
                                }
                            },
                            preCloseCallback: function(res) {
                                if(res != '$closeButton') {
                                    info.error(res);
                                    vm.get();
                                }
                            }
                        });
                    }else{
                        info.error('请先选择店铺！');
                    }
                }else{
                    info.error('请先选择品牌！');
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };
    }

    //餐桌类型
    function manageType($scope,list,$filter,baseData,base,ngDialog,auth,info){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'tableType').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                if(auth.brand()){
                    if(auth.store() || type === 'edit'){
                        ngDialog.open({
                            template: 'addManageType',
                            controller: ['$scope','view', function($scope,view) {
                                var vm = $scope.vm = {
                                    title:view?'修改':'添加',
                                    view:view?view:'',
                                    clientId:view.clientId?view.clientId:0,
                                    save:function(){
                                        var data = {
                                            tableTypeId:this.view.tableTypeId?this.view.tableTypeId:'',
                                            name:this.view.name,
                                            description:this.view.description,
                                            capacity:this.view.capacity,
                                            minimum:this.view.minimum,
                                            clientId:auth.brand(),
                                            orgId:auth.store()
                                        };
                                        if(view){
                                            baseData.update(data,'tableType').then(function(req){
                                                req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                            });
                                        }else {
                                            baseData.save(data,'tableType').then(function (req) {
                                                req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                            });
                                        }
                                    }
                                };
                            }],
                            resolve: {
                                view:function(){
                                    return type === 'edit'?vm.list[index]:'';
                                }
                            },
                            preCloseCallback: function(res) {
                                if(res != '$closeButton') {
                                    info.error(res);
                                    vm.get();
                                }
                            }
                        });
                    }else{
                        info.error('请先选择店铺！');
                    }
                }else{
                    info.error('请先选择品牌！');
                }
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };

    }

    //品牌账号管理
    function accountUser($scope,$rootScope,list,$filter,baseData,base,ngDialog,info){
        var vm = $scope.vm = {
            list: list.aaData,
            view: '',
            count:list.itotalRecords,
            get:function(){
                baseData.query({iDisplayStart:($scope.currentPage - 1)*base.page_length},'user').then(function(data){
                    vm.list = data.aaData;
                    vm.count = data.itotalRecords;
                    if(data.itotalRecords%base.page_length === 1){
                        $scope.pageCount = $filter('pageNum')(vm.count);
                    }
                });
            },
            add:function(type,index){
                ngDialog.open({
                    template: 'addAccountUser',
                    controller: ['$scope','view','brands', function($scope,view,brands) {
                        var vm = $scope.vm = {
                            title:view?'修改':'添加',
                            view:view?view:'',
                            brands:brands || '',
                            clientId:view.clientId?view.clientId:0,
                            orgId:0,
                            getOrgID:function(){
                                baseData.query({iDisplayAll:1,clientId:this.clientId,orgId:''},'org',true).then(function(req){
                                    vm.orgs = req.aaData;
                                    vm.orgId = 0;
                                });
                            },
                            save:function(){
                                var data = {
                                    userId:this.view.userId?this.view.userId:'',
                                    loginId:this.view.loginId,
                                    name:this.view.username,
                                    birthday:new Date(this.view.birthday).getTime(),
                                    phone:this.view.phone || '',
                                    mobile:this.view.mobile || '',
                                    email:this.view.email || ''
                                };
                                if(view){
                                    baseData.update(data,'user',true).then(function(req){
                                        req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                    });
                                }else{
                                    if(this.clientId && this.orgId){
                                        data.orgId = this.orgId>0 ? this.orgId : vm.orgs[0].orgId;
                                        data.clientId = this.clientId;
                                        data.password = this.view.password;
                                        data.roleType = this.view.roleType;

                                        baseData.save(data,'user',true).then(function (req) {
                                            req.success?ngDialog.closeAll(vm.title+'成功！'):info.error(req.message?req.message:'操作失败，请稍后再试！');
                                        });
                                    }else{
                                        info.error('请先选择品牌/店铺！');
                                    }
                                }
                            }
                        };
                        if(vm.view){
                            vm.view.birthday = $filter('dateFormat')(vm.view.birthday,true);
                            vm.roleType = vm.view.roleType;
                        }else{
                            vm.roleType = 'BRANDUSER';
                        }
                    }],
                    resolve: {
                        view: function() {
                            return type === 'edit'?vm.list[index]:'';
                        },
                        brands:function(){
                            return $rootScope.C.brands;
                        }
                    },
                    preCloseCallback: function(res) {
                        if(res != '$closeButton') {
                            info.error(res);
                            vm.get();
                        }
                    }
                });
            }
        };

        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };

    }

    //呼叫服务
    function chartsCalls(list,tables,$scope,$filter,baseData,info,base,auth){
        var today = $filter('today')();
        var vm = $scope.vm = {
            list:list.level1Analysis,
            tables:tables?tables.aaData:'',
            clientId:0,
            orgId:0,
            tableId:0,
            Leve:0,
            mode:0,
            Leves:[{id:0,name:"呼叫服务"},{id:21,name:"优惠专区活动"},{id:22,name:"本店推介活动"},{id:4,name:"呼叫结账"},{id:31,name:"视频广告详情"},{id:36,name:"云端奖券"}],
            thisWeek:new Date(today.getTime() + 86400000 * (8 - today.getDay())-1000),
            lastWeek:new Date(today.getTime() - 86400000 * (today.getDay() + 6)),
            lastWeekLast:new Date(today - 86400000 * (today.getDay())),
            startTime:'',
            endTime:'',
            data_:'',
            data:function(iDisplayAll,group,analysisLevel,actionId){
                if(arguments.length>0){
                    this.data_ = {
                        tableId:this.tableId>0?this.tableId:'',
                        iDisplayAll:iDisplayAll,
                        begin:this.startTime?$filter('today')(this.startTime).getTime():'',
                        end:this.endTime?$filter('today')(this.endTime).getTime()+86400000-1000:'',
                        group:group || 0,
                        actionId:actionId || '',
                        analysisLevel:analysisLevel || 1
                    };
                    return this.data_;
                }else{
                    return this.data_;
                }
            },
            get:function(){
                baseData.query(this.data(),'accessLog').then(function(data){
                    vm.list = vm.Leve>0?data.level2Analysis:data.level1Analysis;
                    vm.pie(vm.Leves[vm.Leve].name,vm.list)
                });
            },
            getTables:function(){
                if(this.data_){
                    this.data_.tableId = this.tableId > 0?this.tableId :'';
                }
            },
            WeekData:function(bool){
                if(bool){
                    this.startTime = $filter('dateFormat')(today-(today.getDay()-1)*86400000,true);
                    this.endTime = $filter('dateFormat')(this.thisWeek,true);
                }else{
                    this.startTime = $filter('dateFormat')(this.lastWeek,true);
                    this.endTime = $filter('dateFormat')(this.lastWeekLast,true);
                }
            },
            chart:function(refresh,down){
                if(down){
                    if(this.startTime && this.endTime){
                        var url_ = [];
                        angular.forEach(this.data_ || this.data(1,1,1,''), function (val,key) {
                            key == 'group'?url_.push('group' + "=" + 1):url_.push(key + "=" + val);
                        });
                        location.href = base.url+'accessLog/excel.do?'+url_.join('&')+'&clientId='+auth.brand()+'&orgId='+auth.store();
                    }else{
                        info.error('请先选择导出时间！');
                    }
                }else{
                    if(this.mode>0 && !refresh){
                        this.pie(this.Leves[this.Leve].name,this.list,true);
                    }else{
                        this.Leve>0?this.data(1,0,2,this.Leves[this.Leve].id):this.data(1,0,1,'');
                        this.get();
                    }
                }
            },
            pie:function(title,data){
                var pie_data = [];
                if(data.length>0){
                    angular.forEach(data, function (res) {
                        var pName = vm.Leve>0?res.levelSecondName:res.actionName;
                        vm.mode == '1'?pie_data.push({value: res.timeStay+1, name: pName+' '+(res.timeStay+1)}):pie_data.push({value: res.clickNum, name: pName+' '+res.clickNum});
                    });
                }else{
                    pie_data.push({value: 0, name:'暂无数据'});
                }

                $scope.pie = {
                    options: {
                        title : {text: title, x:'center'},
                        toolbox: {show: true, feature: {dataView: {show: true, readOnly: false}, restore: {show: true}, saveAsImage: {show: true}}},
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: [0, '70%'],
                                data:pie_data
                            }
                        ]
                    }
                }
            }
        };
        //初始数据
        vm.pie(vm.Leves[0].name,vm.list);
    }

    //统计-服务评价
    function chartsReview($scope,list,baseData,$filter,base,info){
        var today = $filter('today')();
        var vm = $scope.vm = {
            list: list.paginationView.aaData,
            feelAVG:list.feelAVG[0],
            view: '',
            count: list.paginationView.itotalRecords,
            startTime:'',
            endTime:'',
            thisWeek:new Date(today.getTime() + 86400000 * (8 - today.getDay())-1000),
            lastWeek:new Date(today.getTime() - 86400000 * (today.getDay() + 6)),
            lastWeekLast:new Date(today - 86400000 * (today.getDay())),
            get: function () {
                var data = {
                    iDisplayStart:($scope.currentPage - 1) * base.page_length,
                    begin:this.startTime?$filter('today')(this.startTime).getTime():'',
                    end:this.endTime?$filter('today')(this.endTime).getTime()+86400000-1000:''
                };
                baseData.query(data, 'evaluation').then(function (data) {
                    vm.list = data.paginationView.aaData,vm.count = data.paginationView.itotalRecords;
                    $scope.pageCount = vm.count>0?$filter('pageNum')(vm.count):'';
                });
            },
            WeekData:function(bool){
                if(bool){
                    this.startTime = $filter('dateFormat')(today-(today.getDay()-1)*86400000,true);
                    this.endTime = $filter('dateFormat')(this.thisWeek,true);
                }else{
                    this.startTime = $filter('dateFormat')(this.lastWeek,true);
                    this.endTime = $filter('dateFormat')(this.lastWeekLast,true);
                }
            },
            chart:function(d){
                if(d){
                    if(this.startTime && this.endTime){
                        var down_url = [base.url,'evaluation/excel.do?begin=',
                            $filter('today')(this.startTime).getTime(),
                            '&end=',
                            $filter('today')(this.endTime).getTime()
                        ].join('');
                        location.href = down_url;
                    }else{
                        info.error('请先选择导出时间！');
                    }
                }else{
                    vm.get();
                }
            }
        };
        //
        $scope.pageCount = $filter('pageNum')(vm.count);//定义页数
        $scope.onPageChange = function (){//获取分页数据
            vm.thisdata === false ? vm.get() : vm.thisdata = false;
        };

    }

    //监控
    function monitor($scope,cooky,$filter,info,baseData){
        var vm = $scope.vm = {
            list:false,
            cooky:cooky,
            search_list:[],
            Arr:[],
            get:function(){
                this.Arr = [];
                angular.forEach($filter('filter')(this.list, {checked: true}), function(v) {
                    vm.Arr.push({action:v.action});
                });
                if(this.Arr.length>0){
                    var data = {"deviceId": "xxxxx", "members": this.Arr};
                    baseData.query({data:JSON.stringify(data)}, 'monitor').then(function (res) {
                        vm.search_list = res;
                    });
                }else{
                    info.error('请先选择搜索条件！');
                }
            },
            copy:function(){

            },
            reload:function(){

            },
            close:function(){

            }
        };

        if(cooky){
            baseData.json('monitor').then(function(res){
               vm.list = res.data || false;
            });
        }
    }
});