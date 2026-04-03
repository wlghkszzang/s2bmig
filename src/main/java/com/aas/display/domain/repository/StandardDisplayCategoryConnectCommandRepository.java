import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import java.util.List;

public interface StandardDisplayCategoryConnectCommandRepository {
    void insertConnectList(List<StandardDisplayCategoryConnectCudDto> createList);
    void updateConnectList(List<StandardDisplayCategoryConnectCudDto> updateList);
    void deleteConnectList(List<StandardDisplayCategoryConnectCudDto> deleteList);
}
