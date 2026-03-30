package com.aas.goods.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Application Layer: 옵션 분류 리스트 CUD 처리를 위한 Command 인터페이스 캡슐
 */
@Getter
@Setter
public class SaveOptionCategoryListCommand {
    private List<SaveOptionCategoryCommandData> createList;
    private List<SaveOptionCategoryCommandData> updateList;
}
