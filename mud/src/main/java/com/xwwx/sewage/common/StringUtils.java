package com.xwwx.sewage.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串公共类
 * @author Administrator
 *
 */
public class StringUtils {
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null");
	}
	/**
	 * 表单是否为空
	 */
	public static boolean isEmptys(String...args){
		for (String arg : args) {  
			if(isEmpty(arg)){
				return true;
			}
        }  
		return false;
	}
	/**
	 * 字符串为空返回""
	 * @param param
	 * @return
	 */
	public static String nullOrBlankResult(String param) {
		return (param == null || param.trim().length() == 0 || param.trim()
				.equals("")) ? "" : param.trim();
	}
	/** 
     * 获取访问者IP 
     *  
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 
     *  
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 
     * 如果还不存在则调用Request .getRemoteAddr()。 
     *  
     * @param request 
     * @return 
     */  
    public static String getIpAddr(HttpServletRequest request){  
        String ip = request.getHeader("X-Real-IP");  
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            return ip;  
        }  
        ip = request.getHeader("X-Forwarded-For");  
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
            int index = ip.indexOf(',');  
            if (index != -1) {  
                return ip.substring(0, index);  
            } else {  
                return ip;  
            }  
        } else {  
            return request.getRemoteAddr();  
        }  
    }  
    /**
     * 取整型数组中最大最小值
     */
    public static int[] getMaxMinFromIntArray(int[] iarray){

        int max = 0; // 记录最大值
        int min = 0;
 
        for (int i = 0; i < iarray.length; i++) {
            int temp = iarray[i];
            if(i == 0) {
                max = temp;
                min = temp;
            }
            if (temp > max) {
                max = temp;
            }
            if (temp < min) {
                min = temp;
            }
 
        }
        int[] itmp = {max,min};
        
        return itmp;
    }
    /**
     * 取字符串数组中最大最小值
     */
    public static double[] getMaxMinFromStringArray(String[] sarray){

        double max = 0; // 记录最大值
        double min = 0;
 
        for (int i = 0; i < sarray.length; i++) {
            double temp = Double.valueOf(sarray[i]);
            if(i == 0) {
                max = temp;
                min = temp;
            }
            if (temp > max) {
                max = temp;
            }
            if (temp < min) {
                min = temp;
            }
 
        }
        double[] itmp = {max,min};
        
        return itmp;
    }
    /**
     * 判断一个字符串是否能够转换为BigDecimal
     * @param str
     * @return
     */
    public static boolean isBigDecimal(String str){
    	if(str==null || str.trim().length() == 0){
    		return false;
    	}
    	char[] chars = str.toCharArray();
    	int sz = chars.length;
    	int i = (chars[0] == '-') ? 1 : 0;
    	if(i == sz) return false;
    	
    	if(chars[i] == '.') return false;//除了负号，第一位不能为'小数点'
    	
    	boolean radixPoint = false;
    	for(; i < sz; i++){
    		if(chars[i] == '.'){
    			if(radixPoint) return false;
    			radixPoint = true;
    		}else if(!(chars[i] >= '0' && chars[i] <= '9')){
    			return false;
    		}
    	}
    	return true;
    }
}
