import com.tbagroup.domain.dto.CraneDto;
import com.tbagroup.domain.dto.TrackDto;
import com.tbagroup.service.CraneService;
import com.tbagroup.service.impl.CraneServiceImpl;
import org.junit.Test;

public class CraneServiceTest {

    @Test
    public void startWork_SuccessfulScenario() {
        CraneService serviceImpl = getMockCraneService();
        serviceImpl.startWork();
    }

    private CraneService getMockCraneService() {
        TrackDto track = new TrackDto("Test-track", 6, 2);
        track.addCrane(getMockCrane(2));
        track.addCrane(getMockCrane(7));
        return new CraneServiceImpl(track);
    }

    private CraneDto getMockCrane(int position) {
        return new CraneDto(position);
    }
}
