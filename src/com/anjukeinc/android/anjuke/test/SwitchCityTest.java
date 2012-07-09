package com.anjukeinc.android.anjuke.test;


import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anjuke.android.app.R;
import com.anjuke.android.app.activity.WelcomeActivity;
import com.anjukeinc.android.anjuke.function.Action;
import com.anjukeinc.android.anjuke.util.Report;
import com.jayway.android.robotium.solo.Solo;
/*
 * @author:William
 * 测试用例步骤简介：
 * #1.在搜索页面切换城市（非上海）
 * #2.点击附近tab按钮，获取页面上方的定位信息
 * #3.判断当前的定位仍然在上海
 * @CreateDate: 2012/06/05
 */

public class SwitchCityTest extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
    private String address;
    private int count = 5;
	private String testName = "安居客Android客户端V2.3切换城市测试用例";


	public SwitchCityTest(){
		super("com.anjuke.android.app",WelcomeActivity.class);
	}
	
	@Override
	public void setUp(){
		solo = new Solo(getInstrumentation(),getActivity());
	}

	@Override
	public void tearDown(){
		solo.finishOpenedActivities();	
	}

	public void testBrowseHistoryHouseTitle(){
		try {
			Report.setTCNameLog(testName);		           

			//切换当前城市至北京
			Action.switchCity(solo, "北京");		
			
			//切换到”附近“tab
			solo.clickOnText("附近");
			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待详细房源类表加载成功");
			
			//获取当前定位到的地址
			address = ((TextView)solo.getView(R.id.activity_near_property_list_tv_location)).getText().toString();
            Report.writeHTMLLog("切换城市地址验证", "获取当前GPS定位到的地址为："+ address, Report.DONE, "");
            if(address.contains("陆家嘴")){
            	Report.writeHTMLLog("切换城市地址验证", "当前定位到的地址仍然在上海，测试正确", Report.PASS, "");
            }else{
            	String ps = Action.screenShot(solo);
            	Report.writeHTMLLog("切换城市地址验证", "当前定位到的地址已经不在上海，测试失败", Report.FAIL, ps);            	
            }
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}
	}

}