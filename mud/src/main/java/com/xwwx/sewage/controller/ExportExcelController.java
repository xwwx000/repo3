package com.xwwx.sewage.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.bean.TCsOrder;
import com.xwwx.sewage.common.CommTime;
import com.xwwx.sewage.common.Constant;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.service.OrderService;

@Controller
@RequestMapping("/export")
public class ExportExcelController {
	private Logger logger = Logger.getLogger(ExportExcelController.class);
	@Autowired
	private OrderService orderService;

	@RequestMapping("/orderlist")
	public String orderlist(HttpServletRequest request, HttpServletResponse response) {
		String sgoodsdept = WebUtils.findParameterValue(request, "sgoodsdept");
		String rgoodsdept = WebUtils.findParameterValue(request, "rgoodsdept");
		String goodstype = WebUtils.findParameterValue(request, "goodstype");
		String rgoodsstatus = WebUtils.findParameterValue(request, "rgoodsstatus");
		StringBuffer sb = new StringBuffer();
		String stime = WebUtils.findParameterValue(request, "stime");
		String etime = WebUtils.findParameterValue(request, "etime");
		String status = WebUtils.findParameterValue(request, "status");
		if (StringUtils.isEmpty(stime)) {
			stime = CommTime.getCurrDate();
		}
		if (StringUtils.isEmpty(etime)) {
			etime = CommTime.getCurrDate();
		}
		TCommUser user = (TCommUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		Map paramMap = new HashMap();
		if (!StringUtils.isEmpty(sgoodsdept)) {
			paramMap.put("sgoodsdept", sgoodsdept);
		}
		if (!StringUtils.isEmpty(rgoodsdept)) {
			paramMap.put("rgoodsdept", rgoodsdept);
		}
		if (!StringUtils.isEmpty(goodstype)) {
			paramMap.put("goodstype", goodstype);
		}
		if (!StringUtils.isEmpty(rgoodsstatus)) {
			paramMap.put("rgoodsstatus", rgoodsstatus);
		} else {
			rgoodsstatus = "-1";
		}
		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			paramMap.put("stime", stime);
			paramMap.put("etime", etime);
		}
		if (!StringUtils.isEmpty(status)) {
			paramMap.put("status", status);
			if("1".equals(status)) {
				sb.append("运输遗漏数据");
			}else {
				sb.append("运输数据");
			}
		}else {
			sb.append("运输数据");
		}
		List<TCsOrder> orderList = orderService.exportOrderList("sgoodstime", Constant.LIST_ASC, paramMap);
		// 总重量
		Double weight = orderService.getOrderListWeight(paramMap);
		String wstr = "";
		if(weight !=null) {
			wstr = String.valueOf(weight)+"吨";
		}else {
			wstr = "0吨";
		}
		try {
			XSSFWorkbook workbook = exportExcelCsOrderList(orderList,wstr);
			if (workbook != null) {
				printExcel(workbook, response, sb.append(".xlsx").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public XSSFWorkbook exportExcelCsOrderList(List dataList,String allweight) throws Exception {
		XSSFWorkbook workbook = null;
		try {
			// 创建工作簿实例
			workbook = new XSSFWorkbook();
			// 创建工作表实例
			XSSFSheet sheet = workbook.createSheet("运输数据");
			// 设置列宽
			this.setSheetColumnWidth(sheet,13);
			// 获取样式
			XSSFCellStyle style = workbook.createCellStyle();
			//
			if (dataList != null && dataList.size() > 0) {
				// 创建第一行标题,标题名字的本地信息通过resources从资源文件中获取
				XSSFRow row0 = sheet.createRow((short) 0);// 建立新行
				this.createCell(row0, 0, style, XSSFCell.CELL_TYPE_STRING, "流水号");
				this.createCell(row0, 1, style, XSSFCell.CELL_TYPE_STRING, "车牌号");
				this.createCell(row0, 2, style, XSSFCell.CELL_TYPE_STRING, "货物类型");
				this.createCell(row0, 3, style, XSSFCell.CELL_TYPE_STRING, "实重(kg)");
				this.createCell(row0, 4, style, XSSFCell.CELL_TYPE_STRING, "发货单位");
				this.createCell(row0, 5, style, XSSFCell.CELL_TYPE_STRING, "发货人");
				this.createCell(row0, 6, style, XSSFCell.CELL_TYPE_STRING, "发货时间");
				this.createCell(row0, 7, style, XSSFCell.CELL_TYPE_STRING, "收货单位");
				this.createCell(row0, 8, style, XSSFCell.CELL_TYPE_STRING, "收货人");
				this.createCell(row0, 9, style, XSSFCell.CELL_TYPE_STRING, "收货时间");
				this.createCell(row0, 10, style, XSSFCell.CELL_TYPE_STRING, "状态");
				this.createCell(row0, 11, style, XSSFCell.CELL_TYPE_STRING, "备注");

				// 给excel填充数据
				for (int i = 0; i < dataList.size(); i++) {
					TCsOrder order = (TCsOrder) dataList.get(i);
					XSSFRow row = sheet.createRow((short) (i + 1));// 建立新行

					// 流水号
					this.createCell(row, 0, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getSnum()));
					// 车牌号
					this.createCell(row, 1, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getCarnum()));
					// 货物类型
					this.createCell(row, 2, style, XSSFCell.CELL_TYPE_STRING, StringUtils.nullOrBlankResult(order.getGoodstype()));
					// 实重
					String weight = "";
					if(order.getGoodsweight() != null) {
						weight = order.getGoodsweight().toString();
					}
					this.createCell(row, 3, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(weight));
					// 发货单位
					this.createCell(row, 4, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getSgoodsdept()));
					// 发货人
					this.createCell(row, 5, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getConsignor()));
					// 发货时间
					this.createCell(row, 6, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getSgoodstime()));
					// 收货单位
					this.createCell(row, 7, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getRgoodsdept()));
					// 收货人
					this.createCell(row, 8, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getConsignee()));
					// 收货时间
					this.createCell(row, 9, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getRgoodstime()));
					// 状态
					this.createCell(row, 10, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getRgoodsstatus()==0?"未收货":"已收货"));
					// 备注
					this.createCell(row, 11, style, XSSFCell.CELL_TYPE_STRING,
							StringUtils.nullOrBlankResult(order.getRemark()));
				}
				XSSFRow row = sheet.createRow((short) (dataList.size()+1));// 建立新行
				this.createCell(row, 2, style, XSSFCell.CELL_TYPE_STRING, "总重量:");
				this.createCell(row, 3, style, XSSFCell.CELL_TYPE_STRING, allweight);
			} else {
				this.createCell(sheet.createRow(0), 0, style, XSSFCell.CELL_TYPE_STRING, "查无资料");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private void setSheetColumnWidth(XSSFSheet sheet,int colcount) {
		// 根据你数据里面的记录有多少列，就设置多少列

		for(int i=0;i<colcount;i++) {
			sheet.setColumnWidth(i, 5000);
		}
	}

	// 创建Excel单元格
	private void createCell(XSSFRow row, int column, XSSFCellStyle style, int cellType, Object value) {
		XSSFCell cell = row.createCell((short) column);
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		if (style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
		case XSSFCell.CELL_TYPE_BLANK: {
		}
			break;
		case XSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(value.toString());
		}
			break;
		case XSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			// DecimalFormat format = new DecimalFormat("###,##0.00");
			// cell.setCellValue(Float.parseFloat(value.toString()));
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}
	}

	private void printExcel(XSSFWorkbook workbook, HttpServletResponse response, String filename) throws IOException {

		StringBuffer sb = new StringBuffer();
		OutputStream out = response.getOutputStream();
		response.setHeader("Content-disposition",
				sb.append("attachment; filename=").append(toUtf8String(filename)).toString());
		response.setContentType("application/msexcel;charset=UTF-8");
		workbook.write(out);
		out.flush();
		out.close();
	}

	public static String toUtf8String(String s) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					// System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}
