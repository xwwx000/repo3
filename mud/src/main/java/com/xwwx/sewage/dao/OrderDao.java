package com.xwwx.sewage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xwwx.sewage.bean.TCsGoodsDept;
import com.xwwx.sewage.bean.TCsGoodsType;
import com.xwwx.sewage.bean.TCsOrder;

public interface OrderDao {
	//插入数据
	public int insertOrder(TCsOrder order) throws Exception;
	//更新订单
	public int updateOrder(TCsOrder order) throws Exception;
	//删除订单
	public int deleteOrder(TCsOrder order) throws Exception;
	//插入手工数据
	public int insertManualOrder(TCsOrder order) throws Exception;
	//查找目前等待封签的数据
	public List<TCsOrder> getOrderWaitRfidCode(@Param("sgoodsdept") String[] sgoodsdept);
	//查询等待收货数据
	public List<TCsOrder> getOrderByRfidCode(@Param("rfidcode") String rfidcode);
	//pda发货 更新业务数据
	public int updateOrderPdaSGoods(TCsOrder order) throws Exception;
	//pda收货 更新业务数据
	public int updateOrderPdaRGoods(TCsOrder order) throws Exception;
	//分页 业务查询
	public List<TCsOrder> getOrderList(Map paramMap);
	//分页 业务查询数量
	public int getOrderListCount(Map paramMap);
	//分页 业务查询
	public List<TCsOrder> getOrderOmitList(Map paramMap);
	//分页 业务查询数量
	public int getOrderOmitListCount(Map paramMap);
	//总重量
	public Double getOrderListWeight(Map paramMap);
	//导出数据
	public List<TCsOrder> exportOrderList(Map paramMap);
	//查询货物类型
	public List<TCsGoodsType> getAllSGoodsTypeList();
	
	//查询全部发货部门
	public List<TCsGoodsDept> getAllSGoodsDeptList();
	//分页 发货部门
	public List<TCsGoodsDept> getSGoodsDeptList(Map paramMap);
	//分页 发货部门数量
	public int getSGoodsDeptCount(Map paramMap);
	//增加发货部门
	public int insertSGoodsDept(TCsGoodsDept dept)throws Exception;
	//修改发货部门
	public int updateSGoodsDept(TCsGoodsDept dept)throws Exception;
	//删除发货部门
	public int deleteSGoodsDept(TCsGoodsDept dept)throws Exception;
	
	//查询全部收货部门
	public List<TCsGoodsDept> getAllRGoodsDeptList();
	//分页 收货部门
	public List<TCsGoodsDept> getRGoodsDeptList(Map paramMap);
	//分页 收货部门数量
	public int getRGoodsDeptCount(Map paramMap);
	//增加收货部门
	public int insertRGoodsDept(TCsGoodsDept dept)throws Exception;
	//修改收货部门
	public int updateRGoodsDept(TCsGoodsDept dept)throws Exception;
	//删除收货部门
	public int deleteRGoodsDept(TCsGoodsDept dept)throws Exception;
	
	//根据流水号取业务数据
	public TCsOrder getOrderById(@Param("id") Integer id);
	//更新备注
	public int updateRemark(@Param("id") Integer id,@Param("remark") String remark) throws Exception;
	//设置遗漏
	public int updateOmitStatus(@Param("id") Integer id) throws Exception;
	//分页 手工业务查询
	public List<TCsOrder> getOrderManualList(Map paramMap);
	//分页 手工业务查询数量
	public int getOrderManualListCount(Map paramMap);
	//删除手工数据
	public int deleteOrderManual(@Param("id") Integer id) throws Exception;
	//更新手工数据
	public int updateOrderManual(TCsOrder order) throws Exception;
	//收货单位统计封签数据
	public List<Map<String,Integer>> getOrderRfidCountR(Map paramMap);
	//发货单位统计封签数据
	public List<Map<String,Integer>> getOrderRfidCountS(Map paramMap);
}
