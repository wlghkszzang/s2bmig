package com.aas.display.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SaveStandardCategoryAttrGridCommand {
    private List<SaveStandardCategoryAttrCommand> create;
    private List<SaveStandardCategoryAttrCommand> update;
    private List<SaveStandardCategoryAttrCommand> delete;
}
