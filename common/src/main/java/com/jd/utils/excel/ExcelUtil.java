package com.jd.utils.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jd.utils.StringUtil;

/**
 * 类ExcelUtil.java的实现描述：excel处理工具类(main方法中示例)
 * 
 * @author Starty 2015年6月5日 下午5:45:51
 */
public class ExcelUtil {

    // 行高/字体大小比率，处理行高时候需要用到这个换算
    private static final double FONT_HEIGHT_DIV = 1.275F;

    /**
     * 生成 EXECL
     * 
     * @param title 标题
     * @param heads 表头
     * @param contexts 正文
     * @param sheetName sheet名称
     * @param out
     * @throws IOException
     */
    public static void generateExcel(ExeclStyle title, List<ExeclStyle> heads, List<List<ExeclStyle>> contexts,
                                     String sheetName, OutputStream out) throws IOException {
        @SuppressWarnings("resource")
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = null;
        if (sheetName != null) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        int index = 0;

        // 有报表头
        if (heads != null && heads.size() > 0) {
            HSSFCellStyle headStyle = workbook.createCellStyle();
            HSSFFont headFont = workbook.createFont();

            // 标题
            if (title != null) {

                HSSFCellStyle headStyleTitle = workbook.createCellStyle();
                HSSFFont headFontTitle = workbook.createFont();
                row = sheet.createRow(index);
                cell = row.createCell(0);
                cell.setCellValue(new HSSFRichTextString(title.getCellValue()));
                setStyle(headStyleTitle, headFontTitle, cell, title);
                sheet.addMergedRegion(new CellRangeAddress(index, index, 0, heads.size() - 1));
                index++;
            }

            // 创建行
            row = sheet.createRow(index);
            short headHeight = 0;
            short maxHeadHeight = 0;
            for (int i = 0; i < heads.size(); i++) {
                // 写入单元格
                cell = row.createCell(i);
                text = new HSSFRichTextString(heads.get(i).getCellValue());
                cell.setCellValue(text);

                headHeight = setStyle(headStyle, headFont, cell, heads.get(i));
                if (headHeight > maxHeadHeight) {
                    maxHeadHeight = headHeight;
                }
            }
            row.setHeightInPoints(maxHeadHeight);
            index++;
        }

        // 有内容
        if (contexts != null && contexts.size() > 0) {
            HSSFCellStyle contentStyle = workbook.createCellStyle();
            HSSFFont contentFont = workbook.createFont();

            Iterator<List<ExeclStyle>> it = contexts.iterator();
            while (it.hasNext()) {
                List<ExeclStyle> list = it.next();
                // 创建行
                row = sheet.createRow(index);
                row.setHeightInPoints((short) 23);

                short contentHeight = 0;
                short maxContentHeight = 0;
                for (int i = 0; i < list.size(); i++) {
                    // 写入单元格
                    cell = row.createCell(i);
                    text = new HSSFRichTextString(list.get(i).getCellValue());
                    cell.setCellValue(text);

                    // 设置样式
                    contentHeight = setStyle(contentStyle, contentFont, cell, list.get(i));

                    if (contentHeight > maxContentHeight) {
                        maxContentHeight = contentHeight;
                    }
                }
                // 设置行高
                row.setHeightInPoints(maxContentHeight);
                index++;
            }
        }

        // 补偿自适应列宽 ps: 这个操蛋的自适应相对来说很耗时，数据量稍微大一点延迟是秒级别的 by yingchao.zyc
        // 实测数据
        // 10列，490行。 总耗时1494ms。 去掉自适应方法耗时 294ms 该方法用去总时间的80%
        if (heads != null && heads.size() > 0) {
            for (int i = 0; i < heads.size(); i++) {
                sheet.autoSizeColumn(i);
            }
        }

        workbook.write(out);
    }

    /**
     * 设置样式。
     * 
     * @param workbook
     * @param cell
     * @param execlStyle
     * @return 返回当前cell需要占据的高度。
     */
    private static short setStyle(HSSFCellStyle style, HSSFFont font, HSSFCell cell, ExeclStyle execlStyle) {
        style.setWrapText(execlStyle.isNeedWrapText());

        if (execlStyle.getColor() != null) {
            style.setFillForegroundColor(execlStyle.getColor().getIndex());
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        }

        boolean needSetFont = false;
        if (execlStyle.getFontName() != null) {
            font.setFontName(execlStyle.getFontName());
            needSetFont = true;
        }
        if (execlStyle.getBoldWeight() != null) {
            font.setBoldweight(execlStyle.getBoldWeight().getBoldWeight());
            needSetFont = true;
        }

        short singleCellHeight = 0;
        if (execlStyle.getFontHeightInPoints() > 0) {
            font.setFontHeightInPoints(execlStyle.getFontHeightInPoints());
            needSetFont = true;

            // 换算到对应字体应该占据的行高
            singleCellHeight = (short) (execlStyle.getFontHeightInPoints() * FONT_HEIGHT_DIV);
        } else {
            // 这里的14其实是13.5， execl的默认行高
            singleCellHeight = 14;
        }

        if (needSetFont) {
            style.setFont(font);
        }

        if (execlStyle.getBorderBottomStyle() != null) {
            style.setBorderBottom(execlStyle.getBorderBottomStyle().getBorderStyle());
        }

        if (execlStyle.getBorderLeftStyle() != null) {
            style.setBorderLeft(execlStyle.getBorderLeftStyle().getBorderStyle());
        }

        if (execlStyle.getBorderRightStyle() != null) {
            style.setBorderRight(execlStyle.getBorderRightStyle().getBorderStyle());
        }

        if (execlStyle.getBorderTopStyle() != null) {
            style.setBorderTop(execlStyle.getBorderTopStyle().getBorderStyle());
        }

        if (execlStyle.getAlign() != null) {
            style.setBorderBottom(execlStyle.getAlign().getAlignStyle());
        }

        if (ExeclStyle.ExeclAlignStyle.CENTER == execlStyle.getAlign()) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        }

        cell.setCellStyle(style);
        return singleCellHeight;
    }

    /**
     * 使用默认的样式创建报表头数据和样式
     * 
     * @param title
     * @param heads
     * @return
     */
    public static List<ExeclStyle> createHeadUseDefaultStyle(String[] heads) {
        if (heads == null || heads.length == 0) {
            return null;
        }
        List<ExeclStyle> headsStyles = new ArrayList<ExeclStyle>();
        for (int i = 0; i < heads.length; i++) {
            ExeclStyle style = getDefaultExcelHeadStyle();
            style.setCellValue(heads[i]);
            headsStyles.add(style);
        }
        return headsStyles;
    }

    /**
     * 使用默认的样式创建报表头数据和样式
     * 
     * @param title
     * @param heads
     * @return
     */
    public static List<ExeclStyle> createHeadUseDefaultStyle(List<String> heads) {
        if (CollectionUtils.isEmpty(heads)) {
            return null;
        }
        List<ExeclStyle> headsStyles = new ArrayList<ExeclStyle>();
        for (int i = 0; i < heads.size(); i++) {
            ExeclStyle style = getDefaultExcelHeadStyle();
            style.setCellValue(heads.get(i));
            headsStyles.add(style);
        }
        return headsStyles;
    }

    /**
     * 使用默认的样式创建标题数据和样式
     * 
     * @param title
     * @return
     */
    public static ExeclStyle createTitleUseDefaultStyle(String title) {
        ExeclStyle style = new ExeclStyle();
        style.setAlign(ExeclStyle.ExeclAlignStyle.CENTER);
        style.setFontHeightInPoints((short) 28);
        style.setBoldWeight(ExeclStyle.ExeclBoldWeightStyle.BOLD);
        style.setCellValue(title);
        return style;
    }

    /**
     * 获取表头默认样式
     * 
     * @return
     */
    public static ExeclStyle getDefaultExcelHeadStyle() {
        ExeclStyle style = new ExeclStyle();
        style.setColor(new ExeclGray40Color());
        style.setFontHeightInPoints((short) 15);
        style.setBoldWeight(ExeclStyle.ExeclBoldWeightStyle.BOLD);
        return style;
    }

    /**
     * 公用生成excel数据方法,只对String类型的值进行支持,如果列表数据中有date数据, 或者无意义的数据(类型为1),转换成有意义的string类型后传入
     * 
     * @param title 标题
     * @param headLabels 表头label
     * @param dataLabels 数据label,对于数据map中的key ,用于获取map中的数据
     * @param dataMaps 数据列表,通过dataLabels中的key来获取
     * @param sheetName sheetName
     * @param out 输出流
     * @throws IOException
     */
    public static HSSFWorkbook generateExcelUserDefaultStyleWithSimpleData(String title, String[] headLabels,
                                                                           String[] dataLabels,
                                                                           List<Map<String, String>> dataMaps,
                                                                           String sheetName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = null;
        if (sheetName != null) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        int index = 0;
        // 有报表头
        if (headLabels != null && headLabels.length > 0) {
            HSSFCellStyle headStyle = workbook.createCellStyle();
            HSSFFont headFont = workbook.createFont();

            // 标题
            if (StringUtils.isNotBlank(title)) {
                HSSFCellStyle headStyleTitle = workbook.createCellStyle();
                HSSFFont headFontTitle = workbook.createFont();
                row = sheet.createRow(index);
                cell = row.createCell(0);
                cell.setCellStyle(headStyleTitle);
                cell.setCellValue(new HSSFRichTextString(title));
                headStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                headStyleTitle.setFont(headFontTitle);
                headFontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                headFontTitle.setFontHeightInPoints((short) 28);
                sheet.addMergedRegion(new CellRangeAddress(index, index, 0, headLabels.length - 1));
                index++;
            }

            // 创建行
            headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            headStyle.setWrapText(true);// 设置自动换行
            headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
            headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            row = sheet.createRow(index);
            for (int i = 0; i < headLabels.length; i++) {
                // 写入单元格
                cell = row.createCell(i);
                text = new HSSFRichTextString(headLabels[i]);
                cell.setCellValue(text);
                cell.setCellStyle(headStyle);
                headStyle.setFont(headFont);
                headStyle.setFillForegroundColor(new ExeclGray40Color().getIndex());
                headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                headFont.setFontHeightInPoints((short) 15);

                // 如果相邻两列标题相同就合并单元格
                if (i > 0 && headLabels[i - 1].equals(headLabels[i])) {
                    CellRangeAddress cellRange = new CellRangeAddress(index, index, i - 1, i);
                    sheet.addMergedRegion(cellRange);
                }
            }
            index++;
        }

        if (dataMaps != null) {
            HSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            dataStyle.setWrapText(true);// 设置自动换行

            HSSFCell dataCell = null;
            String dataKey = null;
            String value = null;
            for (Iterator<Map<String, String>> itMap = dataMaps.iterator(); itMap.hasNext();) {
                Map<String, String> dataMap = itMap.next();
                row = sheet.createRow(index);
                for (int i = 0; i < dataLabels.length; i++) {
                    dataKey = dataLabels[i];
                    value = dataMap.get(dataKey);
                    dataCell = row.createCell(i);
                    dataCell.setCellValue(value);
                    dataCell.setCellStyle(dataStyle);
                }
                index++;
            }
        }

        sheet.setDefaultColumnWidth(15);

        return workbook;

    }

    /**
     * 当导出报错时，当报错信息写到excle中返回给用户
     * 
     * @param error
     * @param sheetName
     * @return
     * @throws IOException
     */
    public static HSSFWorkbook errorExcelForDisplay(String error, String sheetName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = null;
        if (sheetName != null) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }

        HSSFRow row = null;

        row = sheet.createRow(0);
        row.createCell(0).setCellValue(error);
        sheet.setDefaultColumnWidth(15);

        return workbook;

    }

    public static void main(String[] args) {
        try {
            OutputStream ous = new FileOutputStream("d:\\abc.xls");
            long start = System.currentTimeMillis();
            // 报表表头
            String[] headsLabel = { "数据表头", "数据表头", "数据表头", "数据表头", "数据表头" };
            // String<ExeclStyle> heads = ExeclUtil.createHeadUseDefaultStyle(headsLabel);

            List<Map<String, String>> dataMap = new ArrayList<Map<String, String>>();
            String[] dataLabel = { "data1", "data2", "data3", "data4", "data5" };
            // 数据
            for (int r = 1; r <= 1000; r++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("data1", "数据1");
                map.put("data2", "数据2");
                map.put("data3", "数据3");
                map.put("data4", "数据4");
                map.put("data5", "数据5");
                dataMap.add(map);
            }

            // 第一个参数是标题
            generateExcelUserDefaultStyleWithSimpleData("报表标题", headsLabel, dataLabel, dataMap, null);
            long end = System.currentTimeMillis();
            System.out.println("waste time " + (end - start) + " ms.");
            ous.flush();
            ous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HSSFWorkbook generateExcelUserDefaultStyleWithSimpleDataSetType(String title, String[] headLabels,
                                                                                  String[] dataLabels,
                                                                                  Map<String, String> typeMap,
                                                                                  List<Map<String, String>> dataMaps,
                                                                                  String sheetName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = null;
        if (sheetName != null) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFRichTextString text = null;
        int index = 0;
        // 有报表头
        if (headLabels != null && headLabels.length > 0) {
            HSSFCellStyle headStyle = workbook.createCellStyle();
            HSSFFont headFont = workbook.createFont();

            // 标题
            if (StringUtils.isNotBlank(title)) {
                HSSFCellStyle headStyleTitle = workbook.createCellStyle();
                HSSFFont headFontTitle = workbook.createFont();
                row = sheet.createRow(index);
                cell = row.createCell(0);
                cell.setCellStyle(headStyleTitle);
                cell.setCellValue(new HSSFRichTextString(title));
                headStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                headStyleTitle.setFont(headFontTitle);
                headFontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                headFontTitle.setFontHeightInPoints((short) 28);
                sheet.addMergedRegion(new CellRangeAddress(index, index, 0, headLabels.length - 1));
                index++;
            }

            // 创建行
            headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            headStyle.setWrapText(true);// 设置自动换行
            headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
            headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            row = sheet.createRow(index);
            for (int i = 0; i < headLabels.length; i++) {
                // 写入单元格
                cell = row.createCell(i);
                text = new HSSFRichTextString(headLabels[i]);
                cell.setCellValue(text);
                cell.setCellStyle(headStyle);
                headStyle.setFont(headFont);
                headStyle.setFillForegroundColor(new ExeclGray40Color().getIndex());
                headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                headFont.setFontHeightInPoints((short) 15);

                // 如果相邻两列标题相同就合并单元格
                if (i > 0 && headLabels[i - 1].equals(headLabels[i])) {
                    CellRangeAddress cellRange = new CellRangeAddress(index, index, i - 1, i);
                    sheet.addMergedRegion(cellRange);
                }
            }
            index++;
        }

        if (dataMaps != null) {
            HSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            dataStyle.setWrapText(true);// 设置自动换行
            HSSFCell dataCell = null;
            for (Iterator<Map<String, String>> itMap = dataMaps.iterator(); itMap.hasNext();) {
                Map<String, String> dataMap = itMap.next();

                row = sheet.createRow(index);
                for (int i = 0; i < dataLabels.length; i++) {
                    String dataKey = dataLabels[i];
                    dataCell = row.createCell(i);
                    String value = dataMap.get(dataKey);
                    if (StringUtil.isNotEmpty(typeMap.get(dataKey)) && StringUtil.isNotEmpty(value)) {
                        dataCell.setCellValue(Double.parseDouble(value));
                    } else {
                        dataCell.setCellValue(value);
                    }
                    dataCell.setCellStyle(dataStyle);
                }
                index++;
            }
        }

        sheet.setDefaultColumnWidth(15);

        return workbook;

    }
}
