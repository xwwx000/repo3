package com.xwwx.sewage.common;
/**
 * 功能:生成页属性对象
 * @author xwwx
 * @since 2010-05-20
 */
public class Pages {


	private int page = 1; 		//页号
	private long totalNum = -1; //记录总数
	private int perPageNum = 15;//每页显示记录数
	private int allPage = 1; 	//总页数
	private int cpage = 1; 		//当前页
	private int spage = 1; 		//开始记录数
	private String webpath;		//工程路径
	private String wherestr;	//条件字符串
	private String urlstr;		//返回连接字符串
	
	public Pages() {
		
	}

	public Pages(int page, long totalNum, int perPageNum) {
		
		this.page = page;
		this.totalNum = totalNum;
		this.perPageNum = perPageNum;
		this.executeCount();
	}

	public void executeCount() {
		
		this.allPage = (int) Math.ceil((this.totalNum + this.perPageNum - 1) / this.perPageNum);
		if(allPage<=0){
			cpage = 0;
			spage = 0;
		}else{
			cpage = this.page;
			if(cpage == 1){
				this.spage = 1;
			}else{
				this.spage = (this.cpage - 1) * this.perPageNum+1;
			}
		}
		setUrlStr();
	}
	
	public void setUrlStr(){
		
		StringBuffer sb = new StringBuffer();
		//共几条记录
		sb.append("共<span class=\"hot\">").append(totalNum).append("</span>条记录");
		//当前页大于1
		if(cpage>1){
			sb.append("<a href=\"#\" onclick=\"setpage("+(cpage-1)+")\" class=\"prev_page\" rel=\"prev\">&laquo; 上一页</a>");
		}else{
			sb.append("<SPAN class=current>&laquo; 上一页</SPAN>");
		}
		
		int num1 = 6;   //第一临界点
		int num2 = 10;  //第二临界点
		int num3 = 5;	//第三临界点
		if(allPage <= num2){
			//全部显示
			for(int i = 1;i<=allPage;i++){
				if(cpage == i){
					sb.append("<SPAN class=current>"+i+"</SPAN>");
				}else{
					sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
				}
			}
		}else{
			//显示三段页
			if(cpage < num1){
				//点头几页
				for(int i = 1;i<=num1;i++){
					if(cpage == i){
						sb.append("<SPAN class=current>"+i+"</SPAN>");
					}else{
						sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
					}
				}
				sb.append("<span class=\"gap\">&hellip;</span>");
				for(int i = allPage-1;i<=allPage;i++){
					sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
				}
			}
			
			
			if(cpage>=num1 && (allPage-cpage > num3-1)){
				//点中间几页
				for(int i = 1;i<=2;i++){
					sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
				}	
				sb.append("<span class=\"gap\">&hellip;</span>");
				for(int i = cpage-1;i<cpage+2;i++){
					if(cpage == i){
						sb.append("<SPAN class=current>"+i+"</SPAN>");
					}else{
						sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
					}					
				}
				sb.append("<span class=\"gap\">&hellip;</span>");
				for(int i = allPage-1;i<=allPage;i++){
					sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
				}
			}

			if(allPage-cpage <= num3-1){
				//点最后几页
				for(int i = 1;i<=2;i++){
					sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
				}
				sb.append("<span class=\"gap\">&hellip;</span>");
				for(int i = allPage-num3;i<=allPage;i++){
					if(cpage == i){
						sb.append("<SPAN class=current>"+i+"</SPAN>");
					}else{
						sb.append("<a href=\"#\" onclick=\"setpage("+i+")\" rel=\"prev\">"+i+"</a>");
					}
				}
			}
		}
		
		//当前页大于总页数
		if(cpage == allPage){
			sb.append("<SPAN class=current>下一页 &raquo;</SPAN>");
		}else{
			sb.append("<a href=\"#\" onclick=\"setpage("+(cpage+1)+")\" class=\"next_page\" rel=\"next\">下一页 &raquo;</a>");
		}
		sb.append("每页显示<span class=\"hot\">").append(perPageNum).append("</span>条记录");
		urlstr = sb.toString();
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public int getCpage() {
		return cpage;
	}

	public void setCpage(int cpage) {
		this.cpage = cpage;
	}

	public int getSpage() {
		return spage;
	}

	public void setSpage(int spage) {
		this.spage = spage;
	}

	public String getUrlstr() {
		return urlstr;
	}

	public String getWebpath() {
		return webpath;
	}

	public void setWebpath(String webpath) {
		this.webpath = webpath;
	}

	public String getWherestr() {
		return wherestr;
	}

	public void setWherestr(String wherestr) {
		this.wherestr = wherestr;
	}

	public void setUrlstr(String urlstr) {
		this.urlstr = urlstr;
	}

}
