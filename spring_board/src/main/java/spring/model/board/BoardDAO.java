package spring.model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class BoardDAO {
	@Autowired
	JdbcTemplate template;
	@Autowired
	TransactionTemplate transactionTemplate;
	@Autowired
	TransactionTemplate transactionTemplate1;

	public BoardDAO() {
		// TODO Auto-generated constructor stub
		//this.template = Constant.template;
	}

	/**
	 * 글쓰기 처리
	 * 
	 * @param dto
	 * @return
	 */
	public boolean write(BoardDTO dto) throws Exception {
		boolean flag = false;

		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO board(num, name, passwd, subject, content,");
		sql.append(" regdate, ref, ip, filename, filesize )");
		sql.append(" VALUES(board_seq.nextval,?,?,?,?,sysdate,?,?,?,?) ");

		// 부모글번호 생성
		// 이것을 sql로 바꿔도 가능  board_seq.currval
		int ref = getRef();
		dto.setRef(ref);

		int cnt = template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				System.out.println("create preparestatement called");
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getPasswd());
				pstmt.setString(3, dto.getSubject());
				pstmt.setString(4, dto.getContent());
				pstmt.setInt(5, dto.getRef());
				pstmt.setString(6, dto.getIp());
				pstmt.setString(7, dto.getFilename());
				pstmt.setInt(8, dto.getFilesize());
				return pstmt;
			}
		});
		if (cnt > 0)
			flag = true;

		return flag;
	}

	/**
	 * 부모글번호 생성
	 * 
	 * @return int 부모글번호
	 */
	private int getRef() throws Exception {
		int ref = 0;
		StringBuffer sql = new StringBuffer();
		SqlRowSet srs= null;
		
		//sql.append(" SELECT NVL(MAX(ref),0) + 1 FROM board ");
		sql.append(" SELECT (NVL(MAX(ref),0) + 1) as referr FROM board ");
		
		srs = template.queryForRowSet(sql.toString());
		// ref = this.template.queryForInt(sql.toString());
	
		if (srs.next()) {ref = srs.getInt(1);}
		
		System.out.println("ref = " + ref);

		return ref;
	}

	/**
	 * 전체 레코드 갯수
	 * 
	 * @param searchColumn
	 * @param searchWord
	 * @return
	 */
	public int getTotal(Map<String, String> map) throws Exception {
		int total = 0;
		SqlRowSet srs = null;

		String col = (String) map.get("col");
		String word = (String) map.get("word");

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT COUNT(*) FROM board ");

		if (word.length() > 0 && col.equals("subject_content")) {
			sql.append(" WHERE (subject LIKE " + "'%" + word + "%' ");
			sql.append(" OR content LIKE " + "'%" + word + "%') ");
		} else if (word.length() > 0) {
			sql.append(" WHERE " + col + " LIKE " + "'%" + word + "%' ");
		}

		srs = template.queryForRowSet(sql.toString());
		if (srs.next()) {total = srs.getInt(1);}
		// total = this.template.queryForInt(sql.toString());

		System.out.println("total = " + total);

		return total;

	}

	/**
	 * 게시판 글목록
	 * 
	 * @param searchColumn
	 * @param searchWord
	 * @param beginPerPage
	 * @param numPerPage
	 * @return
	 */
	public List<BoardDTO> getList(Map<String, String> map) throws Exception {
		// List<BoardDTO> list = new ArraylistBoardDTO();

		String col = (String) map.get("col");
		String word = (String) map.get("word");
		int sno = Integer.parseInt((String) map.get("sno"));
		int eno = Integer.parseInt((String) map.get("eno"));
		
		System.out.println("sno:" + sno);
		System.out.print("eno:" + eno);
		System.out.println("col:" + col);

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT num, name, subject, to_char(regdate,'YYYY-MM-dd') as regdate, ");
		sql.append(" count, indent, filename, filesize, r  ");
		sql.append(" FROM(  ");
		sql.append("      SELECT num, name, subject, regdate , ");
		sql.append("    count,  indent, filename, filesize, rownum r ");
		sql.append("      FROM ( ");
		sql.append("            SELECT num, name, subject, regdate,  ");
		sql.append("            count,  indent, filename, filesize ");
		sql.append("            FROM board ");
		if (word.length() > 0 && col.equals("subject_content")) {
			sql.append("         WHERE (subject LIKE " + "'%" + word + "%' ");
			sql.append("         OR content LIKE " + "'%" + word + "%') ");
		} else if (word.length() > 0) {
			sql.append("         WHERE " + col + " LIKE " + "'%" + word + "%' ");
		}
		sql.append("            ORDER BY ref DESC, ansnum ASC ");
		sql.append("      ) ");
		sql.append(" ) ");
		sql.append(" WHERE r >= ? and r <= ? ");
		
		List<BoardDTO> list = template.query(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, sno);
				ps.setInt(2, eno);

			}
		}, new BeanPropertyRowMapper<BoardDTO>(BoardDTO.class));
		// }, new rowMapper<BoardDTO>(BoardDTO.class));
		
		return list;

	}

	/**
	 * 조회수 증가
	 * 
	 * @param num
	 */
	public void upCount(int num) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE board SET count = count +1 ");
		sql.append(" WHERE num = ? ");

		int cnt = template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, num);
				return pstmt;
			}
		});

		if (cnt > 0) {
			System.out.println("조회수 증가");
		} else {
			System.out.println("조회수 증가 실패");
		}

	}

	/**
	 * 게시판 글보기
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO read(int num) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from board ");
		sql.append(" WHERE num = " + num);

		return template.queryForObject(sql.toString(), new BeanPropertyRowMapper<BoardDTO>(BoardDTO.class));
	}

	/**
	 * 비밀번호 검사
	 * 
	 * @param num
	 * @param passwd
	 * @return
	 */
	public boolean passwdCheck(int num, String passwd) throws Exception {
		boolean flag = false;
		SqlRowSet srs = null;

		StringBuffer sql = new StringBuffer();
		// sql.append(" SELECT passwd FROM board ");
		// sql.append(" WHERE num = ? and passwd = ? ");
		sql.append(" SELECT passwd FROM board ");
		sql.append(" WHERE num = " + num + " and passwd = " + passwd);

		srs = template.queryForRowSet(sql.toString());
		if (srs.next()) {flag = true;}

		return flag;
	}

	public boolean update(BoardDTO dto) throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE board SET name=?, ");
		sql.append(" subject=?, content=? ");
		if (dto.getFilesize() > 0) {
			sql.append(" ,filename= ?, filesize=? ");
		}
		sql.append(" WHERE num = ? ");

		int cnt = template.update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				int i = 0;

				ps.setString(++i, dto.getName());
				ps.setString(++i, dto.getSubject());
				ps.setString(++i, dto.getContent());
				if (dto.getFilesize() > 0) {
					ps.setString(++i, dto.getFilename());
					ps.setInt(++i, dto.getFilesize());
				}
				ps.setInt(++i, dto.getNum());

			}
		});

		if (cnt > 0)
			flag = true;

		return flag;
	}

	/**
	 * ansnum 재정렬
	 * 
	 * @param ref
	 * @param ansnum
	 */
public void upAnsnum(int ref, int ansnum) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE board SET ansnum = ansnum +1 ");
		sql.append(" WHERE ref = ? and ansnum > ? ");
		// sql.append(" UPDATE board SET ansnum = ansnum +1 ");
		// sql.append(" WHERE ref = " + ref + " and ansnum > " + ansnum);
		
		
		transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				int cnt = template.update(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setInt(1, ref);
						ps.setInt(2, ansnum);

					}
				});

				if (cnt > 0) {
					System.out.println("답변이 있으므로 재정렬 ");
				} else {
					System.out.println("답변이 없으므로 재정렬 안됨 ");
				}

				
			}
		});

		
	}

	/**
	 * 답변등록
	 * 
	 * @param dto
	 * @return
	 */
public boolean insertReply(BoardDTO dto) throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO board(num, name, passwd, ");
		sql.append(" subject, content, regdate, ref, indent, ansnum, ");
		sql.append(" ip, filename, filesize, refnum ) ");
		sql.append(" VALUES(board_seq.nextval,?,?,?,?,sysdate,?, ");
		sql.append(" ?,?,?,?,?,?) ");
		
		Object result = transactionTemplate1.execute(new TransactionCallback<Object>() {
			
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				int cnt = template.update(sql.toString(), new PreparedStatementSetter() {
//				template.update(sql.toString(), new PreparedStatementSetter() {


					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, dto.getName());
						ps.setString(2, dto.getPasswd());
						ps.setString(3, dto.getSubject());
						ps.setString(4, dto.getContent());
						ps.setInt(5, dto.getRef()); // ★ 중요(부모과 같은 ref)
						ps.setInt(6, dto.getIndent() + 1);// ★ 중요(부모보다 1증가된 indent)
						ps.setInt(7, dto.getAnsnum() + 1);// ★ 중요(부모보다 1증가된 ansnum)
						ps.setString(8, dto.getIp());
						ps.setString(9, dto.getFilename());
						ps.setInt(10, dto.getFilesize());
						// 부모의 글번호를 refnum에 등록(부모글이 삭제되지 않게하기 위해서)
						ps.setInt(11, dto.getNum());
						
					}
				});
				
				System.out.println("cnt = " + cnt);
				return new Integer(cnt);
				
			}//doInTransaction
			
		});
		
		//정상실행되면 null이 return??
		int cnt = ((Integer) result).intValue();
		if (cnt > 0) flag = true;
		
		return flag;
	}


///**
// * ansnum 재정렬
// * 
// * @param ref
// * @param ansnum
// */
////public void upAnsnum(int ref, int ansnum) throws Exception {
//public void upAnsnum(int ref, int ansnum) {
//
//	StringBuffer sql = new StringBuffer();
//	sql.append(" UPDATE board SET ansnum = ansnum +1 ");
//	sql.append(" WHERE ref = ? and ansnum > ? ");
//	// sql.append(" UPDATE board SET ansnum = ansnum +1 ");
//	// sql.append(" WHERE ref = " + ref + " and ansnum > " + ansnum);
//
//	int cnt = template.update(sql.toString(), new PreparedStatementSetter() {
//
//		@Override
//		public void setValues(PreparedStatement ps) throws SQLException {
//			// TODO Auto-generated method stub
//			ps.setInt(1, ref);
//			ps.setInt(2, ansnum);
//
//		}
//	});
//
//	if (cnt > 0) {
//		System.out.println("답변이 있으므로 재정렬 ");
//	} else {
//		System.out.println("답변이 없으므로 재정렬 안됨 ");
//	}
//
//}
//
///**
// * 답변등록
// * 
// * @param dto
// * @return
// */
////public boolean insertReply(BoardDTO dto) throws Exception {
//public boolean insertReply(BoardDTO dto) {
//	boolean flag = false;
//	StringBuffer sql = new StringBuffer();
//	sql.append(" INSERT INTO board(num, name, passwd, ");
//	sql.append(" subject, content, regdate, ref, indent, ansnum, ");
//	sql.append(" ip, filename, filesize, refnum ) ");
//	sql.append(" VALUES(board_seq.nextval,?,?,?,?,sysdate,?, ");
//	sql.append(" ?,?,?,?,?,?) ");
//
//	int cnt = template.update(sql.toString(), new PreparedStatementSetter() {
//
//		@Override
//		public void setValues(PreparedStatement ps) throws SQLException {
//			// TODO Auto-generated method stub
//			ps.setString(1, dto.getName());
//			ps.setString(2, dto.getPasswd());
//			ps.setString(3, dto.getSubject());
//			ps.setString(4, dto.getContent());
//			ps.setInt(5, dto.getRef()); // ★ 중요(부모과 같은 ref)
//			ps.setInt(6, dto.getIndent() + 1);// ★ 중요(부모보다 1증가된 indent)
//			ps.setInt(7, dto.getAnsnum() + 1);// ★ 중요(부모보다 1증가된 ansnum)
//			ps.setString(8, dto.getIp());
//			ps.setString(9, dto.getFilename());
//			ps.setInt(10, dto.getFilesize());
//			// 부모의 글번호를 refnum에 등록(부모글이 삭제되지 않게하기 위해서)
//			ps.setInt(11, dto.getNum());
//
//		}
//	});
//	
//	if (cnt > 0)
//		flag = true;
//
//	return flag;
//}


	/**
	 * 부모글인지 확인
	 * 
	 * @param num
	 * @param con
	 * @return
	 */
	public boolean checkRefnum(int num) throws Exception {
		boolean flag = false;
		SqlRowSet srs = null;
		StringBuffer sql = new StringBuffer();
		int cnt = 0;
		
//		sql.append(" SELECT count(*)  FROM board  ");
//		sql.append(" WHERE refnum = ?  ");
		
		sql.append(" SELECT count(*)  FROM board  ");
		sql.append(" WHERE refnum = " + num);
		
		srs = template.queryForRowSet(sql.toString());
// 		total = this.template.queryForInt(sql.toString());
		if (srs.next()) {cnt = srs.getInt(1);}

		System.out.println("cnt = " + cnt);

		if (cnt > 0) {
			flag = true; // 부모글이다
		}
		
		return flag;
	}

	/**
	 * 게시판 글 삭제
	 * 
	 * @param num
	 * @return
	 */
	public boolean delete(int num) throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		
		sql.append(" delete from board  ");
		sql.append(" WHERE num = ?  ");
		
		int cnt = template.update(sql.toString(), new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, num);
				
			}
		});
		
		if (cnt > 0) {
			flag = true; // 삭제성공
		}
		
		return flag;
	}
}
