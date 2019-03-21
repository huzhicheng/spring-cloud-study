package kite.springcloud.eureka.single.customer.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * SimplePerformanceMonitor
 *
 * @author fengzheng
 * @date 2019/3/14
 */
@Aspect
@Component
@Slf4j
public class SimplePerformanceMonitor {

    @Pointcut("within(kite.springcloud.eureka.single.customer..*)")
    public void logTime(){

    }

    @Around("logTime()")
    public Object timeAround(ProceedingJoinPoint joinPoint) {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("统计某方法执行耗时环绕通知出错", e);
        }

        // 获取执行的方法名
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        // 打印耗时的信息
        log.info(String.format("「%s」执行时间为：%d ms",methodName, endTime - startTime));
        return obj;
    }
}
