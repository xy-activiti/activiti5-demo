package com.xywei.activiti.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author future
 * @Datetime 2019年12月12日 下午3:51:47<br/>
 * @Description 某些方法依赖spring core包
 */
public class ZipUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 
	 * @Description 压缩filePath路径下的files文件，压缩后压缩包文件名为zipFileName
	 * @Datetime 2019年12月11日 下午2:55:01<br/>
	 * @param filePath    位于类路径下，以"/"结尾，zipFileName，files都会在该路径，<br/>
	 *                    例如：activiti/processfile/helloworld/
	 * @param zipFileName 压缩包名，不需要指定后缀名zip，例如:helloworld
	 * @param files       需要被压缩的文件名集合
	 * @throws IOException
	 */
	public static boolean compressFileAsZip(String filePath, String zipFileName, List<String> files)
			throws IOException {

		String zipSuffix = ".zip";
		if (filePath == null || "".equals(filePath)) {
			LOGGER.info("路径：{}为空", filePath);
			return false;
		}
		if (zipFileName == null || "".equals(zipFileName)) {
			LOGGER.info("压缩名：{}为空", zipFileName);
			return false;
		}

		if (!filePath.endsWith("/")) {
			filePath = new StringBuffer(filePath).append("/").toString();
		}

		zipFileName = zipFileName + zipSuffix;
		boolean result = doZipCompress(filePath, zipFileName, files);

		return result;

	}

	/**
	 * 
	 * @Description 执行压缩操作
	 * @Datetime 2019年12月12日 下午3:31:30<br/>
	 * @param destinationZipFilePath 压缩包所在相对于类的全路径 <br/>
	 *                               例如：activiti/processfile/helloworld/Helloworld.zip
	 * @param destinationZipFileName 需要被压缩的文件、压缩包名
	 * @param files                  需要被压缩的文件集合
	 * @return
	 */
	private static boolean doZipCompress(String destinationZipFilePath, String destinationZipFileName,
			List<String> filesList) {

		boolean result = false;

		if (null == filesList || filesList.size() == 0) {
			LOGGER.info("需要被压缩的文件数量为空");
			return result;
		}

		String classPath = null;
		try {
			classPath = new ClassPathResource(destinationZipFilePath).getFile().getAbsolutePath();
			LOGGER.info("操作文件的全路径是：{}", classPath);

			if (null == classPath || "".equals(classPath)) {

				LOGGER.info("获取类路径异常");

				return result;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String zipFilePathName = classPath + File.separator + destinationZipFileName;
		LOGGER.info("目标压缩包全路径为：{}", zipFilePathName);
		File zipFile = new File(zipFilePathName);

		if (!zipFile.exists()) {
			LOGGER.info("目标压缩包不存在，将会被创建：{}", zipFilePathName);
			boolean createNewFile;
			try {
				createNewFile = zipFile.createNewFile();
				if (createNewFile) {
					LOGGER.info("目标压缩包不存在被创建的结果：{}", createNewFile);

				}
			} catch (IOException e) {
				LOGGER.info("创建压缩包文件失败：{}", e.getMessage());
				e.printStackTrace();
			}
		}

		// 压缩目的地
		OutputStream out = null;
		ZipOutputStream zipOutputStream = null;
		try {
			out = new FileOutputStream(zipFile);
			zipOutputStream = new ZipOutputStream(out);
			// 把文件压缩到destinationPath
			for (String file : filesList) {

				String realFile = classPath +File.separator+ file;

				InputStream inputStream = new FileInputStream(new File(realFile));

				// 开启单个压缩
				ZipEntry zipEntry = new ZipEntry(file);
				zipOutputStream.putNextEntry(zipEntry);
				int len = 0;
				byte[] b = new byte[1024];
				while ((len = inputStream.read(b)) > 0) {

					zipOutputStream.write(b, 0, len);
				}

				inputStream.close();

				result = true;

			}
		} catch (Exception e) {
			LOGGER.info("文件读取异常：{}", e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				if (zipOutputStream != null) {

					zipOutputStream.closeEntry();
					zipOutputStream.close();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
