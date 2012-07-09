package com.anjukeinc.android.anjuke.test;

import static junit.framework.Assert.fail;
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
 * #1.附近页设置筛选条件
 * #2.待房源列表信息显示完全后切换到搜索页
 * #3.点击筛选按钮，检查显示的筛选条件是否与刚才设置的一致 
 */


public class FitlerNotRelatedTest extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	int count = 5;
	private String testName = "安居客Android客户端V2.3筛选不关联测试用例";



	//最先起到那个页面
	public FitlerNotRelatedTest(){
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

	public void testFilterNotRelated(){
		try {
			Report.setTCNameLog(testName);

			//如果打开应用后弹出“是否打开定位功能”的提示，选择“取消”
			if(solo.searchText("提示")){
				solo.clickOnButton(1);
			}			
						
			//默认在”搜索“tab
			Action.switchCity(solo, "上海");
			solo.clickOnText("搜索");
			Action.scroolUpPage(solo, "筛选确认");
			solo.clickOnText("浦东");
			solo.clickOnText("金桥");
			Action.wait(solo, R.id.view_list_item_tv_title, count, "等待详细房源类表加载成功");
			//如果房源列表页无法显示，并提示“网络异常”,点击刷新按钮
			if(solo.searchText("刷新")){
				solo.clickOnButton("刷新");
			}	
			solo.clickOnButton("筛选");  

			//点击”售价“tab
			solo.clickOnView(solo.getView(R.id.filter_price_text_tip));
			Action.setInWheelView(solo, 1, "售价");

			//点击“户型” tab   
			solo.clickOnView(solo.getView(R.id.filter_chamber_text_tip));
			Action.setInWheelView(solo, 1, "户型");

			//点击“面积”tab
			solo.clickOnView(solo.getView(R.id.filter_acreage_text_tip));
			Action.setInWheelView(solo, 1, "面积");

			//点击“房龄”tab
			solo.clickOnView(solo.getView(R.id.filter_houseage_text_tip));     
			Action.setInWheelView(solo, 2, "房龄");

			//当前选中的价格filter
			String price = (String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
			if(price.equals("不限")){
				solo.clickOnView(solo.getView(R.id.filter_price_text_tip));
				Action.setInWheelView(solo, 5, "售价");
			}
			price =(String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
			//当前选中的户型filter
			String chamber = (String)((TextView)solo.getView(R.id.filter_chamber_text_content)).getText();
			//当前选中的面积filter
			String acreage = (String)((TextView)solo.getView(R.id.filter_acreage_text_content)).getText();
			//当前选中的房龄filter
			String houseage = (String)((TextView)solo.getView(R.id.filter_houseage_text_content)).getText();
			
            Report.writeHTMLLog("筛选不关联", "筛选条件为:Price:"+ price+" Chamber:"+chamber + " Acreage:"+acreage+" Houseage:"+houseage, Report.DONE, "");
			
			solo.clickOnText("完成");
			Report.writeHTMLLog("筛选不关联", "搜索页筛选条件设置完成", Report.DONE, "");			
			solo.waitForText("筛选");
			
			String  searchTip= (String)((TextView)solo.getView(R.id.activity_near_property_list_tv_conditons)).getText();
			Report.writeHTMLLog("筛选不关联", "serachTip:"+searchTip, Report.DONE, "");
			Boolean flag = Action.assertFilter(solo, "筛选条件和筛选信息栏信息比较", searchTip, price,chamber,acreage,houseage);

			if(flag){				
				Report.writeHTMLLog("筛选不关联", "筛选找房条件匹配成功", Report.PASS, "");				
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("筛选不关联", "筛选条件匹配不成功，不是预期结果,用例执行失败", Report.FAIL, ps);				
				fail("筛选找房条件匹配失败");       
			}
			
            //切换到搜索tab页
			solo.clickOnText("附近");
			Report.writeHTMLLog("筛选不关联", "切换到附近tab页", Report.DONE, "");  
			solo.clickOnText("筛选");

			//当前选中的价格filter
			String currentPrice = (String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
			//当前选中的户型filter
			String currentChamber = (String)((TextView)solo.getView(R.id.filter_chamber_text_content)).getText();
			//当前选中的面积filter
			String currentAcreage = (String)((TextView)solo.getView(R.id.filter_acreage_text_content)).getText();
			//当前选中的房龄filter
			String currentHouseage = (String)((TextView)solo.getView(R.id.filter_houseage_text_content)).getText();

			Report.writeHTMLLog("筛选不关联", "当前显示的筛选条件为Price:"+ currentPrice+
					 " Chamber:"+currentChamber + " Acreage:"+currentAcreage+" Houseage:"+currentHouseage, Report.DONE, "");

			System.out.println("当前显示的筛选条件为Price:"+ currentPrice+
					 " Chamber:"+currentChamber + " Acreage:"+currentAcreage+" Houseage:"+currentHouseage);
			Boolean priceFlag = Action.assertString(solo, "比较价格", price, currentPrice);
			Boolean chamberFlag = Action.assertString(solo, "比较房源室数", chamber, currentChamber);
			Boolean acreageFlag = Action.assertString(solo, "比较面积", acreage, currentAcreage);
			Boolean houseageFlag = Action.assertString(solo, "比较年代", houseage, currentHouseage);

			if(priceFlag&&chamberFlag&&acreageFlag&&houseageFlag){
				String ps = Action.screenShot(solo);
				System.out.println("匹配完全正确，测试失败");
				Report.writeHTMLLog("筛选不关联", "匹配结果完全一致，测试失败", Report.FAIL, ps);
			}else{
				Report.writeHTMLLog("筛选不关联", "匹配结果不一致，测试成功", Report.PASS, "");
			}			
//			Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}
	}
}
	
	
	
