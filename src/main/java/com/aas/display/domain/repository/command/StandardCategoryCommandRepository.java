package com.aas.display.domain.repository.command;

import com.aas.display.domain.entity.PrStdCtg;
import java.util.List;

public interface StandardCategoryCommandRepository {
    void updatePrStdCtg(PrStdCtg prStdCtg);
    void insertPrStdCtgList(List<PrStdCtg> list);
    void updatePrStdCtgGridList(List<PrStdCtg> list);
    void deletePrStdCtgList(List<String> list);

    // Attr Grid Bulk
    void deletePrStdCtgGoodsAttrList(String stdCtgNo);
    void insertPrStdCtgGoodsAttrList(List<PrStdCtg.AttrInfo> list);
}
