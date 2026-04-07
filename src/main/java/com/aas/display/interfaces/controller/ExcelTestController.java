package com.aas.display.interfaces.controller;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Excel Test Controller", description = "엑셀 시트 생성 및 파싱 테스트")
@Controller
@RequestMapping("/test/excel")
@RequiredArgsConstructor
public class ExcelTestController {

    private final ExcelDynamicTestService excelDynamicTestService;

    @GetMapping("/excelUploadTestView.do")
    public String excelUploadTestView() {
        return "views/test/excelUploadTest";
    }

    @Operation(summary = "샘플 엑셀 기반 시트 추가 다운로드 테스트")
    @GetMapping("/downloadTemplateTest.do")
    public void downloadTemplateTest(HttpServletResponse response,
            @RequestParam(value = "stdCtgNo", required = false) String stdCtgNo) throws Exception {
        if (stdCtgNo == null || stdCtgNo.isEmpty()) {
            throw new IllegalArgumentException("표준분류 번호가 필요합니다.");
        }

        // 1. DB 조회 및 동적 엑셀 생성
        try (org.apache.poi.ss.usermodel.Workbook workbook = excelDynamicTestService.createDynamicAttrSheet(stdCtgNo)) {

            // 메모리 스트림에 먼저 써서 크기 계산 및 데이터 무결성 확보
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            workbook.write(baos);
            byte[] content = baos.toByteArray();

            // 2. 파일명 인코딩 (브라우저 호환성 강화)
            String fileName = "CategoryAttr_" + stdCtgNo + ".xlsx";
            String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            // 3. 다운로드 헤더 설정
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
            response.setContentLength(content.length);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            // 4. 스트림 출력 및 플러시
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (Exception e) {
            // 에러 발생 시 로그 출력 및 예외 재발생 (공통 에러 페이지 유도)
            e.printStackTrace();
            throw e;
        }
    }

    @Operation(summary = "공통코드 전체 다운로드 테스트")
    @GetMapping("/downloadCommonCode.do")
    public void downloadCommonCode(HttpServletResponse response) throws Exception {
        try (org.apache.poi.ss.usermodel.Workbook workbook = excelDynamicTestService.createCommonCodeWorkbook()) {

            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            workbook.write(baos);
            byte[] content = baos.toByteArray();

            String fileName = "CommonCodes_All.xlsx";
            String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
            response.setContentLength(content.length);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Operation(summary = "카테고리별 고시정보 조회 (그리드용)")
    @GetMapping("/getNotiList.do")
    @ResponseBody
    public java.util.List<com.aas.display.domain.repository.query.result.StandardCategoryNotiQueryResult> getNotiList(
            @RequestParam("stdCtgNo") String stdCtgNo) {
        return excelDynamicTestService.getNotiList(stdCtgNo);
    }

    @Operation(summary = "엑셀 업로드 데이터 분석 및 미리보기")
    @PostMapping("/uploadExcelPreview.do")
    @ResponseBody
    public java.util.Map<String, Object> uploadExcelPreview(
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        return excelDynamicTestService.parseExcelPreview(file);
    }

    @Operation(summary = "수식 적용 옵션 생성기 다운로드")
    @GetMapping("/downloadFormulaExcel.do")
    public void downloadFormulaExcel(HttpServletResponse response) throws Exception {
        try (org.apache.poi.ss.usermodel.Workbook workbook = excelDynamicTestService.createFormulaWorkbook()) {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            workbook.write(baos);
            byte[] content = baos.toByteArray();

            String fileName = "OptionCombinator_Formula.xlsx";
            String encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
            response.setContentLength(content.length);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
