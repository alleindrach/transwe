package com.transwe.web.controller.aop;


import com.transwe.entity.user.UserEntity;
import com.transwe.exception.CommonException;
import com.transwe.web.utils.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 检查用户登录态切面
 * 
 * @author llh
 * 
 */
@Aspect
@Component
//@Controller
public class AuthLoginAspect implements Ordered {

//	private final String ciSessionCookieKey = "ci_session_id";
//
//	private final String ciSessionCookieUser = "cookie_user_id";

//	@Reference(version = "1.0.0")
//	private RedisService redisService;
//	@Value("${session.SessionIdName}")
//	private String sessionIdName;
//	@Value("${session.UserIdName}")
//	private String userIdName;
	@Autowired
	private WebUtils webUtils;

	private static final Log LOG = LogFactory.getLog(AuthLoginAspect.class);
	private static final Log accessLog = LogFactory.getLog("ACCESS");

	/***
	 * 检查登录态切面
	 */
	@Pointcut("@annotation(com.transwe.aop.annotation.AuthLogin)")
	public void allAuthLoginMethod() {
	};


	/***
	 * 执行方法
	 * 
	 * @throws Throwable
	 */
	@Around("allAuthLoginMethod()")
	public Object authLogin(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return authLoginInternal(joinPoint);
		} catch (CommonException ce) {
			String uniLoginView;
//			if (ViewUtil.getCurrentRequestViewType().isRenderHTML()) {
//				HttpServletRequest request = CommonUtil.getHttpServletRequest();
//				HttpServletResponse response = CommonUtil
//						.getHttpServletResponse();
//				if (WeChatUtil.isInWeChat()) {
//					if (CommonUtil.trimString(request
//							.getParameter("already_authed")) == null) {
//						String backUrl = request.getRequestURI();
//						if (backUrl.contains("?"))
//							backUrl = backUrl + "&already_authed=1";
//						else
//							backUrl = backUrl + "?already_authed=1";
//						int end = backUrl.indexOf('/', 1);
//						if (end < 0)
//							end = 1;
//						ViewUtil.setView(new RedirectView(backUrl.substring(0,
//								end)
//								+ "/wechat/login.raw?targetUrl="
//								+ URLEncoder.encode(backUrl, "UTF-8")));
//						return null;
//					}
//				}
//				// 处理inviter和channel
//				String inviter = CommonUtil.trimString(request
//						.getParameter("inviter"));
//				if (inviter != null
//						&& CommonUtil
//								.trimString(CookieUtil.getValue("inviter")) == null) {
//					Cookie cookie = new Cookie("inviter", inviter);
//					cookie.setPath("/");
//					cookie.setSecure(false);
//					response.addCookie(cookie);
//				}
//				String channel = CommonUtil.trimString(request
//						.getParameter("channel"));
//				if (channel == null)
//					channel = CommonUtil.trimString(request.getParameter("c"));
//				if (channel != null
//						&& CommonUtil
//								.trimString(CookieUtil.getValue("channel")) == null) {
//					Cookie cookie = new Cookie("channel", channel);
//					cookie.setPath("/");
//					cookie.setSecure(false);
//					response.addCookie(cookie);
//				}
//
//				if ((uniLoginView = CommonUtil.trimString(appConfig
//						.get("uniLoginView"))) != null) {
//					// 跳登陆地址
//					ViewUtil.setViewName(uniLoginView);
//					return null;
//				}
//			}
			throw ce;
		}
	}

	private Object authLoginInternal(ProceedingJoinPoint joinPoint) throws Throwable {
//		String debugUserId = CommonUtil.trimString(appConfig
//				.get("debug.auth.userId"));
//		if (debugUserId != null) {
//			// debug模式
//			HttpSession sess = CommonUtil.getHttpServletRequest().getSession();
//			sess.setAttribute("user_id", debugUserId);
//			return joinPoint.proceed();
//		}
		UserEntity ue=webUtils.getUserIdentity();

//		long cookieUserId = Long.parseLong(userIdStr.toString());
//
//		// 打access日志
//		if (disableAccessLog == null || disableAccessLog.equals("1") == false) {
			StringBuilder sb = new StringBuilder();
			sb.append(System.currentTimeMillis() / 1000)
					.append('|')
					.append(ue.getId())
					.append('|')
					.append(joinPoint.getTarget().getClass().getSimpleName())
					.append('.')
					.append(joinPoint.getSignature().getName())
					.append('|')
					.append(webUtils.getRequest().getRemoteAddr());
			accessLog.info(sb.toString());
//		}
		return joinPoint.proceed();

	}

	public int getOrder() {
		return 1;
	}

}
