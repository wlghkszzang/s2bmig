package com.aas.display.infrastructure.db;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
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
    public void insertConnectList(List<StandardDisplayCategoryConnectCudDto> createList) {
        if (createList != null && !createList.isEmpty()) {
            sqlSessionTemplate.insert(NAMESPACE + "insertConnectList", createList);
        }
    }

    @Override
    public void updateConnectList(List<StandardDisplayCategoryConnectCudDto> updateList) {
        if (updateList != null && !updateList.isEmpty()) {
            sqlSessionTemplate.update(NAMESPACE + "updateConnectList", updateList);
        }
    }

    @Override
    public void deleteConnectList(List<StandardDisplayCategoryConnectCudDto> deleteList) {
        if (deleteList != null && !deleteList.isEmpty()) {
            sqlSessionTemplate.update(NAMESPACE + "deleteConnectList", deleteList);
        }
    }
}
