import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.service.TaskService;
import com.tbagroup.service.impl.TaskServiceImpl;
import org.junit.Test;

public class TaskServiceTest {
    private TaskService taskService;

    @Test
    public void produceOneCraneTask_SuccessfulScenario() {
        taskService = getMockTaskService();
        taskService.produceTask(getMockCraneDto());
    }

    @Test
    public void createDummyTask_SuccessfulScenario(){
        taskService = getMockTaskService();
        CraneDto tmp = new CraneDto(18);
        tmp.setInPark(true);
        taskService.produceTask(tmp);
    }

    private TaskService getMockTaskService() {
        return new TaskServiceImpl(8);
    }

    private CraneDto getMockCraneDto() {
        return new CraneDto(2);
    }
}
