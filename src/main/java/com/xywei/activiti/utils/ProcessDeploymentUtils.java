package com.xywei.activiti.utils;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessDeploymentUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDeploymentUtils.class);

	/**
	 * 
	 * @Description 使用文件方式创建流程部署
	 * @Datetime 2019年12月11日 下午2:14:47<br/>
	 * @param repositoryService
	 * @param processName       部署的流程名字
	 * @param bpmnFile          流程bmpn文件，位于类路径，"/"开头
	 * @param pngFile           流程png，位于类路径，"/"开头
	 */
	public static void deployProcess(RepositoryService repositoryService, String processName, String bpmnFile,
			String pngFile) {
		if (!IoUtil.getFile(bpmnFile).exists()) {
			LOGGER.info("文件不存在：", bpmnFile);
			return;
		}
		if (!IoUtil.getFile(pngFile).exists()) {

			LOGGER.info("文件不存在：", pngFile);
			return;
		}

		Deployment deploy = repositoryService.createDeployment().name(processName).addClasspathResource(bpmnFile)
				.addClasspathResource(pngFile).deploy();
		LOGGER.info("普通部署的流程ID：{}, 流程名字：{}", deploy.getId(), deploy.getName());

	}

	/**
	 * 
	 * @Description 通过zip压缩包方式部署流程
	 * @Datetime 2019年12月11日 下午2:22:04<br/>
	 * @param repositoryService
	 * @param filePath          文件所在的路径
	 * @param processName       流程名
	 * @param zipFile           流程bmpn和png文件所在的压缩包,
	 */
	public static void deployProcessByZipInputStream(RepositoryService repositoryService, String filePath,
			String processName, String zipFile) {

		LOGGER.info("流程名:：{}", processName);
		LOGGER.info("zip文件全名：{}", zipFile);
		if (!zipFile.endsWith(".zip")) {
			zipFile = zipFile + ".zip";
		}
		String realZipFile = filePath + zipFile;
		InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(realZipFile);
		if (inputStream == null) {

			LOGGER.info("zip文件为空");

			return;

		}
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deploy = repositoryService.createDeployment().name(processName).addZipInputStream(zipInputStream)
				.deploy();
		LOGGER.info("使用zip部署的流程ID：{}, 流程名字：{}", deploy.getId(), deploy.getName());

	}

}
