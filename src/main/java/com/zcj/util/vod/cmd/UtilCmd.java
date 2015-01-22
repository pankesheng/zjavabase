package com.zcj.util.vod.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UtilCmd {

	private static void printCmd(List<String> cmds) {
		for (String cmd : cmds) {
			System.out.print(cmd + " ");
		}
		System.out.println();
	}
	
	public static void exec(List<String> cmd, String code) {
		BufferedReader stdout = null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			printCmd(cmd);
			Process proc = builder.start();
			stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = stdout.readLine()) != null) {
				if (code != null) {
					code = line;
				}
			}
			proc.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stdout) {
				try {
					stdout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
