package com.lcf.erp.controller;

import java.awt.Font;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.lcf.erp.entity.Emp;
import com.lcf.erp.entity.OrderReport;
import com.lcf.erp.entity.Orders;
import com.lcf.erp.entity.OrdersDetail;
import com.lcf.erp.exception.OutOfStockException;
import com.lcf.erp.service.IOrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {
	@Autowired
	private IOrdersService ordersService;
	
	@RequestMapping("/list.do")
	public void list() {}
	
	@RequestMapping("/add.do")
	public void add() {}
	
	//跳转到订单审核页面
	@RequestMapping("/check.do")
	public void check() {}
	
	//跳转到订单确认页面
	@RequestMapping("/start.do")
	public void start() {}

	//跳转到订单入库页面
	@RequestMapping("/instore.do")
	public void instore() {}
	
	//跳转到订单出库页面
	@RequestMapping("/outstore.do")
	public void outstore() {}
	
	//跳转到统计页面
	@RequestMapping("/report.do")
	public void report() {}
	
	//加载订单表格的数据
	@RequestMapping(path="/getData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> getData(Orders orders, Integer page, Integer rows) {
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		PageHelper.startPage(page, rows); //设置分页的信息
		List<Orders> orderList = ordersService.find(orders);
		int total = ordersService.count(orders);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", orderList);
		map.put("total", total);
		return map;
	}
	
	//保存
	@RequestMapping(path="/save.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> save(Orders orders, String json) {
		//从session中获取当前的登录用户
		Subject subject = SecurityUtils.getSubject();
		Object o = subject.getPrincipal();
		
		if (o == null) {
			return ajaxReturn(false, "请先登录");
		} else {
			try {
				Emp emp = (Emp) o;
				orders.setCreater(emp.getUuid()); //设置下单人
				orders.setCreatetime(new Date()); //设置下单时间
				//orders.setType("1"); //采购类型为“采购订单”
				orders.setState("0"); //订单状态为“未审核”或“未出库”
				//把表格的json格式数据转换成OrderDetail集合 
				List<OrdersDetail> orderDetails = JSON.parseArray(json, OrdersDetail.class);
				orders.setOrderDetails(orderDetails);
				ordersService.addOrders(orders);
				return ajaxReturn(true, "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return ajaxReturn(false, "保存失败");
	}
	
	//订单审核
	@RequestMapping(path="/doCheck.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> doCheck(Orders orders) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Object o = subject.getPrincipal();
			if (o == null) {
				return ajaxReturn(false, "请先登录");
			}
			orders.setState("1"); //修改订单的状态为“已审核”
			orders.setChecktime(new Date()); //审核时间
			orders.setChecker(((Emp)o).getUuid()); //审核人
			ordersService.update(orders);
			return ajaxReturn(true, "审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "审核失败");
		}
	}
	
	//订单确认
	@RequestMapping(path="/doStart.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> doStart(Orders orders) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Object o = subject.getPrincipal();
			if (o == null) {
				return ajaxReturn(false, "请先登录");
			}
			orders.setState("2"); //修改订单的状态为“已确认”
			orders.setStarttime(new Date()); //确认时间
			orders.setStarter(((Emp)o).getUuid()); //审核人
			ordersService.update(orders);
			return ajaxReturn(true, "订单确认成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "订单确认失败");
		}
	}
	
	//订单入库
	@RequestMapping(path="/doInStore.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> doInStore(Integer ordersdetailuuid, Integer storeuuid) {
		System.out.println("ordersdetailuuid = " + ordersdetailuuid + ", storeuuid = " + storeuuid);
		try {
			Subject subject = SecurityUtils.getSubject();
			Object o = subject.getPrincipal();
			if (o == null) {
				return ajaxReturn(false, "请先登录");
			}
			ordersService.doInstore(ordersdetailuuid, storeuuid, ((Emp)o).getUuid());
			return ajaxReturn(true, "入库操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "入库操作失败");
		}
	}
	
	//订单出库
	@RequestMapping(path="/doOutStore.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> doOutStore(Integer ordersdetailuuid, Integer storeuuid) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Object o = subject.getPrincipal();
			if (o == null) {
				return ajaxReturn(false, "请先登录");
			}
			ordersService.doOutstore(ordersdetailuuid, storeuuid, ((Emp)o).getUuid());
			return ajaxReturn(true, "出库操作成功");
		} catch (OutOfStockException e) {
			return ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxReturn(false, "出库操作失败");
		}
	}
	
	//加载订单统计数据
	@RequestMapping(path="/getOrderReportData.do", produces={"application/json;charset=utf-8"})
	@ResponseBody
	public List<OrderReport> getOrderReportData(Date startDate, Date endDate) {
		return ordersService.getOrderReport(startDate, endDate);
	}
	
	@RequestMapping("/chart.do")
	public void getOrdersChart(HttpServletResponse response, Date startDate, Date endDate) throws IOException {
		//创建数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		/*dataset.setValue("家电", 300);
		dataset.setValue("食品", 100);
		dataset.setValue("日用品", 150);*/
		//获取销售额的统计数据
		List<OrderReport> dataList = ordersService.getOrderReport(startDate, endDate);
		for (OrderReport orderReport : dataList) {
			dataset.setValue(orderReport.getName(), orderReport.getMoney());
		}
		/*
		 * 使用默认的配置创建饼图
		 * 	参数1：标题
		 * 	参数2：数据集
		 *  参数3：是否创建图例
		 *  参数4：是否生成提示条
		 *  参数5：是否生成URL
		 */
		JFreeChart chart = ChartFactory.createPieChart(
				"销售统计表", dataset, false, false, false);
		//使用指定的字体风格设置标题
		chart.setTitle(new TextTitle("销售统计表", new Font("黑体", Font.BOLD, 25)));
		//获取绘图区
		PiePlot plot = (PiePlot) chart.getPlot();
		//设置绘图区的字体
		plot.setLabelFont(new Font("黑体", Font.BOLD, 14));
		//输出饼图
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 400, 300);
	}
	
	//导出统计数据
	@RequestMapping("/exportExcel.do")
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate) throws IOException {
		//设置响应头
		response.setHeader("Content-Disposition", "attachement;filename=" 
				+ URLEncoder.encode("销售统计表.xls", "UTF-8"));
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = wb.createSheet("销售统计表");
		//获取销售额的统计数据
		List<OrderReport> dataList = ordersService.getOrderReport(startDate, endDate);
		int rowIndex = 0;
		for (OrderReport orderReport : dataList) {
			//创建行
			HSSFRow row = sheet.createRow(rowIndex); //从0开始
			//创建列
			HSSFCell nameCell = row.createCell(0); //从0开始
			//设置单元格的内容
			nameCell.setCellValue(orderReport.getName());
			//创建列
			HSSFCell moneyCell = row.createCell(1); //从0开始
			//设置单元格的内容
			moneyCell.setCellValue(orderReport.getMoney());
			rowIndex++;
		}
		//把工作簿输出
		wb.write(response.getOutputStream());
		//关闭资源
		wb.close();
	}
}
