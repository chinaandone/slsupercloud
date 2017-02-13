package com.clever.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @version 1.0
 */
public class ReportExcelView extends AbstractExcelView {

	String filename = DateTime.getShortDateTime();

	public ReportExcelView(){

	}

	public ReportExcelView(String filename){
		if(filename != null && !"".equals(filename)){
			this.filename = filename;
		}
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
//		Date date = new Date();
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		sheet = workbook.createSheet("sheet1");

		List<String> dtitles = (List<String>) model.get("dtitles");
		int dlen = dtitles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);

		sheet.getRow(0).setHeight(height);
		
//		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//终端数量sheet
		List<String> devicetitles = new ArrayList<String>();
		devicetitles.add("商户名称");
		devicetitles.add("终端数量");
		devicetitles.add("安装日期");
		sheet = workbook.createSheet("终端数量");
		for(int i=0; i<devicetitles.size(); i++){ //设置终端数标题
			String title = devicetitles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(0).setHeight(height);
		List<ExcelData> deviceList = (List<ExcelData>) model.get("deviceList");
		setCellValues(sheet,deviceList,contentStyle);
		//开关机比例


		//开关机明细

		//有效点击平均值
		List<String> operateTitle = new ArrayList<String>();
		operateTitle.add("商户名称");
		operateTitle.add("总有效点击");
		operateTitle.add("终端数");
		sheet = workbook.createSheet("平均有效点击率");
		for(int i=0; i<operateTitle.size(); i++){ //设置有效点击标题
			String title = operateTitle.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(0).setHeight(height);
		List<ExcelData> clickList = (List<ExcelData>) model.get("clickList");
		setCellValues(sheet,clickList,contentStyle);

		//所有点击平均值
		operateTitle.clear();
		operateTitle.add("商户名称");
		operateTitle.add("所有点击数");
		operateTitle.add("终端数");
		sheet = workbook.createSheet("平均总点击率");
		for(int i=0; i<operateTitle.size(); i++){ //设置有效点击标题
			String title = operateTitle.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(0).setHeight(height);
		List<ExcelData> allclickList = (List<ExcelData>) model.get("allclickList");
		setCellValues(sheet,allclickList,contentStyle);
		//前5按照动作分类

		//前5按照所有日期分类




		List<ExcelData> varList = (List<ExcelData>) model.get("varList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			ExcelData vpd = varList.get(i);
			for(int j=0;j<varCount;j++){
				String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
				cell = getCell(sheet, i+1, j);
				cell.setCellStyle(contentStyle);
				setText(cell,varstr);
			}

		}
	}


	private void setCellValues(HSSFSheet sheet,List<ExcelData> dataList,HSSFCellStyle contentStyle){
		HSSFCell cell;
		int varCount = dataList.size();
		for(int i=0; i<varCount; i++){
			ExcelData vpd = dataList.get(i);
			for(int j=0;j<3;j++){
				String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
				cell = getCell(sheet, i+1, j);
				cell.setCellStyle(contentStyle);
				setText(cell,varstr);
			}

		}
	}

}
