package com.example.common.util;

import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel处理工具
 */
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    public static final int PERCENT_WIDTH = 50;
    public static final int PERCENT_HEIGHT = 20;
    public static final float PXTOPT = 0.75f;

    //excel错误代码
    private static List<String> errorCodes = new ArrayList<>();

    static {
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_DIV_0));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_NA));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_NAME));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_NUM));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_VALUE));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_REF));
        errorCodes.add(HSSFErrorConstants.getText(HSSFErrorConstants.ERROR_NULL));
    }

    private ExcelUtil() {
        //default
    }

    /**
     * 判断是否包含错误代码
     * @param value
     * @return false 没有错误 true 有错误
     */
    public static boolean isContainErrorCode(final String value) {
        if (null == value) {
            return false;
        } else {
            String value2 = replaceBlank(value);
            return errorCodes.contains(value2);
        }
    }

    /**
     * 替换全部空
     * @param value
     * @return
     */
    public static String replaceBlank(String value) {
        String dest = "";
        if (value != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(value);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 检查表头
     * @param table   excel 读出的二维数据
     * @param headers 有序表头
     */
    public static String checkHeader(Table<Integer, Integer, String> table, List<String> headers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; headers != null && i < headers.size(); i++) {
            String head = headers.get(i);
            if (!head.equals(table.get(0, i))) {
                stringBuilder.append("EXCEL表头第").append(i + 1).append("列请填写").append(head).append(";\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 检验列的唯一值
     * @param table       数据
     * @param columnIndex 列索引
     * @param columnName  列名,用于返回提示信息
     * @return 错误信息
     */
    public static String checkUniqueColumn(Table<Integer, Integer, String> table, Integer columnIndex, String columnName) {
        Map<Integer, String> colRows = table.column(columnIndex);
        ListMultimap<String, Integer> inverse = Multimaps.invertFrom(Multimaps.forMap(colRows),
                ArrayListMultimap.<String, Integer>create());
        int maxTimes = 10;//最多检查10组
        StringBuilder sb = new StringBuilder();
        for (String k : inverse.keySet()) {
            if (StringUtils.isBlank(k)) {
                continue;
            }
            List<Integer> idxs = inverse.get(k);
            if (idxs.size() > 1) {
                sb.append(columnName).append("有重复值").append(k).append(",行号:").append(Joiner.on(',').join(idxs)).append(";<br>");
                maxTimes = maxTimes - 1;
                if (maxTimes == 0) {//最多检查10组
                    break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 检验多列联合唯一值
     * @param table        数据
     * @param columnIndexs 列索引
     * @param columnName   列名,用于返回提示信息
     * @return 错误信息
     */
    public static String checkUniqueColumns(Table<Integer, Integer, String> table, List<Integer> columnIndexs, String columnName) {
        int row = table.rowKeySet().size();
        Multimap<String, Integer> map = ArrayListMultimap.create();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            StringBuilder b = new StringBuilder();
            for (Integer colIndex : columnIndexs) {
                String v = table.get(i, colIndex);
                if (StringUtils.isEmpty(v)) {
                    sb.append(columnName).append("第").append(i).append("行有数据为空");
                    return sb.toString();
                }
                b.append(v);
            }

            if (map.get(b.toString()).size() > 1) {
                sb.append(columnName).append("有重复值").append(b).append(",行号:").append(map.get(b.toString())).append(",").append(i).append(";<br>");
                return sb.toString();
            }
            map.put(b.toString(), i);
        }

        return sb.toString();
    }

    /**
     * 检查列中的空值
     * @param table       excel数据
     * @param columnIndex 列索引
     * @param columnName  列名称
     * @return 错误消息
     */
    public static String checkEmptyRow(Table<Integer, Integer, String> table, Integer columnIndex, String columnName) {
        Map<Integer, String> colRows = table.column(columnIndex);
        List<String> rowList = Lists.newArrayList();
        int maxTimes = 10;//最多检查10组
        int index = 0;
        List<Integer> rowIndexs = Lists.newArrayList(colRows.keySet());
        Collections.sort(rowIndexs, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Ints.compare(o1, o2);
            }
        });
        for (Integer rowIndex : rowIndexs) {
            if (StringUtils.isBlank(colRows.get(rowIndex))) {
                index++;
                if (index > maxTimes) {
                    break;
                }
                rowList.add("第" + (rowIndex + 1) + "行");
            }
        }
        if (!CollectionUtils.isEmpty(rowList)) {
            return columnName + "有空值，行号：" + Joiner.on("、").join(rowList);
        }
        return "";
    }

    public static Table<Integer, Integer, String> getExcelDate(Sheet sheet) {
        Table<Integer, Integer, String> dataTable = HashBasedTable.create();
        if (null == sheet) {
            return dataTable;
        }
        // 获得数据总行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        logger.debug("物理行数:{}", rowCount);
        //读取表头
        int first = sheet.getFirstRowNum();
        int last = sheet.getLastRowNum();
        logger.debug("开始行:结束行 ===={}:{}", first, last);
        Row rowHeader = sheet.getRow(first);
        int cellFirst = rowHeader.getFirstCellNum();
        int cellLast = rowHeader.getLastCellNum();
        logger.debug("开始列:结束列 ===={}:{}", cellFirst, cellLast);
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int rowIndex = first; rowIndex <= last; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                continue;
            }
            for (int cellIndex = cellFirst; cellIndex < cellLast; cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                if (null == cell) {
                    continue;
                }
                int y = rowIndex - first;
                int x = cellIndex - cellFirst;
                if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        dataTable.put(y, x, formater.format(cell.getDateCellValue()));
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        dataTable.put(y, x, cell.getStringCellValue());
                    }
                } else if (Cell.CELL_TYPE_ERROR == cell.getCellType()) {
                    dataTable.put(y, x, "");
                } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String value = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(value)) {
                        dataTable.put(y, x, value.trim());
                    }
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(value)) {
                        dataTable.put(y, x, value.trim());
                    }
                }
            }
        }
        return dataTable;
    }

    /**
     * 从EXCEL文件流中获取 Workbook 对象
     * @param inputStream 输入流
     * @return Workbook
     */
    public static Workbook getWorkbook(final InputStream inputStream) throws Exception {
        InputStream inputStream1 = inputStream;
        if (!inputStream.markSupported()) {
            inputStream1 = new PushbackInputStream(inputStream, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inputStream1)) {
            return new HSSFWorkbook(inputStream1);
        } else if (POIXMLDocument.hasOOXMLHeader(inputStream1)) {
            try (OPCPackage pack = OPCPackage.open(inputStream1)) {
                return new XSSFWorkbook(pack);
            }
        }
        return null;
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                Row fRow = sheet.getRow(firstRow);
                Cell fCell = fRow.getCell(firstColumn);
                return fCell.getStringCellValue();
            }
        }
        return null;
    }

    /**
     * 获取合并单元格的值
     * @param workbook   工作簿对象
     * @param sheetIndex 工作表索引
     * @param column     列索引
     * @return
     */
    public static String getMergedRegionValue(Workbook workbook, int sheetIndex, int column) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int sheetMergeCount = sheet.getNumMergedRegions();
        String value = "";
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            Row fRow = sheet.getRow(firstRow);
            Cell fCell = fRow.getCell(column);
            // 全部指定为文本格式
            if (null != fCell) {
                fCell.setCellType(Cell.CELL_TYPE_STRING);
                value = (String) getCellString(fCell);
                if (null == value) {
                    value = "";
                } else {
                    if (ExcelUtil.isContainErrorCode(value)) {//包含错误代码
                        value = "";
                    } else {
                        value = ExcelUtil.replaceBlank(value.replaceAll("（", "(").replaceAll("）", ")"));
                    }
                }
            }
        }
        return value;
    }

    /**
     * 获取所有非合并单元格的值
     * @param sheet      工作表
     * @param startRow   开始行索引
     * @param lastRow    结束行索引
     * @param columnSize 结束列索引
     * @return
     */
    public static List<List<String>> getUnMergedRegionValue(Sheet sheet, int startRow, int lastRow, int columnSize) {
        List<List<String>> list = new ArrayList<>();
        for (int firstRow = startRow; firstRow <= lastRow; firstRow++) {
            List<String> valueList = new ArrayList<>();
            Row row = sheet.getRow(firstRow);
            if (null != row) {
                for (int j = 0; j < columnSize; j++) {
                    Cell cell = row.getCell(j);
                    if (null != cell) {
                        // 全部指定为文本格式
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        Object vob = getCellString(cell);
                        if (null != vob) {
                            String value = (String) getCellString(cell);
                            if (null == value) {
                                value = "";
                            } else {
                                value = value.replace(" ", "");
                            }
                            if (ExcelUtil.isContainErrorCode(value)) {//包含错误代码
                                valueList.add("");
                            } else {
                                valueList.add(ExcelUtil.replaceBlank(value).replaceAll("（", "(").replaceAll("）", ")"));
                            }
                        } else {
                            valueList.add("");
                        }
                    } else {
                        valueList.add("");
                    }
                }
                if (!valueList.isEmpty()) {
                    list.add(valueList);
                }
            }
        }
        return list;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param book
     * @return
     */
    public static Map<String, Table<Integer, Integer, String>> getExcelStringData(Workbook book) {
        Map<String, Table<Integer, Integer, String>> sheetMap = new HashMap<>();
        // 创建EXCEL二维坐标系
        int sheetSize = book.getNumberOfSheets();
        for (int index = 0; index < sheetSize; index++) {
            Table<Integer, Integer, String> dataTable = HashBasedTable.create();
            Sheet sheet = book.getSheetAt(index);
            String sheetName = sheet.getSheetName();
            // 获得数据总行数
            int rowCount = sheet.getLastRowNum();
            if (rowCount > 0) {
                for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (null != row) {
                        int cellCount = row.getLastCellNum();
                        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                            Cell cell = row.getCell(cellIndex);
                            if (null != cell) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (null == cell.getStringCellValue()) {
                                    dataTable.put(rowIndex, cellIndex, "");
                                } else {
                                    if (ExcelUtil.isContainErrorCode(cell.getStringCellValue())) {//包含错误代码
                                        dataTable.put(rowIndex, cellIndex, "");
                                    } else {
                                        dataTable.put(rowIndex, cellIndex, ExcelUtil.replaceBlank(StringUtils.deleteWhitespace(cell.getStringCellValue())).replaceAll("（", "(").replaceAll("）", ")"));
                                    }
                                }
                            } else {
                                dataTable.put(rowIndex, cellIndex, "");
                            }
                        }
                    }
                }
                sheetMap.put(sheetName, dataTable);
            }
        }
        return sheetMap;
    }

    /**
     * @param book excel 工作薄
     * @return 二维数据表
     */
    public static Table<Integer, Integer, String> getFirstSheetData(Workbook book) {
        // 创建EXCEL二维坐标系
        Table<Integer, Integer, String> dataTable = HashBasedTable.create();
        int sheetSize = book.getNumberOfSheets();
        if (sheetSize == 0) {
            return dataTable;//返回空的
        }
        Sheet sheet = book.getSheetAt(0);
        // 获得数据总行数
        int rowCount = sheet.getLastRowNum();
        if (rowCount == 0) {
            return dataTable;//返回空的
        }
        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                continue;
            }
            int cellCount = row.getLastCellNum();
            for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                if (null == cell) {
                    continue;
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null == cell.getStringCellValue()) {
                    dataTable.put(rowIndex, cellIndex, "");
                } else if (ExcelUtil.isContainErrorCode(cell.getStringCellValue())) {//包含错误代码
                    dataTable.put(rowIndex, cellIndex, "");
                } else {
                    dataTable.put(rowIndex, cellIndex, cell.getStringCellValue());
                }
            }
        }

        return dataTable;
    }

    public static Map<String, Table<Integer, Integer, String>> getExcelStringNotDate(Workbook book) {
        Map<String, Table<Integer, Integer, String>> sheetMap = new HashMap<>();
        // 创建EXCEL二维坐标系
        int sheetSize = book.getNumberOfSheets();
        for (int index = 0; index < sheetSize; index++) {
            Table<Integer, Integer, String> dataTable = HashBasedTable.create();
            Sheet sheet = book.getSheetAt(index);
            String sheetName = sheet.getSheetName();
            // 获得数据总行数
            int rowCount = sheet.getLastRowNum();
            if (rowCount > 0) {
                for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (null != row) {
                        int cellCount = row.getLastCellNum();
                        //TODO 修改,根据表头确定表格有效区域
                        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                            Cell cell = row.getCell(cellIndex);
                            if (null != cell) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (null == cell.getStringCellValue()) {
                                    dataTable.put(rowIndex, cellIndex, "");
                                } else {
                                    if (ExcelUtil.isContainErrorCode(cell.getStringCellValue())) {//包含错误代码
                                        dataTable.put(rowIndex, cellIndex, "");
                                    } else {
                                        dataTable.put(rowIndex, cellIndex, ExcelUtil.replaceBlank(cell.getStringCellValue()));
                                    }
                                }
                            }
                        }
                    }
                }
                sheetMap.put(sheetName, dataTable);
            }
        }
        return sheetMap;
    }

    /**
     * 获得表中的数据
     * @param sheetNumber 表格索引(EXCEL 是多表文档,所以需要输入表索引号)
     * @param workbook    工作簿对象
     * @return 由list构成的行和表
     */
    public static List<List<String>> getDatasInSheet(int sheetNumber, Workbook workbook) {
        List<List<String>> result = Lists.newArrayList();
        // 得到第sheetNumber页
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        //获得数据总行数
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {//
            return result;
        }
        //逐行读取数据
        Row row;
        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
            //获得行对象
            row = sheet.getRow(rowIndex);
            if (row != null) {
                List<String> rowData = Lists.newArrayList();
                //获得本行中单元格的个数
                int columnCount = row.getLastCellNum();
                //获得本行中各单元格中的数据
                Cell cell;
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        //全部指定为文本格式
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        //获得指定单元格中数据
                        String cellStr = cell.getStringCellValue();
                        if (null == cellStr) {
                            rowData.add("");
                        } else {
                            if (ExcelUtil.isContainErrorCode(cellStr)) {//包含错误代码
                                rowData.add("");
                            } else {
                                rowData.add(ExcelUtil.replaceBlank(cellStr).replaceAll("（", "(").replaceAll("）", ")"));
                            }
                        }
                    }
                }
                result.add(rowData);
            }
        }
        return result;
    }

    /**
     * 获得单元格中的内容
     * @param cell
     * @return
     */
    public static Object getCellString(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object result = null;

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
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    logger.error("出现错误,后面有逻辑处理", e);
                    result = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 表头样式
     * @param workbook 工作薄
     * @return 样式
     */
    public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        HSSFPalette customPalette = workbook.getCustomPalette();
        font.setFontHeightInPoints((short) 10);//字号
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);

        customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 250, (byte) 242, (byte) 242);
        style.setFillForegroundColor(HSSFColor.ORANGE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderTop((short) 1);// 上边框
        style.setBorderBottom((short) 1);// 下边框
        style.setBorderLeft((short) 1); // 左边框
        style.setBorderRight((short) 1); // 右边框
        return style;
    }

    public static HSSFCellStyle getDataStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
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
    }

    /**
     * 在指定行上填充数据
     * @param row        指定的行
     * @param titleStyle 单元格样式
     * @param values     表达字符，填充值
     */
    public static void createDataRow(HSSFRow row, HSSFCellStyle titleStyle, String... values) {
        for (int i = 0; i < values.length; i++) {
            // 在索引0的位置创建单元格（左上端）
            HSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //样式
            cell.setCellStyle(titleStyle);
            // 在单元格中输入一些内容
            cell.setCellValue(values[i]);
        }
    }

    public static String getByHeaderKey(Map<Integer, String> rowData, Map<String, Integer> headerIndex, String headerKey) {
        if (!headerIndex.containsKey(headerKey)) {//不存在直接返回null
            return null;
        }
        Integer index = headerIndex.get(headerKey);
        if (!rowData.containsKey(index)) {
            return null;
        }
        return rowData.get(index).trim();
    }

    public static void createHeaders(HSSFSheet sheet, String... headers) {
        //设置默认行高和列宽
        sheet.setDefaultColumnWidth(20);
        //冻结标题
        sheet.createFreezePane(0, 1, 0, 1);//冻结第一行
        sheet.setDisplayGridlines(false);
        // 在索引0的位置创建行（最顶端的行）
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeight((short) (30 * 20));
        HSSFCellStyle freeze = sheet.getWorkbook().createCellStyle();
        freeze.setBorderBottom((short) 0);
        headerRow.setRowStyle(freeze);


        HSSFCellStyle titleStyle = ExcelUtil.getHeaderStyle(sheet.getWorkbook());
        ExcelUtil.createDataRow(headerRow, titleStyle, headers);
    }

    /**
     * 在指定行上填充数据
     * @param row              指定的行
     * @param titleStyle       单元格样式
     * @param headerAndComment 填充值和说明（map, 填充值不能重复）
     */
    public static void createUniqueRowWithComment(HSSFRow row, HSSFCellStyle titleStyle, Map<String, String> headerAndComment) {
        HSSFPatriarch draw = row.getSheet().createDrawingPatriarch();
        int i = 0;
        for (Map.Entry<String, String> entry : headerAndComment.entrySet()) {
            // 在索引0的位置创建单元格（左上端）
            HSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //样式
            cell.setCellStyle(titleStyle);
            // 在单元格中输入一些内容
            cell.setCellValue(entry.getKey());

            //设置注释
            HSSFClientAnchor anchor = draw.createAnchor(0, 0, 0, 0, 3, 3, 5, 7);
            HSSFComment comment = draw.createComment(anchor);
            comment.setString(new HSSFRichTextString(entry.getValue()));
            cell.setCellComment(comment);
            i++;
        }
    }

    public static void createHeadersWithComment(HSSFSheet sheet, Map<String, String> headerAndComment) {
        //设置默认行高和列宽
        sheet.setDefaultColumnWidth(20);
        //冻结标题
        sheet.createFreezePane(0, 1, 0, 1);//冻结第一行
        sheet.setDisplayGridlines(false);
        // 在索引0的位置创建行（最顶端的行）
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeight((short) (30 * 20));
        HSSFCellStyle freeze = sheet.getWorkbook().createCellStyle();
        freeze.setBorderBottom((short) 0);
        headerRow.setRowStyle(freeze);


        HSSFCellStyle titleStyle = ExcelUtil.getHeaderStyle(sheet.getWorkbook());
        ExcelUtil.createUniqueRowWithComment(headerRow, titleStyle, headerAndComment);
    }

    /**
     * 设置重复表头行
     * @param sheet    excel便签页
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static void setRepeatingRows(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (lastRow < firstRow || lastCol < firstCol || (firstRow == lastRow && firstCol == lastCol)) {
            return;
        }
        CellRangeAddress titleRegion = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        ExcelUtil.setRangeAddressBorder(titleRegion, sheet);
        sheet.setRepeatingRows(titleRegion);
    }

    /**
     * 设置合并单元格边框
     * @param sheet    excel便签页
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static void setBorderRangeAddress(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (lastRow < firstRow || lastCol < firstCol || (firstRow == lastRow && firstCol == lastCol) || lastCol > 255) {
            return;
        }
        CellRangeAddress cellRegion = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        ExcelUtil.setRangeAddressBorder(cellRegion, sheet);
        sheet.addMergedRegion(cellRegion);
    }

    /**
     * 设置合并单元格边框
     * @param cellRangeAddress 单元格合并区域
     * @param sheet            excel便签页
     */
    public static void setRangeAddressBorder(CellRangeAddress cellRangeAddress, Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, wb);
    }

    /**
     * 设置无边框合并单元格边框
     * @param sheet    excel便签页
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static void setNoBorderRangeAddress(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (lastRow < firstRow || lastCol < firstCol || (firstRow == lastRow && firstCol == lastCol) || lastCol > 255) {
            return;
        }
        CellRangeAddress titleRegion = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        if (titleRegion.getNumberOfCells() < 2) {
            return;
        }
        ExcelUtil.setRangeAddressNoBorder(titleRegion, sheet);
        sheet.addMergedRegion(titleRegion);
    }

    /**
     * 设置无边框合并单元格
     * @param cellRangeAddress 合并单元格
     * @param sheet            excel便签页
     */
    public static void setRangeAddressNoBorder(CellRangeAddress cellRangeAddress, Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        RegionUtil.setBorderLeft(0, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderBottom(0, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderRight(0, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderTop(0, cellRangeAddress, sheet, wb);
    }

    public static String getWeekName(DateTime dt) {
        switch (dt.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                return "周日";
            case DateTimeConstants.MONDAY:
                return "周一";
            case DateTimeConstants.TUESDAY:
                return "周二";
            case DateTimeConstants.WEDNESDAY:
                return "周三";
            case DateTimeConstants.THURSDAY:
                return "周四";
            case DateTimeConstants.FRIDAY:
                return "周五";
            case DateTimeConstants.SATURDAY:
                return "周六";
            default:
                return "";
        }
    }

    /**
     * 绘制斜线表头
     * @param patriarch     绘图工具实例
     * @param startColIndex 开始列
     * @param startRowIndex 开始行
     * @param endColIndex   结束列
     * @param endRowIndex   结束行
     */
    public static void drawNoClassCellSlash(HSSFPatriarch patriarch, int startColIndex, int startRowIndex, int endColIndex, int endRowIndex) {
        HSSFClientAnchor slashAnchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) startColIndex, (short) startRowIndex, (short) endColIndex, (short) endRowIndex);
        HSSFSimpleShape slashShape = patriarch.createSimpleShape(slashAnchor);
        slashShape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
        slashShape.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
    }

    /**
     * 绘制单元格斜线表头
     * @param sheet  工作表实例
     * @param row    行对象实例
     * @param i      单元格行索引
     * @param j      单元格列索引
     * @param width  宽度
     * @param height 高度
     * @param xys    坐标 集合
     */
    public static void drawLine(HSSFSheet sheet, HSSFRow row, int i, int j, int width, int height, int[] xys) {
        int cellWidth = (int) (PERCENT_WIDTH * PXTOPT * width);
        short cellHeight = (short) (PERCENT_HEIGHT * PXTOPT * height);
        sheet.setColumnWidth(j, cellWidth);
        row.setHeight(cellHeight);

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) j, i, (short) (j), i);
        HSSFShapeGroup group = patriarch.createGroup(a);
        float verticalPointsPerPixel = a.getAnchorHeightInPoints(sheet);
        EscherGraphics g = new EscherGraphics(group, sheet.getWorkbook(), java.awt.Color.black, verticalPointsPerPixel);
        EscherGraphics2d g2d = new EscherGraphics2d(g);
        for (int l = 0; l < xys.length; l += 2) {
            int x = (int) ((PERCENT_WIDTH * 0.75 * xys[l] / cellWidth) * 1023);
            int y = (int) ((PERCENT_HEIGHT * 0.75 * xys[l + 1] / cellHeight) * 255);
            g2d.drawLine(0, 0, x, y);
        }
    }

    /**
     * 获得导入内容通过sheet页
     * @param sheet
     * @return
     */
    public static Table<Integer, Integer, String> getSheetExcelStringData(Sheet sheet) {
        Table<Integer, Integer, String> dataTable = HashBasedTable.create();
        if (null != sheet) {
            //String sheetName = sheet.getSheetName();
            // 获得数据总行数
            int rowCount = sheet.getLastRowNum();
            if (rowCount > 0) {
                for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (null != row) {
                        int cellCount = row.getLastCellNum();
                        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                            Cell cell = row.getCell(cellIndex);
                            if (null != cell) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (null == cell.getStringCellValue()) {
                                    dataTable.put(rowIndex, cellIndex, "");
                                } else {
                                    if (ExcelUtil.isContainErrorCode(cell.getStringCellValue())) {//包含错误代码
                                        dataTable.put(rowIndex, cellIndex, "");
                                    } else {
                                        dataTable.put(rowIndex, cellIndex,
                                                ExcelUtil.replaceBlank(cell.getStringCellValue())
                                                        .replaceAll("（", "(")
                                                        .replaceAll("）", ")"));
                                    }
                                }
                            } else {
                                dataTable.put(rowIndex, cellIndex, "");
                            }
                        }
                    }
                }
            }
        }
        return dataTable;
    }

    public static String replaceIllegalCha(String value) {
        if (!StringUtils.isEmpty(value)) {
            return value.replaceAll("\t|\r|\n|\\u202C|\\u202D|\\u202E", "");
        }
        return value;
    }

    public static HSSFComment getDefaultComment(HSSFPatriarch patriarch) {
        return patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
    }

}
