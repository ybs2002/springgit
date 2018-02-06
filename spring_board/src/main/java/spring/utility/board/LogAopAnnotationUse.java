package spring.utility.board;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//@Pointcut("execution(public void get*(..))")	// public void�� ��� get�޼ҵ�
//@Pointcut("execution(* com.javalec.ex.*.*())")	// com.javalec.ex ��Ű���� �Ķ���Ͱ� ���� ��� �޼ҵ�
//@Pointcut("execution(* com.javalec.ex..*.*())")	// com.javalec.ex ��Ű�� & com.javalec.ex ���� ��Ű���� �Ķ���Ͱ� ���� ��� �޼ҵ�
//@Pointcut("execution(* com.javalec.ex.Worker.*())")	// com.javalec.ex.Worker ���� ��� �޼ҵ�

//@Pointcut("within(com.javalec.ex.*)")	//com.javalec.ex ��Ű�� �ȿ� �ִ� ��� �޼ҵ�
//@Pointcut("within(com.javalec.ex..*)")  //com.javalec.ex ��Ű�� �� ���� ��Ű�� �ȿ� �ִ� ��� �޼ҵ�
//@Pointcut("within(com.javalec.ex.Worker)") //com.javalec.ex.Worker ��� �޼ҵ�

//@Pointcut("bean(student)")	//student �󿡸� ����

@Aspect
public class LogAopAnnotationUse {
	
	@Pointcut("within(spring.utility.board.*)") //Aop����Ǵ�  ��Ű������ ����
	private void pointcutMethod() {
	}
	
	
	@Around("pointcutMethod()") //Aop ����Ǵ� �޼ҵ带 ���� Around�� �� �޼ҵ� �հ� �ڿ� exception �߻������� ����
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
			System.out.println( signatureStr + " ����ð� : " + (et - st));
		}
		
	}
	
	@Before("within(spring.utility.board.*)") //����Ʈ���� �̿���ϰ� �޼ҵ� annotation���ٰ�  "within(spring.utility.board.*)"�ٷ� ������ ��,
	//Aop ����Ǵ� �޼ҵ带 ���� Before�� �� �޼ҵ� �տ��� ����
	public void beforAdvice() {
		System.out.println("beforAdvice() AnnotationUse");
	}
	
}