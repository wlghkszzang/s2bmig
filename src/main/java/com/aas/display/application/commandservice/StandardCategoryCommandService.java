package com.aas.display.application.commandservice;

import com.aas.display.application.commandservice.command.SaveStandardCategoryCommand;
import com.aas.display.application.commandservice.command.SaveStandardCategoryGridCommand;
import com.aas.display.application.commandservice.command.SaveStandardCategoryAttrGridCommand;
import com.aas.display.application.commandservice.command.SaveStandardCategoryAttrCommand;
import com.aas.display.domain.entity.PrStdCtg;
import com.aas.display.domain.repository.command.StandardCategoryCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardCategoryCommandService {

    private final StandardCategoryCommandRepository commandRepository;

    public void saveStandardCategoryInfo(SaveStandardCategoryCommand command) {
        PrStdCtg prStdCtg = mapToEntity(command);
        commandRepository.updatePrStdCtg(prStdCtg);
    }

    public void saveStandardCategoryGrid(SaveStandardCategoryGridCommand command) {
        List<PrStdCtg> createList = new ArrayList<>();
        if (command.getCreate() != null) {
            command.getCreate().forEach(c -> createList.add(mapToEntity(c)));
        }

        List<PrStdCtg> updateList = new ArrayList<>();
        if (command.getUpdate() != null) {
            command.getUpdate().forEach(u -> updateList.add(mapToEntity(u)));
        }

        List<String> deleteList = new ArrayList<>();
        if (command.getDelete() != null) {
            command.getDelete().forEach(d -> deleteList.add(d.getStdCtgNo()));
        }

        if (!createList.isEmpty()) commandRepository.insertPrStdCtgList(createList);
        if (!updateList.isEmpty()) commandRepository.updatePrStdCtgGridList(updateList);
        if (!deleteList.isEmpty()) commandRepository.deletePrStdCtgList(deleteList);
    }

    public void saveStandardCategoryAttrGrid(SaveStandardCategoryAttrGridCommand command) {
        String stdCtgNo = null;
        List<PrStdCtg.AttrInfo> attrList = new ArrayList<>();

        // Collect all items to save (added + updated)
        if (command.getCreate() != null) {
            for (SaveStandardCategoryAttrCommand c : command.getCreate()) {
                if (stdCtgNo == null) stdCtgNo = c.getStdCtgNo();
                attrList.add(mapToAttrEntity(c));
            }
        }
        if (command.getUpdate() != null) {
            for (SaveStandardCategoryAttrCommand u : command.getUpdate()) {
                if (stdCtgNo == null) stdCtgNo = u.getStdCtgNo();
                attrList.add(mapToAttrEntity(u));
            }
        }
        
        // If no rows but delete remains, we still need stdCtgNo
        if (stdCtgNo == null && command.getDelete() != null && !command.getDelete().isEmpty()) {
            stdCtgNo = command.getDelete().get(0).getStdCtgNo();
        }

        if (stdCtgNo != null) {
            // Delete existing mapping
            commandRepository.deletePrStdCtgGoodsAttrList(stdCtgNo);
            // Insert new mapping
            if (!attrList.isEmpty()) {
                commandRepository.insertPrStdCtgGoodsAttrList(attrList);
            }
        }
    }

    private PrStdCtg.AttrInfo mapToAttrEntity(SaveStandardCategoryAttrCommand command) {
        PrStdCtg.AttrInfo attr = new PrStdCtg.AttrInfo();
        attr.setStdCtgNo(command.getStdCtgNo());
        attr.setAttCd(command.getAttCd());
        attr.setDispSeq(command.getDispSeq());
        attr.setUseYn(command.getUseYn());
        return attr;
    }

    private PrStdCtg mapToEntity(SaveStandardCategoryCommand command) {
        PrStdCtg prStdCtg = new PrStdCtg();
        prStdCtg.setStdCtgNo(command.getStdCtgNo());
        prStdCtg.setStdCtgNm(command.getStdCtgNm());
        prStdCtg.setLeafCtgYn(command.getLeafCtgYn());
        prStdCtg.setUseYn(command.getUseYn());
        prStdCtg.setSortSeq(command.getSortSeq());
        prStdCtg.setUprStdCtgNo(command.getUprStdCtgNo());
        prStdCtg.setSafeCertiNeedYn(command.getSafeCertiNeedYn());
        prStdCtg.setGoodsNotiLisartCd(command.getGoodsNotiLisartCd());
        prStdCtg.setHsCd(command.getHsCd());
        prStdCtg.setMrgnRate(command.getMrgnRate());
        prStdCtg.setSamMarketYn(command.getSamMarketYn());
        prStdCtg.setMdId(command.getMdId());
        return prStdCtg;
    }
}
