package com.aas.display.infrastructure.db;

import com.aas.display.domain.entity.PrStdCtg;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StandardCategoryCommandMyBatisDao {
    void insertPrStdCtgList(List<PrStdCtg> list);
    void updatePrStdCtg(PrStdCtg prStdCtg);
    void updatePrStdCtgGridList(List<PrStdCtg> list);
    void deletePrStdCtgList(List<String> list);

    // Attr Grid Bulk
    void deletePrStdCtgGoodsAttrList(String stdCtgNo);
    void insertPrStdCtgGoodsAttrList(List<PrStdCtg.AttrInfo> list);
}
