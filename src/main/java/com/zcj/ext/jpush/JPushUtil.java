package com.zcj.ext.jpush;

import java.util.Set;

import org.apache.log4j.Logger;

import com.zcj.util.UtilString;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

//<dependency>
//	<groupId>cn.jpush.api</groupId>
//	<artifactId>jpush-client</artifactId>
//	<version>3.2.5</version>
//</dependency>
public class JPushUtil {

	private static Logger logger = Logger.getLogger(JPushUtil.class);

	private static JPushUtil jPushUtil;

	private JPushClient jPushClient;

	private JPushUtil() {
	}

	public static synchronized JPushUtil getInstance(String appKey, String masterSecret) {
		if (jPushUtil == null || jPushUtil.jPushClient == null) {
			jPushUtil = new JPushUtil();
			jPushUtil.jPushClient = new JPushClient(masterSecret, appKey, 3);
		}
		return jPushUtil;
	}

	public void sendToAll(String content) {
		if (UtilString.isBlank(content)) {
			return;
		}
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.all())
				.setNotification(
						Notification.newBuilder().setAlert(content)
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build()).build();
		try {
			PushResult result = jPushClient.sendPush(payload);
			logger.debug("JPush Result:" + result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	public void sendToUser(String userId, String content) {
		if (UtilString.isBlank(content) || UtilString.isBlank(userId)) {
			return;
		}
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(userId))
				.setNotification(
						Notification.newBuilder().setAlert(content)
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build()).build();

		try {
			PushResult result = jPushClient.sendPush(payload);
			logger.debug("JPush Result:" + result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	public void sendToUser(Set<String> userIDs, String content) {
		if (UtilString.isBlank(content) || userIDs == null || userIDs.size() == 0) {
			return;
		}
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(userIDs))
				.setNotification(
						Notification.newBuilder().setAlert(content)
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build()).build();

		try {
			PushResult result = jPushClient.sendPush(payload);
			logger.debug("JPush Result:" + result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

}
