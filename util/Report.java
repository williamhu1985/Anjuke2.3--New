package com.anjuke.android.util;


import java.io.IOException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

//import com.anjukeinc.ui.Init;




public class Report {
	private static String G_HTMLLog = ""; // HTML Log
	private static String G_TCList = ""; // 已执行用例集
	private static String webHeader = null;
	private static int G_DONE_CNT = 0;
	private static int G_PASS_CNT = 0;
	private static int G_FAIL_CNT = 0;
	private static int G_WARNING_CNT = 0;
	private static int G_OTHERS_CNT = 0; // 记录操作步骤各状态数量
	private static List<Date> G_ExecDateTime = new ArrayList<Date>();
	private static long systemTime=System.currentTimeMillis()+1000*60*60*8;
	private static String nowDateTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date(systemTime));// 存报告和截图用的
	
//	private static long plusTime=1000*60*60*8;
	final public static String DONE = "DONE";
	final public static String PASS = "PASS";
	final public static String FAIL = "FAIL";
	final public static String WARNING = "WARNING";

	public static int getG_FAIL_CNT() {
		return G_FAIL_CNT;
	}

	final public static String getNowDateTime() {
		return nowDateTime;
	}

	public static void setWebHeader(String webHeader) {
		Report.webHeader = webHeader;
	}

	

	/**
	 * 记录脚本运行时的HTML格式log
	 * 
	 * @param string $sTCase 测试业务
	 * @param string $sDetails 操作描述
	 * @param string $sStatus 操作状态 done=操作成功 pass=验证成功 fail=有错误发生 warning=警告
	 * @param string $img 截图文件名
	 * @return boolean
	 */
	final public static boolean writeHTMLLog(String sTCase, String sDetails, String sStatus, String sImg) {
		String cFont = "";
		String imgLink = "";// 放图片完整路径

		sTCase = htmlspecialchars(sTCase);
		sDetails = htmlspecialchars(sDetails);
		

		Date date1 = new Date(System.currentTimeMillis()+1000*60*60*8);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetimeString = df.format(date1);
		G_ExecDateTime.add(date1);

		if (sImg != null && sImg != "") {
			//imgLink = "E:/test/" + nowDateTime + "/" + sImg+".png" ;
			imgLink = sImg+".png" ;
		}

		sStatus = sStatus.toUpperCase();

		if (sStatus == "DONE") {
			G_DONE_CNT++;
			cFont = "#000000";
		} else if (sStatus == "PASS") {
			G_PASS_CNT++;
			cFont = "#008000";
		} else if (sStatus == "FAIL") {
			G_FAIL_CNT++;
			cFont = "#ff0000";
		} else if (sStatus == "WARNING") {
			G_WARNING_CNT++;
			cFont = "#0000ff";
		} else {
			G_OTHERS_CNT++;
			cFont = "#969696";
		}

		G_HTMLLog = G_HTMLLog + "<tr>";
		G_HTMLLog = G_HTMLLog + "<td>" + datetimeString + "</td>";
		G_HTMLLog = G_HTMLLog + "<td>" + sTCase + "</td>";
		G_HTMLLog = G_HTMLLog + "<td><b><font color=" + cFont + ">" + sStatus + "</font></b></td>";
		G_HTMLLog = G_HTMLLog + "<td>" + sDetails + "</td>";
//		G_HTMLLog = G_HTMLLog + "<td><a href=\"" + imgLink + "\" target =\"_blank\">" + sImg + "</a></td>";
		if(sImg!=null && sImg !=""){
			G_HTMLLog = G_HTMLLog + "<td><a href=\"" + imgLink + "\" target =\"_blank\"><img src="+imgLink+" width=50 hight=50></a></td>";
			}else{
				G_HTMLLog = G_HTMLLog + "<td></td>";
			}
		G_HTMLLog = G_HTMLLog + "</tr>";

		return true;
	}

	/**
	 * html标签转义
	 * 
	 * @param String html
	 * @return String
	 */
	private static String htmlspecialchars(String html) {
		if (html == null) {
			return "";
		}
		Map<String, String> special = new HashMap<String, String>();
		special.put("&", "&amp;");
		special.put("\"", "&quot;");
		special.put("'", "&#039;");
		special.put("<", "&lt;");
		special.put(">", "&gt;");
		Iterator<Entry<String, String>> iter = special.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (html.contains(key)) {
				html.replace(key, val);
			}
		}
		return html;
	}

	/**
	 * 生成报表信息
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 * @throws AddressException
	 */
	final public static void seleniumReport() {
		int TOTAL_CNT = 0;
		String sLog = "";
		String timeDiff = "";
		long quot = 0;
		String firstDateTime = "";
		String lastDateTime = "";

		// java.util.Date date1=new java.util.Date();

		if (G_ExecDateTime.size() != 0) {
			Collections.sort(G_ExecDateTime); // 执行时间排序
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			firstDateTime = df.format(G_ExecDateTime.get(0));
			lastDateTime = df.format(G_ExecDateTime.get(G_ExecDateTime.size() - 1));
			quot = G_ExecDateTime.get(G_ExecDateTime.size() - 1).getTime() - G_ExecDateTime.get(0).getTime();
			timeDiff = String.valueOf(quot / 1000);
		}

		TOTAL_CNT = G_DONE_CNT + G_PASS_CNT + G_FAIL_CNT + G_WARNING_CNT + G_OTHERS_CNT;

		sLog = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'>";
		sLog = sLog + "<style type='text/css'>body,table{font-size:13px;}</style></head><body>";
		sLog = sLog + "<table border='0' width='60%'><tr><td>";

		// 执行结果统计
		sLog = sLog + "<table border='1' width='80%' style='border-collapse:collapse;' bordercolor='#dddddd'>";
		
		sLog = sLog + "<tr><td><b>机型:</b></td><td>" + android.os.Build.MODEL+ "</td></tr>";
		sLog = sLog + "<tr><td><b>SDK版本:</b></td><td>" + android.os.Build.VERSION.SDK+ "</td></tr>";
		sLog = sLog + "<tr><td><b>系统版本:</b></td><td>" +  android.os.Build.VERSION.RELEASE+ "</td></tr>";
		sLog = sLog + "<tr><td><b>执行时间:</b></td><td>" + firstDateTime + "</td></tr>";
		sLog = sLog + "<tr><td><b>结束时间:</b></td><td>" + lastDateTime + "</td></tr>";
		sLog = sLog + "<tr><td><b>执行时长:</b></td><td>" + timeDiff + "</td></tr>";
		sLog = sLog + "<tr><td><b>执行总数:</b></td><td>" + String.valueOf(TOTAL_CNT) + "</td></tr>";
//		sLog = sLog + "<tr><td><b>代码执行机器:</b></td><td>" + Init.G_config.get("rcIp") + "</td></tr>";
//		sLog = sLog + "<tr><td><b>RC服务器:</b></td><td>" + Init.G_config.get("rcIp") + "</td></tr>";
		sLog = sLog + "</table>";

		sLog = sLog + "</td><td>";

		// 操作数统计
		sLog = sLog + "<table border='1' width='100%' style='border-collapse:collapse;' bordercolor='#dddddd'>";
		sLog = sLog + "<tr>";
		sLog = sLog + "<td bgcolor='#003366' width='15%'><font color='#FFFFFF'><b>结果</b></font></td>";
		sLog = sLog + "<td bgcolor='#003366' width='35%'><font color='#FFFFFF'><b>数量</b></font></td>";
		sLog = sLog + "</tr>";
		sLog = sLog + "<tr><td><b><font color='#000000'>DONE</font></b></td><td>" + String.valueOf(G_DONE_CNT) + "</td></tr>";
		sLog = sLog + "<tr><td><b><font color='#008000'>PASS</font></b></td><td>" + String.valueOf(G_PASS_CNT) + "</td></tr>";
		sLog = sLog + "<tr><td><b><font color='#ff0000'>FAIL</font></b></td><td>" + String.valueOf(G_FAIL_CNT) + "</td></tr>";
		sLog = sLog + "<tr><td><b><font color='#0000ff'>WARNING</font></b></td><td>" + String.valueOf(G_WARNING_CNT) + "</td></tr>";
		sLog = sLog + "<tr><td><b><font color='#969696'>OTHERS</font></b></td><td>" + String.valueOf(G_OTHERS_CNT) + "</td></tr>";
		sLog = sLog + "</table>";

		sLog = sLog + "</td></tr></table><br>";

		sLog = sLog + "<table border='1' width='30%' style='border-collapse:collapse;' bordercolor='#dddddd'>";
		sLog = sLog + "<tr><td bgcolor='#800000' ><font color='#FFFFFF'><b>用例名称</b></font></td></tr>";
		sLog = sLog + G_TCList;
		sLog = sLog + "</table><br>";

		// 具体操作、检查点明细
		sLog = sLog + "<table border='1' width='100%' style='border-collapse:collapse;' bordercolor='#dddddd'>";
		sLog = sLog + "<tr>";
		sLog = sLog + "<td bgcolor='#003366' width='15%'><font color='#FFFFFF'><b>发生时间</b></font></td>";
		sLog = sLog + "<td bgcolor='#003366' width='20%'><font color='#FFFFFF'><b>测试业务</b></font></td>";
		sLog = sLog + "<td bgcolor='#003366' width='10%'><font color='#FFFFFF'><b>结果</b></font></td>";
		sLog = sLog + "<td bgcolor='#003366' width='40%'><font color='#FFFFFF'><b>注释</b></font></td>";
		sLog = sLog + "<td bgcolor='#003366' width='15%'><font color='#FFFFFF'><b>截图</b></font></td>";
		sLog = sLog + "</tr>";
		
		G_HTMLLog = sLog + G_HTMLLog + "</body></html>";
		LogFile.writelog("sdcard/Robotium/" + nowDateTime + "/", G_HTMLLog);

	}

	/**
	 * 记录用例名称
	 * 
	 */
	public static void setTCNameLog(String tcName) {
		String tmpTCName = "";
		tmpTCName = "<b><font color='#0000ff'>******************************<br>";
		tmpTCName = tmpTCName + "*****　　" + tcName + "<br>";
		tmpTCName = tmpTCName + "******************************</font></b>";

		Report.writeHTMLLog("", tmpTCName, "", "");

		G_TCList = G_TCList + "<tr><td>" + tcName + "</td></tr>";
	}
}
