package spring.utility.board;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//@Pointcut("execution(public void get*(..))")	// public void인 모든 get메소드
//@Pointcut("execution(* com.javalec.ex.*.*())")	// com.javalec.ex 패키지에 파라미터가 없는 모든 메소드
//@Pointcut("execution(* com.javalec.ex..*.*())")	// com.javalec.ex 패키지 & com.javalec.ex 하위 패키지에 파라미터가 없는 모든 메소드
//@Pointcut("execution(* com.javalec.ex.Worker.*())")	// com.javalec.ex.Worker 안의 모든 메소드

//@Pointcut("within(com.javalec.ex.*)")	//com.javalec.ex 패키지 안에 있는 모든 메소드
//@Pointcut("within(com.javalec.ex..*)")  //com.javalec.ex 패키지 및 하위 패키지 안에 있는 모든 메소드
//@Pointcut("within(com.javalec.ex.Worker)") //com.javalec.ex.Worker 모든 메소드

//@Pointcut("bean(student)")	//student 빈에만 적용

@Aspect
public class LogAopAnnotationUse {
	
	@Pointcut("within(spring.utility.board.*)") //Aop적용되는  패키지들을 설정
	private void pointcutMethod() {
	}
	
	
	@Around("pointcutMethod()") //Aop 적용되는 메소드를 설정 Around는 주 메소드 앞과 뒤와 exception 발생했을때 실행
	public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable {
		String signatureStr = joinpoint.getSignature().toShortString();
		System.out.println( signatureStr + " is start. AnnotationUse ");
		long st = System.currentTimeMillis();
		
		try {
			Object obj = joinpoint.proceed();
			return obj;
		} finally {
			long et = System.currentTimeMillis();
			System.out.println( signatureStr + " is finished. AnnotationUse ");
			System.out.println( signatureStr + " 경과시간 : " + (et - st));
		}
		
	}
	
	@Before("within(spring.utility.board.*)") //포인트컷을 이용안하고 메소드 annotation에다가  "within(spring.utility.board.*)"바로 넣으면 됨,
	//Aop 적용되는 메소드를 설정 Before는 주 메소드 앞에서 실행
	public void beforAdvice() {
		System.out.println("beforAdvice() AnnotationUse");
	}
	
}