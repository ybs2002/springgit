package spring.sts.board; 
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.model.board.BoardDTO;
import spring.model.board.BoardMgr;
import spring.model.board.IBoardDAO;
import spring.utility.board.Paging;
import spring.utility.board.Utility; 
 
//@Controller 
public class BoardController_OLD { 
@Autowired 
private BoardMgr boardMgr; 
@Autowired
private SqlSession sqlSession;

//private JdbcTemplate template;

//@Autowired
//public void setTemplate(JdbcTemplate template) {
//	System.out.println("setTemplate called");
//	this.template = template;
//	Constant.template = this.template;
//}

 
@RequestMapping("/board/list.do") 
public String list(HttpServletRequest request){ 
    //page���� ���� 
    //String url = "./list.do"; //page��ũ�� �̵��� ������ 
    int nowPage = 1;   //���������� , ������ ���� ��ȣ 0 ->1page  
    int numPerPage =5;  //�������� ���ڵ� �� 5       
 
    int recNo=1;          //�Խ��� ��Ͽ� ��µ� �� ��ȣ  
 
    //�˻����� ���� �޺��ڽ��� ��
    String col = ""; 
    
    if(request.getParameter("col")!=null){ //�˻��÷�(�̸�,����,����,����+����) 
        col = request.getParameter("col"); 
        System.out.println("�˻��÷�:"+col); 
    }
 
    //�˻���
    String word = ""; 
    if(request.getParameter("word")!=null){ 
        word = request.getParameter("word");  
        System.out.println("�˻���:"+word); 
    } 
    //�˻����� ���� �� 
 
 
    //���� �������� ������ �����ɴϴ�. 
    if(request.getParameter("nowPage")!=null){ 
        nowPage = Integer.parseInt(request.getParameter("nowPage")); 
    } 
 
 
    int sno = ((nowPage-1) * numPerPage) + 1; // (0 * 10) + 1 = 1, 11, 21 
    int eno = nowPage * numPerPage;  
 
 
//    Map map = new HashMap(); 
//    map.put("col",col); 
//    map.put("word",word); 
//    map.put("sno",sno); 
//    map.put("eno",eno); 
    
    Map<String, String> map = new HashMap<String, String>(); 
    map.put("col",col); 
    map.put("word",word); 
    map.put("sno",String.valueOf(sno)); 
    map.put("eno",String.valueOf(eno)); 
    
//	mybatis����Ͽ� �ۼ�
	IBoardDAO boardDao = sqlSession.getMapper(IBoardDAO.class);
	 List<BoardDTO> list = boardDao.getList(map);
	 int total = boardDao.getTotal(map);
	 
    //1. model���  ������ �����ӿ�ũ�� �ۼ�
//    List<BoardDTO> list = boardMgr.getList(map); 
//    int total = boardMgr.getTotal(map); 
// 
    String paging = new Paging().paging3(total, nowPage, numPerPage, col, word);
 
    recNo = total - (nowPage-1) * numPerPage; 
 
    //2. model����� ������� request������ ���� 
    request.setAttribute("list", list); 
    request.setAttribute("paging", paging); 
    request.setAttribute("recNo", recNo+1); 
    request.setAttribute("nowPage",nowPage); 
    request.setAttribute("col", col); 
    request.setAttribute("word", word); 
    request.setAttribute("downDir", "/resources/storage"); 
    //3. ������� ������ view���� 
   // return "./boardList"; 
    return "./list"; 
} 
 
@RequestMapping(value="/board/write.do",method=RequestMethod.GET) 
public String write(){ 
	
	System.out.println("write.do called");
 
	//return "./boardCreate"; 
	return "./writeView"; 
} 

/** ���� ���ε� ó�� 
 * �������丮�� �־�� ���ε尡 ��.
 * ���ϴٿ�ε� �ߴµ� �ð������� ��Ĺ���� ���ֹ���  ���� ���丮  
 * D:\tools\apache-tomcat-8.5.24\wtpwebapps\spring_board\resources\storage\40����4.jpg --> \resources\storage\�� ���� ���� �տ��Ŵ� context ����
 * wtpwebapps ���丮�� ��Ĺ������ Ŭ���ϸ� deploy directory�� �����Ǿ��ּ�
 */
@RequestMapping(value="/board/write.do",method=RequestMethod.POST) 
public String write(BoardDTO dto,HttpServletRequest request,HttpSession session ){ 
	boolean flag = false;
	String upDir =request.getRealPath("/resources/storage"); 
	
	System.out.println("request.getContextPath =" + request.getContextPath());
	System.out.println("request.getPathInfo() =" + request.getPathInfo());
	System.out.println("request.getPathTranslated() =" + request.getPathTranslated());
	System.out.println("request.getRemoteAddr() =" + request.getRemoteAddr());
	

//	ServletConfig config=getServletConfig();
//	System.out.println("id" + config.getInitParameter("id"));
//	String id = config.getInitParameter("id");
//	String pwd = config.getInitParameter("pwd");
//	
	
	//ServletContext ctx = config.getServletContext();
	//String upDir = "/resources/storage";

	System.out.println("upDir =" + upDir);

    //���ε� ó�� 
	// **�������丮�� �־�� ���ε尡 ��.
	// ���ϴٿ�ε� �ߴµ� �ð������� ��Ĺ���� ���ֹ���  ���� ���丮  
	// D:\tools\apache-tomcat-8.5.24\wtpwebapps\spring_board\resources\storage\40����4.jpg --> \resources\storage\�� ���� ���� �տ��Ŵ� context ����
    String filename = Utility.saveFileSpring30(dto.getFilenameMF(), upDir); 
    int filesize = (int)dto.getFilenameMF().getSize(); 
 
    dto.setFilename(filename); 
    dto.setFilesize(filesize); 
 
    String ip = request.getRemoteAddr();  
    dto.setIp(ip); 
    
//	mybatis����Ͽ� �ۼ�
//	IBoardDAO boardDao = sqlSession.getMapper(IBoardDAO.class);
//	boardDao. write(dto);
	
    //mybatis �ٸ�������� insert
	int cnt = sqlSession.insert("spring.model.board.IBoardDAO.write", dto);
	System.out.println("cnt : "+ cnt);
	if (cnt > 0) flag = true;
	
 
	// ���������� �Խ��� �ۼ�
//    boolean flag = boardMgr.write(dto); 
 
    if(flag){ 
        return "redirect:./list.do"; 
    }else{ 
        return "errorPage"; 
} 
 
} 
@RequestMapping("/board/read.do") 
public ModelAndView read(ModelAndView mview,int num,HttpServletRequest request){ 
	System.out.println("read.do called");
	
    boardMgr.upCount(num); 
    mview.addObject("dto", boardMgr.read(num)); 
    mview.addObject("downDir", "/resources/storage"); 
    mview.setViewName("./read"); 
 
    return mview; 
} 
@RequestMapping(value="/board/update.do",method=RequestMethod.GET) 
public ModelAndView update(ModelAndView mview,int num){ 
 
    mview.addObject("dto", boardMgr.read(num)); 
   // mview.setViewName("./update"); 
    mview.setViewName("./updateView"); 
 
    return mview; 
} 
 
@RequestMapping(value="/board/update.do",method=RequestMethod.POST) 
public String update(BoardDTO dto,HttpServletRequest request,String oldfile){ 

    String upDir =request.getRealPath("/resources/storage"); 
    //���ε� ó�� 
 
    String filename = Utility.saveFileSpring30(dto.getFilenameMF(), upDir); 
    int filesize = (int)dto.getFilenameMF().getSize(); 
 
    dto.setFilename(filename); 
    dto.setFilesize(filesize); 
    boolean flag = boardMgr.passwdCheck(dto.getNum(), dto.getPasswd()); 
 
    if(flag){ 
        if(boardMgr.update(dto))Utility.deleteFile(upDir, oldfile); 
        return "redirect:./list.do"; 
    }else{ 
        return "passwdError"; 
    } 
 
} 
@RequestMapping(value="/board/reply.do",method=RequestMethod.GET) 
public ModelAndView reply(ModelAndView mview,int num){ 
 
    mview.addObject("dto", boardMgr.read(num)); 
    mview.setViewName("./replyView"); 
 
    return mview; 
} 
 
@RequestMapping(value="/board/reply.do",method=RequestMethod.POST) 
public String reply(BoardDTO dto,HttpServletRequest request,String oldfile, HttpSession session){ 
	
    String upDir =request.getRealPath("/resources/storage"); 
    //���ε� ó�� 
 
    String filename = Utility.saveFileSpring30(dto.getFilenameMF(), upDir); 
    int filesize = (int)dto.getFilenameMF().getSize(); 
 
    dto.setFilename(filename); 
    dto.setFilesize(filesize); 
     
    String ip = request.getRemoteAddr(); 
    dto.setIp(ip); 
 
    if(boardMgr.reply(dto)){ 
        return "redirect:./list.do"; 
    }else{ 
        return "error"; 
    } 
 
} 
@RequestMapping(value="/board/delete.do",method=RequestMethod.GET) 
public String delete(int num, HttpServletRequest request){ 
    boolean flag = false; 
    if(boardMgr.checkRefnum(num)){ 
        flag = true; 
    } 
    request.setAttribute("flag", flag); 
    return "./deleteView"; 
} 
@RequestMapping(value="/board/delete.do",method=RequestMethod.POST) 
public String delete(int num,String passwd,String oldfile,HttpServletRequest request){ 
	
    String upDir =request.getRealPath("/resources/storage"); 
    boolean flag = boardMgr.passwdCheck(num,passwd); 
 
    if(flag){ 
        if(boardMgr.delete(num))Utility.deleteFile(upDir, oldfile); 
        return "redirect:./list.do"; 
    }else{ 
        return "passwdError"; 
    } 
  } 
} 
