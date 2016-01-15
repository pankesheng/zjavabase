package test.zcj.ext.fastdfs;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.zcj.ext.fastdfs.FastdfsFileInfo;
import com.zcj.ext.fastdfs.FastdfsManager;

public class TestFastDfs {

	public static final String[] szTrackerServers = { "192.168.1.111:22122" };

	public String local_filename = "F:\\111\\wallpaper-2836824.png";

	@Test
	public void testUpload() throws Exception {
		Map<String, Object> nvp = new HashMap<String, Object>();
		nvp.put("t1", 1);
		nvp.put("te1", "AAA");
		String fileId = FastdfsManager.getInstance(szTrackerServers).uploadFile(local_filename, null);
		System.out.println("路径: " + fileId);
	}

	@Test
	public void testDownload() throws Exception {
		byte[] b = FastdfsManager.getInstance(szTrackerServers).downloadFile("group1/M00/00/00/wKgBb1aYrySAROP8AAB6p_aLn_o054.png");
		IOUtils.write(b, new FileOutputStream("D:/" + UUID.randomUUID().toString() + ".png"));
	}

	@Test
	public void testGetFileInfo() throws Exception {
		FastdfsFileInfo fi = FastdfsManager.getInstance(szTrackerServers)
				.getFileInfo("group1/M00/00/00/wKgBb1aXZPSALQhBAAB6p_aLn_o169.png");
		System.out.println(fi);
	}

	@Test
	public void testDelete() throws Exception {
		FastdfsManager.getInstance(szTrackerServers).deleteFile("group1/M00/00/00/wKgBb1aXZPSALQhBAAB6p_aLn_o169.png");
	}
}