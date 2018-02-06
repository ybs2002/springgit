package spring.utility.board;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.utility.board.Student;
import spring.utility.board.Worker;

public class MainLogClass {
	public static void main(String[] args) {
		
		//�̼����� Student�� Worker ���� ������ �б����� �ڵ�
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
		Student student = ctx.getBean("student", Student.class);
		student.getStudentInfo();
		
		Worker worker = ctx.getBean("worker", Worker.class);
		worker.getWorkerInfo();
		
		ctx.close();
		
	}

}
