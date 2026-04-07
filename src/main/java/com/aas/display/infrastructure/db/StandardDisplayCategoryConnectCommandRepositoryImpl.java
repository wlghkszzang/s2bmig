package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.StandardDisplayCategoryConnectCommandRepository;
import com.aas.display.domain.entity.PrStdCtgDispInfo;
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
    public void insertConnectList(List<PrStdCtgDispInfo> createList) {
        if (!createList.isEmpty()) {
            sqlSessionTemplate.insert(NAMESPACE + "insertConnectList", createList);
        }
    }

    @Override
    public void updateConnectList(List<PrStdCtgDispInfo> updateList) {
        if (!updateList.isEmpty()) {
            sqlSessionTemplate.update(NAMESPACE + "updateConnectList", updateList);
        }
    }

    @Override
    public void deleteConnectList(List<PrStdCtgDispInfo> deleteList) {
        if (!deleteList.isEmpty()) {
            sqlSessionTemplate.update(NAMESPACE + "deleteConnectList", deleteList);
        }
    }
}