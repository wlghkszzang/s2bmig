package com.aas.display.application.queryservice;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectReqDto;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;
import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;
import com.aas.display.domain.repository.StandardDisplayCategoryConnectQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StandardDisplayCategoryConnectQueryService {

    private final StandardDisplayCategoryConnectQueryRepository queryRepository;

    public List<StandardDisplayCategoryConnectRspDto> getList(StandardDisplayCategoryConnectReqDto reqDto) {
        StandardDisplayCategoryConnectQueryParam param = new StandardDisplayCategoryConnectQueryParam();
        param.setStdCtgNo(reqDto.getStdCtgNo());

        List<Object> rawList = queryRepository.selectConnectList(param);
        
        return rawList.stream().map(obj -> {
            StandardDisplayCategoryConnectRspDto dto = (StandardDisplayCategoryConnectRspDto) obj;
            return dto;
        }).collect(Collectors.toList());
    }
}
