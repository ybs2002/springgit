package spring.utility.board;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.utility.board.MyBatisSupport;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/mybatis.xml" })
class MybatisSupportTest extends MyBatisSupport{
	
	@Test
	public void testProgramacTraction() throws SQLException {

		MyBatisTransactionManager transaction = getTransactionManager();
		if(transaction == null)System.out.println("transaction is null");
		try {
			
			transaction.start();

//			List results = sqlSession.selectList("test.select");
//			System.out.println("selected = " + results);
//			int cnt = sqlSession.update("test.insert", results.get(0));
//			System.out.println("inserted = " + cnt);
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

