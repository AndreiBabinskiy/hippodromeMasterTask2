import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {
    //@Disabled("Тест временно отключен. Правда, правда")
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Test
    public void mainTest() throws Exception {
        Main.main(null);
    }
}
