package spring.utility.board;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class ApplicationConfig {
	
	public static final String config = "run";  //프려파티 화일에 설정을 하는게 나을것 같음.
	
	/*
	 * xml 파일을 이용한 환경설정
	 * applicationCTX_dev.xml", "applicationCTX_run.xml 개발환경과 운영환경 ip나 port 파일 설정  
	 * config가 'run' 이면 운영 'dev'면 개발
	 */
	public static ServerInfo getInfo() {
		// TODO Auto-generated constructor stub
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles(config);
		ctx.load("applicationCTX_dev.xml", "applicationCTX_run.xml");
		//ctx.refresh();
			
		ServerInfo info = ctx.getBean("serverInfo", ServerInfo.class);
		ctx.close();
		
		return info;
		
	}
	
	/**
	 * application.properties 파일을 이용한 환경설정
	 * 스프링에서 프러퍼티화일을 이용하여 설정 업로드 디렉토리 등등
	 */
	
	public static ApplicationInfo getAppInfo() {
		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
		ConfigurableEnvironment env = ctx.getEnvironment();
		MutablePropertySources propertySources = env.getPropertySources();

		try {
			propertySources.addLast(new ResourcePropertySource("classpath:application.properties"));

			System.out.println(env.getProperty("admin.id"));
			System.out.println(env.getProperty("admin.pw"));
		} catch (IOException e) {
		}

		GenericXmlApplicationContext gCtx = (GenericXmlApplicationContext) ctx;
		//gCtx.load("applicationCTX_dev.xml", "applicationCTX_run.xml"); //동일 디렉토리 xml에 동일한 클래스를을 여러개 선언하면 안된다.
		gCtx.load("applicationCFG.xml");  
		gCtx.refresh();

		ApplicationInfo applicationInfo = gCtx.getBean("applicationInfo", ApplicationInfo.class);
		gCtx.close();
		
		return applicationInfo;
		
	}

}
