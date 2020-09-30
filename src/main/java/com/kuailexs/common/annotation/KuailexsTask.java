package com.kuailexs.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *   任务工具，增加任务到数据库，ID 重复报错
 *   数据库中会比这里多些信息。控制是否执行
 *   不想被管控的需使用其它工具
 *   参数仅为默认值，以数据库为准
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KuailexsTask {

	String id() default "";//

	//1、fixedDelay控制方法执行的间隔时间，是以上一次方法执行完开始算起，
	// 如上一次方法执行阻塞住了，那么直到上一次执行完，并间隔给定的时间后，执行下一次。
	//
	//2、fixedRate是按照一定的速率执行，是从上一次方法执行开始的时间算起，
	// 如果上一次方法阻塞住了，下一次也是不会执行，但是在阻塞这段时间内累计应该执行的次数，
	// 当不再阻塞时，一下子把这些全部执行掉，而后再按照固定速率继续执行。
	//
	//4、initialDelay 。如： @Scheduled(initialDelay = 10000,fixedRate = 15000
	//这个定时器就是在上一个的基础上加了一个initialDelay = 10000
	// 意思就是在容器启动后,延迟10秒后再执行一次定时器,以后每15秒再执行一次该定时器。

	long fixedDelay() default -1L;
	String onTime() default "";

	//long fixedRate() default -1L;
	//long initialDelay() default -1L;
}