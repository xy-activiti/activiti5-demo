package com.xywei.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author future
 * @Datetime 2019年12月10日 下午2:54:30<br/>
 * @Description 初始化创建数据表
 */
public class InitActivitiTableUtils {

	private static final Logger log = LoggerFactory.getLogger(InitActivitiTableUtils.class);

	/**
	 * 
	 * 
	 * @Description 方法1，指定配置文件创建
	 * @Datetime 2019年12月10日 下午2:57:08<br/>
	 */
	@Test
	public void initTableFromResourceTest() {
		initTableFromResource();
	}

	public static ProcessEngine initTableFromResource() {

		String resource = "/activiti.cfg.xml";
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(resource);
		ProcessEngine buildProcessEngine = configuration.buildProcessEngine();
		System.out.println("使用特定xml配置创建的工作流是：" + buildProcessEngine);
		log.info("使用特定xml配置创建的工作流是：{}", buildProcessEngine);

		return buildProcessEngine;

	}

	/**
	 * 
	 * @Description 方法2，使用默认的配置文件方式
	 * @Datetime 2019年12月10日 下午3:45:46<br/>
	 */
	@Test
	public void initTableDefaultTest() {

		initTableDefault();

	}

	public static ProcessEngine initTableDefault() {
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("使用默认xml配置创建的工作流是：" + defaultProcessEngine);
		log.info("使用特定xml配置创建的工作流是：{}", defaultProcessEngine);

		return defaultProcessEngine;
	}

}
