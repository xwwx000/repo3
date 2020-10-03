package com.xwwx.sewage.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.xwwx.sewage.bean.TCsOrder;

public class Constant {

	public static final String USER_SESSION_KEY = "user_session_sewage"; //用户session
	public static final String UNLOGON_JSP = "/views/common/unlogin.jsp";//非法登陆转向页
	public static final long JWT_EXPIRE_TIME = 60 * 60 * 1000; 			 //jwt token过期时间1小时
	public static final long REDIS_EXPIRE_TIME = 3600 * 24 * 2; 		 //redis token过期时间7天
	public static int ADMIN_DEFAULT_PAGE_SIZE = 15;//后台默认每页显示
	public static final String LIST_ASC = "asc";//按递增顺序列出数据
	public static final String LIST_DESC = "desc";//按递减顺序列出数据
	public static final String ARRAY_1 = "~";//根据~产生行数组
	public static final String ARRAY_2 = "`";//根据`产生列数组
	public static final String DEFAULT_USER_PWD = "8888";//默认密码 CF79AE6ADDBA60AD018347359BD144D2
	public static final String BAIDUMAP_KEY = "9a43211e9f10a98a83c41d6f877c0503";//百度用户key
	public static final String GAODE_KEY = "c2737995009d6b3bcfc3fadf6491de1d";//高德用户key
	public static final DecimalFormat df = new DecimalFormat("######0.000000");
	public static final DecimalFormat mileagedf = new DecimalFormat("######0.000");
	
	public static final String RESULT_SUCCESS = "1";
	public static final String RESULT_FAILED = "-8";
	
	public static String WRITE_LOG = "true";
	
	public static String LASTCARNUM = "";//最后上传的车牌号
	public static String LASTAPITIME = "";//最后上传的时间
	
	public static ArrayList<TCsOrder> LASTAPILIST = new ArrayList();
}
