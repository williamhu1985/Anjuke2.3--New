package com.anjuke.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.provider.ContactsContract.Data;
import android.view.View;

public class ScreenShot {
	private static long plusTime=1000*60*60*8;

	// 获取指定Activity的截屏，保存到png文件
	public static Bitmap takeScreenShot(Activity activity) {
		System.out.println("进入截图函数------>");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("等1秒------>");
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		System.out.println("获取view成功------>");
		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);
		System.out.println("获取状态栏高度成功------>");
		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		System.out.println("获取屏幕长高成功------>");
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		System.out.println("去掉标题栏成功------>");
		view.destroyDrawingCache();
		System.out.println("截图函数运行完毕------>");
		return b;
		
	}

	// 保存到sdcard
	public static String savePic(Bitmap b, String strFileName) {
		System.out.println("开始保存图片------>");
	
		FileOutputStream fos = null;

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis()+plusTime);
		//获取当前时间
		String time = formatter.format(curDate);
		System.out.println(time);
		File f = new File("sdcard/Robotium/"+strFileName);
		if(f.exists()==false){
			f.mkdirs();
		}
		try {
			fos = new FileOutputStream("sdcard/Robotium/"+strFileName+"/"+time+".png");
//			fos = new FileOutputStream("Robotium/"+strFileName+"/"+time+".png");
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("保存图片结束------>");
		return time;
	}
	


	// 程序入口
	// public static void shoot(Activity a){
	// ScreenShot.savePic(ScreenShot.takeScreenShot(a), "sdcard/xx.png");
	// }
}