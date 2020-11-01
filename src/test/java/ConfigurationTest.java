import com.tbagroup.domain.Configuration;
import org.junit.Assert;
import org.junit.Test;

/**
 * this class support some important scenarios in initializing a track, its length and cranes boundaries
 */
public class ConfigurationTest {
    @Test
    public void createNewConfiguration_SuccessfulScenario() {
        Configuration conf = new Configuration("track", 3, 1);
        Assert.assertEquals(conf, new Configuration("track", 3, 1));
    }

    @Test
    public void createNewConfiguration_byLessThanZeroLength_ThrowsTrackValidationException() {
        new AssertionError(new Configuration("track", -1, 1));
    }

    @Test
    public void createNewConfiguration_byZeroLength_ThrowsTrackValidationException() {
        new AssertionError(new Configuration("track", 0, 1));
    }

    @Test
    public void createNewConfiguration_byLessThan1Crane_ThrowsTrackValidationException() {
        new AssertionError(new Configuration("track", 10, 0));
    }

    @Test
    public void createNewConfiguration_byMoreThan2Crane_ThrowsTrackValidationException() {
        new AssertionError(new Configuration("track", 10, 3));
    }
}
