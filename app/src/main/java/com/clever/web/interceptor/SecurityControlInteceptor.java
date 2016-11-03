package com.clever.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.clever.common.domain.AccessLog;
import com.clever.common.domain.User;
import com.clever.common.service.AccessLogManageService;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import com.clever.common.view.AjaxResult;
import com.clever.web.controller.BaseController;
import com.clever.web.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * enva.liang@clever-m.com
 * 拦截类,补充请说明
 */
public class SecurityControlInteceptor extends HandlerInterceptorAdapter{

	@Autowired
	AccessLogManageService accessLogManageService;

	private static List<String[]> needAccessLogPatternAfterList = null;


	static {
		needAccessLogPatternAfterList = new LinkedList<String[]>();
		needAccessLogPatternAfterList.add(new String[]{"/user/login", "web端登录"});
		needAccessLogPatternAfterList.add(new String[]{"/user/logout", "web端登出"});

		needAccessLogPatternAfterList.add(new String[]{"/pictrue/save", "图片文件保存"});
		needAccessLogPatternAfterList.add(new String[]{"/pictrue/edit", "图片文件修改"});
		needAccessLogPatternAfterList.add(new String[]{"/pictrue/remove", "图片文件删除"});
		needAccessLogPatternAfterList.add(new String[]{"/pictrue/get", "获取一条图片文件"});
		needAccessLogPatternAfterList.add(new String[]{"/pictrue/list", "分页图片列表"});

		needAccessLogPatternAfterList.add(new String[]{"/rollDetail/remove", "活动详细删除"});

		needAccessLogPatternAfterList.add(new String[]{"/rollMain/save", "活动保存"});
		needAccessLogPatternAfterList.add(new String[]{"/rollMain/edit", "活动修改"});
		needAccessLogPatternAfterList.add(new String[]{"/rollMain/orderSeq/edit", "本店活动排序修改"});
		needAccessLogPatternAfterList.add(new String[]{"/rollMain/remove", "活动删除"});
		needAccessLogPatternAfterList.add(new String[]{"/rollMain/list", "分页获取活动列表"});

		needAccessLogPatternAfterList.add(new String[]{"/rollPublish/save", "活动推送保存"});
		needAccessLogPatternAfterList.add(new String[]{"/rollPublish/remove", "活动推送删除"});
		needAccessLogPatternAfterList.add(new String[]{"/rollPublish/list", "分页活动推送列表"});

		needAccessLogPatternAfterList.add(new String[]{"/advertisement/save", "内嵌广告保存"});
		needAccessLogPatternAfterList.add(new String[]{"/advertisement/edit", "内嵌广告修改"});
		needAccessLogPatternAfterList.add(new String[]{"/advertisement/remove", "内嵌广告删除"});
		needAccessLogPatternAfterList.add(new String[]{"/advertisement/get", "获取一条内嵌广告"});
		needAccessLogPatternAfterList.add(new String[]{"/advertisement/list", "分页内嵌广告列表"});

		needAccessLogPatternAfterList.add(new String[]{"/advertMain/save", "内嵌广告推送保存"});
		needAccessLogPatternAfterList.add(new String[]{"/advertMain/remove", "内嵌广告推送删除"});
		needAccessLogPatternAfterList.add(new String[]{"/advertMain/list", "分页内嵌广告推送列表"});

		needAccessLogPatternAfterList.add(new String[]{"/client/save", "品牌保存"});
		needAccessLogPatternAfterList.add(new String[]{"/client/edit", "品牌修改"});
		needAccessLogPatternAfterList.add(new String[]{"/client/get", "获取一条品牌"});
		needAccessLogPatternAfterList.add(new String[]{"/client/list", "品牌列表"});

		needAccessLogPatternAfterList.add(new String[]{"/org/save", "店铺保存"});
		needAccessLogPatternAfterList.add(new String[]{"/org/edit", "店铺修改"});
		needAccessLogPatternAfterList.add(new String[]{"/org/get", "获取一条店铺"});
		needAccessLogPatternAfterList.add(new String[]{"/org/list", "店铺列表"});

		needAccessLogPatternAfterList.add(new String[]{"/video/save", "视频文件保存"});
		needAccessLogPatternAfterList.add(new String[]{"/video/edit", "视频文件修改"});
		needAccessLogPatternAfterList.add(new String[]{"/video/remove", "视频文件删除"});
		needAccessLogPatternAfterList.add(new String[]{"/video/get", "获取一条视频文件"});
		needAccessLogPatternAfterList.add(new String[]{"/video/list", "分页视频文件列表"});

		needAccessLogPatternAfterList.add(new String[]{"/videoBusiness/save", "视频商业属性保存"});
		needAccessLogPatternAfterList.add(new String[]{"/videoBusiness/edit", "视频商业属性修改"});
		needAccessLogPatternAfterList.add(new String[]{"/videoBusiness/remove", "视频商业属性删除"});
		needAccessLogPatternAfterList.add(new String[]{"/videoBusiness/get", "获取一条视频商业属性"});
		needAccessLogPatternAfterList.add(new String[]{"/videoBusiness/list", "分页视频商业属性列表"});

		needAccessLogPatternAfterList.add(new String[]{"/videoPublish/save", "视频推送保存"});
		needAccessLogPatternAfterList.add(new String[]{"/videoPublish/remove", "视频推送删除"});
		needAccessLogPatternAfterList.add(new String[]{"/videoPublish/list", "分页视频推送列表"});

		needAccessLogPatternAfterList.add(new String[]{"/tablePhone/saveOrEdit", "桌子电话新建修改"});
		needAccessLogPatternAfterList.add(new String[]{"/tablePhone/editPhone", "桌子电话修改"});
		needAccessLogPatternAfterList.add(new String[]{"/tablePhone/get", "获取一条桌子电话"});
		needAccessLogPatternAfterList.add(new String[]{"/tablePhone/list", "分页获取桌子电话列表"});
	}
    /**
     * 访问Controller之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String path = request.getServletPath();
		String uri = request.getRequestURI();
		request.setAttribute("user_operation_log_interceptor_startTime", System.currentTimeMillis());
		if (path.matches(Constants.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
			if(user != null && user.getOrgId() != null && user.getRoleType() != null){
				return true;
			}else{
				AjaxResult ajaxResult = new AjaxResult(9999, null, "未登录");
				writeRd("json", ajaxResult, response);
				return false;
			}

		}
	}


	/**访问Controller之后执行
	 * @param handler 是下一个拦截器*/
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		long startTime = TypeConverter.toLong(request.getAttribute("user_operation_log_interceptor_startTime"));
		long timeout = System.currentTimeMillis() - startTime;
		this.persistUserLog(request, response, request.getRequestURI(), timeout);
	}


	private int persistUserLog(HttpServletRequest request, HttpServletResponse response,
							   final String currentUrl, final long timeout) {
		if(MyStringUtils.isBlank(currentUrl)){
			return 0;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < needAccessLogPatternAfterList.size(); i++) {
					String[] actionProperties = needAccessLogPatternAfterList.get(i);
					if (currentUrl.indexOf(actionProperties[0]) > -1){
						accessLogManageService.addEntity(
								new AccessLog(
										actionProperties[0],
										actionProperties[1],
										timeout));
						break;
					}
				}
			}
		}).start();
		return 1;
	}

	/**输出指定类型的字符串到response对象，并要指定编码*/
	private void writeStr(String formatType, String responseStr, String charset, HttpServletResponse response) {
		PrintWriter pw = null;
		try{
			if(TypeConverter.isEmpty(formatType)){
				throw new RuntimeException("formatType is null!");
			}
			if(response == null){
				throw new RuntimeException("response is null when write string to response !");
			}
			response.setContentType("text/" + formatType + ";charset=" + charset);
			pw = response.getWriter();
			if (pw != null){
				pw.write(responseStr==null ? "" : responseStr);
			}
		}catch(IOException ie){
			if (pw != null){
				pw.close();
			}
		}
	}

	/**输出指定类型的字符串到response对象*/
	protected void writeStr(String formatType, String responseStr, HttpServletResponse response) {
		writeStr(formatType, responseStr, "UTF-8", response);
	}

	private void writeRd(String formatType, AjaxResult ajaxResult, HttpServletResponse response) {
		String rdStr = "";
		if(TypeConverter.isEmpty(formatType)){
			throw new RuntimeException("formatType is null!");
		}
		if(formatType.equalsIgnoreCase("json")){
			//默认json格式
			rdStr = JSON.toJSONString(ajaxResult);
		}else{
			//xml格式
		}
		writeStr(formatType, rdStr, response);
	}
}
