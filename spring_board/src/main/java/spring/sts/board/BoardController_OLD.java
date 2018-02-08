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
    //page관련 변수 
    //String url = "./list.do"; //page링크시 이동할 페이지 
    int nowPage = 1;   //현재페이지 , 페이지 시작 번호 0 ->1page  
    int numPerPage =5;  //페이지당 레코드 수 5       
 
    int recNo=1;          //게시판 목록에 출력될 글 번호  
 
    //검색관련 변수 콤보박스의 값
    String col = ""; 
    
    if(request.getParameter("col")!=null){ //검색컬럼(이름,제목,내용,제목+내용) 
        col = request.getParameter("col"); 
        System.out.println("검색컬럼:"+col); 
    }
 
    //검색어
    String word = ""; 
    if(request.getParameter("word")!=null){ 
        word = request.getParameter("word");  
        System.out.println("검색어:"+word); 
    } 
    //검색관련 변수 끝 
 
 
    //현재 페이지의 정보를 가져옵니다. 
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
    
//	mybatis사용하여 작성
	IBoardDAO boardDao = sqlSession.getMapper(IBoardDAO.class);
	 List<BoardDTO> list = boardDao.getList(map);
	 int total = boardDao.getTotal(map);
	 
    //1. model사용  스프링 프레임워크용 작성
//    List<BoardDTO> list = boardMgr.getList(map); 
//    int total = boardMgr.getTotal(map); 
// 
    String paging = new Paging().paging3(total, nowPage, numPerPage, col, word);
 
    recNo = total - (nowPage-1) * numPerPage; 
 
    //2. model사용후 결과값을 request영역에 저장 
    request.setAttribute("list", list); 
    request.setAttribute("paging", paging); 
    request.setAttribute("recNo", recNo+1); 
    request.setAttribute("nowPage",nowPage); 
    request.setAttribute("col", col); 
    request.setAttribute("word", word); 
    request.setAttribute("downDir", "/resources/storage"); 
    //3. 결과값을 보여줄 view리턴 
   // return "./boardList"; 
    return "./list"; 
} 
 
@RequestMapping(value="/board/write.do",method=RequestMethod.GET) 
public String write(){ 
	
	System.out.println("write.do called");
 
	//return "./boardCreate"; 
	return "./writeView"; 
} 

/** 파일 업로드 처리 
 * 실제디렉토리가 있어야 업로드가 됨.
 * 파일다운로드 했는데 시간지나면 톰캣에서 없애버림  실제 디렉토리  
 * D:\tools\apache-tomcat-8.5.24\wtpwebapps\spring_board\resources\storage\40링팩4.jpg --> \resources\storage\는 내가 설정 앞에거는 context 내용
 * wtpwebapps 디렉토리는 톰캣서버를 클릭하면 deploy directory에 설정되어있숨
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

    //업로드 처리 
	// **실제디렉토리가 있어야 업로드가 됨.
	// 파일다운로드 했는데 시간지나면 톰캣에서 없애버림  실제 디렉토리  
	// D:\tools\apache-tomcat-8.5.24\wtpwebapps\spring_board\resources\storage\40링팩4.jpg --> \resources\storage\는 내가 설정 앞에거는 context 내용
    String filename = Utility.saveFileSpring30(dto.getFilenameMF(), upDir); 
    int filesize = (int)dto.getFilenameMF().getSize(); 
 
    dto.setFilename(filename); 
    dto.setFilesize(filesize); 
 
    String ip = request.getRemoteAddr();  
    dto.setIp(ip); 
    
//	mybatis사용하여 작성
//	IBoardDAO boardDao = sqlSession.getMapper(IBoardDAO.class);
//	boardDao. write(dto);
	
    //mybatis 다른방법으로 insert
	int cnt = sqlSession.insert("spring.model.board.IBoardDAO.write", dto);
	System.out.println("cnt : "+ cnt);
	if (cnt > 0) flag = true;
	
 
	// 스프링으로 게시판 작성
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
    //업로드 처리 
 
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
    //업로드 처리 
 
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
