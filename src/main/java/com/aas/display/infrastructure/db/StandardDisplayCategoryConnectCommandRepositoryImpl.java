package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.StandardDisplayCategoryConnectCommandRepository;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StandardDisplayCategoryConnectCommandRepositoryImpl implements StandardDisplayCategoryConnectCommandRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private static final String NAMESPACE = "com.aas.display.domain.repository.StandardDisplayCategoryConnectCommandRepository.";

    @Override
    public void insertConnectList(List<Object> createList) {
        if (!createList.isEmpty()) {
            sqlSessionTemplate.insert(NAMESPACE + "insertConnectList", createList);
        }
    }

    @Override
    public void updateConnectList(List<Object> updateList) {
        if (!updateList.isEmpty()) {
            sqlSessionTemplate.update(NAMESPACE + "updateConnectList", updateList);
        }
    }

    @Override
    public void deleteConnectList(List<Object> deleteList) {
        if (!deleteList.isEmpty()) {
            sqlSessionTemplate.delete(NAMESPACE + "deleteConnectList", deleteList);
        }
    }
}
