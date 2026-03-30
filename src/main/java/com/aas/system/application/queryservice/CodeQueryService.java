package com.aas.system.application.queryservice;

import com.aas.system.domain.model.StStdCd;
import com.aas.system.domain.repository.query.CodeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeQueryService {

    private final CodeQueryRepository codeQueryRepository;

    /**
     * 그룹코드 배열을 받아 언어(Lang)에 따라 공통코드 맵을 반환합니다.
     * @param grpCds 그룹코드 문자열 콤마 구분 (예: "PR018,PR019")
     * @return 그룹코드를 키로 가지는 코드 리스트 Map
     */
    public Map<String, List<StStdCd>> getStStdCd(String grpCds) {
        if (grpCds == null || grpCds.isBlank()) {
            return Map.of();
        }

        // 콤마 구분 파싱 및 공백 제거
        String[] arr = grpCds.replace(" ", "").toUpperCase().split(",");
        
        // 다국어 컨텍스트 대신 고정값 'ko' 설정 (사용자 요청 사항)
        String langCd = "ko";

        List<StStdCd> stdCdEntityList = codeQueryRepository.getStStdCd(arr, langCd);
        
        // 그룹코드(GRP_CD) 별로 그룹핑하여 Map으로 반환
        return stdCdEntityList.stream().collect(Collectors.groupingBy(StStdCd::getGrpCd));
    }
}
