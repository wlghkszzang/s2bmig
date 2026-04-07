package com.aas.display.domain.repository;

import com.aas.display.domain.entity.PrStdCtgDispInfo;
import java.util.List;

public interface StandardDisplayCategoryConnectCommandRepository {
    void insertConnectList(List<PrStdCtgDispInfo> createList);
    void updateConnectList(List<PrStdCtgDispInfo> updateList);
    void deleteConnectList(List<PrStdCtgDispInfo> deleteList);
}