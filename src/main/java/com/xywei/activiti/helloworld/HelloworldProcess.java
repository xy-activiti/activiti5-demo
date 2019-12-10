package com.xywei.activiti.helloworld;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class HelloworldProcess {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 
	 * @Description 部署流程
	 * @Datetime 2019年12月10日 下午3:07:10<br/>
	 */
	@Test
	public void deployProcess() {

		Deployment deployment = this.processEngine.getRepositoryService().createDeployment().name("helloworld第一个程序")
				.addClasspathResource("Helloworld.bpmn").addClasspathResource("Helloworld.png").deploy();
		System.out.println("部署的流程图是：id=" + deployment.getId() + ", name=" + deployment.getName());

	}

	/**
	 * 
	 * @Description 使用zip压缩包方式部署流程
	 * @Datetime 2019年12月10日 下午4:25:42<br/>
	 */
	@Test
	public void deployProcessByZip() {

		RepositoryService repositoryService = processEngine.getRepositoryService();

		String helloworldProcessZipName = "helloworld.zip";
		InputStream helloworldZipInputStream = this.getClass().getResourceAsStream(helloworldProcessZipName);
		ZipInputStream zipInputStream = new ZipInputStream(helloworldZipInputStream);
		Deployment deployment = repositoryService.createDeployment().name("使用zip部署的helloworld流程")
				.addZipInputStream(zipInputStream).deploy();
		System.out.println("部署的流程, id=" + deployment.getId() + ", name=" + deployment.getName());

	}

}
