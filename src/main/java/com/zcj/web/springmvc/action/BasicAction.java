package com.zcj.web.springmvc.action;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zcj.ext.fastdfs.FastdfsManager;
import com.zcj.util.UtilDate;
import com.zcj.util.UtilString;
import com.zcj.util.filenameutils.FilenameUtils;
import com.zcj.web.dto.Page;
import com.zcj.web.dto.ServiceResult;
import com.zcj.web.dto.UploadErrorResult;
import com.zcj.web.dto.UploadResult;
import com.zcj.web.dto.UploadSuccessResult;

public class BasicAction {

	protected Page page = new Page();

	protected String RESULT_PAR_ERROR = "404";// 参数错误的返回值

	// 处理参数中的日期格式
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	private class DateEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			Date date = UtilDate.format(text);
			setValue(date);
		}
	}

	/**
	 * 上传附件
	 * 
	 * @param request
	 * @param type
	 *            文件夹名称，支持“-”连接的多级文件夹结构
	 * @param basePath
	 *            文件保存的物理根路径
	 * @return ServiceResult对象。
	 *         <p>
	 *         操作失败：s=0；d=失败原因
	 *         <p>
	 *         操作成功（单文件上传）：s=1；d=UploadSuccessResult对象
	 *         <p>
	 *         操作成功（多文件上传）：s=1；d=UploadResult对象
	 */
	protected ServiceResult uploadFile(HttpServletRequest request, String type, String basePath) {
		if (!(request instanceof MultipartHttpServletRequest)) {
			return ServiceResult.initError("请设置正确的上传方式");
		}

		MultipartHttpServletRequest rm = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> mfs = rm.getFileMap();
		if (mfs == null || mfs.size() == 0) {
			return ServiceResult.initError("请确定上传的内容中包含文件域");
		}

		int fileCount = 0;
		for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				fileCount++;
			}
		}

		if (fileCount == 0) {
			return ServiceResult.initError("请选择文件后上传");
		}

		if (StringUtils.isBlank(type)) {
			return ServiceResult.initError("请选择存储文件的文件夹");
		}

		if (!basePath.endsWith(File.separator)) {
			basePath += File.separator;
		}
		String absolutePath = basePath + type.replace("-", File.separator) + File.separator;
		File dir = new File(absolutePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		if (fileCount == 1) {
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					String newFilename = UtilString.getSoleCode() + "." + suffix;// 保存后的文件名
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absolutePath + newFilename));
						return ServiceResult.initSuccess(new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file
								.getContentType(), entry.getKey(), suffix, newFilename, "/" + type.replace("-", "/") + "/" + newFilename));
					} catch (Exception e) {
						e.printStackTrace();
						return ServiceResult.initError("文件写入出错");
					}
				}
			}
			return ServiceResult.initError("读取文件失败");
		} else {
			UploadResult uploadResult = new UploadResult();
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					String newFilename = UtilString.getSoleCode() + "." + suffix;// 保存后的文件名
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absolutePath + newFilename));
						uploadResult.getSuccess().add(
								(new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file.getContentType(), entry.getKey(),
										suffix, newFilename, "/" + type.replace("-", "/") + "/" + newFilename)));
					} catch (Exception e) {
						e.printStackTrace();
						uploadResult.getError().add(
								new UploadErrorResult(file.getOriginalFilename(), file.getSize(), entry.getKey(), file.getContentType(),
										FilenameUtils.getExtension(file.getOriginalFilename()), "文件写入出错"));
					}
				}
			}
			return ServiceResult.initSuccess(uploadResult);
		}
	}

	/**
	 * 通过FastDFS上传附件</br> 依赖：fastdfs-client-java-20141207.jar</br>
	 * 
	 * @param request
	 * @param szTrackerServers
	 *            FastDFS服务器地址及端口</br>
	 *            例：{"192.168.1.111:22122","192.168.1.119:22122"}
	 * @param param
	 *            上传附件时附带的参数
	 * @return ServiceResult对象。
	 *         <p>
	 *         操作失败：s=0；d=失败原因
	 *         <p>
	 *         操作成功（单文件上传）：s=1；d=UploadSuccessResult对象
	 *         <p>
	 *         操作成功（多文件上传）：s=1；d=UploadResult对象
	 */
	protected ServiceResult uploadFileFastDFS(HttpServletRequest request, String[] szTrackerServers, Map<String, Object> param) {
		return uploadFileFastDFS(request, szTrackerServers, param, null);
	}

	/**
	 * 通过FastDFS上传附件，并指定Group</br> 依赖：fastdfs-client-java-20141207.jar</br>
	 * 
	 * @param request
	 * @param szTrackerServers
	 *            FastDFS服务器地址及端口</br>
	 *            例：{"192.168.1.111:22122","192.168.1.119:22122"}
	 * @param param
	 *            上传附件时附带的参数
	 * @param group
	 *            FastDFS的group名
	 * @return ServiceResult对象。
	 *         <p>
	 *         操作失败：s=0；d=失败原因
	 *         <p>
	 *         操作成功（单文件上传）：s=1；d=UploadSuccessResult对象
	 *         <p>
	 *         操作成功（多文件上传）：s=1；d=UploadResult对象
	 */
	protected ServiceResult uploadFileFastDFS(HttpServletRequest request, String[] szTrackerServers, Map<String, Object> param, String group) {
		if (!(request instanceof MultipartHttpServletRequest)) {
			return ServiceResult.initError("请设置正确的上传方式");
		}
		MultipartHttpServletRequest rm = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> mfs = rm.getFileMap();
		if (mfs == null || mfs.size() == 0) {
			return ServiceResult.initError("请确定上传的内容中包含文件域");
		}

		int fileCount = 0;
		for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				fileCount++;
			}
		}

		if (fileCount == 0) {
			return ServiceResult.initError("请选择文件后上传");
		}

		if (fileCount == 1) {
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					try {
						String fileId = FastdfsManager.getInstance(szTrackerServers).uploadFile(file.getBytes(), suffix, param, group);
						return ServiceResult.initSuccess(new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file
								.getContentType(), entry.getKey(), suffix, FilenameUtils.getName(fileId), fileId));
					} catch (Exception e) {
						e.printStackTrace();
						return ServiceResult.initError("文件上传失败");
					}
				}
			}
			return ServiceResult.initError("读取文件失败");
		} else {
			UploadResult uploadResult = new UploadResult();
			for (Map.Entry<String, MultipartFile> entry : mfs.entrySet()) {
				MultipartFile file = entry.getValue();
				if (!file.isEmpty()) {
					String suffix = FilenameUtils.getExtension(file.getOriginalFilename());// 文件后缀
					try {
						String fileId = FastdfsManager.getInstance(szTrackerServers).uploadFile(file.getBytes(), suffix, param, group);
						uploadResult.getSuccess().add(
								(new UploadSuccessResult(file.getOriginalFilename(), file.getSize(), file.getContentType(), entry.getKey(),
										suffix, FilenameUtils.getName(fileId), fileId)));
					} catch (Exception e) {
						e.printStackTrace();
						uploadResult.getError().add(
								new UploadErrorResult(file.getOriginalFilename(), file.getSize(), entry.getKey(), file.getContentType(),
										FilenameUtils.getExtension(file.getOriginalFilename()), "文件写入出错"));
					}
				}
			}
			return ServiceResult.initSuccess(uploadResult);
		}
	}

	protected Map<String, Object> initQbuilder(String key, Object value) {
		Map<String, Object> query = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(key) && value != null) {
			query.put(key, value);
		}
		return query;
	}

	protected Map<String, Object> initQbuilder(String[] keys, Object[] values) {
		Map<String, Object> query = new HashMap<String, Object>();
		if (keys != null && keys.length > 0 && values != null && values.length > 0 && keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isNotBlank(keys[i]) && values[i] != null) {
					query.put(keys[i], values[i]);
				}
			}
		}
		return query;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}