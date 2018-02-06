package spring.utility.board;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.utility.board.Student;
import spring.utility.board.Worker;

public class MainLogClass {
	public static void main(String[] args) {
		
		//이설정은 Student와 Worker 빈의 정보를 읽기위한 코딩
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
		Student student = ctx.getBean("student", Student.class);
		student.getStudentInfo();
		
		Worker worker = ctx.getBean("worker", Worker.class);
		worker.getWorkerInfo();
		
		ctx.close();
		
	}

}
