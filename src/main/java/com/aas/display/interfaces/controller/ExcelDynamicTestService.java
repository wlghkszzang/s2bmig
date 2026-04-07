package com.aas.display.interfaces.controller;

import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryAttrQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryNotiQueryResult;
import com.aas.display.domain.repository.query.result.StandardCodeQueryResult;
import com.aas.display.domain.repository.query.result.StandardOptionCodeQueryResult;
import com.aas.display.infrastructure.db.StandardCategoryQueryMyBatisDao;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>엑셀 동적 생성 및 분석 테스트 서비스</h3>
 */
@Service
@RequiredArgsConstructor
public class ExcelDynamicTestService {

    private final StandardCategoryQueryMyBatisDao standardCategoryQueryMyBatisDao;

    /**
     * 선택된 표준분류의 속성 및 고시 정보 기반 엑셀 생성
     */
    public Workbook createDynamicAttrSheet(String stdCtgNo) {
        Workbook workbook;
        String filePath = "C:/Users/jeinf/OneDrive/바탕 화면/아이스크림몰/iscream_git/iscream_git/s2bmig/src/main/resources/static/file/시트테스트.xlsx";

        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = WorkbookFactory.create(fis);
            } else {
                workbook = new XSSFWorkbook();
                if (workbook.getSheet("상품정보") == null)
                    workbook.createSheet("상품정보");
            }
        } catch (Exception e) {
            e.printStackTrace();
            workbook = new XSSFWorkbook();
            workbook.createSheet("상품정보");
        }

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);

        StandardCategoryMgmtQueryParam param = new StandardCategoryMgmtQueryParam();
        param.setStdCtgNo(stdCtgNo);
        param.setLangCd("ko");

        // [1] 속성정보 시트
        String attrSheetName = "속성정보";
        int existingAttrIndex = workbook.getSheetIndex(attrSheetName);
        if (existingAttrIndex != -1)
            workbook.removeSheetAt(existingAttrIndex);
        Sheet attrSheet = workbook.createSheet(attrSheetName);

        List<StandardCategoryAttrQueryResult> attrList = standardCategoryQueryMyBatisDao
                .getStandardCategoryGoodsAttrList(param);
        Row attrCodeRow = attrSheet.createRow(0);
        Row attrNameRow = attrSheet.createRow(1);

        Cell attrCodeCell0 = attrCodeRow.createCell(0);
        attrCodeCell0.setCellValue("TEMP_ID");
        attrCodeCell0.setCellStyle(headerStyle);
        Cell attrNameCell0 = attrNameRow.createCell(0);
        attrNameCell0.setCellValue("임시번호(연결용)");
        attrNameCell0.setCellStyle(headerStyle);

        int aColIdx = 1;
        for (StandardCategoryAttrQueryResult attr : attrList) {
            Cell cCell = attrCodeRow.createCell(aColIdx);
            cCell.setCellValue(attr.getAttCd());
            cCell.setCellStyle(headerStyle);
            Cell nCell = attrNameRow.createCell(aColIdx);
            nCell.setCellValue(attr.getAttNm());
            nCell.setCellStyle(headerStyle);
            attrSheet.setColumnWidth(aColIdx, 6000);
            aColIdx++;
        }
        attrSheet.getRow(0).setZeroHeight(true);
        attrSheet.setColumnWidth(0, 4000);

        for (int i = 2; i < 20; i++) {
            Row dataRow = attrSheet.createRow(i);
            for (int k = 0; k < aColIdx; k++) {
                Cell dataCell = dataRow.createCell(k);
                dataCell.setCellStyle(dataStyle);
            }
        }

        // [2] 고시정보 시트
        String notiSheetName = "고시정보";
        int existingNotiIndex = workbook.getSheetIndex(notiSheetName);
        if (existingNotiIndex != -1)
            workbook.removeSheetAt(existingNotiIndex);
        Sheet notiSheet = workbook.createSheet(notiSheetName);

        List<StandardCategoryNotiQueryResult> notiList = standardCategoryQueryMyBatisDao
                .getStandardCategoryGoodsNotiList(param);
        Row notiCodeRow = notiSheet.createRow(0);
        Row notiNameRow = notiSheet.createRow(1);

        Cell notiCodeCell0 = notiCodeRow.createCell(0);
        notiCodeCell0.setCellValue("TEMP_ID");
        notiCodeCell0.setCellStyle(headerStyle);
        Cell notiNameCell0 = notiNameRow.createCell(0);
        notiNameCell0.setCellValue("임시번호(연결용)");
        notiNameCell0.setCellStyle(headerStyle);

        int nColIdx = 1;
        for (StandardCategoryNotiQueryResult noti : notiList) {
            Cell cCell = notiCodeRow.createCell(nColIdx);
            cCell.setCellValue(noti.getGoodsNotiItemCd());
            cCell.setCellStyle(headerStyle);
            Cell nCell = notiNameRow.createCell(nColIdx);
            nCell.setCellValue(noti.getNotiItemNm());
            nCell.setCellStyle(headerStyle);
            notiSheet.setColumnWidth(nColIdx, 6000);
            nColIdx++;
        }
        notiSheet.getRow(0).setZeroHeight(true);
        notiSheet.setColumnWidth(0, 4000);

        for (int i = 2; i < 20; i++) {
            Row dataRow = notiSheet.createRow(i);
            for (int k = 0; k < nColIdx; k++) {
                Cell dataCell = dataRow.createCell(k);
                dataCell.setCellStyle(dataStyle);
            }
        }

        return workbook;
    }

    public List<StandardCategoryNotiQueryResult> getNotiList(String stdCtgNo) {
        StandardCategoryMgmtQueryParam param = new StandardCategoryMgmtQueryParam();
        param.setStdCtgNo(stdCtgNo);
        param.setLangCd("ko");
        return standardCategoryQueryMyBatisDao.getStandardCategoryGoodsNotiList(param);
    }

    /**
     * 공통코드 및 옵션코드 다중 시트 생성
     */
    public Workbook createCommonCodeWorkbook() {
        Workbook workbook = new XSSFWorkbook();

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // [1] 공통코드 시트
        Sheet sheet1 = workbook.createSheet("공통코드_전체");
        String[] headers1 = { "그룹코드명", "코드", "코드명", "설명" };
        Row headerRow1 = sheet1.createRow(0);
        for (int i = 0; i < headers1.length; i++) {
            Cell cell = headerRow1.createCell(i);
            cell.setCellValue(headers1[i]);
            cell.setCellStyle(headerStyle);
        }

        List<StandardCodeQueryResult> codeList = standardCategoryQueryMyBatisDao.getAllStandardCodeList();
        int rowIdx1 = 1;
        for (StandardCodeQueryResult code : codeList) {
            Row row = sheet1.createRow(rowIdx1++);
            row.createCell(0).setCellValue(code.getGrpCdNm());
            row.createCell(1).setCellValue(code.getCd());
            row.createCell(2).setCellValue(code.getCdNm());
            row.createCell(3).setCellValue(code.getCdDesc());
        }
        for (int i = 0; i < headers1.length; i++) {
            sheet1.autoSizeColumn(i);
        }

        // [2] 옵션코드 시트 생성 (요청 사항 반영)
        Sheet sheet2 = workbook.createSheet("옵션코드_전체");
        String[] headers2 = { "옵션분류명", "옵션분류코드", "옵션명", "옵션코드" };
        Row headerRow2 = sheet2.createRow(0);
        for (int i = 0; i < headers2.length; i++) {
            Cell cell = headerRow2.createCell(i);
            cell.setCellValue(headers2[i]);
            cell.setCellStyle(headerStyle);
        }

        List<StandardOptionCodeQueryResult> optList = standardCategoryQueryMyBatisDao.getAllStandardOptionCodeList();
        int rowIdx2 = 1;
        for (StandardOptionCodeQueryResult opt : optList) {
            Row row = sheet2.createRow(rowIdx2++);
            row.createCell(0).setCellValue(opt.getOptnCatNm());
            row.createCell(1).setCellValue(opt.getOptnCatNo());
            row.createCell(2).setCellValue(opt.getOptnNm());
            row.createCell(3).setCellValue(opt.getOptnNo());
        }
        for (int i = 0; i < headers2.length; i++) {
            sheet2.autoSizeColumn(i);
        }

        return workbook;
    }

    public Map<String, Object> parseExcelPreview(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            result.put("attrData", readSheetData(workbook, "속성정보"));
            result.put("notiData", readSheetData(workbook, "고시정보"));
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "엑셀 분석 중 오류: " + e.getMessage());
        }
        return result;
    }

    private Map<String, Object> readSheetData(Workbook wb, String sheetName) {
        Map<String, Object> dataSet = new HashMap<>();
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null)
            return dataSet;

        List<String> codes = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        Row codeRow = sheet.getRow(0);
        Row nameRow = sheet.getRow(1);
        if (codeRow == null || nameRow == null)
            return dataSet;

        int lastCellNum = codeRow.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            codes.add(getCellValue(codeRow.getCell(i)));
            headers.add(getCellValue(nameRow.getCell(i)));
        }

        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row))
                continue;
            Map<String, Object> rowData = new HashMap<>();
            for (int j = 0; j < lastCellNum; j++) {
                String code = codes.get(j);
                String value = getCellValue(row.getCell(j));
                rowData.put(code, value);
            }
            rows.add(rowData);
        }
        dataSet.put("codes", codes);
        dataSet.put("headers", headers);
        dataSet.put("rows", rows);
        return dataSet;
    }

    private String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue().toString();
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK)
                return false;
        }
        return true;
    }

    /**
     * 수식 적용 옵션 조합 생성기 엑셀 생성 (5단계 확장)
     */
    public Workbook createFormulaWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        workbook.setForceFormulaRecalculation(true);
        Sheet sheet = workbook.createSheet("5단계_옵션조합생성기");

        // 스타일 설정
        CellStyle greenHeader = workbook.createCellStyle();
        greenHeader.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        greenHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        greenHeader.setAlignment(HorizontalAlignment.CENTER);
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        greenHeader.setFont(boldFont);

        CellStyle blueHeader = workbook.createCellStyle();
        blueHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        blueHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        blueHeader.setAlignment(HorizontalAlignment.CENTER);
        blueHeader.setFont(boldFont);

        // 헤더 생성 (A~E 입력 / G~K 출력)
        Row headerRow = sheet.createRow(0);
        String[] inHeaders = { "옵션1(A)", "옵션2(B)", "옵션3(C)", "옵션4(D)", "옵션5(E)" };
        for (int i = 0; i < 5; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(inHeaders[i]);
            cell.setCellStyle(greenHeader);
            sheet.setColumnWidth(i, 4000);
        }

        String[] outHeaders = { "조합1", "조합2", "조합3", "조합4", "조합5" };
        for (int i = 0; i < 5; i++) {
            Cell cell = headerRow.createCell(i + 6);
            cell.setCellValue(outHeaders[i]);
            cell.setCellStyle(blueHeader);
            sheet.setColumnWidth(i + 6, 4000);
        }

        // 샘플 데이터
        Row r1 = sheet.createRow(1);
        r1.createCell(0).setCellValue("빨강");
        r1.createCell(1).setCellValue("95");
        r1.createCell(2).setCellValue("면");
        Row r2 = sheet.createRow(2);
        r2.createCell(0).setCellValue("파랑");
        r2.createCell(1).setCellValue("100");
        r2.createCell(2).setCellValue("폴리");

        // 동적 조합 수식 변수
        String c1 = "MAX(1, COUNTA($A$2:$A$100))";
        String c2 = "MAX(1, COUNTA($B$2:$B$100))";
        String c3 = "MAX(1, COUNTA($C$2:$C$100))";
        String c4 = "MAX(1, COUNTA($D$2:$D$100))";
        String c5 = "MAX(1, COUNTA($E$2:$E$100))";

        // 2000줄 수식 미리 채우기
        for (int i = 1; i <= 2000; i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                row = sheet.createRow(i);

            String rIdx = "(ROW()-2)";
            String limit = "(" + c1 + "*" + c2 + "*" + c3 + "*" + c4 + "*" + c5 + ")";
            String base = "IF(" + rIdx + " < " + limit + ", ";

            row.createCell(6).setCellFormula(base + "INDEX($A$2:$A$100, INT(" + rIdx + "/(" + c2 + "*" + c3 + "*" + c4
                    + "*" + c5 + ")) + 1), \"\")");
            row.createCell(7).setCellFormula(base + "INDEX($B$2:$B$100, MOD(INT(" + rIdx + "/(" + c3 + "*" + c4 + "*"
                    + c5 + ")), " + c2 + ") + 1), \"\")");
            row.createCell(8).setCellFormula(
                    base + "INDEX($C$2:$C$100, MOD(INT(" + rIdx + "/(" + c4 + "*" + c5 + ")), " + c3 + ") + 1), \"\")");
            row.createCell(9).setCellFormula(
                    base + "INDEX($D$2:$D$100, MOD(INT(" + rIdx + "/" + c5 + "), " + c4 + ") + 1), \"\")");
            row.createCell(10).setCellFormula(base + "INDEX($E$2:$E$100, MOD(" + rIdx + ", " + c5 + ") + 1), \"\")");
        }

        return workbook;
    }
}
