package spring.model.board; 
 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map; 
 
import org.springframework.stereotype.Component; 
 
import spring.utility.board.DBClose;
 
@Component 
public class BoardDAO_OLD { 
 
/** 
 * �۾��� ó�� 
 * @param dto 
 * @return 
 */ 
public boolean write(BoardDTO dto,Connection con)throws Exception { 
    boolean flag = false; 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" INSERT INTO board(num, name, passwd, subject, content,"); 
    sql.append(" regdate, ref, ip, filename, filesize )"); 
    sql.append(" VALUES(board_seq.Nextval,?,?,?,?,sysdate,?,?,?,?) "); 
    //�θ�۹�ȣ ���� 
    int ref=getRef(con); 
    dto.setRef(ref); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setString(1, dto.getName()); 
        pstmt.setString(2, dto.getPasswd()); 
        pstmt.setString(3, dto.getSubject()); 
        pstmt.setString(4, dto.getContent()); 
        pstmt.setInt(5, dto.getRef()); 
        pstmt.setString(6, dto.getIp()); 
        pstmt.setString(7, dto.getFilename()); 
        pstmt.setInt(8, dto.getFilesize()); 
        int cnt = pstmt.executeUpdate(); 
        if(cnt>0) flag = true; 
 
 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
    return flag; 
} 
/** 
 * �θ�۹�ȣ ���� 
 * @return int �θ�۹�ȣ 
 */ 
private int getRef(Connection con)throws Exception { 
    int ref = 0; 
    PreparedStatement stmt = null; 
    ResultSet rs = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" SELECT NVL(MAX(ref),0) + 1 FROM board "); 
 
    try{ 
        stmt = con.prepareStatement(sql.toString()); 
        rs = stmt.executeQuery(); 
        if(rs.next()){ 
            ref = rs.getInt(1); 
         } 
 
    }finally{ 
        DBClose.close(stmt, rs); 
    } 
 
    return ref; 
} 
/** 
 * ��ü ���ڵ� ���� 
 * @param searchColumn 
 * @param searchWord 
 * @return 
 */ 
public int getTotal(Map map, Connection con) 
throws Exception  
{ 
    int total =0; 
    PreparedStatement pstmt=null; 
    ResultSet rs = null; 
 
    String col = (String)map.get("col"); 
    String word = (String)map.get("word"); 
    
    StringBuffer sql = new StringBuffer(); 
    
    sql.append(" SELECT COUNT(*) FROM board "); 
 
    if(word.length() >0 && col.equals("subject_content")){ 
        sql.append(" WHERE (subject LIKE "+"'%"+word+"%' "); 
       sql.append(" OR content LIKE "+"'%"+word+"%') "); 
    }else if(word.length() >0){ 
       sql.append(" WHERE "+col+" LIKE "+"'%"+word+"%' "); 
    } 
    try{ 
       pstmt = con.prepareStatement(sql.toString()); 
       rs = pstmt.executeQuery(); 
       if(rs.next()){ 
          total = rs.getInt(1); 
       } 
 
    }finally{ 
        DBClose.close(pstmt, rs); 
    } 
 
    return total; 
 
} 
/** 
 * �Խ��� �۸�� 
 * @param searchColumn 
 * @param searchWord 
 * @param beginPerPage 
 * @param numPerPage 
 * @return 
 */ 
public List getList(Map map, Connection con) 
throws Exception 
{ 
    List list = new ArrayList(); 
    PreparedStatement pstmt = null; 
    ResultSet rs = null; 
 
    String col = (String)map.get("col"); 
    String word = (String)map.get("word"); 
    int sno =(Integer)map.get("sno"); 
    int eno =(Integer)map.get("eno"); 
 
    System.out.println("sno:"+sno); 
    System.out.print("eno:"+eno); 
    System.out.println("col:"+col); 
 
 
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
    if(word.length() >0 && col.equals("subject_content")){ 
       sql.append("         WHERE (subject LIKE "+"'%"+word+"%' "); 
       sql.append("         OR content LIKE "+"'%"+word+"%') "); 
    }else if(word.length() >0){ 
       sql.append("         WHERE "+col+" LIKE "+"'%"+word+"%' "); 
    } 
    sql.append("            ORDER BY ref DESC, ansnum ASC "); 
    sql.append("      ) "); 
    sql.append(" ) "); 
    sql.append(" WHERE r >= ? and r <= ? "); 
 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, sno); 
        pstmt.setInt(2, eno); 
 
 
        rs = pstmt.executeQuery(); 
        while(rs.next()){ 
        BoardDTO dto = new BoardDTO(); 
 
        dto.setNum(rs.getInt("num")); 
        dto.setName(rs.getString("name")); 
        dto.setSubject(rs.getString("subject")); 
        dto.setRegdate(rs.getString("regdate")); 
        dto.setCount(rs.getInt("count")); 
        dto.setIndent(rs.getInt("indent")); 
        dto.setFilename(rs.getString("filename")); 
        dto.setFilesize(rs.getInt("filesize")); 
 
        list.add(dto); 
    } 
 
    }finally{ 
        DBClose.close(pstmt, rs); 
    } 
 
    return list; 
 
} 
/** 
 * ��ȸ�� ���� 
 * @param num 
 */ 
public void upCount(int num,Connection con) throws Exception{ 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" UPDATE board SET count = count +1 "); 
    sql.append(" WHERE num = ? "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, num); 
        int cnt = pstmt.executeUpdate(); 
        if(cnt>0){ 
            System.out.println("��ȸ�� ����"); 
        }else{ 
            System.out.println("��ȸ�� ���� ����"); 
        } 
                 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
} 
/** 
 * �Խ��� �ۺ��� 
 * @param num 
 * @return 
 */ 
public BoardDTO read(int num,Connection con)  
throws Exception 
{ 
    BoardDTO dto = null; 
    PreparedStatement pstmt = null; 
    ResultSet rs = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" SELECT * from board "); 
    sql.append(" WHERE num = ? "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, num); 
        rs = pstmt.executeQuery(); 
        if(rs.next()){ 
            dto = new BoardDTO(); 
            dto.setNum(rs.getInt("num")); 
            dto.setName(rs.getString("name")); 
            dto.setSubject(rs.getString("subject")); 
            dto.setContent(rs.getString("content")); 
            dto.setCount(rs.getInt("count")); 
            dto.setFilename(rs.getString("filename")); 
            dto.setFilesize(rs.getInt("filesize")); 
            dto.setRef(rs.getInt("ref")); 
            dto.setIndent(rs.getInt("indent")); 
            dto.setAnsnum(rs.getInt("ansnum")); 
        } 
        
    }finally{ 
        DBClose.close(pstmt, rs); 
    } 
 
    return dto; 
} 
/** 
 * ��й�ȣ �˻� 
 * @param num 
 * @param passwd 
 * @return 
 */ 
public boolean passwdCheck(int num, String passwd,Connection con) 
throws Exception 
{ 
    boolean flag = false; 
    PreparedStatement pstmt = null; 
    ResultSet rs = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" SELECT passwd FROM board "); 
    sql.append(" WHERE num = ? and passwd = ? "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, num); 
        pstmt.setString(2, passwd); 
        rs = pstmt.executeQuery(); 
        flag = rs.next(); 
    }finally{ 
        DBClose.close(pstmt, rs); 
    } 
 
    return flag; 
} 
 
public boolean update(BoardDTO dto,Connection con)  
throws Exception 
{ 
    boolean flag = false; 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
 
    try{ 
        int i=0; 
   sql.append(" UPDATE board SET name=?, "); 
   sql.append(" subject=?, content=? "); 
    if(dto.getFilesize()>0){ 
     sql.append(" ,filename= ?, filesize=? "); 
    } 
    sql.append(" WHERE num = ? "); 
 
    pstmt= con.prepareStatement(sql.toString()); 
 
    pstmt.setString(++i, dto.getName()); 
    pstmt.setString(++i, dto.getSubject()); 
    pstmt.setString(++i, dto.getContent()); 
    if(dto.getFilesize()>0){ 
       pstmt.setString(++i, dto.getFilename()); 
       pstmt.setInt(++i, dto.getFilesize()); 
    } 
    pstmt.setInt(++i, dto.getNum()); 
 
    int cnt = pstmt.executeUpdate(); 
    if(cnt>0) flag = true; 
 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
    return flag; 
} 
/** 
 * ansnum ������ 
 * @param ref 
 * @param ansnum 
 */ 
public void upAnsnum(int ref, int ansnum,Connection con) 
throws Exception 
{ 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" UPDATE board SET ansnum = ansnum +1 "); 
    sql.append(" WHERE ref = ? and ansnum > ? "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, ref); 
        pstmt.setInt(2, ansnum); 
        int cnt = pstmt.executeUpdate(); 
        if(cnt>0){ 
            System.out.println("�亯�� �����Ƿ� ������ "); 
        }else{ 
            System.out.println("�亯�� �����Ƿ� ������ �ȵ� "); 
        } 
                 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
} 
/** 
 * �亯��� 
 * @param dto 
 * @return 
 */ 
public boolean insertReply(BoardDTO dto,Connection con) 
throws Exception 
{ 
    boolean flag = false; 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" INSERT INTO board(num, name, passwd, "); 
    sql.append(" subject, content, regdate, ref, indent, ansnum, "); 
    sql.append(" ip, filename, filesize, refnum ) "); 
    sql.append(" VALUES(board_seq.Nextval,?,?,?,?,sysdate,?, "); 
    sql.append(" ?,?,?,?,?,?) "); 
 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setString(1, dto.getName()); 
        pstmt.setString(2, dto.getPasswd()); 
        pstmt.setString(3, dto.getSubject()); 
        pstmt.setString(4, dto.getContent()); 
        pstmt.setInt(5, dto.getRef()); //�� �߿�(�θ�� ���� ref) 
        pstmt.setInt(6, dto.getIndent()+1);//�� �߿�(�θ𺸴� 1������ indent) 
        pstmt.setInt(7, dto.getAnsnum()+1);//�� �߿�(�θ𺸴� 1������ ansnum) 
        pstmt.setString(8, dto.getIp()); 
        pstmt.setString(9, dto.getFilename()); 
        pstmt.setInt(10, dto.getFilesize()); 
        //�θ��� �۹�ȣ�� refnum�� ���(�θ���� �������� �ʰ��ϱ� ���ؼ�) 
        pstmt.setInt(11, dto.getNum()); 
        int cnt = pstmt.executeUpdate(); 
        if(cnt>0) flag = true; 
 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
    return flag; 
} 
/** 
 * �θ������ Ȯ�� 
 * @param num 
 * @param con  
 * @return 
 */ 
public boolean checkRefnum(int num, Connection con) 
throws Exception 
{ 
    boolean flag = false; 
    PreparedStatement pstmt = null; 
    ResultSet rs = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" SELECT count(*)  FROM board  "); 
    sql.append(" WHERE refnum = ?  "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, num); 
 
        rs = pstmt.executeQuery(); 
        rs.next(); 
        int cnt = rs.getInt(1); 
        if(cnt>0){ 
           flag = true; //�θ���̴� 
        } 
    }finally{ 
        DBClose.close(pstmt,rs); 
    } 
 
    return flag; 
    } 
 
/** 
 * �Խ��� �� ���� 
 * @param num 
 * @return 
 */ 
public boolean delete(int num, Connection con) throws Exception {
boolean flag = false; 
    PreparedStatement pstmt = null; 
    StringBuffer sql = new StringBuffer(); 
    sql.append(" delete from board  "); 
    sql.append(" WHERE num = ?  "); 
    try{ 
        pstmt = con.prepareStatement(sql.toString()); 
        pstmt.setInt(1, num); 
 
        int cnt =pstmt.executeUpdate(); 
        if(cnt>0){ 
           flag = true; //�θ���̴� 
        } 
    }finally{ 
        DBClose.close(pstmt); 
    } 
 
    return flag; 
} 
} 
