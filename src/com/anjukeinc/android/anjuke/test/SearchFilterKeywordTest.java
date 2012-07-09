package com.anjukeinc.android.anjuke.test;

import static junit.framework.Assert.fail;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anjuke.android.app.R;
import com.anjuke.android.app.activity.WelcomeActivity;
import com.anjuke.android.library.component.wheel.LibWheelView;
import com.anjukeinc.android.anjuke.function.Action;
import com.anjukeinc.android.anjuke.util.Report;
import com.jayway.android.robotium.solo.Solo;

/*
 * @author:WilliamHu
 * #1.搜索页设置关键字筛选条件
 * #2.待房源列表信息显示完全后检查页头搜索信息是否一致
 */

public class SearchFilterKeywordTest extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	private int count = 5;
	private String bureau = "浦东";
	private String area = "金桥";
	private String testName = "安居客Android客户端V2.3搜索页关键字筛选测试用例";



	//最先起到那个页面
	public SearchFilterKeywordTest(){
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

	public void testSearchFilterKeyword(){
		try {
			Report.setTCNameLog(testName);

			//如果打开应用后弹出“是否打开定位功能”的提示，选择“取消”
			if(solo.searchText("提示")){
				solo.clickOnButton(1);
			}		

			//如果当前城市不是上海，就切换至上海
			Action.switchCity(solo, "上海");
			solo.clickOnText("搜索");
			Action.resetSearchInput(solo);
			Action.scroolUpPage(solo, "搜索页关键字搜索");
			solo.clickOnText(bureau);
			solo.clickOnText(area);

			Report.writeHTMLLog("搜索页关键字搜索", "选中区域："+ bureau + area,Report.DONE ,"");
			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待房源列表信息显示成功");
            
			//点击房源列表页的搜索按钮
			solo.clickOnButton(0);
			solo.clickOnView(solo.getView(R.id.activity_search_address_et));
			solo.enterText(0, bureau.concat(area));
			solo.clickOnButton(0);		
			Report.writeHTMLLog("搜索页关键字搜索", "输入关键字："+ bureau + area,Report.DONE ,"");
			
			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待房源列表页面加载成功");
			String searchTip = ((TextView)solo.getView(R.id.activity_near_property_list_tv_location)).getText().toString();
			Report.writeHTMLLog("搜索页关键字搜索","筛选信息为："+searchTip,Report.DONE ,"");
			Boolean flag = Action.assertFilter(solo, "关键字筛选找房信息匹配", searchTip, bureau,area);

			if(flag){
				Report.writeHTMLLog("搜索页关键字搜索", "关键字筛选找房信息匹配成功", Report.PASS, "");				
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("搜索页关键字搜索", "关键字筛选找房信息匹配不成功,用例执行失败---->", Report.FAIL, ps);				
				fail("区域选择筛选信息匹配失败");       
			}
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}
	}
}



