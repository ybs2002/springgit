package spring.model.board; 
 
import org.springframework.web.multipart.MultipartFile; 
 
public class BoardDTO { 
    private int  num ;  // 글 일련 번호 
    private String  name;      // 글쓴이 성명 
    private String  subject; // 제목 
    private String  content; // 내용 
    private int    ref; // 부모글 번호(그룹번호) 
    private int    indent; // 답변여부,깊이 
    private int    ansnum; // 답변 순서(최신답변은 부모글 바로 아래 달려집니다.) 
    private String  regdate; // 글 등록일 
    private String  passwd; // 패스워드 
    private int    count; // 조회수  
    private String  ip; // 글쓴이 IP  
    private MultipartFile filenameMF; //form에서 <input type=file name="filenameMF">★★★★(중요) 
    private String  filename; // 파일명  
    private int    filesize; // 파일 사이즈 
    private int    refnum; // 답변확인 컬럼 
    
    public int getNum() { 
        return num; 
    } 
    public void setNum(int num) { 
        this.num = num; 
    } 
    public String getName() { 
        return name; 
    } 
    public void setName(String name) { 
        this.name = name; 
    } 
    public String getSubject() { 
        return subject; 
    } 
    public void setSubject(String subject) { 
        this.subject = subject; 
    } 
    public String getContent() { 
        return content; 
    } 
    public void setContent(String content) { 
        this.content = content; 
    } 
    public int getRef() { 
        return ref; 
    } 
    public void setRef(int ref) { 
        this.ref = ref; 
    } 
    public int getIndent() { 
        return indent; 
    } 
    public void setIndent(int indent) { 
        this.indent = indent; 
    } 
    public int getAnsnum() { 
        return ansnum; 
    } 
    public void setAnsnum(int ansnum) { 
        this.ansnum = ansnum; 
    } 
    public String getRegdate() { 
        return regdate; 
    } 
    public void setRegdate(String regdate) { 
        this.regdate = regdate; 
    } 
    public String getPasswd() { 
        return passwd; 
    } 
    public void setPasswd(String passwd) { 
        this.passwd = passwd; 
    } 
    public int getCount() { 
        return count; 
    } 
    public void setCount(int count) { 
        this.count = count; 
    } 
    public String getIp() { 
        return ip; 
    } 
    public void setIp(String ip) { 
        this.ip = ip; 
    } 
    public String getFilename() { 
        return filename; 
    } 
    public void setFilename(String filename) { 
        this.filename = filename; 
    } 
    public int getFilesize() { 
        return filesize; 
    } 
    public void setFilesize(int filesize) { 
        this.filesize = filesize; 
    } 
    public int getRefnum() { 
        return refnum; 
    } 
    public void setRefnum(int refnum) { 
        this.refnum = refnum; 
    } 
    public MultipartFile getFilenameMF() { 
        return filenameMF; 
    } 
    public void setFilenameMF(MultipartFile filenameMF) { 
        this.filenameMF = filenameMF; 
    } 
} 
