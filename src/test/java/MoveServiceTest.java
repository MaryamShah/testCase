import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;
import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.domain.dto.TaskDto;
import com.tbagroup.domain.dto.TrackDto;
import com.tbagroup.service.MoveService;
import com.tbagroup.service.impl.MoveServiceImpl;
import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

public class MoveServiceTest {
    private MoveService moveService;


    @ThreadedBefore
    public void before() {
        //fillMoveService();
    }

    @ThreadedMain
    public void mainThread() {
        moveService.move(getMockCraneDto(1));
    }

    @ThreadedSecondary
    public void secondThread() {
        moveService.move(getMockCraneDto(2));
    }

    @Test()
    public void move_Scenario() {
        this.fillMoveService();
        new AnnotatedTestRunner().runTests(this.getClass(), MoveServiceImpl.class);
    }

    private void fillMoveService() {
        TrackDto trackDto = new TrackDto("Test-track", 6, 2);
        trackDto.addCrane(getMockCraneDto(1));
        trackDto.addCrane(getMockCraneDto(2));
        moveService = new MoveServiceImpl(trackDto);
    }

    private CraneDto getMockCraneDto(int craneNo) {
        CraneDto craneDto;
        PriorityBlockingQueue<TaskDto> tasks = new PriorityBlockingQueue<>();
        if (craneNo == 1) {
            craneDto = new CraneDto(2);
            tasks.add(new TaskDto(2, 5));   //forward movement
        } else {
            craneDto = new CraneDto(7);
            tasks.add(new TaskDto(7, 3));   //backward movement
        }
        craneDto.addTasks(tasks);
        return craneDto;
    }

}
