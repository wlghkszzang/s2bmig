package com.aas.display.application.commandservice;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import com.aas.display.domain.repository.StandardDisplayCategoryConnectCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardDisplayCategoryConnectCommandService {

    private final StandardDisplayCategoryConnectCommandRepository commandRepository;

    public void saveCategoryConnect(List<StandardDisplayCategoryConnectCudDto> createList,
                                    List<StandardDisplayCategoryConnectCudDto> updateList,
                                    List<StandardDisplayCategoryConnectCudDto> deleteList) {
        if (createList != null && !createList.isEmpty()) {
            commandRepository.insertConnectList(createList);
        }
        if (updateList != null && !updateList.isEmpty()) {
            commandRepository.updateConnectList(updateList);
        }
        if (deleteList != null && !deleteList.isEmpty()) {
            commandRepository.deleteConnectList(deleteList);
        }
    }
}
