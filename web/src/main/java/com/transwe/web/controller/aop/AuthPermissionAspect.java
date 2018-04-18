package com.transwe.web.controller.aop;


import com.transwe.aop.annotation.AuthPermission;
import com.transwe.entity.user.UserEntity;
import com.transwe.exception.CommonException;
import com.transwe.exception.ExceptionEnum;
import com.transwe.web.utils.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
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
public class AuthPermissionAspect implements Ordered {

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

	private static final Log LOG = LogFactory.getLog(AuthPermissionAspect.class);
	private static final Log accessLog = LogFactory.getLog("ACCESS");

	/***
	 * 授权切面
	 */
	@Pointcut("@annotation(com.transwe.aop.annotation.AuthPermission)")
	public void allAuthPermissionMethod() {
	};
	@Around("allAuthPermissionMethod()")
	public Object authPermission(ProceedingJoinPoint joinPoint) throws Throwable {
		UserEntity ue=webUtils.getUserIdentity();
		if(ue==null)
		{
			throw new CommonException(ExceptionEnum.NOT_AUHTORIZED);
		}
		AuthPermission anno=AnnotationUtils.getAnnotation(
				((MethodSignature)((MethodInvocationProceedingJoinPoint) joinPoint).getSignature()).getMethod()
				, AuthPermission.class);
		if(anno!=null)
		{
			try {
				for (String permRequired : anno.values()
						) {
					if (ue.getRoleEntity().hasPermission(permRequired))
						return joinPoint.proceed();
				}
			}
			catch(Exception ex)
			{
			}
			throw new CommonException(ExceptionEnum.NOT_AUHTORIZED);
		}else
			return joinPoint.proceed();
	}

	public int getOrder() {
		return 1;
	}

}
