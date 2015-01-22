package com.zcj.util.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PdfToSwf {

	private static String getFilePath(String file) {
		String result = file.substring(0, file.lastIndexOf("/"));
		if (file.substring(2, 3) == "/") {
			result = file.substring(0, file.lastIndexOf("/"));
		} else if (file.substring(2, 3) == "\\") {
			result = file.substring(0, file.lastIndexOf("\\"));
		}
		return result;
	}

	private static void newFolder(String folderPath) {
		try {
			File myFolderPath = new File(folderPath.toString());
			if (!myFolderPath.exists()) {
				myFolderPath.mkdir();
			}
		} catch (Exception e) {
			throw new RuntimeException("转swf失败：新建目录"+folderPath+"失败");
		}
	}

	/**
	 * pdf转swf
	 * @param swfToolsPath
	 * 			swftools软件的安装目录，如：C:/Program Files (x86)/SWFTools
	 * @param sourcePath
	 * 			源文件的目录，如：D:/4444.pdf 
	 * @param destPath
	 * 			目标文件的目录，如：D:/4444.swf
	 * @return
	 * @throws IOException
	 */
	public static boolean pdf2swf(String swfToolsPath, String sourcePath, String destPath) throws IOException {
		
		// 如果目标文件的路径是新的，则新建路径
		newFolder(getFilePath(destPath));

		// 源文件不存在
		File source = new File(sourcePath);
		if (!source.exists()) {
			throw new RuntimeException("转swf失败：源文件"+sourcePath+"不存在");
		}

		// 调用pdf2swf命令进行转换
		String command = swfToolsPath + "/pdf2swf.exe  -t \"" + sourcePath + "\" -o  \"" + destPath + "\" -s flashversion=9 -s languagedir=D:\\xpdf\\xpdf-chinese-simplified ";
		System.out.println("命令操作:" + command + "开始转换...");
		
		// 调用外部程序
		Process process = Runtime.getRuntime().exec(command);
		final InputStream is1 = process.getInputStream();
		new Thread(new Runnable() {
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(is1));
				try {
					while (br.readLine() != null)
						;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
		InputStream is2 = process.getErrorStream();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
		// 保存输出结果流
		StringBuilder buf = new StringBuilder();
		String line = null;
		while ((line = br2.readLine()) != null)
			// 循环等待ffmpeg进程结束
			buf.append(line);
		while (br2.readLine() != null)
			;
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("转换结束...");
		return process.exitValue() == 1 ? true : false;
	}

}

