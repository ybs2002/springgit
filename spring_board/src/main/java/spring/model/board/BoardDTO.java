package spring.model.board; 
 
import org.springframework.web.multipart.MultipartFile; 
 
public class BoardDTO { 
    private int  num ;  // �� �Ϸ� ��ȣ 
    private String  name;      // �۾��� ���� 
    private String  subject; // ���� 
    private String  content; // ���� 
    private int    ref; // �θ�� ��ȣ(�׷��ȣ) 
    private int    indent; // �亯����,���� 
    private int    ansnum; // �亯 ����(�ֽŴ亯�� �θ�� �ٷ� �Ʒ� �޷����ϴ�.) 
    private String  regdate; // �� ����� 
    private String  passwd; // �н����� 
    private int    count; // ��ȸ��  
    private String  ip; // �۾��� IP  
    private MultipartFile filenameMF; //form���� <input type=file name="filenameMF">�ڡڡڡ�(�߿�) 
    private String  filename; // ���ϸ�  
    private int    filesize; // ���� ������ 
    private int    refnum; // �亯Ȯ�� �÷� 
    
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
