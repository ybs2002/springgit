package spring.utility.board;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("myBatisSupport")
public class MyBatisSupport {

	@Autowired(required = false)
	@Qualifier("sqlSession")
	public SqlSessionTemplate sqlSession;

	@Autowired
	ApplicationContext applicationContext;

	public MyBatisTransactionManager getTransactionManager() {
//		if(applicationContext == null) System.out.println("applicationContext is null");
//		MyBatisTransactionManager myBatisTransactionManager = applicationContext.getBean("myBatisTransactionManager", MyBatisTransactionManager.class);
//		if(myBatisTransactionManager == null) System.out.println("myBatisTransactionManager is null");
//		return myBatisTransactionManager;
		if(applicationContext == null) System.out.println("applicationContext is null");
		return applicationContext.getBean(MyBatisTransactionManager.class);
	}
	
//	public MyBatisTransactionManager getTransactionManager() {
//		
//		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//		ctx.load("mybatis.xml");
//		
//		if(ctx != null) System.out.println("ctx is not null");
//		
//		MyBatisTransactionManager myBatisTransactionManager = ctx.getBean("myBatisTransactionManager", MyBatisTransactionManager.class);
//		//MyBatisTransactionManager myBatisTransactionManager = ctx.getBean(MyBatisTransactionManager.class);
//		ctx.close();
//		
//		if(myBatisTransactionManager == null) System.out.println("myBatisTransactionManager is null");
//		return myBatisTransactionManager;
//	}

	
}
