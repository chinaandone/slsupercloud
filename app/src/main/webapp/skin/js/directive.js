/**
 * mckay.
 * directive
 *
 * Functions (directives)
 * cTitle[meta title]
 */
define(function (require) {

	var app = require('./app');

	app.directive('cTitle', ['$rootScope', function ($rootScope) {
		//meta title
		return {
			link: function (scope, ele) {
				$rootScope.$on('$stateChangeStart', function (event, toState) {
					scope.title = toState.data && toState.data.pageTitle ? toState.data.pageTitle : '木爷机器人';
					ele.text(scope.title);
				});
			}
		}
	}])
	.directive('cHeader',['baseData','auth','$state', function (baseData,auth,$state) {
		//顶部
		return {
			restrict: 'EA',
			replace: true,
			templateUrl: 'view/header.html',
			link: function (scope,ele,attr) {
				//登陆成功后加载
				scope.$watch(attr.ngShow, function(value) {
					value?controllers():false;
				});
				//加载品牌
				scope.brandCtrl = function(){
					baseData.query({iDisplayAll:1,clientId:'',orgId:''},'client').then(function(req){
						scope.brands = req.aaData;
						auth.allBrand(req);
					});
				};
				//加载店铺
				scope.orgsCtrl = function(id){
					baseData.query({iDisplayAll:1,clientId:id,orgId:''},'org').then(function(req){
						scope.orgs = req.aaData;
						auth.allOrgs(req);
					});
				};

				//点击品牌事件
				scope.orgCtrl = function(id,reload){
					if(id != '0'){
						scope.brandId = id;
						auth.brand(id);
						scope.orgsCtrl(id);
					}else{
						auth.brand('');
						scope.brandId = '0';
						auth.allOrgs();
					}

					if(!reload){
						scope.orgId = '0';
						auth.store('');
						$state.go($state.current, {}, {reload: true});
					}
				};

				scope.orgGetId = function(id){
					id != '0'?auth.store(id):auth.store('');
					$state.go($state.current, {}, {reload: true});
				};

				if(auth.brand()){
					scope.brandId = auth.brand();
					scope.orgCtrl(scope.brandId,true);
				}else{
					scope.brandId = '0';
				}
				auth.store()?scope.orgId = auth.store():scope.orgId = '0' ;

				//监听事件
				function controllers (){
					if(auth.state().roleType === 'BRANDUSER'){
						auth.allBrand();
						auth.allOrgs();
						auth.brand(auth.state().clientId);
						auth.store('');
						scope.orgsCtrl(auth.state().clientId);
					}else if(auth.state().roleType === 'SUPERADMIN' || auth.state().roleType === 'JF'){
						//预加载品牌
						scope.brandCtrl();
					}else{
						auth.allBrand();
						auth.allOrgs();
/*						auth.brand('');
						auth.store('');*/
					}
				}

				//登出
				scope.logout = function (){
					scope.brandId = '0';
					scope.orgId = '0';
					auth.allOrgs();
					auth.logout();
				};
			}
		};
	}])
	.directive('cMenu',['auth','baseData', function (auth,baseData) {
		return {
			restrict: 'E',
			replace: true,
			templateUrl: 'view/menu.html',
			link: function (scope,ele,attr) {
				scope.$watch(attr.ngShow, function(value) {
					value?controllers():false;
				});
				function controllers(){
					//加载菜单数据
					var menu_type;
					if(auth.state().roleType === 'SUPERADMIN'){
						menu_type = 'c_menu';
					}else if(auth.state().roleType === 'JF'){
						menu_type = 'jf_menu';
					}else{
						menu_type = 's_menu';
					}
					//var menu_type = auth.state().roleType === 'SUPERADMIN' ? 'c_menu' : 's_menu';
					baseData.json(menu_type).then(function(req){
						scope.menu = req.data;
					});

					//菜单切换
					var $ = angular.element;
					$(ele).on('click', function (event) {
						var g = $(event.target);
						if (!g || g.length == 0) return;

						var is_has = g.hasClass("dropdown-toggle")?g.parent():g.parent().parent();

						angular.forEach(is_has.parent().find("li"), function (task) {
							if($(task).hasClass("open") || $(task).hasClass("active")){
								$(task).removeClass("open active");
							}
						});

						is_has.toggleClass('active');

					});
				}
			}
		}
	}])
	.directive('cBreadcrumb', function () {
		//面包屑
		return {
			restrict: 'E',
			replace: true,
			template: '<div class="breadcrumbs" id="breadcrumbs"><ul class="breadcrumb"><li><i class="icon-home home-icon"></i><a href="">首页</a></li><li class="active">控制台</li></ul></div>',
			link: function (scope, ele) {

			}
		};
	})
	.directive('upFile',['cApi','$filter','info', function (cApi,$filter,info) {
		return {
			restrict: 'E',
			replace: true,
			scope:{
				requp:'&'
			},
			template: '<div class="upload"><var style="width: {{per}}%;"></var><input type="file" onchange="angular.element(this).scope().change(this)"/></div>',
			link: function (scope, ele,attr) {
				scope.per = 0;
				scope.change = function(event){
					//上传对象
					var mypic = event.files[0],name = mypic.name.replace(/.+\./, "");

					if(attr.type === 'pictrue'){
						if($filter('inputType')(name,'img')<0){
							info.error("请上传png,jpg,gif,bmp图片格式！");
							return false;
						}
					}else if(attr.type === 'video'){
						if($filter('inputType')(name,'video')<0){
							info.error("请上传rmvb,aiv,mp4视频格式！");
							return false;
						}
						if(mypic.size>419430400){
							info.error('请上传视频小于50M！');
							return false;
						}
					}else{
						info.error('非法操作！');
					}

					var fd = new FormData();
					fd.append("file",mypic);

					//调用上传接口
					cApi.upFile(fd,attr.type,function(req){
						scope.$apply(function() {
							scope.per = req;
						});
					}).then(function(req){
						scope.requp({type:req});
						scope.per = 0;
						event.outerHTML = event.outerHTML.replace(/(value=\").+\"/i, "$1\"");
					});
				}
			}
		};
	}])
	.directive('ngQiniu', ['$parse','$qupload','$filter','files','info','cApi',function ($parse,$qupload,$filter,files,info,cApi) {
		return function (scope, elem, attr) {
			var fn = $parse(attr['ngQiniu']),fileType = 1;
			var fileElem = angular.element('<input type="file">');
			for (var i = 0; i < elem[0].attributes.length; i++) {
				fileElem.attr(elem[0].attributes[i].name, elem[0].attributes[i].value);
			}
			elem.append(fileElem);
			//
			elem = fileElem;

			//change qiniu file
			elem.bind('change', function (evt) {
				var fileList = evt.__files_ || evt.target.files,flist = {file: fileList[0]},name = flist.file.name.replace(/.+\./, ""),error = null;
				if(attr.type === 'pictrue'){
					if($filter('inputType')($filter('lowercase')(name),'img')<0){
						error = '请上传png,jpg,gif,bmp图片格式！';
					}
					fileType = 1;
				}else if(attr.type === 'video'){
					if($filter('inputType')($filter('lowercase')(name),'video')<0){
						error = '请上传rmvb,avi,mp4视频格式！';
					}

					if(flist.file.size>52428800){
						error = '请上传视频小于50M！';
					}
					fileType = 2;
				}else if(attr.type === 'material'){
                    if($filter('inputType')($filter('lowercase')(name),'material')>=0){
						fileType = 3;
					}
					else if($filter('inputType')($filter('lowercase')(name),'video')>=0){
						fileType = 4;
					}
					else if($filter('inputType')($filter('lowercase')(name),'img')>=0 ){
						fileType = 5;
					}
					else{
                        error = '请上传视频(rmvb、mp4、avi),apk,音频(mp3、wmv、wav),txt,图片(jpg、png、bmp)格式！';
                    }
                }else{
					error = '非法操作';
				}

				if(error){
					scope.$apply(function() {
						info.error(error);
					});
				}else if(fileType === 3){
					//material：apk,txt只走服务器流程
					var fd = new FormData();
					fd.append("file",flist.file);
					//fd.append("qiniuPath","FuMJj5jpAK8_wd2c0KvdwEmCaATt");
					fd.append("qiniuPath","");

					cApi.upFile(fd,attr.type,function(res){
						scope.$apply(function() {
							fn(scope, {
								progress:res
							});
						})

					}).then(function(res){
						fn(scope, {
							progress:0,
							res:res
						});
					});
                }else{
					//console.log("else qiniu");
					files.qnToken(fileType).then(function(req){
						//七牛图片上传
						var q = function(){
							flist.upload = $qupload.upload({
								key: req.save_key,
								file: flist.file,
								token: req.uptoken
							});
							flist.upload.then(function (res) {

							}, function (res) {
								console.info(res);
							}, function (evt) {
								fn(scope, {
									progress:Math.floor(100 * evt.loaded / evt.totalSize),
									res:'',
									qn:true
								});
							});
							return flist.upload;
						};

						//木爷图片上传
						var m = function(){
							return files.upFile(flist.file,attr.type,req.save_key);
						};

						//处理视频时间
						function getVidDur(key,call) {
							var qVideo = document.createElement("video");
							qVideo.src = 'http://cdn.myee7.com/'+key;
							qVideo.addEventListener("loadedmetadata", function () {
								call(Math.round(qVideo.duration*1000));
							});
						}

						q().then(function(res){
							var fd = new FormData();
							fd.append("file",flist.file);
							//fd.append("kind",1);
							fd.append("qiniuPath",res.key);
							if(fileType == 2 || fileType == 4){
								getVidDur(res.key,function(dur){
									fd.append("playSecond",dur);
									upFileYun();
								});
							}else{
								upFileYun();
							}

							function upFileYun(){
								cApi.upFile(fd,attr.type,function(res){
									scope.$apply(function() {
										fn(scope, {
											progress:res
										});
									})

								}).then(function(res){
									fn(scope, {
										progress:0,
										res:res
									});
								});
							}

						});

						/*$q.all([q(),m()]).then(function(res){
							fn(scope, {
								progress:0,
								res:res
							});
						});*/
					});
				}
			});
		};
	}])
	.directive('cVideo', ['$filter', function ($filter) {
		return {
			link: function (scope, ele,attr) {
				scope.$watch(attr.ngShow, function(val) {
					if(val){
						ele.html('<video src="'+$filter("myeeUrlVideo")(val.qiniuPath)+'" controls></video>');
					}
				});
			}
		}
	}])
	.directive('pieChart', [function () {
		return {
			restrict: 'E',
			template: '<div class="chart"></div>',
			replace: true,
			scope: {
				"options": "="
			},
			link: function (scope, element,attrs) {
				require.async(['http://echarts.baidu.com/dist/echarts.simple.min.js'], function() {
					//设置饼图高度
					element[0].style.height = attrs.height + "px";
					element[0].style.width = attrs.width + "px";

					// 基于准备好的dom，初始化echarts图表
					var myChart = echarts.init(element[0]);
					//监听options变化
					scope.$watch('options', function () {
						if(scope.options){
							myChart.setOption(scope.options);
						}
					}, true);
				});
			}
		};
	}])
	.directive('monitor', [function () {
		return {
			restrict: 'A',
			link:function(scope,element){
				require.async(['ng/jquery.circliful.min.js?a=17'], function() {
					var ele = element[0];
					$(ele).circliful();

				});

			}
		}
	}])
});