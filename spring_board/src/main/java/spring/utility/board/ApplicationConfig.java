package spring.utility.board;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class ApplicationConfig {
	
	public static final String config = "run";  //������Ƽ ȭ�Ͽ� ������ �ϴ°� ������ ����.
	
	/*
	 * xml ������ �̿��� ȯ�漳��
	 * applicationCTX_dev.xml", "applicationCTX_run.xml ����ȯ��� �ȯ�� ip�� port ���� ����  
	 * config�� 'run' �̸� � 'dev'�� ����
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
	 * application.properties ������ �̿��� ȯ�漳��
	 * ���������� ������Ƽȭ���� �̿��Ͽ� ���� ���ε� ���丮 ���
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
		//gCtx.load("applicationCTX_dev.xml", "applicationCTX_run.xml"); //���� ���丮 xml�� ������ Ŭ�������� ������ �����ϸ� �ȵȴ�.
		gCtx.load("applicationCFG.xml");  
		gCtx.refresh();

		ApplicationInfo applicationInfo = gCtx.getBean("applicationInfo", ApplicationInfo.class);
		gCtx.close();
		
		return applicationInfo;
		
	}

}
