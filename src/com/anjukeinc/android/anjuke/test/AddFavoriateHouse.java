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
 * @author:WilliamHu
 * #1.搜索页设置关键字筛选条件
 * #2.待房源列表信息显示完全后点击进入第一套房源
 * #3.点击收藏房源按钮
 * #4.进入”收藏“页面检查收藏列表里的第一部房源是否匹配
 */


public class AddFavoriateHouse extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	private int count = 3;
	private String bureau = "浦东";
	private String area = "金桥";
	private String testName = "安居客Android客户端V2.3收藏房源测试用例";
	
	
	
	//最先起到那个页面
	public AddFavoriateHouse(){
		super("com.anjuke.android.app",WelcomeActivity.class);
	}
	
	@Override
	//初始化
	public void setUp() throws Exception{
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@Override
	//方法的最后结束所有activities
	public void tearDown() throws Exception{
		solo.finishOpenedActivities();
	}
			
	public void testAddFavoriateHouse(){
		try {
			Report.setTCNameLog(testName);
			//如果打开应用后弹出“是否打开定位功能”的提示，选择“取消”
			if(solo.searchText("提示")){
				solo.clickOnButton(1);
			}
			Action.switchCity(solo, "上海");
			solo.clickOnText("搜索");
			Action.scroolUpPage(solo, "anjuke收藏房源");
			solo.clickOnText(bureau);
			solo.clickOnText(area);
			Report.writeHTMLLog("安居客收藏房源用例", "选中区域:".concat(bureau).concat(area), Report.DONE, "");

            Action.refresh(solo);
            Action.scroolUpPage(solo, "anjuke收藏房源");
			solo.clickOnView(solo.getView(R.id.view_list_item_tv_title));			
			solo.waitForText("返回");			
			String houseTitle =  (String)((TextView)solo.getCurrentActivity().findViewById(R.id.prop_title)).getText();			
			Report.writeHTMLLog("获取房源列表第一套房源名字", "房源列表第一套房源标题为：" + houseTitle, "Done",Action.screenShot(solo));
			solo.clickOnView(solo.getView(R.id.acticity_detail_for_picture_btn_favorite));
			if(solo.searchText("已取消收藏")){
				Report.writeHTMLLog("安居客收藏房源用例", "当前房源是取消收藏状态，应先取消收藏", Report.DONE, "");
				Action.clickById(solo, R.id.acticity_detail_for_picture_btn_favorite, "点击收藏房源按钮");
			}			
			Report.writeHTMLLog("获取房源列表第一套房源名字", "收藏房源成功", Report.DONE,"");	
			
			//点击返回按钮
			Action.clickById(solo, R.id.detail_back_button, "点击返回按钮"); 		
			solo.clickOnText("我的");
			solo.clickOnText("收藏的房源");
			
			Report.writeHTMLLog("获取房源列表第一套房源名字", "进入收藏房源列表", Report.DONE,"");

			Action.clickById(solo, R.id.view_list_item_tv_title, "点击收藏列表第一套房源");			
			Action.wait(solo,R.id.prop_title,count,"等待显示第一套房源标题");
			String myFavHouseTitle = (String)((TextView) solo.getCurrentActivity().findViewById(R.id.prop_title)).getText();
			
			Report.writeHTMLLog("获取我的收藏房源第一套房源标题", "收藏房源第一套房源标题为："+myFavHouseTitle, Report.DONE, "");	
			Boolean flag = Action.assertString(solo, "比较收藏房源标题是否匹配", houseTitle,myFavHouseTitle);
			if(flag){
				Report.writeHTMLLog("安居客收藏房源用例", "匹配成功", Report.PASS, "");
			}else{
				Report.writeHTMLLog("安居客收藏房源用例", "匹配不成功用例失败", Report.FAIL, Action.screenShot(solo));
			}
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}		
	}
	
	
	
}