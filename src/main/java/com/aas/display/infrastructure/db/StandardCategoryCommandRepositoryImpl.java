package com.aas.display.infrastructure.db;

import com.aas.display.domain.entity.PrStdCtg;
import com.aas.display.domain.repository.command.StandardCategoryCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StandardCategoryCommandRepositoryImpl implements StandardCategoryCommandRepository {
    private final StandardCategoryCommandMyBatisDao mapper;

    @Override
    public void updatePrStdCtg(PrStdCtg prStdCtg) {
        mapper.updatePrStdCtg(prStdCtg);
    }

    @Override
    public void insertPrStdCtgList(List<PrStdCtg> list) {
        mapper.insertPrStdCtgList(list);
    }

    @Override
    public void updatePrStdCtgGridList(List<PrStdCtg> list) {
        mapper.updatePrStdCtgGridList(list);
    }

    @Override
    public void deletePrStdCtgList(List<String> list) {
        mapper.deletePrStdCtgList(list);
    }

    @Override
    public void deletePrStdCtgGoodsAttrList(String stdCtgNo) {
        mapper.deletePrStdCtgGoodsAttrList(stdCtgNo);
    }

    @Override
    public void insertPrStdCtgGoodsAttrList(List<PrStdCtg.AttrInfo> list) {
        mapper.insertPrStdCtgGoodsAttrList(list);
    }
}