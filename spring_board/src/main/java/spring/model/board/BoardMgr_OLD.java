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
	
//autowired 하면 생성자를 호출하여 BoardDAO에서 멤버변수 jdbctemplate를 쓸줄알았는데  new BoardDAO로 해야 
//멤버변수 jdbctemplate가 연결이 되어서 BoardDAO에서 @Autowired를 멤버변수 jdbctemplate에 씀
//setsetBoardDao으로 하면 연결됨
// 마지막방법 기냥 Autowired로 연결하니 됨	
@Autowired
private BoardDAO boardDao; 
@Autowired
private TransactionTemplate transactionTemplate2;

public BoardMgr_OLD() { 
    super(); 
} 
 
/** 
 * 글쓰기 
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
 * 전체 레코드 갯수 
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
 * 글 목록 
 * @param searchColumn 검색컬럼 
 * @param searchWord   검색어 
 * @param beginPerPage 시작레코드번호 
 * @param numPerPage   한페이지당보여줄 레코드 갯수(10) 
 * @return 페이지에 보여줄 글의목록들 
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
 * 조회수 증가 
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
 * 특정게시판 내용보기 
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
 * 비빌번호 확인 
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
 * 게시판 글수정 
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
 * 답변처리  트랜잭션으로 처리함
 * 1.부모의 또다른 답변의 순서(ansnum)를  
 *   1증가 시켜줌(UPDATE) 
 * 2.새로운 답변글을 인서트 함(INSERT) 
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
		        //ansnum의 순서를 재정렬 
		        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
		        //답변글을 등록(ref는부모글의 ref와 같아야하고,indent, ansnum 
		        //부모글보다 1증가한 값으로 등록) 
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
	if(flag) System.out.println("정상처리");
	else System.out.println("비정상처리");
	
	return flag; 
} 
//    Object result = transactionTemplate2.execute(new TransactionCallback() {
//		
//		@Override
//		public Object doInTransaction(TransactionStatus arg0) {
//			boolean inflag = false;
//			// TODO Auto-generated method stub
//			try {
//		        //ansnum의 순서를 재정렬 
//		        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
//		        //답변글을 등록(ref는부모글의 ref와 같아야하고,indent, ansnum 
//		        //부모글보다 1증가한 값으로 등록) 
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
// * 답변처리  
// * 1.부모의 또다른 답변의 순서(ansnum)를  
// *   1증가 시켜줌(UPDATE) 
// * 2.새로운 답변글을 인서트 함(INSERT) 
// * @param dto 
// * @return 
// */ 
//public boolean reply(BoardDTO dto){
//    // BoardDAO boardDao = new BoardDAO();
//    boolean flag = false; 
//    
//    try{ 
//        //ansnum의 순서를 재정렬 
//        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum()); 
//        //답변글을 등록(ref는부모글의 ref와 같아야하고,indent, ansnum 
//        //부모글보다 1증가한 값으로 등록) 
//        flag = boardDao.insertReply(dto); 
//    }catch(Exception e){ 
//        System.out.print(e); 
//    
//    }
//    
//    return flag; 
// } 
//


/// transaction 처리해하라
///** 
// * 답변처리  
// * 1.부모의 또다른 답변의 순서(ansnum)를  
// *   1증가 시켜줌(UPDATE) 
// * 2.새로운 답변글을 인서트 함(INSERT) 
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
//        //ansnum의 순서를 재정렬 
//        boardDao.upAnsnum(dto.getRef(),dto.getAnsnum(), con); 
//        //답변글을 등록(ref는부모글의 ref와 같아야하고,indent, ansnum 
//        //부모글보다 1증가한 값으로 등록) 
//        flag = boardDao.insertReply(dto, con); 
//        //실제로 DB에 적용하라... 
//        con.commit(); 
//        }catch(Exception e){ 
//        System.out.print(e); 
//    try{ 
//        //지금까지의 문장을 취소처리하라.. 
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
     * 부모글인지 확인 
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
 * 게시판 글 삭제 
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
