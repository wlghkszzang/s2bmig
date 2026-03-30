package com.aas.display.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SaveStandardCategoryGridCommand {
    private List<SaveStandardCategoryCommand> create;
    private List<SaveStandardCategoryCommand> update;
    private List<SaveStandardCategoryCommand> delete;
}
