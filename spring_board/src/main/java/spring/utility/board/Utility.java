package spring.utility.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Utility {
    public static void deleteFile(String upDir,String oldfile){
        File file = new File(upDir,oldfile);
        
        if(file.exists()){
            file.delete();
        }
    }
    
    public static String checkNull(String str){
        if(str==null){
            str = "";
        }
        
        return str;
    }
    
    /**
     * ����,����,���� ��¥ ��������
     * @return List- ��¥�� ����
     */
    public static List<String> getDay(){
        List<String> list = new ArrayList<String>();
        
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        for(int j = 0; j < 3; j++){
            list.add(sd.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
        }
        
        return list;
    }
    /**
     * ��ϳ�¥�� ����,����,������¥�� ��
     * @param wdate - ��ϳ�¥
     * @return - true:����,����,������ ��ϳ�¥�� ����
     *           false:����,����,���� ��¥�� ��ϳ�¥�� �� �ٸ�
     */
    public static boolean compareDay(String wdate){
        boolean flag = false;
        List<String> list = getDay();
        if(wdate.equals(list.get(0)) 
           || wdate.equals(list.get(1))
           || wdate.equals(list.get(2))){
            flag = true;
        }
          
        return flag;
    }
    
    /** 
     * ���Ͼ��ε� ó��(model1,mvc) 
     * @param fileItem 
     * @param upDir 
     * @return 
     */ 
    public static String saveFileSpring30(MultipartFile multipartFile, String basePath) { 
        // input form's parameter name 
        String fileName = ""; 
        // original file name 
        String originalFileName = multipartFile.getOriginalFilename(); 
        // file content type 
        String contentType = multipartFile.getContentType(); 
        // file size 
        long fileSize = multipartFile.getSize(); 
         
        System.out.println("fileSize: " + fileSize); 
        System.out.println("originalFileName: " + originalFileName); 
         
        InputStream inputStream = null; 
        OutputStream outputStream = null; 
 
        try { 
            if( fileSize > 0 ) { // ������ �����Ѵٸ� 
                // ��ǲ ��Ʈ���� ��´�. 
                inputStream = multipartFile.getInputStream(); 
 
                File oldfile = new File(basePath, originalFileName); 
             
                if ( oldfile.exists()){ 
                    for(int k=0; true; k++){ 
                        //���ϸ� �ߺ��� ���ϱ� ���� �Ϸ� ��ȣ�� �����Ͽ� 
                        //���ϸ����� ���� 
                        oldfile = new File(basePath,"("+k+")"+originalFileName); 
                     
                        //���յ� ���ϸ��� �������� �ʴ´ٸ�, �Ϸù�ȣ�� 
                        //���� ���ϸ� �ٽ� ���� 
                        if(!oldfile.exists()){ //�������� �ʴ� ��� 
                            fileName = "("+k+")"+originalFileName; 
                            break; 
                        } 
                    } 
                }else{ 
                    fileName = originalFileName; 
                } 
                //make server full path to save 
                String serverFullPath = basePath + "\\" + fileName; 
                 
                System.out.println("fileName: " + fileName); 
                System.out.println("serverFullPath: " + serverFullPath); 
                 
                outputStream = new FileOutputStream( serverFullPath ); 
  
                // ���۸� �����. 
                int readBytes = 0; 
                byte[] buffer = new byte[8192]; 
 
                while((readBytes = inputStream.read(buffer, 0, 8192)) != -1 ) { 
                    outputStream.write( buffer, 0, readBytes ); 
                } 
                outputStream.close(); 
                inputStream.close(); 
                         
            } 
 
        } catch(Exception e) { 
            e.printStackTrace();   
        }finally{ 
             
        } 
         
        return fileName; 
    } 

}
