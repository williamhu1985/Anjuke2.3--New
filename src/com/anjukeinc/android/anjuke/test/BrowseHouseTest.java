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
 * #1.打开anjuke客户端,进入搜索tab页
 * #2.设置搜索条件，待房源列表信息显示完全后点击进入第一套房源
 * #3.退回到主页，并点击“更多”-->"最近浏览"进入浏览页面
 * #4.检查收藏列表里的第一部房源是否匹配
 */


public class BrowseHouseTest extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	private int count = 3;
	private String bureau = "浦东";
	private String area = "金桥";
	private String testName = "安居客Android客户端V2.3浏览房源历史记录测试用例";



	//最先起到那个页面
	public BrowseHouseTest(){
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

	public void testBrowseHouseTest(){
		try {
			Report.setTCNameLog(testName);
			//如果打开应用后弹出“是否打开定位功能”的提示，选择“取消”
			if(solo.searchText("提示")){
				solo.clickOnButton(1);
			}		
			if(solo.searchText("刷新")){
				solo.clickOnText("刷新");
			}
            
//			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待详细房源类表加载成功");
//			Report.writeHTMLLog("安居客浏览房源用例", "附近页房源列表显示完全",Report.DONE,"");
			
			//如果当前城市不是上海，就切换至上海
            Action.switchCity(solo, "上海");
            solo.clickOnText("搜索"); 
			Action.scroolUpPage(solo, "浏览房源");
			solo.clickOnText(bureau);
			solo.clickOnText(area);
			Report.writeHTMLLog("安居客浏览房源用例", "选中区域".concat(bureau).concat(area), Report.DONE, "");

			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待房源列表信息显示成功");
			solo.clickOnView(solo.getView(R.id.view_list_item_tv_title));
			solo.waitForText("收藏");
			String houseTitle =  (String)((TextView)solo.getCurrentActivity().findViewById(R.id.prop_title)).getText();
			Report.writeHTMLLog("安居客浏览房源用例", "房源列表第一套房源标题为：" + houseTitle, "Done",Action.screenShot(solo));

			//点击返回按钮
			Action.clickById(solo, R.id.detail_back_button, "点击返回按钮"); 		

			//点击”更多"tab
			solo.clickOnText("我的");
			solo.clickOnText("浏览记录");
			solo.waitForText("清空");
			solo.clickOnView(solo.getView(R.id.view_for_house_history_item_tv_title));	
			Report.writeHTMLLog("安居客浏览房源用例", "选中最近浏览的第一套房源", Report.DONE, "");
			String browseHouseTitle = (String)((TextView) solo.getCurrentActivity().findViewById(R.id.prop_title)).getText();			
			Report.writeHTMLLog("安居客浏览房源用例", "最近浏览的第一套房源标题为："+browseHouseTitle, Report.DONE, "");
			Boolean flag = Action.assertString(solo, "比较收藏房源标题是否匹配", houseTitle,browseHouseTitle);	
			if(flag){
				Report.writeHTMLLog("安居客浏览房源用例", "匹配成功", Report.PASS, "");
			}else{
				Report.writeHTMLLog("安居客浏览房源用例", "匹配不成功用例失败", Report.FAIL, Action.screenShot(solo));
			}
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}




	}

	
	
}