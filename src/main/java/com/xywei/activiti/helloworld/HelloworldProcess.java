package com.xywei.activiti.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xywei.activiti.utils.InitActivitiTableUtils;
import com.xywei.activiti.utils.ProcessDeploymentUtils;
import com.xywei.activiti.utils.ProcessTaskUtils;
import com.xywei.activiti.utils.ZipUtils;

public class HelloworldProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloworldProcess.class);

	private static String helloworldProcessZipName = "helloworld流程";
	private ProcessEngine processEngine = InitActivitiTableUtils.initTableFromResource();

	/**
	 * 
	 * @Description 压缩helloworld流程文件，前提需要写好文件
	 * @Datetime 2019年12月11日 下午3:04:59<br/>
	 */
	@Test
	public void compressHelloworldProcessFileTest() {
		System.out.println(compressHelloworldProcessFile());
	}

	public static boolean compressHelloworldProcessFile() {

		String filePath = "activiti/processfile/helloworld/";
		String bpmnFile = "Helloworld.bpmn";
		String pngFile = "Helloworld.png";
		List<String> processFiles = new ArrayList<String>();
		processFiles.add(bpmnFile);
		processFiles.add(pngFile);

		String zipFileName = helloworldProcessZipName;

		boolean compressFileResult = false;
		try {
			compressFileResult = ZipUtils.compressFileAsZip(filePath, zipFileName, processFiles);
			LOGGER.info("压缩结果: {}", compressFileResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressFileResult;

	}

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

		InputStream helloworldZipInputStream = this.getClass().getResourceAsStream(helloworldProcessZipName);
		ZipInputStream zipInputStream = new ZipInputStream(helloworldZipInputStream);
		Deployment deployment = repositoryService.createDeployment().name("使用zip部署的helloworld流程2")
				.addZipInputStream(zipInputStream).deploy();
		System.out.println("部署的流程, id=" + deployment.getId() + ", name=" + deployment.getName());

	}

	/**
	 * 
	 * @Description 开启helloworld流程实例
	 * @Datetime 2019年12月10日 下午5:30:31<br/>
	 */
	@Test
	public void startHelloworldProcessInstance() {

		String processDefinitionKey = "helloworldProcessId";
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例启动完毕, id=" + processInstance.getId() + ", name=" + processInstance.getName());

	}

	/**
	 * 
	 * @Description 由用户1 大Q哥完成任务：提出helloworld口号
	 * @Datetime 2019年12月10日 下午5:55:20<br/>
	 */
	@Test
	public void completeTaskByUser1() {

		TaskService taskService = processEngine.getTaskService();
		String taskId = "2504";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("helloworldKey", "跟着我喊helloworld" + Calendar.getInstance().getTime());
		ProcessTaskUtils.completeTask(taskService, taskId, variables);

	}

	/*********************************
	 * 以下使用ProcessDeploymentUtils来做
	 **************************************/
	@Test
	public void deployProcessByZip2() {

		if (compressHelloworldProcessFile()) {

			RepositoryService repositoryService = processEngine.getRepositoryService();

			String filePath = "activiti/processfile/helloworld/";
			String processName = "helloworld流程zip";
			String zipFile = helloworldProcessZipName;

			ProcessDeploymentUtils.deployProcessByZipInputStream(repositoryService, filePath, processName, zipFile);
		}
	}

}
