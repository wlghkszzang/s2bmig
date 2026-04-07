package com.aas.display.application.queryservice;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectReqDto;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;
import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;
import com.aas.display.domain.repository.StandardDisplayCategoryConnectQueryRepository;
import com.aas.display.domain.entity.PrStdCtgDispInfo;
import com.aas.display.application.transfer.StandardDisplayCategoryConnectAppTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StandardDisplayCategoryConnectQueryService {

    private final StandardDisplayCategoryConnectQueryRepository queryRepository;
    private final StandardDisplayCategoryConnectAppTransfer transfer;

    public List<StandardDisplayCategoryConnectRspDto> getList(StandardDisplayCategoryConnectReqDto reqDto) {
        StandardDisplayCategoryConnectQueryParam param = new StandardDisplayCategoryConnectQueryParam();
        param.setStdCtgNo(reqDto.getStdCtgNo());

        List<PrStdCtgDispInfo> entities = queryRepository.selectConnectList(param);
        return transfer.toRspDtoList(entities);
    }
}
