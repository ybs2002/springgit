package spring.utility.board;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class MainClass {

	public static void main(String[] args) {
		
		//gCtx.load("applicationCTX_dev.xml", "applicationCTX_run.xml"); //동일 디렉토리 xml에 동일한 클래스를을 여러개 선언하면 안된다.

		System.out.println("ip : " + ApplicationConfig.getInfo().getIpNum());
		System.out.println("port : " + ApplicationConfig.getInfo().getPortNum());
		
		
//		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
//		ConfigurableEnvironment env = ctx.getEnvironment();
//		MutablePropertySources propertySources = env.getPropertySources();
//
//		try {
//			propertySources.addLast(new ResourcePropertySource("classpath:application.properties"));
//
//			System.out.println(env.getProperty("admin.id"));
//			System.out.println(env.getProperty("admin.pw"));
//		} catch (IOException e) {
//		}
//
//		GenericXmlApplicationContext gCtx = (GenericXmlApplicationContext) ctx;
//
//		gCtx.load("applicationCFG.xml");  
//		gCtx.refresh();
//
//		ApplicationInfo applicationInfo = (ApplicationInfo)gCtx.getBean("applicationInfo", ApplicationInfo.class);
//		System.out.println("admin ID : " + applicationInfo.getAdminId());
//		System.out.println("amdin PW : " + applicationInfo.getAdminPw());
//		System.out.println("up directory : " + applicationInfo.getUpDir());
//		gCtx.close();
//		ctx.close();
		System.out.println("admin ID : " + ApplicationConfig.getAppInfo().getAdminId());
		System.out.println("amdin PW : " + ApplicationConfig.getAppInfo().getAdminPw());
		System.out.println("upload directory : " + ApplicationConfig.getAppInfo().getUpDir());

	}

}
