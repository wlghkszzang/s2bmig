package com.aas.display.application.commandservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDisplayGoodsGridCommand {
    private List<SaveDisplayGoodsCommand> create;
    private List<SaveDisplayGoodsCommand> update;
    private List<SaveDisplayGoodsCommand> delete;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveDisplayGoodsCommand {
        private String dispCtgNo;
        private String goodsNo;
        private String dispYn;
        // 필요 시 정렬순서 등 추가 가능
    }
}
