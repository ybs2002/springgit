package spring.model.board; 
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate; 
 
@Service 
public class BoardMgr_OLD { 
	
//autowired �ϸ� �����ڸ� ȣ���Ͽ� BoardDAO���� ������� jdbctemplate�� ���پ˾Ҵµ�  new BoardDAO�� �ؾ� 
//������� jdbctemplate�� ������ �Ǿ BoardDAO���� @Autowired�� ������� jdbctemplate�� ��
//setsetBoardDao���� �ϸ� �����
// ��������� ��� Autowired�� �����ϴ� ��	
@Autowired
private BoardDAO boardDao; 
@Autowired
private TransactionTemplate transactionTemplate2;

public BoardMgr_OLD() { 
    super(); 
} 
 
/** 
 * �۾��� 
 * @param dto 
 * @return 
 */ 
public boolean write(BoardDTO dto){ 
    boolean flag = false; 
    // BoardDAO boardDao = new BoardDAO();

    try{ 
     flag = boardDao.write(dto); 
 
    }catch(Exception e){ 
    	System.out.print(e);
    }
    	
    return flag; 
} 
 
/** 
 * ��ü ���ڵ� ���� 
 * @return 
 */ 
public int getTotal(Map<String, String> map){ 
    // BoardDAO boardDao = new BoardDAO();

    int total=0; 
    try {
		total = boardDao.getTotal(map);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.print(e);
	} 
     return total; 
} 
/** 
 * �� ��� 
 * @param searchColumn �˻��÷� 
 * @param searchWord   �˻��� 
 * @param beginPerPage ���۷��ڵ��ȣ 
 * @param numPerPage   ���������纸���� ���ڵ� ����(10) 
 * @return �������� ������ ���Ǹ�ϵ� 
 */ 
public List<BoardDTO> getList(Map<String, String> map){ 
    // BoardDAO boardDao = new BoardDAO();

    List<BoardDTO> v = null; 
 
    try{ 
 
        v = boardDao.getList(map); 
 
    }catch(Exception e){ 
        System.out.print(e); 
    }
    
    return v; 
} 
 
 
/** 
 * ��ȸ�� ���� 
 * @param num 
 */ 
public void upCount(int num){ 
    // BoardDAO boardDao = new BoardDAO();

    try{ 
        boardDao.upCount(num); 
    }catch(Exception e){ 
        System.out.print(e); 
    }
} 
/** 
 * Ư���Խ��� ���뺸�� 
 * @param num 
 * @return 
 */ 
public BoardDTO read(int num){
    // BoardDAO boardDao = new BoardDAO();

    BoardDTO dto = null; 
    try{ 
        dto = boardDao.read(num); 
 
    }catch(Exception e){ 
        System.out.print(e); 
    }
    return dto; 
} 
 
/** 
 * �����ȣ Ȯ�� 
 * @param num 
 * @param passwd 
 * @return 
 */ 
public boolean passwdCheck(int num, String passwd){ 
    // BoardDAO boardDao = new BoardDAO();
    boolean flag = false; 
    
    try{ 
        flag = boardDao.passwdCheck(num,passwd); 
    }catch(Exception e){ 
        System.out.print(e); 
    }
    return flag; 
} 
 
/** 
 * �Խ��� �ۼ��� 
 * @param dto 
 * @return 
 */ 
public boolean update(BoardDTO dto){ 
    // BoardDAO boardDao = new BoardDAO();
    boolean flag = false; 
    
    try{ 
        flag = boardDao.update(dto); 
    }catch(Exception e){ 
        System.out.print(e); 
    }
    return flag; 
} 


/** 
 * �亯ó��  Ʈ��������� ó����
 * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��  
 *   1���� ������(UPDATE) 
 * 2.���ο� �亯���� �μ�Ʈ ��(INSERT) 
 * @param dto 
 * @return 
 */ 
public boolean reply(BoardDTO dto){
    // BoardDAO boardDao = new BoardDAO();
	boolean flag = false; 
	
	Object result = transactionTemplate2.execute(new TransactionCallback<Object>() {
		@Override
		public Object doInTransaction(TransactionStatus status) {
			// TODO Auto-generated method stub
			boolean inflag = false;
			// TODO Auto-generated method stub
			try {
		        //ansnum�� ������ ������ 
		        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
		        //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum 
		        //�θ�ۺ��� 1������ ������ ���) 
		        inflag =  boardDao.insertReply(dto);
		        
		        System.out.println("inflag = " + inflag);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return new Boolean(inflag);
		}
	});
    
	flag =  ((Boolean)result).booleanValue();
	if(flag) System.out.println("����ó��");
	else System.out.println("������ó��");
	
	return flag; 
} 
//    Object result = transactionTemplate2.execute(new TransactionCallback() {
//		
//		@Override
//		public Object doInTransaction(TransactionStatus arg0) {
//			boolean inflag = false;
//			// TODO Auto-generated method stub
//			try {
//		        //ansnum�� ������ ������ 
//		        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
//		        //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum 
//		        //�θ�ۺ��� 1������ ������ ���) 
//		        inflag =  boardDao.insertReply(dto);
//		        
//		        System.out.println("inflag = " + inflag);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//	
//		}
//	});
    

///** 
// * �亯ó��  
// * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��  
// *   1���� ������(UPDATE) 
// * 2.���ο� �亯���� �μ�Ʈ ��(INSERT) 
// * @param dto 
// * @return 
// */ 
//public boolean reply(BoardDTO dto){
//    // BoardDAO boardDao = new BoardDAO();
//    boolean flag = false; 
//    
//    try{ 
//        //ansnum�� ������ ������ 
//        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
//        //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum 
//        //�θ�ۺ��� 1������ ������ ���) 
//        flag = boardDao.insertReply(dto); 
//    }catch(Exception e){ 
//        System.out.print(e); 
//    
//    }
//    
//    return flag; 
// } 
//


/// transaction ó�����϶�
///** 
// * �亯ó��  
// * 1.�θ��� �Ǵٸ� �亯�� ����(ansnum)��  
// *   1���� ������(UPDATE) 
// * 2.���ο� �亯���� �μ�Ʈ ��(INSERT) 
// * @param dto 
// * @return 
// */ 
//public boolean reply(BoardDTO dto){ 
//     BoardDAO boardDao = new BoardDAO();
//    boolean flag = false; 
//    Connection con = null;
//    try{ 
//     con = DBOpen.getConnection(); 
//        con.setAutoCommit(false); 
//        //ansnum�� ������ ������ 
//        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum(), con); 
//        //�亯���� ���(ref�ºθ���� ref�� ���ƾ��ϰ�,indent, ansnum 
//        //�θ�ۺ��� 1������ ������ ���) 
//        flag = boardDao.insertReply(dto, con); 
//        //������ DB�� �����϶�... 
//        con.commit(); 
//        }catch(Exception e){ 
//        System.out.print(e); 
//    try{ 
//        //���ݱ����� ������ ���ó���϶�.. 
//        con.rollback(); 
//    }catch(Exception ex){} 
//    }finally{ 
//    try{ 
//       con.setAutoCommit(true); 
//    }catch(Exception ex){} 
//        DBClose.close(con); 
//    } 
//    return flag; 
//    } 
 
 
    /** 
     * �θ������ Ȯ�� 
     * @param num 
     * @return 
     */ 
public boolean checkRefnum(int num){ 
    // BoardDAO boardDao = new BoardDAO();
    boolean flag = false; 
    
    try{ 
        flag = boardDao.checkRefnum(num); 
    }catch(Exception e){ 
        System.out.print(e); 
    }
    
    return flag; 
} 
 
/** 
 * �Խ��� �� ���� 
 * @param num 
 * @return 
 */ 
public boolean delete(int num){ 
    // BoardDAO boardDao = new BoardDAO();
    boolean flag = false; 
    
    try{ 
        flag = boardDao.delete(num); 
 
    }catch(Exception e){ 
        System.out.print(e); 
         
    }
    
    return flag; 
} 
 
 
} 
