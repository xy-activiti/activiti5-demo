package com.xywei.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.xywei.activiti.utils.ZipUtils;

public class TestFile {

	@Test
	public void testReadFile() {

		InputStream inputstream = ClassLoader.getSystemClassLoader()
				.getSystemResourceAsStream("activiti/config/activiti.cfg.xml");
		System.out.println(inputstream == null);
		int len = 0;
		try {
			len = inputstream.available();
			byte[] b = new byte[len];
			inputstream.read(b);
			System.out.println("====\n" + new String(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteFile() {

		Resource resource = new ClassPathResource("activiti/config");
		try {
			File file = resource.getFile();
			System.out.println(file.exists());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Description 测试压缩文件代码
	 * @Datetime 2019年12月11日 下午7:25:49<br/>
	 */
	@Test
	public void testZipFile() {

		// 压缩目的地 helloworld.zip，如果不存在就先创建
		File zipfile = new File("E:\\IT\\OA-activiti\\helloworld.zip");
		if (!zipfile.exists()) {
			try {
				zipfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		OutputStream out = null;
		ZipOutputStream zipOutputStream = null;
		try {
			out = new FileOutputStream(zipfile);
			zipOutputStream = new ZipOutputStream(out);

			String file1 = "E:\\IT\\OA-activiti\\Helloworld.bpmn";
			// 压缩名
			ZipEntry zipEntry = new ZipEntry("Helloworld.bpmn");

			zipOutputStream.putNextEntry(zipEntry);

			InputStream inputStream = new FileInputStream(file1);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) > 0) {
				zipOutputStream.write(buffer, 0, len);
			}
			inputStream.close();
			zipOutputStream.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 *@Description 测试压缩类路径下activiti/processfile/helloworld/目录的文件
	 *@Datetime 2019年12月12日 下午4:16:52<br/>
	 */
	@Test
	public void compressHelloworldProcessFile() {

		String filePath = "activiti/processfile/helloworld/";
		String bpmnFile = "Helloworld.bpmn";
		String pngFile = "Helloworld.png";
		List<String> processFiles = new ArrayList<String>();
		processFiles.add(bpmnFile);
		processFiles.add(pngFile);

		String zipFileName = "helloworld流程";

		boolean compressFileResult = false;
		try {
			compressFileResult = ZipUtils.compressFileAsZip(filePath, zipFileName, processFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("压缩结果：" + compressFileResult);
	}

	@Test
	public void testClassPath() {
///E:/workspace-sts-3.9.9.RELEASE/activiti5-demo/target/test-classes/
//		String path = this.getClass().getResource("/").getPath();
		String path = this.getClass().getResource("").getPath();
//		String path = ClassLoader.getSystemClassLoader().getResource("/").getPath();
		System.out.println(path);

	}

}
