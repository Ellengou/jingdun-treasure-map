package com.jd.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 类ExeclStyle.java的实现描述：控制execl输出单元格的样式。
 * 
 * @author Starty 2015年6月5日 下午5:29:34
 */
public class ExeclStyle {

    // 单元格中的值
    private String               cellValue;

    // 背景色
    private HSSFColor            color;

    // 底边框
    private ExeclBorderStyle     borderBottomStyle = ExeclBorderStyle.THIN;

    // 左边框
    private ExeclBorderStyle     borderLeftStyle   = ExeclBorderStyle.THIN;

    // 右边框
    private ExeclBorderStyle     borderRightStyle  = ExeclBorderStyle.THIN;

    // 顶边框
    private ExeclBorderStyle     borderTopStyle    = ExeclBorderStyle.THIN;

    // 居中，居左还是居右
    private ExeclAlignStyle      align;

    // 是否能自动换行
    private boolean              needWrapText;

    // 字体名称, 默认宋体
    private String               fontName          = "宋体";

    // 字体大小
    private short                fontHeightInPoints;

    // 字体是否加粗，默认不加粗
    private ExeclBoldWeightStyle boldWeight        = ExeclBoldWeightStyle.NORMAL;

    /**
     * execl单元格字体显示是否粗体枚举类。
     * 
     * @author yingchao.zyc
     * @date 2013-10-12 下午1:57:34
     */
    public enum ExeclBoldWeightStyle {
        BOLD(HSSFFont.BOLDWEIGHT_BOLD), NORMAL(HSSFFont.BOLDWEIGHT_NORMAL);

        private short boldWeight;

        private ExeclBoldWeightStyle(short boldWeight){
            this.boldWeight = boldWeight;
        }

        public short getBoldWeight() {
            return boldWeight;
        }

        public void setBoldWeight(short boldWeight) {
            this.boldWeight = boldWeight;
        }

    }

    /**
     * execl边框样式枚举类。
     * 
     * @author yingchao.zyc
     * @date 2013-10-12 下午1:18:59
     */
    public enum ExeclBorderStyle {
        DASH_DOT(HSSFCellStyle.BORDER_DASH_DOT), DASH_DOT_DOT(HSSFCellStyle.BORDER_DASH_DOT_DOT),
        DASHED(HSSFCellStyle.BORDER_DASHED), DOTTED(HSSFCellStyle.BORDER_DOTTED), DOUBLE(HSSFCellStyle.BORDER_DOUBLE),
        HAIR(HSSFCellStyle.BORDER_HAIR), MEDIUM(HSSFCellStyle.BORDER_MEDIUM),
        MEDIUM_DASH_DOT(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT),
        MEDIUM_DASH_DOT_DOT(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT),
        MEDIUM_DASHED(HSSFCellStyle.BORDER_MEDIUM_DASHED), NONE(HSSFCellStyle.BORDER_NONE),
        SLANTED_DASH_DOT(HSSFCellStyle.BORDER_SLANTED_DASH_DOT), THICK(HSSFCellStyle.BORDER_THICK),
        THIN(HSSFCellStyle.BORDER_THIN);

        private short borderStyle;

        private ExeclBorderStyle(short borderStyle){
            this.borderStyle = borderStyle;
        }

        public short getBorderStyle() {
            return borderStyle;
        }

        public void setBorderStyle(short borderStyle) {
            this.borderStyle = borderStyle;
        }
    }

    /**
     * execl单元格内容位置枚举类
     * 
     * @author yingchao.zyc
     * @date 2013-10-12 下午1:19:18
     */
    public enum ExeclAlignStyle {
        CENTER(HSSFCellStyle.ALIGN_CENTER, "居中"), FILL(HSSFCellStyle.ALIGN_FILL, "填充"),
        JUSTIFY(HSSFCellStyle.ALIGN_JUSTIFY, "自适应"), LEFT(HSSFCellStyle.ALIGN_LEFT, "居左"),
        RIGHT(HSSFCellStyle.ALIGN_RIGHT, "居右");

        public short  alignStyle;

        public String alignStyleName;

        ExeclAlignStyle(short alignStyle, String alignStyleName){
            this.alignStyle = alignStyle;
            this.alignStyleName = alignStyleName;
        }

        public short getAlignStyle() {
            return alignStyle;
        }

        public void setAlignStyle(short alignStyle) {
            this.alignStyle = alignStyle;
        }

        public String getAlignStyleName() {
            return alignStyleName;
        }

        public void setAlignStyleName(String alignStyleName) {
            this.alignStyleName = alignStyleName;
        }
    }

    public HSSFColor getColor() {
        return color;
    }

    public void setColor(HSSFColor color) {
        this.color = color;
    }

    public ExeclBorderStyle getBorderBottomStyle() {
        return borderBottomStyle;
    }

    public void setBorderBottomStyle(ExeclBorderStyle borderBottomStyle) {
        this.borderBottomStyle = borderBottomStyle;
    }

    public ExeclBorderStyle getBorderLeftStyle() {
        return borderLeftStyle;
    }

    public void setBorderLeftStyle(ExeclBorderStyle borderLeftStyle) {
        this.borderLeftStyle = borderLeftStyle;
    }

    public ExeclBorderStyle getBorderRightStyle() {
        return borderRightStyle;
    }

    public void setBorderRightStyle(ExeclBorderStyle borderRightStyle) {
        this.borderRightStyle = borderRightStyle;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    public void setFontHeightInPoints(short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
    }

    public ExeclBoldWeightStyle getBoldWeight() {
        return boldWeight;
    }

    public void setBoldWeight(ExeclBoldWeightStyle boldWeight) {
        this.boldWeight = boldWeight;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public ExeclBorderStyle getBorderTopStyle() {
        return borderTopStyle;
    }

    public void setBorderTopStyle(ExeclBorderStyle borderTopStyle) {
        this.borderTopStyle = borderTopStyle;
    }

    public ExeclAlignStyle getAlign() {
        return align;
    }

    public void setAlign(ExeclAlignStyle align) {
        this.align = align;
    }

    public boolean isNeedWrapText() {
        return needWrapText;
    }

    public void setNeedWrapText(boolean needWrapText) {
        this.needWrapText = needWrapText;
    }
}
