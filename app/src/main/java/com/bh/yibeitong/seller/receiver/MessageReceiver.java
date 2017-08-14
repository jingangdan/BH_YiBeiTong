package com.bh.yibeitong.seller.receiver;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

//public class MessageReceiver extends XGPushBaseReceiver {
//	private Intent intent = new Intent("com.bh.yibeitong.seller.activity.SOrderManageActivity");
//	public static final String LogTag = "TPushReceiver";
//
//	private void show(Context context, String text) {
////		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//	}
//
//	@Override
//	public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
//
//	}
//
//	@Override
//	public void onUnregisterResult(Context context, int i) {
//
//	}
//
//	@Override
//	public void onSetTagResult(Context context, int i, String s) {
//
//	}
//
//	@Override
//	public void onDeleteTagResult(Context context, int i, String s) {
//
//	}
//
//	@Override
//	public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
//
//	}
//
//	@Override
//	public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
//
//
//	}
//
//	@Override
//	public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
//
//		if (context == null || notifiShowedRlt == null) {
//			return;
//		}
//
//		XGNotification notific = new XGNotification();
//		notific.setMsg_id(notifiShowedRlt.getMsgId());
//		notific.setTitle(notifiShowedRlt.getTitle());
//		notific.setContent(notifiShowedRlt.getContent());
//		// notificationActionType==1为Activity，2为url，3为intent
//		notific.setNotificationActionType(notifiShowedRlt
//				.getNotificationActionType());
//		// Activity,url,intent都可以通过getActivity()获得
//		notific.setActivity(notifiShowedRlt.getActivity());
//		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.format(Calendar.getInstance().getTime()));
//
//		NotificationService.getInstance(context).save(notific);
//		context.sendBroadcast(intent);
//		show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
//
//		System.out.println("通知展示"+notifiShowedRlt.toString());
//
//		/*XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
//		build.setSound(
//				RingtoneManager.getActualDefaultRingtoneUri(
//						context, RingtoneManager.TYPE_ALARM)) // 设置声音
//				.setSound(
//						Uri.parse("android.resource://" + context.getPackageName()
//								+ "/" + R.raw.aidemo)) //设定Raw下指定声音文件
//				.setDefaults(Notification.DEFAULT_VIBRATE) // 振动
//				.setDefaults(Notification.DEFAULT_SOUND)
//				.setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
//
//
//		// 设置自定义通知layout,通知背景等可以在layout里设置
//		build.setLayoutId(R.layout.item_shopcart);
//		// 设置自定义通知内容id
//		build.setLayoutTextId(R.id.tv_item_sc_gname);
//		// 设置自定义通知标题id
//		//build.setLayoutTitleId(R.id.title);
//		// 设置自定义通知图片id
//		build.setLayoutIconId(R.id.iv_item_sc_img);
//		// 设置自定义通知图片资源
//		build.setLayoutIconDrawableId(R.mipmap.ic_launcher);
//		// 设置状态栏的通知小图标
//		build.setIcon(R.mipmap.ic_launcher);
//		// 设置时间id
//		build.setLayoutTimeId(R.id.tv_item_sc_cost);
//		// 若不设定以上自定义layout，又想简单指定通知栏图片资源
//		build.setNotificationLargeIcon(R.mipmap.ic_adai);
//
//		XGPushManager.setDefaultNotificationBuilder(context, build);*/
//
//	}
//
//	// 通知展示
////	@Override
////	public void onNotifactionShowedResult(Context context,
////			XGPushShowedResult notifiShowedRlt) {
////		if (context == null || notifiShowedRlt == null) {
////			return;
////		}
////
////		XGNotification notific = new XGNotification();
////		notific.setMsg_id(notifiShowedRlt.getMsgId());
////		notific.setTitle(notifiShowedRlt.getTitle());
////		notific.setContent(notifiShowedRlt.getContent());
////		// notificationActionType==1为Activity，2为url，3为intent
////		notific.setNotificationActionType(notifiShowedRlt
////				.getNotificationActionType());
////		// Activity,url,intent都可以通过getActivity()获得
////		notific.setActivity(notifiShowedRlt.getActivity());
////		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
////				.format(Calendar.getInstance().getTime()));
////
////		NotificationService.getInstance(context).save(notific);
////		context.sendBroadcast(intent);
////		show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
////
////		System.out.println("通知展示"+notifiShowedRlt.toString());
////
////	}
////
////	@Override
////	public void onUnregisterResult(Context context, int errorCode) {
////		if (context == null) {
////			return;
////		}
////		String text = "";
////		if (errorCode == XGPushBaseReceiver.SUCCESS) {
////			text = "反注册成功";
////		} else {
////			text = "反注册失败" + errorCode;
////		}
////		Log.d(LogTag, text);
////		show(context, text);
////
////	}
////
////	@Override
////	public void onSetTagResult(Context context, int errorCode, String tagName) {
////		if (context == null) {
////			return;
////		}
////		String text = "";
////		if (errorCode == XGPushBaseReceiver.SUCCESS) {
////			text = "\"" + tagName + "\"设置成功";
////		} else {
////			text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
////		}
////		Log.d(LogTag, text);
////		show(context, text);
////
////	}
////
////	@Override
////	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
////		if (context == null) {
////			return;
////		}
////		String text = "";
////		if (errorCode == XGPushBaseReceiver.SUCCESS) {
////			text = "\"" + tagName + "\"删除成功";
////		} else {
////			text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
////		}
////		Log.d(LogTag, text);
////		show(context, text);
////
////	}
////
////	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
////	@Override
////	public void onNotifactionClickedResult(Context context,
////			XGPushClickedResult message) {
////		if (context == null || message == null) {
////			return;
////		}
////		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
////			// 通知在通知栏被点击啦。。。。。
////			// APP自己处理点击的相关动作
////			// 这个动作可以在activity的onResume也能监听，请看第3点相关内容
////			System.out.println("通知被打开");
////
////			/*Intent intent = new Intent(context, SOrderManageActivity.class);
////			intent.putExtra("data", message.getMsgId());
////			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////			context.startActivity(intent);*/
////
////		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
////			// 通知被清除啦。。。。
////			// APP自己处理通知被清除后的相关动作
////			System.out.println("通知被清除");
////		} else {
////			System.out.println("不知道");
////		}
////
////	}
////
////	@Override
////	public void onRegisterResult(Context context, int errorCode,
////			XGPushRegisterResult message) {
////		if (context == null || message == null) {
////			return;
////		}
////		String text = "";
////		if (errorCode == XGPushBaseReceiver.SUCCESS) {
////			text = message + "注册成功";
////			// 在这里拿token
////			String token = message.getToken();
////		} else {
////			text = message + "注册失败，错误码：" + errorCode;
////		}
////		//Log.d(LogTag, text);
////		show(context, text);
////	}
////
////	// 消息透传
////	@Override
////	public void onTextMessage(Context context, XGPushTextMessage message) {
////		String text = "收到消息:" + message.toString();
////		// 获取自定义key-value
////		String customContent = message.getCustomContent();
////		if (customContent != null && customContent.length() != 0) {
////			try {
////				JSONObject obj = new JSONObject(customContent);
////				// key1为前台配置的key
////				if (!obj.isNull("key")) {
////					String value = obj.getString("key");
////					Log.d(LogTag, "get custom value:" + value);
////				}
////				// ...
////			} catch (JSONException e) {
////				e.printStackTrace();
////			}
////		}
////		// APP自主处理消息的过程...
////		//Log.d(LogTag, text);
////		show(context, text);
////
////	}
//
//
//}


public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";

	/*private void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}*/

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }

        //show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        sendIconCountMessage(context);
        //samsungShortCut(context, "25");
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
//		Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
//				Toast.LENGTH_SHORT).show();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("ID")) {
                    String value = obj.getString("ID");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
        Log.d(LogTag, text);
        //show(context, text);
    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        //show(context, text);
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // TODO Auto-generated method stub
        //show(context, "haha");
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        XGLocalMessage localMessage = new XGLocalMessage();
        localMessage.setTitle("haha");
        localMessage.setContent(message.getContent());
        XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
        build.setSound(
                RingtoneManager.getActualDefaultRingtoneUri(
                        context, RingtoneManager.TYPE_ALARM)) // 设置声音
                // setSound(
                // Uri.parse("android.resource://" + getPackageName()
                // + "/" + R.raw.wind)) 设定Raw下指定声音文件
                .setDefaults(Notification.DEFAULT_VIBRATE) // 振动
                .setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
        // 设置自定义通知layout,通知背景等可以在layout里设置
//		build.setLayoutId(R.layout.layout_notification);
//		// 设置自定义通知内容id
//		build.setLayoutTextId(R.id.ssid);
//		// 设置自定义通知标题id
//		build.setLayoutTitleId(R.id.title);
//		// 设置自定义通知图片id
//		build.setLayoutIconId(R.id.icon);
//		// 设置自定义通知图片资源
//		build.setLayoutIconDrawableId(R.drawable.ic_launcher);
//		// 设置状态栏的通知小图标
//		build.setIcon(R.drawable.ic_launcher);
//		// 设置时间id
//		build.setLayoutTimeId(R.id.time);
//		// 若不设定以上自定义layout，又想简单指定通知栏图片资源
//		build.setNotificationLargeIcon(R.drawable.tenda_icon);
        // 客户端保存build_id
        XGPushManager.setDefaultNotificationBuilder(context, build);

        XGPushManager.addLocalNotification(context, localMessage);
        Log.d(LogTag, text);
        //show(context, text);
    }

    private void sendIconCountMessage(Context context) {
        Intent it = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
        it.putExtra("android.intent.extra.update_application_component_name", "com.example.wujie.xungetest/.MainActivity");
        String iconCount = "50";
        it.putExtra("android.intent.extra.update_application_message_text", iconCount);
        context.sendBroadcast(it);
    }
}
