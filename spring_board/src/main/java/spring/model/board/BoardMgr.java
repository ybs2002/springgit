package spring.model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import spring.utility.board.MyBatisTransactionManager;

@Service
public class BoardMgr {

	// autowired �ϸ� �����ڸ� ȣ���Ͽ� BoardDAO���� ������� jdbctemplate�� ���پ˾Ҵµ� new BoardDAO�� �ؾ�
	// ������� jdbctemplate�� ������ �Ǿ BoardDAO���� @Autowired�� ������� jdbctemplate�� ��
	// setsetBoardDao���� �ϸ� �����
	// ��������� ��� Autowired�� �����ϴ� ��
	@Autowired
	private BoardDAO boardDao;
	@Autowired
	private TransactionTemplate transactionTemplate2;
	
	@Autowired
	private MyBatisTransactionManager myBatisTransactionManager;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	PlatformTransactionManager transactionManager;

	public BoardMgr() {
		super();
	}

	/**
	 * �۾���
	 * 
	 * @param dto
	 * @return
	 */
	public boolean write(BoardDTO dto) {
		boolean flag = false;
		// BoardDAO boardDao = new BoardDAO();

		try {
			
			//mybatis
			int cnt = sqlSession.insert("spring.model.board.IBoardDAO.write", dto);
			System.out.println("cnt : "+ cnt);
			if (cnt > 0) flag = true;
			
			//������
			//flag = boardDao.write(dto);

		} catch (Exception e) {
			System.out.print(e);
		}

		return flag;
	}

	/**
	 * ��ü ���ڵ� ����
	 * 
	 * @return
	 */
	public int getTotal(Map<String, String> map) {
		// BoardDAO boardDao = new BoardDAO();

		int total = 0;
		try {
			//mybatis
			total = sqlSession.selectOne("spring.model.board.IBoardDAO.getTotal", map);
			//������
			//total = boardDao.getTotal(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e);
		}
		return total;
	}

	/**
	 * �� ���
	 * 
	 * @param searchColumn
	 *            �˻��÷�
	 * @param searchWord
	 *            �˻���
	 * @param beginPerPage
	 *            ���۷��ڵ��ȣ
	 * @param numPerPage
	 *            ���������纸���� ���ڵ� ����(10)
	 * @return �������� ������ ���Ǹ�ϵ�
	 */
	public List<BoardDTO> getList(Map<String, String> map) {
		// BoardDAO boardDao = new BoardDAO();

		List<BoardDTO> v = null;

		try {
			//mybatis
			v = sqlSession.selectList("spring.model.board.IBoardDAO.getList", map);

			//������
			//v = boardDao.getList(map);

		} catch (Exception e) {
			System.out.print(e);
		}

		return v;
	}

	/**
	 * ��ȸ�� ����
	 * 
	 * @param num
	 */
	public void upCount(int num) {
		// BoardDAO boardDao = new BoardDAO();

		try {
			// mybatis sql update
			sqlSession.update("spring.model.board.IBoardDAO.upCount", num);

			// boardDao.upCount(num);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * Ư���Խ��� ���뺸��
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO read(int num) {
		// BoardDAO boardDao = new BoardDAO();

		BoardDTO dto = null;

		try {
			// mybatis sql select
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("num",String.valueOf(num));
			// mybatis sql
			dto = (BoardDTO) sqlSession.selectOne("spring.model.board.IBoardDAO.read", num);

			// ������ ��ȸ
			// dto = boardDao.read(num);

		} catch (Exception e) {
			System.out.print(e);
		}
		return dto;
	}

	/**
	 * �����ȣ Ȯ��
	 * 
	 * @param num
	 * @param passwd
	 * @return
	 */
	public boolean passwdCheck(int num, String passwd) {
		// BoardDAO boardDao = new BoardDAO();
		boolean flag = false;
		BoardDTO dto = null;

		try {
			// mybatis sql
			Map<String, String> map = new HashMap<String, String>();
			map.put("num", String.valueOf(num));
			map.put("passwd", passwd);
			dto = (BoardDTO) sqlSession.selectOne("spring.model.board.IBoardDAO.passwdCheck", map);
			System.out.println("dto.getPasswd() : " + dto.getPasswd());
			if (dto != null)
				flag = true;
			// ��������
			// flag = boardDao.passwdCheck(num,passwd);
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * �Խ��� �ۼ���
	 * 
	 * @param dto
	 * @return
	 */
	public boolean update(BoardDTO dto) {
		// BoardDAO boardDao = new BoardDAO();
		boolean flag = false;

		try {
			// mybatis sql update
			int cnt = sqlSession.update("spring.model.board.IBoardDAO.update", dto);
			if (cnt > 0)
				flag = true;

			// flag = boardDao.update(dto);
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * mybatis �亯ó�� Ʈ��������� ó���� 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)�� 1���� ������(UPDATE) 2.���ο� �亯����
	 * �μ�Ʈ ��(INSERT)
	 * 
	 * @param dto
	 * @return
	 */
	public boolean reply(BoardDTO dto) {
		// BoardDAO boardDao = new BoardDAO();
		boolean flag = false;
		
//plantransaction���� ó��		
//		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(definition);
//		
//		try {
//		
//			//mybatis ����
//			dto.setRef(dto.getRef()); // �� �߿�(�θ�� ���� ref)
//			dto.setIndent(dto.getIndent() + 1);// �� �߿�(�θ𺸴� 1������ indent)
//			dto.setAnsnum(dto.getAnsnum() + 1);// �� �߿�(�θ𺸴� 1������ ansnum)
//			
//		
//			sqlSession.update("spring.model.board.IBoardDAO.upAnsnum", dto);
//			int cnt = sqlSession.insert("spring.model.board.IBoardDAO.insertReply", dto);
//			System.out.println("cnt : "+ cnt);
//			if (cnt > 0) flag = true;
//			
//			transactionManager.commit(status);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			transactionManager.rollback(status);
//		}
		

		// TODO Auto-generated method stub
//		MyBatisSupport myBatisSupport = new MyBatisSupport();
//		MyBatisTransactionManager transaction = myBatisSupport.getTransactionManager();
	
		myBatisTransactionManager.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		if(myBatisTransactionManager == null) {System.out.println("myBatisTransactionManager is null");}
		else System.out.println("not null");
		
		try {
//			// ansnum�� ������ ������
//			boardDao.upAnsnum(dto.getRef(), dto.getAnsnum());
			
//			// �亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum
//			// �θ�ۺ��� 1������ ������ ���)
//			flag = boardDao.insertReply(dto);
			
			myBatisTransactionManager.start();
			
			//mybatis ����
			sqlSession.update("spring.model.board.IBoardDAO.upAnsnum", dto);
		
			dto.setRef(dto.getRef()); // �� �߿�(�θ�� ���� ref)
			dto.setIndent(dto.getIndent() + 1);// �� �߿�(�θ𺸴� 1������ indent)
			dto.setAnsnum(dto.getAnsnum() + 1);// �� �߿�(�θ𺸴� 1������ ansnum)
			int cnt = sqlSession.insert("spring.model.board.IBoardDAO.insertReply", dto);
			System.out.println("cnt : "+ cnt);
			if (cnt > 0) flag = true;
			
			myBatisTransactionManager.commit();

			System.out.println("flag = " + flag);
		} catch (Exception e) {
			// TODO: handle exception
			//myBatisTransactionManager.rollback();
			e.printStackTrace();
		} finally {
			myBatisTransactionManager.rollback();
		}
		
		return flag;
	}

	// ������ Ʈ���������� ó����
	/// **
	// * �亯ó�� Ʈ��������� ó����
	// * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��
	// * 1���� ������(UPDATE)
	// * 2.���ο� �亯���� �μ�Ʈ ��(INSERT)
	// * @param dto
	// * @return
	// */
	// public boolean reply(BoardDTO dto){
	// // BoardDAO boardDao = new BoardDAO();
	// boolean flag = false;
	//
	// Object result = transactionTemplate2.execute(new
	// TransactionCallback<Object>() {
	// @Override
	// public Object doInTransaction(TransactionStatus status) {
	// // TODO Auto-generated method stub
	// boolean inflag = false;
	// // TODO Auto-generated method stub
	// try {
	// //ansnum�� ������ ������
	// boardDao.upAnsnum(dto.getRef(),dto.getAnsnum());
	// //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum
	// //�θ�ۺ��� 1������ ������ ���)
	// inflag = boardDao.insertReply(dto);
	//
	// System.out.println("inflag = " + inflag);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// return new Boolean(inflag);
	// }
	// });
	//
	// flag = ((Boolean)result).booleanValue();
	// if(flag) System.out.println("����ó��");
	// else System.out.println("������ó��");
	//
	// return flag;
	// }

	// Object result = transactionTemplate2.execute(new TransactionCallback() {
	//
	// @Override
	// public Object doInTransaction(TransactionStatus arg0) {
	// boolean inflag = false;
	// // TODO Auto-generated method stub
	// try {
	// //ansnum�� ������ ������
	// boardDao.upAnsnum(dto.getRef(),dto.getAnsnum());
	// //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum
	// //�θ�ۺ��� 1������ ������ ���)
	// inflag = boardDao.insertReply(dto);
	//
	// System.out.println("inflag = " + inflag);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// }
	// });

	/// **
	// * �亯ó��
	// * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��
	// * 1���� ������(UPDATE)
	// * 2.���ο� �亯���� �μ�Ʈ ��(INSERT)
	// * @param dto
	// * @return
	// */
	// public boolean reply(BoardDTO dto){
	// // BoardDAO boardDao = new BoardDAO();
	// boolean flag = false;
	//
	// try{
	// //ansnum�� ������ ������
	// boardDao.upAnsnum(dto.getRef(),dto.getAnsnum());
	// //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum
	// //�θ�ۺ��� 1������ ������ ���)
	// flag = boardDao.insertReply(dto);
	// }catch(Exception e){
	// System.out.print(e);
	//
	// }
	//
	// return flag;
	// }
	//

	/// transaction ó�����϶�
	/// **
	// * �亯ó��
	// * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��
	// * 1���� ������(UPDATE)
	// * 2.���ο� �亯���� �μ�Ʈ ��(INSERT)
	// * @param dto
	// * @return
	// */
	// public boolean reply(BoardDTO dto){
	// BoardDAO boardDao = new BoardDAO();
	// boolean flag = false;
	// Connection con = null;
	// try{
	// con = DBOpen.getConnection();
	// con.setAutoCommit(false);
	// //ansnum�� ������ ������
	// boardDao.upAnsnum(dto.getRef(),dto.getAnsnum(), con);
	// //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum
	// //�θ�ۺ��� 1������ ������ ���)
	// flag = boardDao.insertReply(dto, con);
	// //������ DB�� �����϶�...
	// con.commit();
	// }catch(Exception e){
	// System.out.print(e);
	// try{
	// //���ݱ����� ������ ���ó���϶�..
	// con.rollback();
	// }catch(Exception ex){}
	// }finally{
	// try{
	// con.setAutoCommit(true);
	// }catch(Exception ex){}
	// DBClose.close(con);
	// }
	// return flag;
	// }

	/**
	 * �θ������ Ȯ��
	 * 
	 * @param num
	 * @return
	 */
	public boolean checkRefnum(int num) {
		// BoardDAO boardDao = new BoardDAO();
		boolean flag = false;

		try {
			//mybatis
			int cnt =sqlSession.selectOne("spring.model.board.IBoardDAO.checkRefnum", num);
			System.out.println("cnt : "+ cnt);
			if (cnt > 0) flag = true;

			//������
			//flag = boardDao.checkRefnum(num);
		} catch (Exception e) {
			System.out.print(e);
		}

		return flag;
	}

	/**
	 * �Խ��� �� ����
	 * 
	 * @param num
	 * @return
	 */
	public boolean delete(int num) {
		// BoardDAO boardDao = new BoardDAO();
		boolean flag = false;

		try {
			System.out.println("BoardMgr.called  num :" + num);
			//mybatis
			int cnt = sqlSession.delete("spring.model.board.IBoardDAO.delete", num);
			System.out.println("cnt : "+ cnt);
			if (cnt > 0) flag = true;
			
			// ������
			//flag = boardDao.delete(num);

		} catch (Exception e) {
			System.out.print(e);

		}

		return flag;
	}

}
