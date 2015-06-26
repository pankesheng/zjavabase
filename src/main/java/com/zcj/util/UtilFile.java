package com.zcj.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zcj.util.filenameutils.FilenameUtils;

public class UtilFile {

	/** 图片文件类型数组 */
	public static final String[] imageFileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	/** 视频文件类型数组 */
	public static final String[] videoFileType = { ".swf", ".wmv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogg", ".mov", ".wmv", ".mp4" };
	/** 附件类型数组 */
	public static final String[] fileType = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".avi", ".rm", ".rmvb",
			".mpeg", ".mpg", ".ogg", ".mov", ".wmv", ".mp4" };

	/**
	 * 截图
	 * 
	 * @param srcPath
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 */
	public static void cut(String srcPath, int startX, int startY, int width, int height) {
		try {
			BufferedImage bufImg = ImageIO.read(new File(srcPath));
			BufferedImage subImg = bufImg.getSubimage(startX, startY, width, height);
			ImageIO.write(subImg, FilenameUtils.getExtension(srcPath), new File(srcPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按最大长宽压缩，不变形，补白
	 * 
	 * @param fi
	 * @param w
	 * @param h
	 */
	public static void doCompressByWhite(String filePath, int width, int height) {
		Image src;
		try {
			src = javax.imageio.ImageIO.read(new File(filePath)); // 构造Image对象

			int old_w = src.getWidth(null); // 得到源图宽
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0; // 得到源图长

			double w2 = (old_w * 1.00) / (width * 1.00);
			double h2 = (old_h * 1.00) / (height * 1.00);

			// 图片跟据长宽留白，成一个正方形图。
			BufferedImage oldpic;
			if (old_w > old_h) {
				oldpic = new BufferedImage(old_w, old_w, BufferedImage.TYPE_INT_RGB);
			} else {
				if (old_w < old_h) {
					oldpic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
				} else {
					oldpic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
				}
			}
			Graphics2D g = oldpic.createGraphics();
			g.setColor(Color.white);
			if (old_w > old_h) {
				g.fillRect(0, 0, old_w, old_w);
				g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
			} else {
				if (old_w < old_h) {
					g.fillRect(0, 0, old_h, old_h);
					g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
				} else {
					// g.fillRect(0,0,old_h,old_h);
					g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
				}
			}
			g.dispose();
			src = oldpic;
			// 图片调整为方形结束
			if (old_w > width)
				new_w = (int) Math.round(old_w / w2);
			else
				new_w = old_w;
			if (old_h > height)
				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
			else
				new_h = old_h;
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
			FileOutputStream newimage = new FileOutputStream(filePath); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/* 压缩质量 */
			jep.setQuality(1, true);
			encoder.encode(tag, jep);
			// encoder.encode(tag); //近JPEG编码
			newimage.close();
		} catch (IOException ex) {

		}
	}

	/**
	 * 压缩图片方法
	 * 
	 * @deprecated
	 * 
	 * @param oldFile
	 *            将要压缩的图片
	 * @param width
	 *            压缩宽
	 * @param height
	 *            压缩高
	 * @param quality
	 *            压缩清晰度 <b>建议为1.0</b>
	 * @param smallIcon
	 *            压缩图片后,添加的扩展名（在图片后缀名前添加）
	 * @param percentage
	 *            是否等比压缩 若true宽高比率将将自动调整
	 * @return 如果处理正确返回压缩后的文件名 null则参数可能有误
	 */
	public static String doCompress(String oldFile, int width, int height, float quality, String smallIcon, boolean percentage) {
		if (oldFile != null && width > 0 && height > 0) {
			Image srcFile = null;
			String newImage = null;
			try {
				File file = new File(oldFile);
				// 文件不存在
				if (!file.exists()) {
					return null;
				}
				/* 读取图片信息 */
				srcFile = ImageIO.read(file);
				int new_w = width;
				int new_h = height;
				if (percentage) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
					double rate = rate1 > rate2 ? rate1 : rate2;
					new_w = (int) (((double) srcFile.getWidth(null)) / rate);
					new_h = (int) (((double) srcFile.getHeight(null)) / rate);
				}
				/* 宽高设定 */
				BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
				/* 压缩后的文件名 */
				String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
				newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
				/* 压缩之后临时存放位置 */
				FileOutputStream out = new FileOutputStream(newImage);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/* 压缩质量 */
				jep.setQuality(quality, true);
				encoder.encode(tag, jep);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				srcFile.flush();
			}
			return newImage;
		} else {
			return null;
		}
	}

	// UtilFile.doCompress("D:/JAVA/Software/eclipse/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pwork/upload/zkgl/20130910154224163060.jpg",
	// 200, 200, 1, "", false);

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @param imageFileTypes
	 * @return
	 */
	private static boolean checkFileType(String fileName, String[] imageFileTypes) {
		if (imageFileTypes == null) {
			return true;
		}
		Iterator<String> type = Arrays.asList(imageFileTypes).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 依据原始文件名生成新文件名
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getName(String fileName) {
		return UtilString.getSoleCode() + "." + FilenameUtils.getExtension(fileName);
	}

	private static void persistence(InputStream in, String fileName, String absoluteCatalog) {
		File file = new File(absoluteCatalog);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream out = null;
		FileChannel fout = null;
		try {
			out = new FileOutputStream(absoluteCatalog + fileName);
			fout = out.getChannel();
			byte[] buffers = new byte[1024 * 4];
			int readLen = -1;
			while ((readLen = in.read(buffers)) != -1) {
				ByteBuffer buffer = ByteBuffer.wrap(buffers, 0, readLen);
				fout.write(buffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件写入出错");
		} finally {
			if (null != fout) {
				try {
					fout.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 下载图片（下载到项目运行环境的硬盘里）
	 * 
	 * @param imgUrl
	 *            图片地址，如http://www.baidu.com/a.jpg
	 * @param path
	 *            保存的地址，如C:\\ABC.jpg
	 * @throws IOException
	 */
	public static void download(String imgUrl, String path) throws IOException {
		File file = new File(path);
		URL url = new URL(imgUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		DataInputStream in = new DataInputStream(conn.getInputStream());
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		byte[] buffer = new byte[1024 * 4];
		int num = 0;
		while ((num = in.read(buffer)) != -1) {
			out.write(buffer, 0, num);
		}
		out.flush();
		out.close();
		in.close();
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param inputFileName
	 *            源文件夹目录 如D:\\TEMP\\ABC
	 * @param zipFileName
	 *            保存压缩文件的目录 如C:\\ABC.zip
	 * @throws Exception
	 */
	public static void zip(String inputFileName, String zipFileName) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		File f = new File(inputFileName);
		zip(out, f, FilenameUtils.getBaseName(zipFileName));
		out.close();
	}

	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/**
	 * 删除文件夹或文件
	 * 
	 * @param sPath
	 *            绝对路径
	 * @return
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (!file.exists()) {
			return flag;
		} else {
			if (file.isFile()) {
				return deleteFile(sPath);
			} else {
				return deleteDirectory(sPath);
			}
		}
	}

	private static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static File getFileByBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}
	
	public static Map<String, String> MIMETYPE_MAP = new HashMap<String, String>();

	static {
		MIMETYPE_MAP.put("doc", "application/msword");
		MIMETYPE_MAP.put("js", "application/x-javascript");
		MIMETYPE_MAP.put("css", "text/css");
		MIMETYPE_MAP.put("png", "image/png");
		MIMETYPE_MAP.put("apk", "application/vnd.android.package-archive");
	}

	/**
	 * 获取文件的MIME类型
	 * @param filename
	 * 			带后缀的文件名。支持：foo.txt、a/b/c.jpg等格式
	 * @return
	 */
	public static String getMimeType(String filename) {
		if (UtilString.isNotBlank(filename)) {
			String ext = FilenameUtils.getExtension(filename);
			if (UtilString.isNotBlank(ext)) {
				ext = ext.toLowerCase();
				return MIMETYPE_MAP.get(ext);
			}
		}
		return null;
	}
	
	/**
	 * 百度编辑器远程抓取图片到本地<br/>
	 * window.UEDITOR_CONFIG.catchRemoteImageEnable = true;// 是否开启远程图片抓取,默认开启<br/>
	 * window.UEDITOR_CONFIG.catcherUrl =
	 * "http://localhost:8080/touch/0204?type=news_imgs";<br/>
	 * window.UEDITOR_CONFIG.catcherPath = "http://localhost:8080/touch/";<br/>
	 * 
	 * @param request
	 * @param absoluteBasePath
	 *            例：D:\\JAVA\\apache-tomcat-6.0.35\\webapps\\projectName\\
	 * @param catalog
	 *            例：upload\\s_logo\\
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static String uploadRemoteImage(HttpServletRequest request, String absoluteBasePath, String catalog) throws Exception {
		request.setCharacterEncoding("utf-8");
		String url = request.getParameter("upfile");
		String state = "远程图片抓取成功！";

		String[] arr = url.split("ue_separate_ue");
		String[] outSrc = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {

			// 保存文件路径
			String savePath = absoluteBasePath + catalog;
			// 格式验证
			if (!checkFileType(arr[i], imageFileType)) {
				state = "图片类型不正确！";
				continue;
			}
			String saveName = getName(arr[i]);
			// 大小验证
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection conn = (HttpURLConnection) new URL(arr[i]).openConnection();
			if (conn.getContentType().indexOf("image") == -1) {
				state = "请求地址头不正确";
				continue;
			}
			if (conn.getResponseCode() != 200) {
				state = "请求地址不存在！";
				continue;
			}
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File savetoFile = new File(savePath + saveName);
			outSrc[i] = catalog.replaceAll("\\\\", "/") + saveName;

			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(savetoFile);
			int b;
			while ((b = is.read()) != -1) {
				os.write(b);
			}
			os.close();
			is.close();
		}
		String outstr = "";
		for (int i = 0; i < outSrc.length; i++) {
			outstr += outSrc[i] + "ue_separate_ue";
		}
		outstr = outstr.substring(0, outstr.lastIndexOf("ue_separate_ue"));
		String result = "{'url':'" + outstr + "','tip':'" + state + "','srcUrl':'" + url + "'}";
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param absoluteBasePath
	 *            例：D:\\JAVA\\apache-tomcat-6.0.35\\webapps\\projectName\\
	 * @param catalog
	 *            例：upload\\s_logo\\
	 * @return
	 */
	@Deprecated
	public static UploadResult upload(HttpServletRequest request, String absoluteBasePath, String catalog, String[] fileType) {
		String originalName = "";// 原始文件名
		String fileName = "";// 保存后的文件名
		String url;// 相对项目的地址

		try {
			request.setCharacterEncoding("utf-8");
			MultiPartRequestWrapper mulRequest = (MultiPartRequestWrapper) request;
			if (ServletFileUpload.isMultipartContent(mulRequest)) {
				Enumeration<String> en = mulRequest.getFileParameterNames();
				String absoluteCatalog = absoluteBasePath + catalog;
				while (en.hasMoreElements()) {
					String tfileName = en.nextElement();
					int i = 0;
					for (String originalName2 : mulRequest.getFileNames(tfileName)) {
						originalName = originalName2;
						if (!UtilFile.checkFileType(originalName, fileType)) {
							return UploadResult.initError("不允许的文件格式");
						}
						File file = mulRequest.getFiles(tfileName)[i];
						FileInputStream in = new FileInputStream(file);
						fileName = UtilFile.getName(originalName);
						UtilFile.persistence(in, fileName, absoluteCatalog);
						i++;
					}
				}

				url = "/" + catalog.replaceAll("\\\\", "/") + fileName;
				return UploadResult.initSuccess(new SuccessResult(url, originalName, "SUCCESS", "."
						+ FilenameUtils.getExtension(originalName)));
			} else {
				return UploadResult.initError("未包含文件上传域");
			}
		} catch (Exception e) {
			return UploadResult.initError("文件上传失败");
		}
	}

	@Deprecated
	public static class SuccessResult {
		private String url;
		private String original;
		private String state;
		private String fileType;// 文件后缀名,包含.

		public SuccessResult() {
			super();
		}

		public SuccessResult(String url, String originalName, String state, String fileType) {
			super();
			this.url = url;
			this.original = originalName;
			this.state = state;
			this.fileType = fileType;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getOriginal() {
			return original;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public void setOriginal(String original) {
			this.original = original;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	@Deprecated
	public static class UploadResult {

		private static final int S_SUCCESS = 1;// 成功标识
		private static final int S_ERROR = 0;// 失败标识
		private int s;
		private Object d;

		public static UploadResult initSuccess(Object d) {
			return new UploadResult(S_SUCCESS, d);
		}

		public static UploadResult initError(String d) {
			return new UploadResult(S_ERROR, d);
		}

		private UploadResult() {
		}

		private UploadResult(int s, Object d) {
			this.s = s;
			this.d = d;
		}

		public int getS() {
			return s;
		}

		public void setS(int s) {
			this.s = s;
		}

		public Object getD() {
			return d;
		}

		public void setD(Object d) {
			this.d = d;
		}
	}


}
