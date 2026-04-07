package com.aas.display.application.commandservice;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import com.aas.display.domain.repository.StandardDisplayCategoryConnectCommandRepository;
import com.aas.display.domain.entity.PrStdCtgDispInfo;
import com.aas.display.application.transfer.StandardDisplayCategoryConnectAppTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardDisplayCategoryConnectCommandService {

    private final StandardDisplayCategoryConnectCommandRepository commandRepository;
    private final StandardDisplayCategoryConnectAppTransfer transfer;

    public void saveCategoryConnect(List<StandardDisplayCategoryConnectCudDto> createList,
                                    List<StandardDisplayCategoryConnectCudDto> updateList,
                                    List<StandardDisplayCategoryConnectCudDto> deleteList) {
        
        if (createList != null && !createList.isEmpty()) {
            List<PrStdCtgDispInfo> domainList = transfer.toEntityList(createList);
            commandRepository.insertConnectList(domainList);
        }
        
        if (updateList != null && !updateList.isEmpty()) {
            List<PrStdCtgDispInfo> domainList = transfer.toEntityList(updateList);
            commandRepository.updateConnectList(domainList);
        }
        
        if (deleteList != null && !deleteList.isEmpty()) {
            List<PrStdCtgDispInfo> domainList = transfer.toEntityList(deleteList);
            commandRepository.deleteConnectList(domainList);
        }
    }
}
