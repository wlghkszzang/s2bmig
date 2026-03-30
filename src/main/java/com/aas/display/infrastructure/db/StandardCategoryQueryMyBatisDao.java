package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryAttrQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryGoodsQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryMgmtQueryResult;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface StandardCategoryQueryMyBatisDao {
    List<StandardCategoryMgmtQueryResult> getPrStdCtgListWithHierarchy();
    StandardCategoryMgmtQueryResult getStandardCategoryInfo(StandardCategoryMgmtQueryParam search);
    int getStandardCategoryMgmtChildListCount(StandardCategoryMgmtQueryParam search);
    List<StandardCategoryMgmtQueryResult> getStandardCategoryMgmtChildList(StandardCategoryMgmtQueryParam search);
    int getStandardCategoryMgmtGoodsListCount(StandardCategoryMgmtQueryParam search);
    List<StandardCategoryGoodsQueryResult> getStandardCategoryMgmtGoodsList(StandardCategoryMgmtQueryParam search);
    int getStandardCategoryGoodsAttrListCount(StandardCategoryMgmtQueryParam search);
    List<StandardCategoryAttrQueryResult> getStandardCategoryGoodsAttrList(StandardCategoryMgmtQueryParam search);
    int getStandardCategoryDisplayListCount(StandardCategoryMgmtQueryParam search);
    List<StandardCategoryAttrQueryResult> getAttInfoList(Map<String, Object> param);
}
