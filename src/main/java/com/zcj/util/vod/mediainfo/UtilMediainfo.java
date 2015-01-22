package com.zcj.util.vod.mediainfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.lang3.StringUtils;

public class UtilMediainfo {

	public static MediainfoBean parser(final String softExePath, final String filePath) {
		return parser(softExePath, filePath, false);
	}
	
	public static MediainfoBean parser(final String softExePath, final String filePath, final boolean printLog) {
		if (StringUtils.isBlank(softExePath) || StringUtils.isBlank(filePath)) {
			return null;
		}
		String cmd = softExePath + " " + filePath;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != process) {
			return parser(process.getInputStream(), printLog);
		} else {
			return null;
		}
	}

	private static MediainfoBean parser(final InputStream stream, boolean printLog) {
		LineNumberReader in = null;
		MediainfoBean mediaInfo = new MediainfoBean();
		try {
			in = new LineNumberReader(new InputStreamReader(stream));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (printLog) {
					System.out.println(line);
				}
				mediaInfo.parse(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mediaInfo;
	}
	
}
