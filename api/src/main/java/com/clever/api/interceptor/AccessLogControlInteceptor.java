package com.clever.api.interceptor;

import com.clever.common.domain.AccessLog;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.service.AccessLogManageService;
import com.clever.common.service.UploadAccessLogManageService;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * enva.liang@clever-m.com
 * 拦截类,补充请说明
 */
public class AccessLogControlInteceptor extends HandlerInterceptorAdapter{

	@Autowired
	UploadAccessLogManageService uploadAccessLogManageService;

	@Autowired
	AccessLogManageService accessLogManageService;

	private static List<String[]> needAccessLogPatternList = null;
	private static List<String[]> needAccessLogPatternAfterList = null;

    static {
		needAccessLogPatternList = new LinkedList<String[]>();
		needAccessLogPatternList.add(new String[]{"1000", "/v10/forward/driveCar", "代驾转跳"});
    }

	static {
		needAccessLogPatternAfterList = new LinkedList<String[]>();
		needAccessLogPatternAfterList.add(new String[]{"/v10/roll/main/list", "api活动列表"});
		needAccessLogPatternAfterList.add(new String[]{"/v10/video/list", "api待机视频广告列表"});
		needAccessLogPatternAfterList.add(new String[]{"/v10/uploadAccessLog/save", "api上传excel文件"});
		needAccessLogPatternAfterList.add(new String[]{"/v10/forward/driveCar", "api代驾跳转"});
		needAccessLogPatternAfterList.add(new String[]{"/v10/evaluation/save", "api保存服务评价"});
	}

    /**
     * 访问Controller之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		this.persistUserLog(request, response, request.getRequestURI(),
				request.getParameter("orgId"), request.getParameter("clientId"), request.getParameter("tableId"));
		request.setAttribute("user_operation_log_interceptor_startTime", System.currentTimeMillis());
		return true;
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

	private int persistUserLog(HttpServletRequest request, HttpServletResponse response,
							   final String currentUrl, final String orgId, final String clientId, final String tableId) {
		if(MyStringUtils.isBlank(currentUrl)){
			return 0;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < needAccessLogPatternList.size(); i++) {
					String[] actionProperties = needAccessLogPatternList.get(i);
					if (currentUrl.indexOf(actionProperties[1]) > -1){
						uploadAccessLogManageService.addEntity(
								new UploadAccessLog(
										orgId == null ? 100 : TypeConverter.toLong(orgId),
										clientId == null ? 100 : TypeConverter.toLong(clientId),
										tableId == null ? 100 : TypeConverter.toLong(tableId),
										TypeConverter.toLong(actionProperties[0]),
										1,
										new Date()));
						break;
					}
				}
			}
		}).start();
		return 1;
	}
}
