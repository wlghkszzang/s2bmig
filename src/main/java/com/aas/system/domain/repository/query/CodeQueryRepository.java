package com.aas.system.domain.repository.query;

import com.aas.system.domain.model.StStdCd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeQueryRepository {
    
    /**
     * 공통그룹코드(들)에 속한 공통코드 목록 조회
     * @param arr 공통코드 그룹 배열
     * @param langCd 언어 코드 (예: "ko")
     * @return 코드 목록
     */
    List<StStdCd> getStStdCd(@Param("arr") String[] arr, @Param("langCd") String langCd);
}
