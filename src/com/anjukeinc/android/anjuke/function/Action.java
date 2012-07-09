package com.anjukeinc.android.anjuke.function;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anjukeinc.android.anjuke.util.ScreenShot;
import com.anjukeinc.android.anjuke.util.Report;
import com.anjuke.android.app.R;
import com.anjuke.android.library.component.wheel.LibWheelView;
import com.anjukeinc.android.anjuke.test.*;
import com.jayway.android.robotium.solo.Solo;
import static junit.framework.Assert.*;

public class Action {

	/**
	 * 点击定位框的取消并选择上海
	 * @param solo
	 */

	public static void alertCancleButton(Solo solo) {
		if (solo.searchButton("取消")) {
			clickOnText(solo, "取消", "定位框的取消");
			Action.clickOnText(solo, "上海", "城市：上海");
		}
	}

	/**
	 * 多值匹配比较 主要用于筛选条件较多的情况
	 * 
	 * @param solo
	 * @param message
	 *            比较内容
	 * @param searchTip
	 *            信息栏显示的筛选条件
	 * @param params
	 *            筛选选择的条件
	 * @return
	 */
	public static boolean assertFilter(Solo solo, String message,
			String searchTip, String... params) {
		String str = "";
		String expect = "";
		for (String a : params) {
			if (a.equals("不限") == false) {
				if (searchTip.indexOf(a) < 0) {
					str += a + " ";
				}
				expect += a + " ";
			}
		}
		if (str == "") {
			Report.writeHTMLLog("比较","【"+ message +"】"+ "一致，value="+searchTip, "PASS", Action.screenShot(solo));
			System.out.println("比较结果一致---->");
			return true;
		} else {
			Report.writeHTMLLog("比较","【"+ message +"】"+"不一致,expect=" + expect
					+ "receive=" + searchTip, "FAIL", Action.screenShot(solo));
			System.out.println("比较结果不一致---->");
			return false;
		}
	}

	/**
	 * 比较两个字符是否一致
	 * 
	 * @param message
	 *            比较的内容
	 * @param expect
	 *            期望值
	 * @param receive
	 *            接收到的值
	 */

	public static Boolean assertString(Solo solo, String message, String expect,String actual) {
		Boolean flag = true;
		if (expect.equals(actual)) {
			Report.writeHTMLLog("比较","【"+ message +"】" + "一致,value="+expect, Report.DONE, "");
		} else {
			flag = false;
			String img =screenShot(solo);	
			Report.writeHTMLLog("比较","【"+ message +"】"+ "不一致,expect=" + expect
					+ "receive:" + actual, Report.DONE, img);
//			fail();
		}
		return flag;
	}

	/**
	 * 用id点击
	 * 
	 * @param solo
	 * @param id
	 *            资源id
	 * @param message
	 *            点击控件的名字
	 */
	public static void clickById(Solo solo, int id, String message) {
		String detail = "点击" +"【"+ message +"】";
		//		 String imag =screenShot(solo);
		String imag = "";
		try {
			// solo.clickOnView(solo.getCurrentActivity().findViewById(id));
			solo.clickOnView(solo.getView(id));
			Report.writeHTMLLog("点击", detail, "DONE", imag);
		} catch (NoSuchElementException e) {
			Report.writeHTMLLog("点击", detail, "FAIL", imag);
		}
	}

	/**
	 * 设置找房筛选条件 
	 * @param solo
	 * @param priceIndex
	 * @param typeIndex
	 * @param fromIndex         
	 * @param methodIndex
	 * @return flag
	 * 
	 */
//	public static void selectFilterCondition(Solo solo, int priceIndex, int typeIndex, int fromIndex, int methodIndex){
//		Boolean flag = false;;
//		String searchTip = "";
////		String price = clickInFilter(solo,priceIndex,"租金");
//		//过滤价格里的空格
//		if(price.indexOf("-")>=0){
//			price=price.substring(0, price.indexOf("-")-1)+"-"+price.substring(price.indexOf("-")+2,price.length());
//		}
//		String type  = clickInFilter(solo,typeIndex,"户型");
//		String source = clickInFilter(solo,fromIndex,"来源");
//		String rentType = clickInFilter(solo,methodIndex,"方式");
//		solo.clickOnButton("确定");
//		int count = 0; 
//		while(count<=5){
//			if(solo.searchText("正在刷新")){
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				count ++;
//			}
//		}
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		Action.wait(solo, R.id.activity22_search_list_searchtip, 5, "等待searchTip显示");
//		//获取搜索栏信息
//		if(solo.searchText("地图")){
//			searchTip = (String) ((TextView) solo
//					.getView(R.id.activity22_nearby_list_searchtip)).getText();
//		}else if(solo.searchText("切换城市")){
//			searchTip = (String) ((TextView) solo
//					.getView(R.id.activity22_search_list_searchtip)).getText();
//		}
//
//		System.out.println("搜索栏信息为：" + searchTip+ "---->");
//		Report.writeHTMLLog("好租V2.2搜索页筛选条件测试用例1", "搜索栏信息为："+ searchTip, Report.DONE, "");
//		flag = Action.assertFilter(solo, "筛选条件和筛选信息栏", searchTip, price, type,
//				source, rentType);
//		if(flag){
//			Report.writeHTMLLog("设置筛选找房条件", "筛选找房条件设置成功", Report.PASS, "");				
//		}else{
//			String ps = Action.screenShot(solo);
//			Report.writeHTMLLog("好租搜索页筛选条件测试用例3", "筛选条件匹配成功，不是预期结果,用例执行失败---->", Report.FAIL, ps);				
//		    fail("筛选找房条件设置失败---->");
//		}
//	}	


	
	/*
	 *设置筛选条件
	 *@param solo
	 *@param message 筛选条件种类
	 *@param index 筛选的下标值
	 */
	public static Boolean setInWheelView(Solo solo,int index, String message){
		Boolean flag = false;

		if(message.equals("售价")){
			LibWheelView priceView1 = (LibWheelView)solo.getView(R.id.activity_filter_property_wheel_double_conditions_level1);
			priceView1.libSetCurrentItem(0,true);  //售价tab的下限设为”20w“
			LibWheelView priceView2 = (LibWheelView)solo.getView(R.id.activity_filter_property_wheel_double_conditions_level2);
			priceView2.libSetCurrentItem(index,true);     //上限	
			Report.writeHTMLLog("设置筛选条件", "设置售价", Report.DONE, "");
		}else if(message.equals("户型")||message.equals("面积")||message.equals("房龄")){
		    LibWheelView othersView = (LibWheelView)solo.getView(R.id.activity_filter_property_wheel_conditions);
		    othersView.libSetCurrentItem(index,true);
			Report.writeHTMLLog("设置筛选条件", "设置"+message, Report.DONE, "");    
		}
		solo.clickOnText("确定");
		solo.waitForText("完成");
		return flag;
	}

	/*
	 * 检验设置的筛选条件是否与显示的筛选提示信息一致
	 * @param solo
	 */
	public static Boolean checkInWheelView(Solo solo){
		//当前选中的价格filter
		String price = (String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
		//当前选中的户型filter
		String chamber = (String)((TextView)solo.getView(R.id.filter_chamber_text_content)).getText();
		//当前选中的面积filter
		String acreage = (String)((TextView)solo.getView(R.id.filter_acreage_text_content)).getText();
		//当前选中的房龄filter
		String houseage = (String)((TextView)solo.getView(R.id.filter_houseage_text_content)).getText();

		Report.writeHTMLLog("检验设置的筛选条件是否与显示的筛选提示信息一致", "当前设置的条件为Price:"+ price+" Chamber:"+chamber + " Acreage:"+acreage+" Houseage:"+houseage, Report.DONE, "");
		solo.clickOnText("完成");
		solo.waitForText("筛选");
		String  searchTip= (String)((TextView)solo.getView(R.id.activity_near_property_list_tv_conditons)).getText();
		Report.writeHTMLLog("检验设置的筛选条件是否与显示的筛选提示信息一致","serachTip:"+searchTip, Report.DONE, "");
		Boolean flag = Action.assertFilter(solo, "筛选条件和筛选信息栏信息比较", searchTip, price,chamber,acreage,houseage);
		return flag;
	}
	
	/*
	 * 验证显示的筛选提示信息是否与设置的筛选条件一致
	 * @param solo
	 *@param searchTip
	 */
	public static Boolean verifyInWheelView(Solo solo,String searchTip){
		 
		solo.clickOnText("筛选");
	    solo.waitForText("完成");
		//当前选中的价格filter
		String price = (String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
		//当前选中的户型filter
		String chamber = (String)((TextView)solo.getView(R.id.filter_chamber_text_content)).getText();
		//当前选中的面积filter
		String acreage = (String)((TextView)solo.getView(R.id.filter_acreage_text_content)).getText();
		//当前选中的房龄filter
		String houseage = (String)((TextView)solo.getView(R.id.filter_houseage_text_content)).getText();

		Report.writeHTMLLog("验证显示的筛选提示信息是否与设置的筛选条件一致", 
                                 "当前显示的筛选条件为Price:"+ price+" Chamber:"+chamber + " Acreage:"+acreage+" Houseage:"+houseage +"---->", Report.DONE, "");
		Boolean flag = Action.assertFilter(solo, "筛选信息栏信息和筛选条件比较", searchTip, price,chamber,acreage,houseage);
		solo.clickOnText("完成");
		solo.waitForText("筛选");
		return flag;
	}


	/*
	 * 比较房源价格
	 * @param solo
	 * @param price 
	 * @param propPrice
	 */
	public static void assertPrice(Solo solo,String price,String propPrice){
		int minPrice = 0;
		int maxPrice = 0;
		int index = 0;
		if(price.equals("不限")){
			Report.writeHTMLLog("比较价格", "选取的价格为不限，当前价格为:"+propPrice, Report.PASS, "");
		}else if(price.indexOf("以上")>0){
			index = price.indexOf("万");
			price = price.substring(0, index);
			minPrice = Integer.parseInt(price);
			Report.writeHTMLLog("比较价格", "minPrice价格为:"+minPrice, Report.DONE, "");
			if(Integer.parseInt(propPrice)>= minPrice){
				Report.writeHTMLLog("比较价格", "价格在区间之内", Report.PASS, "");
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较价格", "价格在不区间之内", Report.FAIL, ps);	
			}
		}else if(price.indexOf("以下")>0){
			index = price.indexOf("万");
			price = price.substring(0, index);
			maxPrice = Integer.parseInt(price);
			Report.writeHTMLLog("比较价格", "maxPrice价格为:"+maxPrice, Report.DONE, "");
			if(Integer.parseInt(propPrice) <= maxPrice){
					Report.writeHTMLLog("比较价格", "价格在区间之内", Report.PASS, "");
				}else{
					String ps = Action.screenShot(solo);
					Report.writeHTMLLog("比较价格", "价格在不区间之内", Report.FAIL, ps);	
				}			
		}else{
			index = price.indexOf("-");
			System.out.println("index---->"+index);
			minPrice = Integer.parseInt(price.substring(0, index));
			Report.writeHTMLLog("比较价格", "minPrice价格为:"+minPrice, Report.DONE, "");
			maxPrice = Integer.parseInt(price.substring(index+1, price.length()));
			Report.writeHTMLLog("比较价格", "maxPrice价格为:"+maxPrice, Report.DONE, "");			
			if(Integer.parseInt(propPrice)>=minPrice && Integer.parseInt(propPrice)<=maxPrice){
				Report.writeHTMLLog("比较价格", "价格在区间之内", Report.PASS, "");	
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较价格", "价格在不区间之内", Report.FAIL, ps);		
			}
		}
	}
	
//	/**
//	 * 专门用来判断筛选条件为“不限”和其他条件的函数
//	 * @param solo
//	 * @param message 	比较的内容
//	 * @param expect	筛选选择的值
//	 * @param receive	实际房子的值
//	 */
//	public void assertString(Solo solo,String message,String expect,String receive){
//		if(expect.equals("不限")==false){
//			Action.assertString(solo, message, expect, receive);
//		}else{
//			Report.writeHTMLLog("比较", message, "PASS", "");
//		}
//	}
	
	
	/*
	 * 比较房源面积
	 * @param solo
	 * @param acreage 
	 * @param propAcreage
	 */
	public static void assertAcreage(Solo solo,String acreage,String propAcreage){
		int minAcreage = 0;
		int maxAcreage = 0;
		int index = 0;
		if(acreage.equals("不限")){
			Report.writeHTMLLog("比较房源面积", "选取的面积为不限，当前价格为:"+propAcreage, Report.PASS, "");
		}else if(acreage.indexOf("以上")>0){
			index = acreage.indexOf("平");
			acreage = acreage.substring(0, index);
			minAcreage = Integer.parseInt(acreage);
			Report.writeHTMLLog("比较面积", "minAcreage面积为："+minAcreage, Report.DONE, "");
			if(Integer.parseInt(propAcreage)>= minAcreage){
				Report.writeHTMLLog("比较面积", "面积在区间之内", Report.PASS, "");
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较面积", "面积不在区间之内", Report.FAIL, ps);	
			}
		}else if(acreage.indexOf("以下")>0){
			index = acreage.indexOf("平");
			acreage = acreage.substring(0, index);
			maxAcreage = Integer.parseInt(acreage);
			Report.writeHTMLLog("比较面积", "maxAcreage面积为："+maxAcreage, Report.DONE, "");
			if(Integer.parseInt(propAcreage) <= maxAcreage){				
					Report.writeHTMLLog("比较面积", "面积在区间之内", Report.PASS, "");
				}else{
					String ps = Action.screenShot(solo);
					Report.writeHTMLLog("比较面积", "面积不在区间之内", Report.FAIL, ps);	
			}
		}else{
			index = acreage.indexOf("-");
			minAcreage = Integer.parseInt(acreage.substring(0, index));
			Report.writeHTMLLog("比较面积", "minAcreage面积为："+minAcreage, Report.DONE, "");
			maxAcreage = Integer.parseInt(acreage.substring(index+1, acreage.length()-2));
			Report.writeHTMLLog("比较面积", "maxAcreage面积为："+maxAcreage, Report.DONE, "");		
			if(Integer.parseInt(propAcreage)>=minAcreage && Integer.parseInt(propAcreage)<=maxAcreage){
				Report.writeHTMLLog("比较面积", "面积在区间之内", Report.PASS, "");	
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较面积", "面积不在区间之内", Report.FAIL, ps);		
			}
		}
	}
	
	
	/*
	 * 比较房源年代
	 * @param solo
	 * @param houseage 
	 * @param propHouseage
	 */
	public static void assertHouseage(Solo solo,String houseage,String propHouseage){
		int minHouseage = 0;
		int maxHouseage = 0;
		int index = 0;
		if(houseage.equals("不限")){
			Report.writeHTMLLog("比较房源年代", "选取的年代为不限，当前年代为:"+propHouseage, Report.PASS, "");
		}else if(houseage.indexOf("内")>0){
			minHouseage = 2011;
			maxHouseage = 2012;
			Report.writeHTMLLog("比较年代", "minHouseage:"+ minHouseage +" maxHouseage:"+maxHouseage, Report.DONE, "");
			if(Integer.parseInt(propHouseage)>= minHouseage && Integer.parseInt(propHouseage)<=maxHouseage){
				Report.writeHTMLLog("比较年代", "年代在2年内", Report.PASS, "");
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较年代", "年代不在2年内", Report.FAIL, ps);	
			}
		}else if(houseage.indexOf("以上")>0){			
			maxHouseage = 2003;
			Report.writeHTMLLog("比较年代","maxHouseage:"+maxHouseage, Report.DONE, "");
			if(Integer.parseInt(propHouseage) <= maxHouseage){				
					Report.writeHTMLLog("比较年代", "年代在区间之内", Report.PASS, "");
				}else{
					String ps = Action.screenShot(solo);
					Report.writeHTMLLog("比较年代", "年代不在区间之内", Report.FAIL, ps);	
			}
		}else{
			index = houseage.indexOf("-");
			maxHouseage = 2013 - Integer.parseInt(houseage.substring(0, index));
			Report.writeHTMLLog("比较年代", "maxHouseage年代为:"+maxHouseage, Report.DONE, "");
			minHouseage = 2013 - Integer.parseInt(houseage.substring(index+1, houseage.length()-1));
			Report.writeHTMLLog("比较年代", "minHouseage年代为:"+minHouseage, Report.DONE, "");			
			if(Integer.parseInt(propHouseage)>=minHouseage && Integer.parseInt(propHouseage)<=maxHouseage){
				Report.writeHTMLLog("比较年代", "年代在区间之内", Report.PASS, "");	
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("比较年代", "年代不在区间之内", Report.FAIL, ps);		
			}
		}
	}
	
	
	
	
	/**
	 * 点击文字
	 * 
	 * @param solo
	 * @param name 控件显示的字
	 * @param message 相关信息
	 */
	public static void clickOnText(Solo solo, String name, String message) {
		// String imag =screenShot(solo);
		String imag = "";
		try {
			solo.clickOnText(name);
			Report.writeHTMLLog("点击", "点击" +"【"+ message +"】", "DONE", imag);
		} catch (NotFoundException e) {
			Report.writeHTMLLog("点击", "点击" +"【"+ message +"】", "Fail", imag);
		}
	}

	/**
	 * 点击view
	 * 
	 * @param solo
	 * @param view 需要点击的view
	 * @param message 相关信息
	 */
	public static void clickOnView(Solo solo, View view, String message) {
		String imag = "";
		String detail = "点击" + "【"+ message +"】";
		try {
			solo.clickOnView(view);
			Report.writeHTMLLog("点击", detail, "DONE", imag);
		} catch (NotFoundException e) {
			Report.writeHTMLLog("点击", detail, "FAIL", imag);
		}
	}

	/**
	 * 返回主页
	 * 
	 * @param solo
	 */
	public static void goHomePage(Solo solo) {
		// String imag =screenShot(solo);
		String imag = "";
		CharSequence name = "";
		while (solo.searchButton("菜单") == false
				|| solo.searchButton("地图") == false) {
			name = solo.getButton(0).getText();

			solo.clickOnButton(0);
			Report.writeHTMLLog("点击", "点击" +"【"+ name+"】", "DONE", imag);
		}
	}




	/**
	 * 封装截图函数
	 * 
	 * @param solo
	 * @return
	 */
	public static String screenShot(Solo solo) {
		System.out.println("截图开始------>");
		return ScreenShot.savePic(
				ScreenShot.takeScreenShot(solo.getCurrentActivity()),
				Report.getNowDateTime());
	}



	/**
	 * 等待控件出现
	 * 
	 * @param solo
	 * @param id
	 *            资源id
	 * @param count
	 *            总共等待秒数
	 * @param message
	 *            控件名字
	 * @return 出现就返回true
	 * @throws InterruptedException
	 */
	public static boolean wait(Solo solo, int id, int count, String message){
		int i = 1;
		String imag = "";
		while (solo.getView(id) == null) {
			if (i < count) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Report.writeHTMLLog("加载中...等待",	message + "：等待" + (i++) + " 次 ", "DONE", imag);
			} else {
				imag =screenShot(solo);
				Report.writeHTMLLog("加载中...等待", "【"+ message +"】" + "无法显示", "FAIL", imag);
				return false;
			}
		}
		Report.writeHTMLLog("加载中完成", "【"+ message +"】" + " total wait " + (i - 1) + " 次",
				"DONE", "");
		return true;
	}
	
	/**
	 * 
	 * @param solo
	 * @param SCase
	 */
	public static void scroolUpPage(Solo solo,String SCase){
		boolean scroolFlag = true;
		while(scroolFlag){
			scroolFlag = solo.scrollUp();
			Report.writeHTMLLog(SCase, "scrool up", Report.DONE, "");
		}
		Report.writeHTMLLog(SCase, "已scroll到顶", Report.DONE, "");
	}
	
	/**
	 * 
	 * @param solo
	 * @param cityName
	 */
	public static void switchCity(Solo solo,String cityName){
		solo.clickOnText("更多");
		solo.clickOnText("切换城市");
		solo.clickOnText(cityName);	
		Report.writeHTMLLog("切换城市", "成功切换至城市："+cityName, Report.PASS, "");
	}
	
	/**
	 * 
	 * @param solo
	 */
	public static void refresh(Solo solo){
		if(solo.searchText("刷新")){
			solo.clickOnText("刷新");
		}
	}
	/**
	 * 
	 * @param solo
	 */
	public static void resetSearchInput(Solo solo){
		String text = ((TextView)solo.getView(R.id.activity_search_address_et)).getText().toString();
		System.out.println("text"+ text+"---->");
		if(!text.equals("")&&!text.equals(null)){
			solo.clickOnText(text);
			System.out.println("进入输入栏---->");				
			solo.enterText(0, "");
			System.out.println("将输入栏置空");
			solo.clickOnText("确定");
			solo.clickOnText("搜索");
		}
	}
}
