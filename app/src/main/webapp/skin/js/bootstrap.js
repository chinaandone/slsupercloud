/**
 * Created by mckay on 16-1-21.
 * 启动
 */
'use strict';
/**
 * [加载ng插件 调用route 启动ng]
 */
define(function(require){
    //加载ng 主库/插件
    seajs.use(['ng/plug'], function() {
    //加载业务库
        require.async(['./routes','./directive','./services','./controllers','./filters',window.attachEvent?"ng/jquery-1.10.2.min":"ng/jquery-2.0.3.min"], function () {
             angular.element(document).ready(function () {
                angular.bootstrap(document, ['clever']);//准备完成开始渲染
             });
        });
    });
});