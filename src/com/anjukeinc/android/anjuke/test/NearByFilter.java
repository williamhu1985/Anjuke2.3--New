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
 * #1.附近页设置完筛选条件，包括售价、户型、面积、房龄
 * #2.检查头部灰色信息栏筛选条件是否符合
 * #3.检查房源列表页信息是否符合筛选条件
 */


public class NearByFilter extends ActivityInstrumentationTestCase2<WelcomeActivity>{
	private Solo solo;
	private int count = 3;
	private int interceptor = 0;
	Boolean scroolFlag = true;
	private String testName = "安居客Android客户端V2.3附近页筛选房源用例";
    


	//最先起到那个页面
	public NearByFilter(){
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
//			Report.writeHTMLLog("附近页筛选房源", "等待详细房源类表加载成功", Report.DONE, "");
			
			solo.clickOnText("附近");
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
			Action.setInWheelView(solo, 2, "面积");

			//点击“房龄”tab
			solo.clickOnView(solo.getView(R.id.filter_houseage_text_tip));     
			Action.setInWheelView(solo, 2, "房龄");

			//当前选中的价格filter
			String price = (String)((TextView)solo.getView(R.id.filter_price_text_content)).getText();
			
			//当前选中的户型filter
			String chamber = (String)((TextView)solo.getView(R.id.filter_chamber_text_content)).getText();
			//当前选中的面积filter
			String acreage = (String)((TextView)solo.getView(R.id.filter_acreage_text_content)).getText();
			//当前选中的房龄filter
			String houseage = (String)((TextView)solo.getView(R.id.filter_houseage_text_content)).getText();

			Report.writeHTMLLog("附近页筛选房源", "Price:"+ price+" Chamber:"+chamber + " Acreage:"+acreage+" Houseage:"+houseage +"---->", Report.DONE, "");

			solo.clickOnText("完成");
			solo.waitForText("筛选");
			String  searchTip= (String)((TextView)solo.getView(R.id.activity_near_property_list_tv_conditons)).getText();
			Report.writeHTMLLog("附近页筛选房源","serachTip:"+searchTip, Report.DONE, "");
			Boolean flag = Action.assertFilter(solo, "筛选条件和筛选信息栏信息比较", searchTip, price,chamber,acreage,houseage);

			if(flag){
				Report.writeHTMLLog("附近页筛选房源", "筛选找房条件匹配成功---->", Report.PASS, "");				
			}else{
				String ps = Action.screenShot(solo);
				Report.writeHTMLLog("附近页筛选房源", "筛选条件匹配不成功，不是预期结果,用例执行失败---->", Report.FAIL, ps);				
				fail("附近页筛选房源失败---->");       
			}
			
			//对价格过滤条件进行处理-去掉中间的空格
			if(price.indexOf("-")>0){
				price = price.substring(0, price.indexOf("-")-1) + "-" + price.substring(price.indexOf("-")+2, price.length()-2);
				Report.writeHTMLLog("附近页筛选房源", "去掉空格的价格条件为:"+price, Report.DONE, "");				
			}
			//点击显示的房源列表中的第一套房子
			solo.clickOnView(solo.getView(R.id.view_list_item_tv_title));
			//等待房源详细信息显示完全
			solo.waitForText("收藏");
			Report.writeHTMLLog("附近页筛选房源", "房源信息详细页加载成功", Report.DONE, "");				
		
			//获取房源详细页中的售价.户型.面积.房龄
			String propPrice = ((TextView)solo.getView(R.id.prop_total_price)).getText().toString();
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页价格"+propPrice, Report.DONE, "");	
			String propChamber = ((TextView)solo.getView(R.id.prop_type)).getText().toString();
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页室为"+propChamber, Report.DONE, "");	
			String propAcreage = ((TextView)solo.getView(R.id.prop_area)).getText().toString();
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页面积为"+propAcreage, Report.DONE, "");	
			String propHouseage = ((TextView)solo.getView(R.id.prop_build)).getText().toString();
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页年代为"+propHouseage, Report.DONE, "");	
			
			//对价格.户型.面积.房龄进行处理
			//价格直接是整数不用处理
//			int interceptor = propPrice.indexOf("万");
//			propPrice  = propPrice.substring(0, interceptor);
//			System.out.println("房源详细信息页价格1---->"+propPrice);
			
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页价格为"+propPrice, Report.DONE, "");	

			//户型
			interceptor = propChamber.indexOf("室");
			propChamber = propChamber.substring(0, interceptor);
			if(propChamber.equals("1")){
				propChamber = "一室";
			}else if(propChamber.equals("2")){
				propChamber = "二室";
			}else if(propChamber.equals("3")){
				propChamber = "三室";
			}else if(propChamber.equals("4")){
				propChamber = "四室";
			}else if(propChamber.equals("5")){
				propChamber = "五室";
			}else{
				propChamber = "五室以上";
			}
			Report.writeHTMLLog("附近页筛选房源", "房源详细信息页室数为"+propChamber, Report.DONE, "");	
			
			//面积
			interceptor = propAcreage.indexOf("平");
			propAcreage = propAcreage.substring(0, interceptor);
			Report.writeHTMLLog("附近页筛选房源","房源详细信息页面积为"+propAcreage, Report.DONE, "");	
		
			//年代
			interceptor = propHouseage.indexOf("年");
			propHouseage = propHouseage.substring(0, interceptor);
			Report.writeHTMLLog("附近页筛选房源","房源详细信息页年代为"+propHouseage, Report.DONE, "");		
			Report.writeHTMLLog("附近页筛选房源","房源详细信息页信息为"+ propPrice +" " + propChamber + " " + propAcreage + " " + propHouseage, Report.DONE, "");		
			
			//比较房子价格是否在价格筛选区间内
			Action.assertPrice(solo,price,propPrice);
			
			//比较户型
			Action.assertString(solo,"户型",chamber,propChamber);
			
			//比较面积
			Action.assertAcreage(solo,acreage,propAcreage);
			
			//比较年代
			Action.assertHouseage(solo,houseage,propHouseage);			
			Report.writeHTMLLog("附近页筛选房源", "用例成功执行完毕", Report.PASS, "");			
//      		Report.seleniumReport();
		} catch (Exception e) {
			String ps = Action.screenShot(solo);
			Report.writeHTMLLog("出现异常:"+ e.getMessage(), "脚本停止", Report.FAIL, ps);
//			Report.seleniumReport();
		}
	}
	

	
	
}
	
	
	
