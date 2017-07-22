package com.bh.yibeitong.appupdate;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

public class ApkInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long id = SpUtils.getInstance(context).getLong("downloadId", -1L);
            if (downloadApkId == id) {
                installApk(context, downloadApkId);
            }
        }
    }

    private static void installApk(Context context, long downloadApkId) {

        Intent install = new Intent(Intent.ACTION_VIEW);
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
        if (downloadFileUri != null) {

            install.setDataAndType(downloadFileUri,
                    "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);

            /*if (Build.VERSION.SDK_INT >= 23) {
                // 适配android7.0 ，不能直接访问原路径
                // 需要对intent 授权
                Log.d("DownloadManager", "7.0" + downloadFileUri.toString());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.setDataAndType(FileProvider.getUriForFile(context,
                        context.getPackageName() + ".fileProvider",
                        new File(String.valueOf(downloadFileUri))),
                        "application/vnd.android.package-archive");
            } else {
                //i.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");

                Log.d("DownloadManager", "no 7.0" + downloadFileUri.toString());
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }*/

        } else {
            Log.e("DownloadManager", "下载失败");
        }
    }
}
