package test.zcj.ext.jpush;

import com.zcj.ext.jpush.JPushUtil;
import com.zcj.web.context.SystemContext;

public class JpushTest {

	private static final String appKey = "7b36e02dd169049532922aba";
	private static final String masterSecret = "b5b14617a4832e6d44cc6ddb2";

	private static void sendToUser() {
		SystemContext.getExecutorService().submit(new Runnable() {
			@Override
			public void run() {
				JPushUtil.getInstance(appKey, masterSecret).sendToUser("14325434", "测试一下");
			}
		});
	}

	public static void main(String[] args) {
		sendToUser();
	}
}
