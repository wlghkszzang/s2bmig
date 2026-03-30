package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.StandardCategoryQueryRepository;
import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryAttrQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryGoodsQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryMgmtQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StandardCategoryQueryRepositoryImpl implements StandardCategoryQueryRepository {

    private final StandardCategoryQueryMyBatisDao myBatisDao;

    @Override
    public List<StandardCategoryMgmtQueryResult> getPrStdCtgListWithHierarchy() {
        return myBatisDao.getPrStdCtgListWithHierarchy();
    }

    @Override
    public StandardCategoryMgmtQueryResult getStandardCategoryInfo(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryInfo(param);
    }

    @Override
    public int getStandardCategoryMgmtChildListCount(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryMgmtChildListCount(param);
    }

    @Override
    public List<StandardCategoryMgmtQueryResult> getStandardCategoryMgmtChildList(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryMgmtChildList(param);
    }

    @Override
    public int getStandardCategoryMgmtGoodsListCount(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryMgmtGoodsListCount(param);
    }

    @Override
    public List<StandardCategoryGoodsQueryResult> getStandardCategoryMgmtGoodsList(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryMgmtGoodsList(param);
    }

    @Override
    public int getStandardCategoryGoodsAttrListCount(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryGoodsAttrListCount(param);
    }

    @Override
    public List<StandardCategoryAttrQueryResult> getStandardCategoryGoodsAttrList(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryGoodsAttrList(param);
    }

    @Override
    public int getStandardCategoryDisplayListCount(StandardCategoryMgmtQueryParam param) {
        return myBatisDao.getStandardCategoryDisplayListCount(param);
    }

    @Override
    public List<StandardCategoryAttrQueryResult> getAttInfoList(Map<String, Object> param) {
        return myBatisDao.getAttInfoList(param);
    }
}
