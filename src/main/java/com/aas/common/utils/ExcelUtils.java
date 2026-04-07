package com.aas.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 범용 엑셀 다운로드 및 업로드(파싱) 유틸리티
 */
public class ExcelUtils {

    /**
     * 엑셀 다운로드 (Map 리스트 기반)
     */
    public static void downloadExcel(HttpServletResponse response, String fileName, String sheetName,
                                     List<String> headerLabels, List<String> headerKeys,
                                     List<Map<String, Object>> dataList) throws IOException {

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
            Sheet sheet = workbook.createSheet(sheetName);
            CellStyle headerStyle = createHeaderStyle(workbook);

            // 헤더 생성
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headerLabels.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerLabels.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 데이터 생성
            int rowNum = 1;
            for (Map<String, Object> data : dataList) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < headerKeys.size(); i++) {
                    Object value = data.get(headerKeys.get(i));
                    setCellValue(row.createCell(i), value);
                }
            }

            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + ".xlsx\"");

            workbook.write(response.getOutputStream());
            workbook.dispose();
        }
    }

    /**
     * 엑셀 파일 파싱
     */
    public static List<Map<String, String>> readExcel(InputStream inputStream) throws Exception {
        List<Map<String, String>> resultList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return resultList;

            int lastCellNum = headerRow.getLastCellNum();
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < lastCellNum; i++) {
                headers.add(getCellValueAsString(headerRow.getCell(i)));
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Map<String, String> rowMap = new HashMap<>();
                for (int j = 0; j < lastCellNum; j++) {
                    rowMap.put(headers.get(j), getCellValueAsString(row.getCell(j)));
                }
                resultList.add(rowMap);
            }
        }
        return resultList;
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell) ? cell.getLocalDateTimeCellValue().toString() : String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value == null) cell.setCellValue("");
        else if (value instanceof Number num) cell.setCellValue(num.doubleValue());
        else if (value instanceof Boolean bool) cell.setCellValue(bool);
        else cell.setCellValue(value.toString());
    }
}
