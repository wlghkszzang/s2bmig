package com.aas.display.infrastructure.db;

import com.aas.display.domain.entity.PrDispCtg;
import com.aas.display.domain.repository.command.DisplayCategoryCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DisplayCategoryCommandRepositoryImpl implements DisplayCategoryCommandRepository {
    private final DisplayCategoryCommandMyBatisDao mapper;

    @Override
    public void registerPrDispCtgBase(PrDispCtg item) {
        mapper.insertPrDispCtgBase(item);
    }

    @Override
    public void modifyPrDispCtgBaseDispCtgNo(PrDispCtg item) {
        mapper.updatePrDispCtgBaseDispCtgNo(item);
    }

    @Override
    public void modifyPrDispCtgBaseDispCtgNoList(List<PrDispCtg> createList) {
        mapper.updatePrDispCtgBaseDispCtgNoList(createList);
    }

    @Override
    public void registerPrDispCtgBaseMl(PrDispCtg item) {
        mapper.insertPrDispCtgBaseMl(item);
    }

    @Override
    public void registerPrDispCtgBaseMlList(List<PrDispCtg> createList) {
        mapper.insertPrDispCtgBaseMlList(createList);
    }

    @Override
    public void modifyPrDispCtgBaseDetail(PrDispCtg item) {
        mapper.updatePrDispCtgBaseDetail(item);
    }

    @Override
    public void modifyPrDispCtgBaseMl(PrDispCtg item) {
        mapper.updatePrDispCtgBaseMl(item);
    }

    @Override
    public void modifyPrDispCtgBaseList(List<PrDispCtg> updateList) {
        mapper.updatePrDispCtgBaseList(updateList);
    }

    @Override
    public void modifyPrDispCtgBaseMlList(List<PrDispCtg> updateList) {
        mapper.updatePrDispCtgBaseMlList(updateList);
    }

    @Override
    public void removePrDispCtgBaseList(List<PrDispCtg> deleteList) {
        mapper.deletePrDispCtgBaseList(deleteList);
    }

    @Override
    public void removePrDispCtgBaseMlList(List<PrDispCtg> deleteList) {
        mapper.deletePrDispCtgBaseMlList(deleteList);
    }

    @Override
    public void registerPrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> createList) {
        mapper.insertPrDispGoodsInfoList(createList);
    }

    @Override
    public void modifyPrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> updateList) {
        mapper.updatePrDispGoodsInfoList(updateList);
    }

    @Override
    public void removePrDispGoodsInfoList(PrDispCtg.GoodsInfo item) {
        mapper.deletePrDispGoodsInfoList(item);
    }
}