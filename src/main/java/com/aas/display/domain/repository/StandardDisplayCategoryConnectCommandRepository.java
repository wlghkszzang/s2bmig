package com.aas.display.domain.repository;

import java.util.List;

public interface StandardDisplayCategoryConnectCommandRepository {
    void insertConnectList(List<Object> createList);
    void updateConnectList(List<Object> updateList);
    void deleteConnectList(List<Object> deleteList);
}
