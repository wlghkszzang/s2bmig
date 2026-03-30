package com.aas.display.domain.repository.command;

import com.aas.display.domain.entity.PrDispCtg;
import java.util.List;

public interface DisplayCategoryCommandRepository {
    void registerPrDispCtgBase(PrDispCtg item);
    void modifyPrDispCtgBaseDispCtgNo(PrDispCtg item);
    void modifyPrDispCtgBaseDispCtgNoList(List<PrDispCtg> createList);
    void registerPrDispCtgBaseMl(PrDispCtg item);
    void registerPrDispCtgBaseMlList(List<PrDispCtg> createList);
    
    void modifyPrDispCtgBaseDetail(PrDispCtg item);
    void modifyPrDispCtgBaseMl(PrDispCtg item);
    
    void modifyPrDispCtgBaseList(List<PrDispCtg> updateList);
    void modifyPrDispCtgBaseMlList(List<PrDispCtg> updateList);
    
    void removePrDispCtgBaseList(List<PrDispCtg> deleteList);
    void removePrDispCtgBaseMlList(List<PrDispCtg> deleteList);
    
    void registerPrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> createList);
    void modifyPrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> updateList);
    void removePrDispGoodsInfoList(PrDispCtg.GoodsInfo item);
}
