package com.aas.goods.application.commandservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.aas.goods.domain.repository.command.OptionMgmtCommandRepository;
import com.aas.goods.application.commandservice.command.*;
import com.aas.goods.application.transfer.OptionMgmtAppTransfer;
import com.aas.goods.domain.model.PrOptnClssCd;
import com.aas.goods.domain.model.PrOptnCd;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionMgmtCommandService {

    private final OptionMgmtCommandRepository optionMgmtCommandRepository;
    private final OptionMgmtAppTransfer appTransfer;

    @Transactional
    /**
     * 옵션 분류 CUD
     */
    public void saveOptionCategoryList(SaveOptionCategoryListCommand command) {
        // 기존 x2bee-bo/api-bo 의 로직과 Validator를 이곳에 배치
        // insertPrOptnClssCd, validateInsertPrOptnClssCd 분기 등 이관

        // App Transfer (Command -> Domain)
        List<PrOptnClssCd> creates = appTransfer.toCategoryEntityList(command.getCreateList());
        List<PrOptnClssCd> updates = appTransfer.toCategoryEntityList(command.getUpdateList());

        if (creates != null && !creates.isEmpty()) {
            for(PrOptnClssCd cd : creates) {
                optionMgmtCommandRepository.insertPrOptnClssCd(cd);
            }
        }
        if (updates != null && !updates.isEmpty()) {
            for(PrOptnClssCd cd : updates) {
                optionMgmtCommandRepository.updatePrOptnClssCd(cd);
            }
        }
    }

    @Transactional
    /**
     * 옵션 코드 CUD
     */
    public void saveOptionList(SaveOptionCodeListCommand command) {
        // App Transfer (Command -> Domain)
        List<PrOptnCd> creates = appTransfer.toCodeEntityList(command.getCreateList());
        List<PrOptnCd> updates = appTransfer.toCodeEntityList(command.getUpdateList());

        if (creates != null && !creates.isEmpty()) {
            for(PrOptnCd cd : creates) {
                optionMgmtCommandRepository.insertPrOptnCd(cd);
            }
        }
        if (updates != null && !updates.isEmpty()) {
            for(PrOptnCd cd : updates) {
                optionMgmtCommandRepository.updatePrOptnCd(cd);
            }
        }
    }
}
