package spring.utility.board;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/mybatis.xml" })
public class MybatisSupportTest extends MyBatisSupport{
	
	@Test
	public void testProgramacTraction() throws SQLException {

		MyBatisTransactionManager transaction = getTransactionManager();
		if(transaction == null)System.out.println("transaction is null");
		System.out.println("aaaaa11111");

		try {
			
			transaction.start();

			List results = sqlSession.selectList("test.select");
			System.out.println("selected = " + results);
			
			int cnt = 7;
			Map map = new HashMap(); 
		    map.put("num",results.get(0)); 
		    map.put("cnt",cnt); 
		    
			int rcnt = sqlSession.update("test.insert", map);
			System.out.println("inserted = " + rcnt);
			System.out.println("aaaaa");

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

