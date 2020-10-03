package com.xwwx.sewage.cache;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.xwwx.sewage.bean.PickList;
import com.xwwx.sewage.bean.TCsGoodsDept;
import com.xwwx.sewage.bean.TCsOrder;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.service.OrderService;
import com.xwwx.sewage.service.PdaService;
import com.xwwx.sewage.service.PickListService;
@Component
@PropertySource(value={"classpath:jdbc.properties"})
public class Cache{
	@Autowired
	private PdaService pdaService;
	@Autowired
	private PickListService pickListService;
	@Autowired
	private OrderService orderService;
	@Value("${write.log}")
	private String logflag;
	//送货状态
	public static List<PickList> shzt_list = new ArrayList<PickList>();
	
	//记录最后上传数据
	public static List<TCsOrder> lastapi_list = new ArrayList<TCsOrder>();
	private Logger logger = Logger.getLogger(Cache.class);
	public static List<String> macAddrList = new ArrayList<String>();
	public void init(){
		Constant.WRITE_LOG = logflag;
		System.out.println(logflag);
		reload();
		setLaseApiData();
	}
	public void reload(){
		macAddrList = pdaService.getAllPdaMacAddrList();
		shzt_list = pickListService.getPickListById("shzt");
		logger.info("cache reload" +macAddrList.toString());
	}
	
	//插入上传最后一条记录列表
	public void setLaseApiData() {
		lastapi_list.clear();
		
		List<TCsGoodsDept> goodsdeptlist = orderService.getAllSGoodsDeptList();
		for(TCsGoodsDept dept:goodsdeptlist) {
			TCsOrder order = new TCsOrder();
			order.setCarnum("");
			order.setSgoodstime("");
			order.setSgoodsdept(dept.getDeptname());
			order.setRemark(dept.getDeptmark());
			lastapi_list.add(order);
		}
	}
}
