package com.aas.display.application.commandservice;

import com.aas.display.application.commandservice.command.*;
import com.aas.display.application.transfer.DisplayCategoryAppTransfer;
import com.aas.display.domain.entity.PrDispCtg;
import com.aas.display.domain.repository.command.DisplayCategoryCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

/**
 * 전시 카테고리 명령 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DisplayCategoryCommandService {

    private final DisplayCategoryCommandRepository commandRepository;
    private final DisplayCategoryAppTransfer appTransfer;

    /**
     * 최상위 카테고리 추가
     */
    public String saveTopCategoryBasicInfo(SaveDisplayCategoryCommand command) {
        PrDispCtg item = appTransfer.toEntity(command);
        item.setDispCtgNm("NEW 최상위카테고리");
        item.setLeafYn("N");
        item.setUseYn("Y");
        item.setDispSeq(1);
        
        commandRepository.registerPrDispCtgBase(item);
        
        if (!StringUtils.hasText(item.getLrgCtgNo())) {
            item.setLrgCtgNo(item.getDispCtgNo());
        }
        
        commandRepository.modifyPrDispCtgBaseDispCtgNo(item);
        commandRepository.registerPrDispCtgBaseMl(item);
        
        return item.getDispCtgNo();
    }

    /**
     * 기본 정보 수정
     */
    public void modifyDisplayCategory(SaveDisplayCategoryCommand command) {
        PrDispCtg item = appTransfer.toEntity(command);
        commandRepository.modifyPrDispCtgBaseDetail(item);
        commandRepository.modifyPrDispCtgBaseMl(item);
    }

    /**
     * 하위 카테고리 리스트 저장 (Bulk Grid Operation)
     */
    public void saveCategoryList(SaveDisplayCategoryGridCommand command) {
        if (command.getCreate() != null && !command.getCreate().isEmpty()) {
            SaveDisplayCategoryCommand first = command.getCreate().getFirst(); // baseInfo 용도로 사용
            registerPrDispCtgBaseList(appTransfer.toEntityList(command.getCreate()), appTransfer.toEntity(first));
        }
        if (command.getUpdate() != null && !command.getUpdate().isEmpty()) {
            modifyPrDispCtgBaseList(appTransfer.toEntityList(command.getUpdate()));
        }
        if (command.getDelete() != null && !command.getDelete().isEmpty()) {
            removePrDispCtgBaseList(appTransfer.toEntityList(command.getDelete()));
        }
    }

    private void registerPrDispCtgBaseList(List<PrDispCtg> createList, PrDispCtg baseInfo) {
        for (PrDispCtg item : createList) {
            item.setUprDispCtgNo(baseInfo.getUprDispCtgNo());
            item.setSiteNo(baseInfo.getSiteNo());
            item.setLrgCtgNo(baseInfo.getLrgCtgNo());
            item.setMidCtgNo(baseInfo.getMidCtgNo());
            item.setSmlCtgNo(baseInfo.getSmlCtgNo());
            item.setThnCtgNo(baseInfo.getThnCtgNo());
            item.setDpmlNo(baseInfo.getDpmlNo());
            item.setLangCd(baseInfo.getLangCd());

            commandRepository.registerPrDispCtgBase(item);

            // Hierarchy Logic
            if (!StringUtils.hasText(item.getLrgCtgNo())) {
                item.setLrgCtgNo(item.getDispCtgNo());
            } else if (!StringUtils.hasText(item.getMidCtgNo())) {
                item.setMidCtgNo(item.getDispCtgNo());
            } else if (!StringUtils.hasText(item.getSmlCtgNo())) {
                item.setSmlCtgNo(item.getDispCtgNo());
            } else if (!StringUtils.hasText(item.getThnCtgNo())) {
                item.setThnCtgNo(item.getDispCtgNo());
            }
        }
        commandRepository.modifyPrDispCtgBaseDispCtgNoList(createList);
        commandRepository.registerPrDispCtgBaseMlList(createList);
    }

    private void modifyPrDispCtgBaseList(List<PrDispCtg> updateList) {
        commandRepository.modifyPrDispCtgBaseList(updateList);
        commandRepository.modifyPrDispCtgBaseMlList(updateList);
    }

    private void removePrDispCtgBaseList(List<PrDispCtg> deleteList) {
        commandRepository.removePrDispCtgBaseList(deleteList);
        commandRepository.removePrDispCtgBaseMlList(deleteList);
    }

    /**
     * 다국어 정보 저장
     */
    public void saveSubCategoryMultiLanguage(List<PrDispCtg> updateList) {
        commandRepository.registerPrDispCtgBaseMlList(updateList);
    }

    /**
     * 다국어 정보 저장
     */
    public void saveSubCategoryMultiLanguage(SaveDisplayCategoryGridCommand command) {
        if (command.getUpdate() != null && !command.getUpdate().isEmpty()) {
            commandRepository.registerPrDispCtgBaseMlList(appTransfer.toEntityList(command.getUpdate()));
        }
    }

    /**
     * 전시 상품 리스트 저장 (Bulk Grid Operation)
     */
    public void saveGoodsList(SaveDisplayGoodsGridCommand command) {
        if (command.getCreate() != null && !command.getCreate().isEmpty()) {
            commandRepository.registerPrDispGoodsInfoList(appTransfer.toGoodsInfoList(command.getCreate()));
        }
        if (command.getUpdate() != null && !command.getUpdate().isEmpty()) {
            commandRepository.modifyPrDispGoodsInfoList(appTransfer.toGoodsInfoList(command.getUpdate()));
        }
        if (command.getDelete() != null && !command.getDelete().isEmpty()) {
            List<PrDispCtg.GoodsInfo> deleteList = appTransfer.toGoodsInfoList(command.getDelete());
            PrDispCtg.GoodsInfo item = new PrDispCtg.GoodsInfo();
            item.setDispCtgNo(deleteList.getFirst().getDispCtgNo());
            item.setGoodsList(deleteList.stream().map(PrDispCtg.GoodsInfo::getGoodsNo).toList());
            commandRepository.removePrDispGoodsInfoList(item);
        }
    }
}
