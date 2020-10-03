package com.xwwx.sewage.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 通用时间相关类
 * @author Administrator
 * @since 2016-05-27
 */
public class CommTime {

	public static int getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinute() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}
	public static String getNewTime(){
        SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyyMMddHHmmss");
        GregorianCalendar gc=new GregorianCalendar();
        return dateFormatter.format(gc.getTime());
	}
	public static String getNewSSSTime(){
        SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyyMMddHHmmssSSS");
        GregorianCalendar gc=new GregorianCalendar();
        return dateFormatter.format(gc.getTime());
	}
	public static String getCurrTime() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd-HH:mm:ss");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}
	public static String getCurrTime2() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}
	public static String getCurrTime1() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}

	public static String getCurrDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}

	public static String getCurrDate2() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy年MM月dd日");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}

	public static String getCurrDate1() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}

	public static String getNewDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}
	//得到年月
	public static String getYear_Month() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
		GregorianCalendar gc = new GregorianCalendar();
		return dateFormatter.format(gc.getTime());
	}

	/*
	 * 日期比较
	 */
	public static boolean compareDate(String d1,String d2){
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date dt1 = df.parse(d1);
			Date dt2 = df.parse(d2);
			if (dt1.getTime() > dt2.getTime()) {
				return true;
			}else {
				return false;
			}				
		}catch(Exception e){
			return false;
		}

	}
	
	public static int getYear() {
		Calendar CD = Calendar.getInstance();
		int YY = CD.get(Calendar.YEAR);

		return YY;
	}

	public static int getmonth() {
		Calendar CD = Calendar.getInstance();
		int MM = CD.get(Calendar.MONTH) + 1;
		return MM;
	}

	public static String getYearMonthDay(String str) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String temp = "";
		Date date = null;
		try {
			date = dateFormatter.parse(str);
		} catch (Exception e) {
			//System.out.print(e.getMessage());
		}
		temp = (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月"
				+ date.getDate() + "日";
		return temp;
	}
	public static String getYearMonthDay1(String str) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd_HH:mm:ss");
		String temp = "";
		Date date = null;
		try {
			date = dateFormatter.parse(str);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		temp = (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月"
				+ date.getDate() + "日";
		return temp;
	}
	// 根据字符串得到date类型
	public static Date getDate(String str) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String temp = "";
		Date date = null;
		try {
			date = dateFormatter.parse(str);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return date;
	}

	// 根据当前时间得到一个时间段后的日期格式
	public static String getFutureDate(long millisecond) {

		Date date = new Date();
		Date date1 = new Date(date.getTime() + millisecond);

		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = dateFormatter.format(date1);
		return time;

	}
	//将字符串转化为date格式
	public static String getDateBystr(Date date){
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = "";
		try {
			datestr = sformat.format(date);
			System.out.println(datestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datestr;
	}	
	//将字符串转化为date格式
	public static String getStrByDate(Date date){
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = "";
		try {
			datestr = sformat.format(date);
			System.out.println(datestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datestr;
	}
	//将字符串转化为date格式
	public static Date getDateBystr(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	//将字符串转化为date格式
	public static String getDateBystr1(String str){  
		String sdate="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");   
		try{
			Date d = df.parse(str);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");   
		    sdate = sdf.format(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdate;
	}	
	//将字符串转化为date格式
	public static String getDateBystr2(String str){  
		String sdate="";
		String stime="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");   
		try{
			Date d = df.parse(str);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:ss:mm"); 
		    sdate = sdf1.format(d);
		    stime = sdf2.format(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdate+"_"+stime;
	}	
	
	//将字符串转化为时间格式
	public static String getDateBystr3(String str){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");  
		String stime = "";
		try{
			Date d = df.parse(str);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:ss:mm"); 
		    stime = sdf.format(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		return stime;
	}
	
	//封装日期时间字符串
	public static String getDateSfm(String str,int type){
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		if(type == 1){
			sb.append(" 00:00:00");
		}else{
			sb.append(" 23:59:59");
		}
		return sb.toString();
	}
	//得到一年内的月份  包括本月
	public static String[] getYearMonth(){
		
		String[] yearmonth = new String[14];
		int yeartmp = getYear();
		int monthtmp = getmonth();
		for(int i=0;i<14;i++){
			if(monthtmp == 0){
				yeartmp = yeartmp - 1;
				monthtmp = 12;
			}
			if(monthtmp>=10){
				yearmonth[i] = yeartmp + "-" + monthtmp;
			}else{
				yearmonth[i] = yeartmp + "-" + "0" + monthtmp;
			}
			monthtmp--;
		}
		return yearmonth;
	}
	//得到小时列表
	public static String[] getHours(){
		String[] hours = new String[24];
		for(int i=0;i<24;i++){
			if(i<10){
				hours[i] = "0" + i;
			}else{
				hours[i] = String.valueOf(i);
			}
		}
		return hours;
	}
	//得到分钟列表
	public static String[] getMinutes(){
		String[] minutes = new String[60];
		for(int i=0;i<60;i++){
			if(i<10){
				minutes[i] = "0" + i;
			}else{
				minutes[i] = String.valueOf(i);
			}
		}
		return minutes;
	}	
	//得到本年 指定长度列表
	public static String[] getYears(int num){
		String[] years = new String[num];
		int yeartmp = getYear();
		for(int i=0;i<num;i++){
			years[i] = String.valueOf(yeartmp-i);
		}
		return years;
	}
	
	//取指定年月最后一天
	public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
         
        return lastDayOfMonth;
    }

	//根据指定日期 得到第二天日期
	public static String getNextDayByDate(String str){
		String sdate="";
		String stime="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
		try{
			Date date = df.parse(str);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
			date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		    sdate = sdf1.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sdate;
	}
	public static void main(String args[]) {

		System.out.println(getLastDayOfMonth(2017,8));
	}
}