package com.aas.display.infrastructure.db;

import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;
import com.aas.display.domain.repository.StandardDisplayCategoryConnectQueryRepository;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StandardDisplayCategoryConnectQueryRepositoryImpl implements StandardDisplayCategoryConnectQueryRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private static final String NAMESPACE = "com.aas.display.domain.repository.StandardDisplayCategoryConnectQueryRepository.";

    @Override
    public List<StandardDisplayCategoryConnectRspDto> selectConnectList(StandardDisplayCategoryConnectQueryParam param) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectConnectList", param);
    }
}
