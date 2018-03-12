package spring.sts.board;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import spring.utility.board.MyBatisSupport;
import spring.utility.board.MyBatisTransactionManager;


@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mybatis.xml" })
public class MybatisSupportTest extends MyBatisSupport{
	
	public MybatisSupportTest() {
		// TODO Auto-generated constructor stub
	}
	@Test
	public void testProgramacTraction() throws SQLException {

		MyBatisTransactionManager transaction = getTransactionManager();
		if(transaction == null)System.out.println("transaction is null");
		System.out.println("aaaaa11111");

		try {
			
			transaction.start();
			System.out.println("aaaaa2222222");

			List  results = sqlSession.selectList("test.select");
			System.out.println("selected = " + results);
			
			System.out.println("results.get(0) : " + results.get(0));
			
			
			int cnt = 7;
			Map map = new HashMap(); 
		    //map.put("num",results.get(0));
			map.put("num",String.valueOf(10)); 
		    map.put("cnt",String.valueOf(8)); 
			int rcnt = sqlSession.insert("test.insert", map);
			System.out.println("inserted111 = " + rcnt);
			map.clear();
			
			System.out.println("inserted2222 start");
			map.put("num",String.valueOf(11)); 
		    map.put("cnt",String.valueOf(3)); 
		    rcnt = sqlSession.insert("test.insert", map);
			System.out.println("inserted2222 = " + rcnt);

			transaction.commit();

		} finally {
			transaction.end();
		}
	}

//	public static void main(String[] args) {
//	
////		MyBatisSupport myBatisSupport = new MyBatisSupport();
////		MyBatisTransactionManager transaction = myBatisSupport.getTransactionManager();
//		try {
//			
//			transaction.start();
//
////			List results = sqlSession.selectList("test.select");
////			System.out.println("selected = " + results);
////			int cnt = sqlSession.update("test.insert", results.get(0));
////			System.out.println("inserted = " + cnt);
//			System.out.println("transaction start");
//
//			transaction.commit();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			transaction.rollback();
//			e.printStackTrace();
//		}
//		
//	}
}

