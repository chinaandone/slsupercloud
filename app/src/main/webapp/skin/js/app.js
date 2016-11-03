/**
 * Created by mckay on 16-1-21.
 * 定义ng主模块 clever
 */
'use strict';
define(function (require, exports, module) {
	var clever = angular.module('clever', ['ui.router','ngAnimate','ngLoading','cmPage','ngQiniu','ngDialog','toaster','ngdatepicker']);
    module.exports = clever;
});
