package com.example.practiceexam.controller;

import com.example.common.util.BrowserUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel处理的通用方法类,注意这个类没有注解
 */
public class BaseExcelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExcelController.class);

    protected static final String STYLE_TITLE = "title";
    protected static final String STYLE_BIG_TITLE = "bigtitle";
    protected static final String STYLE_SECTITLE = "sectitle";
    protected static final String STYLE_HEADER = "header";
    protected static final String STYLE_DATA = "data";
    protected static final String STYLE_DATA2 = "data2";
    protected static final String STYLE_DATA_TOP = "datatop";
    protected static final String STYLE_DATA_TOP2 = "datatop2";
    protected static final String STYLE_DATA_BOTTOM = "databottom";
    protected static final String STYLE_DATA_BOTTOM2 = "databottom2";
    protected static final String STYLE_DATA_SIDE = "dataside";
    protected static final String STYLE_DATA_SIDE2 = "dataside2";
    protected static final String STYLE_LINK = "link";

    /**
     * 设置EXCEL响应
     * @param request http请求对象
     * @param response http响应对象
     * @param fileName 文件名
     */
    protected void setExcelResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            // 根据浏览器进行转码，使其支持中文文件名
            if (BrowserUtil.isIE(request)) {
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");//这个地方要修改成和模板一样的文件类型
            } else {
                String newFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                response.setHeader("content-disposition", "attachment;filename=" + newFileName + ".xls");
            }
        }catch (UnsupportedEncodingException e){
            throw new Exception(e);
        }
    }

    protected ServletOutputStream generateResponseExcel(String excelName, HttpServletResponse response) throws IOException {
        excelName = excelName == null || "".equals(excelName) ? "excel" : URLEncoder.encode(excelName, "UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + excelName + ".xlsx");

        return response.getOutputStream();
    }

    /**
     * 创建单个excel文件
     * @param sheetName
     *            工作表名称
     * @param sheetNum
     *            sheetNum工作表索引
     * @param titles
     *            Excel文件Field标题集合
     * @param workbook
     * 	 		      工作簿
     * @param dataList
     *            Excel文件数据内容部分
     */
    protected void createExcel(String sheetName, int sheetNum, HSSFWorkbook workbook ,
                             List<String> titles, List<Map<String, String>> dataList) throws Exception {
        // 在Excel工作簿中建一工作表，其名为缺省值
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, WorkbookUtil.createSafeSheetName(sheetName,'|'));
        //设置默认行高和列宽
        sheet.setDefaultColumnWidth(20);
        //冻结标题
        sheet.createFreezePane(0,1,0,1);//冻结第一行
        sheet.setDisplayGridlines(false);
        // 在索引0的位置创建行（最顶端的行）
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) (30*20));
        HSSFCellStyle freeze = workbook.createCellStyle();
        freeze.setBorderBottom((short)0);
        row.setRowStyle(freeze);

        HSSFCellStyle titleStyle = getTitleStyle( workbook,STYLE_HEADER);

        for (int i = 0; i < titles.size(); i++) {
            // 在索引0的位置创建单元格（左上端）
            HSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //样式
            cell.setCellStyle(titleStyle);
            // 在单元格中输入一些内容
            cell.setCellValue(titles.get(i));
        }
        HSSFCellStyle dataStyle = getTitleStyle( workbook,STYLE_DATA);

        if(!CollectionUtils.isEmpty(dataList)){//有数据填充数据
            for (int n = 0; n < dataList.size(); n++) {
                // 在索引1的位置创建行（最顶端的行）
                HSSFRow rowValue = sheet.createRow(n + 1);
                rowValue.setHeight((short) (25*20));
                Map<String, String> dataMap = dataList.get(n);
                for (int i = 0; i < titles.size(); i++) {
                    // 在索引0的位置创建单元格（左上端）
                    HSSFCell cell = rowValue.createCell(i);
                    // 定义单元格为字符串类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    //设置样式
                    cell.setCellStyle(dataStyle);
                    // 在单元格中输入一些内容
                    cell.setCellValue(objToString(dataMap.get(titles.get(i))));
                }
            }
        }else{//空表格
            for (int n = 0; n < 6; n++) {//没数据画空白表格
                // 在索引1的位置创建行（最顶端的行）
                HSSFRow rowValue = sheet.createRow(n + 1);
                rowValue.setHeight((short) (25*20));
                for (int i = 0; i < titles.size(); i++) {
                    // 在索引0的位置创建单元格（左上端）
                    HSSFCell cell = rowValue.createCell(i);
                    //设置样式
                    cell.setCellStyle(dataStyle);
                    // 定义单元格为字符串类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    // 在单元格中输入一些内容
                    cell.setCellValue("");
                }
            }
        }
    }

    /**
     * 获取单元格及合并单元格的宽度
     *
     * @param cell
     */
    private Map<String, Object> getCellInfo(HSSFCell cell) {
        HSSFSheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();

        boolean isPartOfRegion = false;
        int firstColumn = 0;
        int lastColumn = 0;
        int firstRow = 0;
        int lastRow = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            firstColumn = ca.getFirstColumn();
            lastColumn = ca.getLastColumn();
            firstRow = ca.getFirstRow();
            lastRow = ca.getLastRow();
            if (rowIndex >= firstRow && rowIndex <= lastRow) {
                if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
                    isPartOfRegion = true;
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        int width = 0;
        int height = 0;
        boolean isPartOfRowsRegion = false;
        if (isPartOfRegion) {
            for (int i = firstColumn; i <= lastColumn; i++) {
                width += sheet.getColumnWidth(i);
            }
            for (int i = firstRow; i <= lastRow; i++) {
                height += sheet.getRow(i).getHeight();
            }
            if (lastRow > firstRow) {
                isPartOfRowsRegion = true;
            }
        } else {
            width = sheet.getColumnWidth(columnIndex);
            height += cell.getRow().getHeight();
        }
        map.put("isPartOfRowsRegion", isPartOfRowsRegion);
        map.put("firstRow", firstRow);
        map.put("lastRow", lastRow);
        map.put("width", width);
        map.put("height", height);
        return map;
    }

    /**
     * 获得单元格中的内容
     *
     * @param cell
     */
    private Object getCellString(Cell cell) {
        Object result = null;
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case HSSFCell.CELL_TYPE_STRING:
                    result = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                   try {
                       result = cell.getNumericCellValue();
                    } catch (IllegalStateException e) {
                       result = String.valueOf(cell.getRichStringCellValue());
                       LOGGER.error("",e);
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                case HSSFCell.CELL_TYPE_BLANK:
                default:
                    result = "";
                    break;
            }
        }
        return result;
    }

    /**
     * 获取excel表格样式
     * @param workbook HSSFWorkbook
     * @param type 自定义的type,具体参照源代码
     * @return HSSFCellStyle
     */
    protected HSSFCellStyle getTitleStyle(HSSFWorkbook workbook, String type) throws Exception {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        HSSFPalette customPalette = workbook.getCustomPalette();
        switch (type){
            case STYLE_TITLE:
                font.setFontHeightInPoints((short) 10);//字号
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
                font.setColor(HSSFColor.BLACK.index);

                style.setFont(font);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.WHITE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short)1);// 上边框
                style.setBorderBottom((short)1);// 下边框
                style.setBorderLeft((short)1); // 左边框
                style.setBorderRight((short)1); // 右边框
                return  style;
            case STYLE_BIG_TITLE:
                font.setFontHeightInPoints((short) 20);//字号
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
                font.setColor(HSSFColor.BLACK.index);

                style.setFont(font);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.WHITE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short)1);// 上边框
                style.setBorderBottom((short)1);// 下边框
                style.setBorderLeft((short)1); // 左边框
                style.setBorderRight((short)1); // 右边框
                return  style;
            case STYLE_SECTITLE:
                font.setFontHeightInPoints((short) 10);//字号
                font.setColor(HSSFColor.BLACK.index);

                style.setFont(font);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.WHITE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short)1);// 上边框
                style.setBorderBottom((short)1);// 下边框
                style.setBorderLeft((short)1); // 左边框
                style.setBorderRight((short)1); // 右边框
                return style;
            case STYLE_HEADER:
                font.setFontHeightInPoints((short) 10);//字号
                font.setColor(HSSFColor.BLACK.index);
                style.setFont(font);

                customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.ORANGE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short)1);// 上边框
                style.setBorderBottom((short)1);// 下边框
                style.setBorderLeft((short)1); // 左边框
                style.setBorderRight((short)1); // 右边框
                return style;
            case STYLE_DATA:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short) 1);// 上边框
                style.setBorderBottom((short) 1);// 下边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA2:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.ORANGE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short) 1);// 上边框
                style.setBorderBottom((short) 1);// 下边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_TOP:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short) 1);// 上边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_BOTTOM:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderBottom((short) 1);// 下边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_SIDE:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_TOP2:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.ORANGE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short) 1);// 上边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_BOTTOM2:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.ORANGE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderBottom((short) 1);// 下边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_DATA_SIDE2:
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//正常粗细
                font.setColor(HSSFFont.COLOR_NORMAL);//正常颜色
                style.setFont(font);

                customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
                style.setFillBackgroundColor(HSSFColor.AQUA.index);
                style.setFillForegroundColor(HSSFColor.ORANGE.index);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            case STYLE_LINK:
                font.setColor(HSSFColor.BLUE.index);//正常颜色
                style.setFont(font);
                style.setFillBackgroundColor(HSSFFont.COLOR_NORMAL);
                style.setFillForegroundColor(HSSFFont.COLOR_NORMAL);
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                style.setBorderTop((short) 1);// 上边框
                style.setBorderBottom((short) 1);// 下边框
                style.setBorderLeft((short) 1); // 左边框
                style.setBorderRight((short) 1); // 右边框
                style.setWrapText(true);//自动换行
                return style;
            default:
        }
        throw new Exception("没有找到对应的excel单元样式");
    }

    /**
     * 对象实例转为字符串
     * @param obj
     * @return
     */
    protected String objToString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj instanceof Date) {
                DateTime dt = new DateTime(obj);
                return dt.toString("yyyy-MM-dd HH:mm:ss");
            } else {
                return obj.toString();
            }
        }
    }

}
