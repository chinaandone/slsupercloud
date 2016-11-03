/**
 * Created by mckay on 16-1-21.
 * router配置 
 */
'use strict';
define(function (require){
    var app = require('./app');

    app.value('base',{
        //url:'/app_test/',
        //url:'/app/',
        url:'/',
        page_length:10,
        page_start:0,
        page_all:0
    })
    .config(config)
    .run(run);

    function config ($stateProvider, $urlRouterProvider,$httpProvider,ngDialogProvider){
        //ngDialog
        ngDialogProvider.setDefaults({
            closeByDocument: false
        });

        //h5
        $httpProvider.defaults.transformRequest = function(obj){
            if(typeof obj === 'string'){
                return obj;
            }else{
                var str = [];
                for(var p in obj){
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            }
        };

        $httpProvider.defaults.headers.post = {
            'Content-Type': 'application/x-www-form-urlencoded'
        };

        //router
        $stateProvider
            .state('index', {
                url: '/index',
                templateUrl: 'view/index.html',
                controller: 'indexCtrl',
                data: { pageTitle: '控制面板' }
            })
            .state('account', {
                url: '/account',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('account.login', {
                url: '/login',
                templateUrl: 'view/login.html',
                controller: 'loginCtrl',
                data: { pageTitle: '登录' },
                onEnter:function($state,auth){
                    if(auth.state()){
                        $state.go('index');
                    }
                },
                onExit: function($rootScope,auth){
                    $rootScope.C.userInfo = auth.state();
                }
            })
            .state('super', {
                url: '/super',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('super.menu', {
                url: '/menu',
                templateUrl: 'view/superMenu.html',
                controller: 'menuCtrl',
                data: { pageTitle: '小超人-菜单' },
                resolve:{
                    list:function(baseData,auth,info){
                        if(auth.store()){
                            return baseData.query({shopId:auth.store()},'menu');
                        }else{
                            info.error('请先选择店铺！');
                            return false;
                        }
                    }
                }
            })
            .state('store', {
                url: '/store',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('store.activity', {
                url: '/activity',
                templateUrl: 'view/libraryActivity.html',
                controller: 'libraryActivity',
                data: { pageTitle: '本店活动',store:true },
                resolve:{
                    list:function(baseData){
                        return baseData.query({isStore:1},'rollMain');
                    }
                }
            })
            .state('store.video', {
                url: '/video',
                templateUrl: 'view/libraryVideo.html',
                controller: 'libraryVideo',
                data: { pageTitle: '本店视频',store:true },
                resolve:{
                    list:function(baseData){
                        return baseData.query({isStore:1},'videoBusiness');
                    }
                }
            })
            .state('clever', {
                url: '/clever',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('clever.activity_p', {
                url: '/activity_p',
                templateUrl: 'view/cleverActivity.html',
                controller: 'cleverActivity',
                data: { pageTitle: '木爷活动推送' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'rollPublish');
                    }
                }
            })
            .state('clever.video_p', {
                url: '/video_p',
                templateUrl: 'view/cleverVideo.html',
                controller: 'cleverVideo',
                data: { pageTitle: '木爷视频推送' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'videoPublish');
                    }
                }
            })
            .state('clever.advert_p', {
                url: '/advert_p',
                templateUrl: 'view/cleverAdvert.html',
                controller: 'cleverAdvert',
                data: { pageTitle: '内嵌广告推送' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'advertMain');
                    }
                }
            })
            .state('clever.material_p', {
                url: '/material_p',
                templateUrl: 'view/cleverMaterial.html',
                controller: 'cleverMaterial',
                data: { pageTitle: '素材推送' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'materialPublish');
                    }
                }
            })
            .state('clever.activity_d', {
                url: '/activity_d',
                templateUrl: 'view/libraryActivity.html',
                controller: 'libraryActivity',
                data: { pageTitle: '资源库-木爷活动' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'rollMain');
                    }
                }
            })
            .state('clever.video_d', {
                url: '/video_d',
                templateUrl: 'view/libraryVideo.html',
                controller: 'libraryVideo',
                data: { pageTitle: '资源库-木爷视频' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'videoBusiness');
                    }
                }
            })
            .state('clever.advert_d', {
                url: '/advert_d',
                templateUrl: 'view/libraryAdvert.html',
                controller: 'libraryAdvert',
                data: { pageTitle: '资源库-内嵌广告' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'advertisement');
                    }
                }
            })
            .state('clever.material_d', {
                url: '/material_d',
                templateUrl: 'view/libraryMaterial.html',
                controller: 'libraryMaterial',
                data: { pageTitle: '素材资源' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'materialBusiness');
                    }
                }
            })
            .state('jf', {
                url: '/jf',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('jf.marketManage', {
                url: '/marketManage',
                templateUrl: 'view/marketManage.html',
                controller: 'marketManage',
                data: { pageTitle: '推广管理' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'market');
                    }
                }
            })
            .state('jf.phone', {
                url: '/phone',
                templateUrl: 'view/tablePhone.html',
                controller: 'tablePhone',
                data: { pageTitle: '手机号设置' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'tablePhone');
                    }
                }
            })
            .state('jf.brand', {
                url: '/brand',
                templateUrl: 'view/manageBrand.html',
                controller: 'manageBrand',
                data: { pageTitle: '品牌管理' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'client');
                    }
                }
            })
            .state('jf.store', {
                url: '/store',
                templateUrl: 'view/manageStore.html',
                controller: 'manageStore',
                data: { pageTitle: '店铺管理' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({orgId:''},'org');
                    }
                }
            })
            .state('jf.tables', {
                url: '/tables',
                templateUrl: 'view/manageTables.html',
                controller: 'manageTables',
                data: { pageTitle: '餐桌管理' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'table');
                    }
                }
            })
            .state('jf.type', {
                url: '/type',
                templateUrl: 'view/manageType.html',
                controller: 'manageType',
                data: { pageTitle: '餐桌类型' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'tableType');
                    }
                }
            })
            .state('jf.user', {
                url: '/user',
                templateUrl: 'view/accountUser.html',
                controller: 'accountUser',
                data: { pageTitle: '账户管理' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'user');
                    }
                }
            })
            .state('charts', {
                url: '/charts',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('charts.calls', {
                url: '/calls',
                templateUrl: 'view/chartsCalls.html',
                controller: 'chartsCalls',
                data: { pageTitle: '呼叫服务' },
                resolve:{
                    list:function(baseData,auth){
                        return baseData.query({group:0,analysisLevel:1,clientId:auth.brand(),orgId:auth.store()},'accessLog');
                    },
                    tables:function(baseData,auth){
                        return auth.brand() && auth.store()?baseData.query({iDisplayAll:1,clientId:auth.brand(),orgId:auth.store()},'table'):'';
                    }
                }
            })
            .state('charts.review', {
                url: '/review',
                templateUrl: 'view/chartsReview.html',
                controller: 'chartsReview',
                data: { pageTitle: '服务评价' },
                resolve:{
                    list:function(baseData){
                        return baseData.query({},'evaluation');
                    }
                }
            })
            .state('monitor', {
                url: '/monitor',
                templateUrl: 'view/ui-view.html',
                controller: ''
            })
            .state('monitor.cooky', {
                url: '/cooky',
                templateUrl: 'view/monitor.html',
                controller: 'monitor',
                data: { pageTitle: '大学士-Cooky' },
                resolve:{
                    cooky:function(baseData,auth,info){
                        if(auth.store()){
                            return baseData.query({},'computer');
                        }else{
                            info.error('请先选择店铺！');
                            return false;
                        }
                    },
                    list:function(baseData){
                        return baseData.json('monitor');
                    }
                }
            });
            
        $urlRouterProvider.otherwise('/index');//重定向
    }

    function run ($state, $rootScope, $location ,$filter,auth){
        //全局变量（备用）
       $rootScope.C = {
           boxDisplay:false,
           //img:'http://www.myee7.com/push/',
           //img:'http://www.myee7.com/push_test/',
           img:'http://127.0.0.1/push/',
           qiNiuImg:'http://7xl2nm.com2.z0.glb.qiniucdn.com/',
           //qiNiuImg:'http://cdn.myee7.com/',
           brands:false,
           orgs:false,
           error:'',
           today:new Date()
        };


        //初始化界面/登陆信息
        if(auth.state()){
            $rootScope.C.boxDisplay = true;
            $rootScope.C.userInfo = auth.state();
        }

        //过滤登陆
        $rootScope.$on('$stateChangeStart', function (event, next) {
            $rootScope.state = $state;
            if($filter('routers')(next.url.replace("/",""))){
                if(auth.state() === null){
                    $location.path('account/login');
                }
            }
        });

    }
});