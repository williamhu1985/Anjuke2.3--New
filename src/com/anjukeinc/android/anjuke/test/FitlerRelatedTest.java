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
 * #1.附近页设置筛选条件
 * #2.待房源列表信息显示完全后切换到地图页
 * #3.点击筛选按钮，检查显示的筛选条件是否与刚才设置的一致 
 */


public class FitlerRelatedTest extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	int count = 3;
	private String testName = "安居客Android客户端V2.3筛选关联测试用例";



	//最先起到那个页面
	public FitlerRelatedTest(){
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

	public void testNearByFilter(){
		try {
			Report.setTCNameLog(testName);

			//如果打开应用后弹出“是否打开定位功能”的提示，选择“取消”
			if(solo.searchText("提示")){
				solo.clickOnButton(1);
			}					
//			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待详细房源类表加载成功");
        	
			//默认在”搜索“tab
			Action.switchCity(solo, "上海");
			solo.clickOnText("搜索");
			Action.scroolUpPage(solo, "筛选关联");
			solo.clickOnText("浦东");
			solo.clickOnText("金桥");
			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待详细房源类表加载成功");
			solo.clickOnButton("筛选");  

			//点击”售价“tab
			solo.clickOnView(solo.getView(R.id.filter_price_text_tip));
			Action.setInWheelView(solo, 5, "售价");

			//点击“户型” tab   
			solo.clickOnView(solo.getView(R.id.filter_chamber_text_tip));
			Action.setInWheelView(solo, 1, "户型");

			//点击“面积”tab
			solo.clickOnView(solo.getView(R.id.filter_acreage_text_tip));
			Action.setInWheelView(solo, 1, "面积");

			//点击“房龄”tab
			solo.clickOnView(solo.getView(R.id.filter_houseage_text_tip));     
			Action.setInWheelView(solo, 2, "房龄");

            Boolean flag = Action.checkInWheelView(solo);

			if(flag){
				Report.writeHTMLLog("筛选关联", "筛选找房条件匹配成功", Report.PASS, "");				
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("筛选关联", "筛选条件匹配不成功，不是预期结果,用例执行失败", Report.FAIL, ps);				
				fail("筛选找房条件匹配失败");       
			}		
			//当前显示的serachTip
			String  searchTip= (String)((TextView)solo.getView(R.id.activity_near_property_list_tv_conditons)).getText();
			Report.writeHTMLLog("筛选关联","serachTip:"+searchTip, Report.DONE, "");			

			//点击”地图“图标以切换至”地图“模式
			solo.clickOnView(solo.getView(R.id.activity_search_host_btn_map));
			Report.writeHTMLLog("筛选关联", "成功切换至地图模式", Report.DONE, "");
			
			//地图模式下显示的searchTip
			String  searchTipInMap= (String)((TextView)solo.getView(R.id.activity_nearpro_map_tv_conditons)).getText();
			Report.writeHTMLLog("筛选关联","serachTipInMap:"+searchTipInMap, Report.DONE, "");			
			Action.assertString(solo, "比较列表和地图模式下searchTip的内容是否一致", searchTip, searchTipInMap);			
            Report.writeHTMLLog("筛选关联", "列表和地图模式下searchTip的内容一致", Report.PASS, "");
            
			flag = Action.verifyInWheelView(solo,searchTip);
			if(flag){
				Report.writeHTMLLog("筛选关联", "地图模式筛选条件匹配成功", Report.PASS, "");				
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("筛选关联", "地图模式筛选条件匹配不成功", Report.FAIL, ps);				
				fail("筛选找房条件匹配失败---->");       
			}			
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}
	}
}
	
	
	
