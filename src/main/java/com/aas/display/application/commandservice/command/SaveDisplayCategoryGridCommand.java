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
public class SaveDisplayCategoryGridCommand {
    private List<SaveDisplayCategoryCommand> create;
    private List<SaveDisplayCategoryCommand> update;
    private List<SaveDisplayCategoryCommand> delete;
}
