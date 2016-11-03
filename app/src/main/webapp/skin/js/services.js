/**
 * Created by mckay 2016-1-21.
 * service
 *
 * Functions (service)
 *
 */
define(function (require) {
    var app = require('./app');
    app.factory('ngApi',ngApi)
        .factory('cApi',cApi)
        .factory('auth',auth)
        .factory('info',info)
        .factory('baseData',baseData)
        .factory('files',files)
        .factory('superMenu',superMenu)

    //所有API请求主
    function ngApi(base,$http){
        return {
            post:function(fun,act,fd){
                return fd?$http({method: 'POST', url: base.url+fun.controller+'/'+fun.fun+'.do',data:act,headers: {'Content-Type': undefined}, transformRequest: angular.identity}):$http({method: 'POST', url: base.url+fun.controller+'/'+fun.fun+'.do',data:act});
            },
            json:function(act,fun){
                return $http({method: 'POST', url: fun.controller+'/'+fun.fun+'.json'});
            }
        }
    }

    //clever-m API数据请求与回调
    function cApi($rootScope,$q,ngApi,$state,base,info,cfpLoadingBar){
        return {
            post:function(act,fun,a){
                cfpLoadingBar.start();
                var defer = $q.defer();
                ngApi.post(fun, act,a).then(function(req){
                    if(req.data.success === false && req.data.code === 9999){
                        localStorage.removeItem("userinfo");
                        $rootScope.C.boxDisplay = false;
                        $state.go('account.login');
                        return false;
                    }
                    defer.resolve(req.data);
                },function(req){
                    info.error('错误了！联系程序猿欧巴');
                    defer.reject(req.data);
                });
                return defer.promise;
            },
            upFile:function(fd,ctrl,call){
                //$http没有progress 以下简陋模拟
                var defer = $q.defer();
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function(){
                    if(xhr.readyState==4 && xhr.status==200){
                        defer.resolve(JSON.parse(xhr.responseText));
                    }
                };

                xhr.upload.onprogress = function(evt){
                    var loaded = evt.loaded,tot = evt.total;
                    call(Math.floor(100*loaded/tot));
                };

                xhr.open("post",base.url+ctrl+'/save.do?kind=0');
                xhr.send(fd);
                return defer.promise;
            }
        }
    }

/*-----------------关于用户------------------*/

    //用户信息与验证
    function auth($rootScope,cApi,$state){
        return{
            get:function(loginId,password){
                return cApi.post({loginId:loginId,password:password}, {controller: 'user',fun:'login'});
            },
            state:function(){
                return localStorage.userinfo ? JSON.parse(localStorage.userinfo) : null;
            },
            logout:function(){
                localStorage.removeItem("userinfo");
                $rootScope.C.boxDisplay = false;
                this.brand('');
                this.store('');
                $state.go('account.login');
                cApi.post('', {controller: 'user',fun:'logout'});
            },
            allBrand:function(req){
                $rootScope.C.brands = req?req.aaData:'';
            },
            allOrgs:function(req){
                $rootScope.C.orgs = req?req.aaData:'';
            },
            brand:function(id){
                if(id || id == ''){
                    localStorage.brand = id;
                }else{
                    return localStorage.brand?localStorage.brand:'';
                }
            },
            store:function(id){
                if(id || id == ''){
                    localStorage.store = id;
                }else{
                    return localStorage.store?localStorage.store:'';
                }
            }
        }
    }

    //处理一些信息反馈
    function info(toaster){
        return {
            error:function(error){
                toaster.pop({type: 'success', title: error, timeout: 3000});
            }
        }
    }

    //统一接口数据调用
    function baseData(ngApi,cApi,$filter){
        return {
            get:function(res,controller){
                return cApi.post(res, {controller: controller,fun:'get'}).then(function(data){
                    return data;
                });
            },
            query:function(res,controller,is){
                if(is){
                    return cApi.post(res, {controller: controller,fun:'list'}).then(function(data){
                        return data.data;
                    });
                }else{
                    return cApi.post($filter('apiData')(res), {controller: controller,fun:'list'}).then(function(data){
                        return data.data;
                    });
                }
            },
            json:function(file){
                return ngApi.json('', {controller: 'data',fun:file}).then(function(data){
                    return data.data
                });
            },
            delete:function(res,controller){
                return cApi.post($filter('apiData')(res), {controller: controller,fun:'remove'}).then(function(data){
                    return data;
                });
            },
            save:function(res,controller,is){
                if(is){
                    return cApi.post(res, {controller: controller,fun:'save'}).then(function(data){
                        return data;
                    });
                }else{
                    return cApi.post($filter('apiData')(res), {controller: controller,fun:'save'}).then(function(data){
                        return data;
                    });
                }
            },
            update:function(res,controller){
                return cApi.post(res, {controller: controller,fun:'edit'}).then(function(data){
                    return data;
                });
            }
        }
    }

/*-----------------静态资源------------------*/
//七牛静态资源
function files(cApi){
    return {
        upFile:function(file,ctrl,qiniuPath){
            var fd = new FormData();
            fd.append("file",file);
            fd.append("kind",1);
            fd.append("qiniuPath",qiniuPath);
            return cApi.post(fd,{controller: ctrl,fun:'save'},true).then(function(data){
                return data;
            });
        },
        qnToken:function(type){
            return cApi.post({fileType:type}, {controller: 'pictrue',fun:'tokenAndKey'}).then(function(data){
                return data;
            });
        }
    }
}
/*-----------------小超人------------------*/

    //小超人菜单列表数据请求
    function superMenu(cApi,auth,$filter){
        return{
            save:function(type){
                var fd = new FormData();
                fd.append("file",type.files[0]);
                fd.append("shopId",auth.store());
                return cApi.post(fd,{controller: 'menu',fun:'upload'},true).then(function(data){
                    return data;
                });
            }
        }
    }

});