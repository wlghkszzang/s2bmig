package com.aas.display.infrastructure.db;

import com.aas.display.domain.entity.PrDispCtg;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DisplayCategoryCommandMyBatisDao {
    void insertPrDispCtgBase(PrDispCtg item);
    void updatePrDispCtgBaseDispCtgNo(PrDispCtg item);
    void updatePrDispCtgBaseDispCtgNoList(List<PrDispCtg> createList);
    void insertPrDispCtgBaseMl(PrDispCtg item);
    void insertPrDispCtgBaseMlList(List<PrDispCtg> createList);
    
    void updatePrDispCtgBaseDetail(PrDispCtg item);
    void updatePrDispCtgBaseMl(PrDispCtg item);
    
    void updatePrDispCtgBaseList(List<PrDispCtg> updateList);
    void updatePrDispCtgBaseMlList(List<PrDispCtg> updateList);
    
    void deletePrDispCtgBaseList(List<PrDispCtg> deleteList);
    void deletePrDispCtgBaseMlList(List<PrDispCtg> deleteList);
    
    void insertPrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> createList);
    void updatePrDispGoodsInfoList(List<PrDispCtg.GoodsInfo> updateList);
    void deletePrDispGoodsInfoList(PrDispCtg.GoodsInfo item);
}
