package com.xwwx.sewage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwwx.sewage.bean.TCsGoodsDept;
import com.xwwx.sewage.bean.TCsGoodsType;
import com.xwwx.sewage.bean.TCsOrder;
import com.xwwx.sewage.common.CommTime;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.dao.OrderDao;
import com.xwwx.sewage.service.OrderService;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderDao orderDao;

	public int insertOrder(TCsOrder order) throws Exception {

		return orderDao.insertOrder(order);
	}
	//更新订单
	public int updateOrder(TCsOrder order) throws Exception{
		
		return orderDao.updateOrder(order);
	}
	//删除订单
	public int deleteOrder(TCsOrder order) throws Exception{
		return orderDao.deleteOrder(order);
	}
	// 插入手工数据
	public int insertManualOrder(TCsOrder order) throws Exception {

		return orderDao.insertManualOrder(order);
	}

	// 查找目前等待封签的数据
	public List<TCsOrder> getOrderWaitRfidCode(@Param("sgoodsdept") String[] sgoodsdept) {

		return orderDao.getOrderWaitRfidCode(sgoodsdept);
	}

	public int updateOrderPdaSGoods(TCsOrder order) throws Exception {

		return orderDao.updateOrderPdaSGoods(order);
	}

	public int updateOrderPdaRGoods(TCsOrder order) throws Exception {

		return orderDao.updateOrderPdaRGoods(order);
	}

	// 发货pda封签
	public synchronized String pdaSGoodsRfid(String sGoodsDept, String rfidCode) {

		try {
			String[] depts = sGoodsDept.split("###");
			// 是否有需要封签的记录
			List<TCsOrder> orderList = orderDao.getOrderWaitRfidCode(depts);
			if (orderList == null || orderList.isEmpty() || orderList.size() < 1) {
				return "0";
			} else {
				if (orderList.size() > 1) {
					return "2";
				} else {
					//判断是否已经存在该标签
					List<TCsOrder> orderListtmp = getOrderByRfidCode(rfidCode);
					if(orderListtmp!=null && orderListtmp.size()>0) {
						return "3";
					}
					
					TCsOrder tCsOrder = orderList.get(0);
					tCsOrder.setRfid(rfidCode);
					tCsOrder.setRfidstatus(1);
					updateOrderPdaSGoods(tCsOrder);
					return tCsOrder.getCarnum();
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "-1";
		}
	}
	//查询等待收货数据
	public List<TCsOrder> getOrderByRfidCode(@Param("rfidcode") String rfidcode) {
		
		return orderDao.getOrderByRfidCode(rfidcode);
	}
	// 收货
	public synchronized String pdaRGoods(String rfidCode, String lo, String la) {
		try {
			List<TCsOrder> orderList = new ArrayList<TCsOrder>();
			orderList =  getOrderByRfidCode(rfidCode);
			
			if(orderList == null || orderList.size()<1) {
				//没有要收货的数据
				return "0";
			}
			if(orderList.size()>1) {
				//该封签rfid码对应多条业务数据
				return "100";
			}
			
			TCsOrder tCsOrder = orderList.get(0);
			if(tCsOrder.getRgoodsstatus() == 1) {
				//已经被收货
				return "-2"+tCsOrder.getCarnum();
			}

			tCsOrder.setRfid(rfidCode);
			if (StringUtils.isBigDecimal(lo)) {
				BigDecimal bd = new BigDecimal(lo);
				tCsOrder.setLo(bd);
			} else {
				tCsOrder.setLo(null);
			}
			if (StringUtils.isBigDecimal(la)) {
				BigDecimal bd = new BigDecimal(la);
				tCsOrder.setLa(bd);
			} else {
				tCsOrder.setLa(null);
			}
			tCsOrder.setRgoodstime(CommTime.getCurrTime2());
			tCsOrder.setRgoodsstatus(1);
			tCsOrder.setRgoodsostatus(0);
			//tCsOrder.setStatus(0);
			//判断是否有封签数据
			updateOrderPdaRGoods(tCsOrder);
			String tmp = tCsOrder.getCarnum();
			return tmp+" 扫码收货成功!";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "-1";
		}
	}

	// 分页 业务查询
	public PageList getOrderList(String orderby, String ascordesc, Pages pages, Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getOrderListCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());// 每页显示数量
		paramMap.put("start", (pages.getPage() - 1) * pages.getPerPageNum());// 开始记录号
		List list = orderDao.getOrderList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}

	// 分页 业务查询数量
	public int getOrderListCount(Map paramMap) {
		return orderDao.getOrderListCount(paramMap);
	}

	// 分页 业务遗漏查询
	public PageList getOrderOmitList(String orderby, String ascordesc, Pages pages, Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getOrderOmitListCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());// 每页显示数量
		paramMap.put("start", (pages.getPage() - 1) * pages.getPerPageNum());// 开始记录号
		List list = orderDao.getOrderOmitList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}

	// 分页 业务遗漏查询数量
	public int getOrderOmitListCount(Map paramMap) {
		return orderDao.getOrderOmitListCount(paramMap);
	}

	// 总重量
	public Double getOrderListWeight(Map paramMap) {
		return orderDao.getOrderListWeight(paramMap);
	}

	// 导出数据
	public List<TCsOrder> exportOrderList(String orderby, String ascordesc, Map paramMap) {
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		return orderDao.exportOrderList(paramMap);
	}

	// 查询货物类型
	public List<TCsGoodsType> getAllSGoodsTypeList() {
		return orderDao.getAllSGoodsTypeList();
	}

	// 查询全部发货部门
	public List<TCsGoodsDept> getAllSGoodsDeptList() {

		return orderDao.getAllSGoodsDeptList();
	}

	// 分页 发货部门
	public PageList getSGoodsDeptList(String orderby, String ascordesc, Pages pages, Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getSGoodsDeptCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());// 每页显示数量
		paramMap.put("start", (pages.getPage() - 1) * pages.getPerPageNum());// 开始记录号
		List<TCsGoodsDept> list = orderDao.getSGoodsDeptList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}

	// 分页 发货部门数量
	public int getSGoodsDeptCount(Map paramMap) {
		return orderDao.getSGoodsDeptCount(paramMap);
	}

	// 增加发货部门
	public int insertSGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.insertSGoodsDept(dept);
	}

	// 修改发货部门
	public int updateSGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.updateSGoodsDept(dept);
	}

	// 删除发货部门
	public int deleteSGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.deleteSGoodsDept(dept);
	}

	// 查询全部收货部门
	public List<TCsGoodsDept> getAllRGoodsDeptList() {

		return orderDao.getAllRGoodsDeptList();
	}

	// 分页 发货部门
	public PageList getRGoodsDeptList(String orderby, String ascordesc, Pages pages, Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getRGoodsDeptCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());// 每页显示数量
		paramMap.put("start", (pages.getPage() - 1) * pages.getPerPageNum());// 开始记录号
		List<TCsGoodsDept> list = orderDao.getRGoodsDeptList(paramMap);
		for (TCsGoodsDept dept : list) {
			System.out.println(dept.getDeptname() + "........");
		}
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}

	// 分页 发货部门数量
	public int getRGoodsDeptCount(Map paramMap) {
		return orderDao.getRGoodsDeptCount(paramMap);
	}

	// 增加发货部门
	public int insertRGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.insertRGoodsDept(dept);
	}

	// 修改发货部门
	public int updateRGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.updateRGoodsDept(dept);
	}

	// 删除发货部门
	public int deleteRGoodsDept(TCsGoodsDept dept) throws Exception {
		return orderDao.deleteRGoodsDept(dept);
	}

	// 根据流水号取业务数据
	public TCsOrder getOrderById(Integer id) {

		return orderDao.getOrderById(id);
	}

	// 更新备注
	public int updateRemark(Integer id, String remark) throws Exception {
		return orderDao.updateRemark(id, remark);
	}

	// 设置遗漏
	public int updateOmitStatus(@Param("id") Integer id) throws Exception {
		return orderDao.updateOmitStatus(id);
	}

	// 分页 手工业务查询
	public PageList getOrderManualList(String orderby, String ascordesc, Pages pages, Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getOrderManualListCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());// 每页显示数量
		paramMap.put("start", (pages.getPage() - 1) * pages.getPerPageNum());// 开始记录号
		List list = orderDao.getOrderManualList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}

	// 分页 手工业务查询数量
	public int getOrderManualListCount(Map paramMap) {
		return orderDao.getOrderManualListCount(paramMap);
	}

	// 删除手工数据
	public int deleteOrderManual(@Param("id") Integer id) throws Exception {
		return orderDao.deleteOrderManual(id);
	}

	// 更新手工数据
	public int updateOrderManual(TCsOrder order) throws Exception {
		return orderDao.updateOrderManual(order);
	}
	//收货统计封签数据
	public List<Map<String,Integer>> getOrderRfidCountR(Map paramMap){
		return orderDao.getOrderRfidCountR(paramMap);
	}
	//发货统计封签数据
	public List<Map<String,Integer>> getOrderRfidCountS(Map paramMap){
		return orderDao.getOrderRfidCountS(paramMap);
	}
}
