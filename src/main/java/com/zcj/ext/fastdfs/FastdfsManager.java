package com.zcj.ext.fastdfs;

import java.net.InetSocketAddress;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

import com.zcj.util.filenameutils.FilenameUtils;

/**
 * FastDFS客户端
 * 
 * @author zouchongjin@sina.com
 * @data 2016年1月15日
 */
public class FastdfsManager {

	private static FastdfsManager singleton;

	private final String charset = "UTF-8";
	private final int trackerHttpPort = 80;
	private final boolean antiStealToken = false;
	private final String secretKey = null;
	private String[] szTrackerServers = null;

	private TrackerClient tracker = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;

	private FastdfsManager() {

	}

	public static synchronized FastdfsManager getInstance(String[] szTrackerServers) {
		if (singleton == null) {
			singleton = new FastdfsManager();
			singleton.setSzTrackerServers(szTrackerServers);
		}
		return singleton;
	}

	/** 上传文件（根据文件字节码上传） */
	public String uploadFile(byte[] file_buff, String suffix, Map<String, Object> param) throws Exception {
		init();
		NameValuePair nvp[] = null;
		if (param != null && param.size() > 0) {
			nvp = new NameValuePair[param.size()];
			int i = 0;
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				nvp[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return storageClient.upload_file1(file_buff, suffix, nvp);
	}

	/** 上传文件（根据文件存储的绝对路径上传） */
	public String uploadFile(String filePath, Map<String, Object> param) throws Exception {
		init();
		NameValuePair nvp[] = null;
		if (param != null && param.size() > 0) {
			nvp = new NameValuePair[param.size()];
			int i = 0;
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				nvp[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return storageClient.upload_file1(filePath, FilenameUtils.getExtension(filePath), nvp);
	}

	/** 下载文件 */
	public byte[] downloadFile(String fileId) throws Exception {
		init();
		return storageClient.download_file1(fileId);
	}

	/** 删除文件 */
	public boolean deleteFile(String fileId) throws Exception {
		init();
		int i = storageClient.delete_file1(fileId);
		return i == 0;
	}

	/** 获取文件参数 */
	public FastdfsFileInfo getFileInfo(String fileId) throws Exception {
		init();
		FileInfo fInfo = storageClient.get_file_info1(fileId);
		NameValuePair nvps[] = storageClient.get_metadata1(fileId);
		return new FastdfsFileInfo(fInfo, nvps);
	}

	private void init() throws Exception {
		if (storageClient == null) {
			initClientGlobal();
			tracker = new TrackerClient();
			trackerServer = tracker.getConnection();
			storageClient = new StorageClient1(trackerServer, storageServer);
		}
	}

	private void initClientGlobal() throws Exception {
		if (szTrackerServers == null) {
			throw new Exception("the value of item \"tracker_server\" is invalid, the correct format is host:port");
		}
		String[] parts;
		ClientGlobal.setG_connect_timeout(ClientGlobal.DEFAULT_CONNECT_TIMEOUT * 1000);
		ClientGlobal.setG_network_timeout(ClientGlobal.DEFAULT_NETWORK_TIMEOUT * 1000);
		ClientGlobal.setG_charset(charset);
		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
		for (int i = 0; i < szTrackerServers.length; i++) {
			parts = szTrackerServers[i].split("\\:", 2);
			if (parts.length != 2) {
				throw new Exception("the value of item \"tracker_server\" is invalid, the correct format is host:port");
			}
			tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
		}
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
		ClientGlobal.setG_tracker_http_port(trackerHttpPort);
		ClientGlobal.setG_anti_steal_token(antiStealToken);
		if (ClientGlobal.getG_anti_steal_token()) {
			ClientGlobal.setG_secret_key(secretKey);
		}
	}

	public String[] getSzTrackerServers() {
		return szTrackerServers;
	}

	public void setSzTrackerServers(String[] szTrackerServers) {
		this.szTrackerServers = szTrackerServers;
	}

}
