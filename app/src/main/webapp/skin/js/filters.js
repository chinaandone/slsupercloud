/**
 * Created by mckay on 16-1-25.
 * service
 *
 * Functions (filters)
 *
 */
define(function (require) {
    var app = require('./app');
    app.filter('inputType',inputType)//上传资源过滤
        .filter('routers',routers)//路由访问控制
        .filter('dateFormat',dateFormat)//时间戳格式
        .filter('myeeUrlImg',myeeUrlImg)//木爷图片链接
        .filter('myeeUrlVideo',myeeUrlVideo)//木爷视频链接
        .filter('pageNum',pageNum)
        .filter('apiData',apiData)
        .filter('roleType',roleType)
        .filter('bool',bool)
        .filter('today',today);

    //上传资源过滤
    function inputType(){
        var img  = ['png','jpg','jpeg','gif','bmp'],
            video = ['swf','avi','rmvb','mp3','mp4','mp5','mkv','wmv','csf','3gp'],
            txt = ['txt','pdf'],
            material = ['wav','apk','txt','bin','img'];
        return function(val,type){
            switch(type)
            {
                case 'img':
                    return img.indexOf(val);
                    break;
                case 'video':
                    return video.indexOf(val);
                    break;
                case 'txt':
                    return txt.indexOf(val);
                    break;
                case 'material':
                    return material.indexOf(val);
                    break;
                default:
                    return false;
            }
        }
    }

    //路由访问控制
    function routers(){
        var types = ['','index','account','org','advert','menu','slider','interact','history'];
        return function(type){
            if(typeof(type)=='string'){
                return types.indexOf(type) >= 0 ? true : false;
            }
        }
    }

    //时间戳格式
    function dateFormat($filter){
        var dateFilter = $filter('date');
        return function(value,ymd){
            return ymd?dateFilter(value, 'yyyy-MM-dd'):dateFilter(value, 'yyyy-MM-dd HH:mm:ss');
        }
    }

    //木爷图片链接qiNiuImg
    function myeeUrlImg($rootScope){
        return function (val){
            return val?$rootScope.C.qiNiuImg+val+'?imageView2/1/w/150/h/95':'';
            //return val?$rootScope.C.img+val.substring(0,val.lastIndexOf('/'))+'/240_240'+val.substring(val.lastIndexOf('/')):'';
        }
    }

    //视频链接
    function myeeUrlVideo($rootScope){
        return function (val,type){
            if(type){
                return val?$rootScope.C.qiNiuImg+val+'?vframe/jpg/offset/1':'';
            }else{
                return val?$rootScope.C.qiNiuImg+val:'';
            }
        }
    }

    //计算页数(分页)
    function pageNum(base){
        return function (num){
            return num % base.page_length == 0 ? num / base.page_length : parseInt(num / base.page_length + 1);
        }
    }

    //是品牌还是店铺
    function apiData(auth,base){
        return function (res){
            res.iDisplayLength = res.iDisplayLength || base.page_length;
            res.iDisplayStart = res.iDisplayStart || base.page_start;
            res.clientId = (res.clientId != undefined && res.clientId.length<=0)?res.clientId:auth.brand();
            res.orgId = (res.orgId != undefined && res.orgId.length<=0)?res.orgId:auth.store();

            return res;
        }
    }

    //账户类型
    function roleType(){
        return function(val){
            switch(val)
            {
                case 'SUPERADMIN':
                    return '超级用户';
                    break;
                case 'JF':
                    return '交付账号';
                    break;
                case 'CLIENTUSER':
                    return '店铺账号';
                    break;
                case 'BRANDUSER':
                    return '品牌账号';
                    break;
                default:
                    return '非法账号';
            }
        }
    }

    function today(){
        return function(t){
            var today = t?new Date(t):new Date();
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            today.setMilliseconds(0);
            return today;
        }
    }

    //bool转int，或int转bool
    function bool(){
        return function(val,isIntToBool){
            if(isIntToBool){
                return val==0?false:true;
            }else{
                return val==false?0:1;
            }
        }
    }
});