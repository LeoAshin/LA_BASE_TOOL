package la.web.starter;

import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 业务日志
 *
 * @author LeoAshin
 * @date 2023/1/30 15:21
 */
@Aspect
@Configuration
@ConditionalOnProperty(name = "la.web.log-on", havingValue = "true")
public class LogInterceptorConfig {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void definition() {

    }

    @Before(value = "definition()")
    public void before(JoinPoint joinPoint) {
        var clazz = joinPoint.getTarget().getClass();
        var logger = LoggerFactory.getLogger(clazz);

        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求入参
        var args = joinPoint.getArgs();
        var builder = new StringBuilder();
        var list = new ArrayList<>();
        Arrays.asList(args).forEach(arg -> {
            if (arg instanceof String) {
                list.add(arg);
            } else {
                if (!(arg instanceof ServletRequest || arg instanceof MultipartFile || arg instanceof ServletResponse)) {
                    list.add(JSON.toJSONString(arg));
                }
            }
            builder.append("{}");
        });
        var info = "Request Args   : " + builder;
        logger.info(info, list);
    }

    @Around(value = "definition()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        var clazz = proceedingJoinPoint.getTarget().getClass();
        var logger = LoggerFactory.getLogger(clazz);
        var result = proceedingJoinPoint.proceed();
        logger.info("Time-Consuming : {} ms \n", System.currentTimeMillis() - startTime);
        return result;
    }
}
