package com.anjuke.android.util;


import java.util.Map;

public class Init {
	final public static Map<String, String> G_config = LogFile.readConfig();// 存放配置文件中的信息
	public static String G_Result = "start";

//	@BeforeSuite
//	public void beforeSuite() {
//		Init.G_Result = "start";
//	}

//	@Before
//	public void afterSuite() {
//		if (Report.getG_FAIL_CNT() == 0) {
//			Report.writeHTMLLog("*****脚本执行状况*****", "执行完毕", Report.DONE, "");
//		} else {
//			Report.writeHTMLLog("*****脚本执行状况*****", "异常终止", Report.FAIL, "");
//		}
//		String str = Init.G_config.get("isSendReportOrMail");
//		if( str!= null && (str.equals("1") || str.equals("2"))){
//			Report.seleniumReport();
//		}
//	}
}
