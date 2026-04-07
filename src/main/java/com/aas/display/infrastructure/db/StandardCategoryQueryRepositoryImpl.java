package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.StandardCategoryQueryRepository;
import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 표준 카테고리 조회 레파지토리 상세 구현 (원본 복구)
 */
@Repository
@RequiredArgsConstructor
public class StandardCategoryQueryRepositoryImpl implements StandardCategoryQueryRepository {

    private final StandardCategoryQueryMyBatisDao mapper;

    @Override
    public List<StandardCategoryMgmtQueryResult> getPrStdCtgListWithHierarchy() {
        return mapper.getPrStdCtgListWithHierarchy();
    }

    @Override
    public StandardCategoryMgmtQueryResult getStandardCategoryInfo(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryInfo(param);
    }

    @Override
    public int getStandardCategoryMgmtChildListCount(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryMgmtChildListCount(param);
    }

    @Override
    public List<StandardCategoryMgmtQueryResult> getStandardCategoryMgmtChildList(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryMgmtChildList(param);
    }

    @Override
    public int getStandardCategoryMgmtGoodsListCount(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryMgmtGoodsListCount(param);
    }

    @Override
    public List<StandardCategoryGoodsQueryResult> getStandardCategoryMgmtGoodsList(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryMgmtGoodsList(param);
    }

    @Override
    public int getStandardCategoryGoodsAttrListCount(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryGoodsAttrListCount(param);
    }

    @Override
    public List<StandardCategoryAttrQueryResult> getStandardCategoryGoodsAttrList(StandardCategoryMgmtQueryParam param) {
        return mapper.getStandardCategoryGoodsAttrList(param);
    }

    @Override
    public List<StandardCategoryAttrQueryResult> getAttInfoList(Map<String, Object> param) {
        return mapper.getAttInfoList(param);
    }
}
