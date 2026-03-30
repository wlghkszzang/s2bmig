package com.aas.display.infrastructure.db;

import com.aas.display.domain.entity.PrStdCtg;
import com.aas.display.domain.repository.command.StandardCategoryCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StandardCategoryCommandRepositoryImpl implements StandardCategoryCommandRepository {

    private final StandardCategoryCommandMyBatisDao myBatisDao;

    @Override
    public void updatePrStdCtg(PrStdCtg prStdCtg) {
        myBatisDao.updatePrStdCtg(prStdCtg);
    }

    @Override
    public void insertPrStdCtgList(List<PrStdCtg> list) {
        myBatisDao.insertPrStdCtgList(list);
    }

    @Override
    public void updatePrStdCtgGridList(List<PrStdCtg> list) {
        myBatisDao.updatePrStdCtgGridList(list);
    }

    @Override
    public void deletePrStdCtgList(List<String> list) {
        myBatisDao.deletePrStdCtgList(list);
    }

    @Override
    public void deletePrStdCtgGoodsAttrList(String stdCtgNo) {
        myBatisDao.deletePrStdCtgGoodsAttrList(stdCtgNo);
    }

    @Override
    public void insertPrStdCtgGoodsAttrList(List<PrStdCtg.AttrInfo> list) {
        myBatisDao.insertPrStdCtgGoodsAttrList(list);
    }
}
