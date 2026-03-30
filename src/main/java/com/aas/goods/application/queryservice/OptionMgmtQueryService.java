package com.aas.goods.application.queryservice;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.aas.goods.application.queryservice.query.GetOptionListQuery;
import com.aas.goods.application.queryservice.query.OptionListQueryResponse;
import com.aas.goods.application.transfer.OptionMgmtAppTransfer;
import com.aas.goods.domain.repository.query.OptionMgmtQueryRepository;
import com.aas.goods.domain.repository.query.param.OptionMgmtQueryParam;
import com.aas.goods.domain.repository.query.result.OptionMgmtQueryResult;

@Service
@RequiredArgsConstructor
public class OptionMgmtQueryService {

    private final OptionMgmtQueryRepository optionMgmtQueryRepository;
    private final OptionMgmtAppTransfer appTransfer;

    public List<OptionListQueryResponse> getOptionCategoryList(GetOptionListQuery query) {
        OptionMgmtQueryParam param = appTransfer.toQueryParam(query);
        List<OptionMgmtQueryResult> domainResult = optionMgmtQueryRepository.getOptionCategoryList(param);
        return appTransfer.toQueryResponseList(domainResult);
    }

    public List<OptionListQueryResponse> getOptionList(GetOptionListQuery query) {
        OptionMgmtQueryParam param = appTransfer.toQueryParam(query);
        List<OptionMgmtQueryResult> domainResult = optionMgmtQueryRepository.getOptionList(param);
        return appTransfer.toQueryResponseList(domainResult);
    }
}
