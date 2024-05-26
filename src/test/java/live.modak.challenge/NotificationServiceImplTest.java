package live.modak.challenge;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {

    private Gateway gateway;
    private NotificationServiceImpl service;

    @Before
    public void setUp() {
        gateway = mock(Gateway.class);
        service = new NotificationServiceImpl(gateway);
    }

    @Test
    public void testSendStatusNotificationsWithinLimit() {
        service.send("status", "user1", "status update 1");
        service.send("status", "user1", "status update 2");

        verify(gateway, times(1)).send("user1", "status update 1");
        verify(gateway, times(1)).send("user1", "status update 2");
    }

    @Test
    public void testSendStatusNotificationsExceedingLimit() {
        service.send("status", "user1", "status update 1");
        service.send("status", "user1", "status update 2");
        service.send("status", "user1", "status update 3");

        verify(gateway, times(1)).send("user1", "status update 1");
        verify(gateway, times(1)).send("user1", "status update 2");
        verify(gateway, never()).send("user1", "status update 3");
    }

    @Test
    public void testSendNewsNotificationsWithinLimit() {
        service.send("news", "user1", "news 1");

        verify(gateway).send("user1", "news 1");
    }

    @Test
    public void testSendNewsNotificationsExceedingLimit() {
        service.send("news", "user1", "news 1");
        service.send("news", "user1", "news 2");

        verify(gateway).send("user1", "news 1");
        verify(gateway, never()).send("user1", "news 2");
    }

    @Test
    public void testSendMarketingNotificationsWithinLimit() {
        service.send("marketing", "user1", "marketing 1");
        service.send("marketing", "user1", "marketing 2");
        service.send("marketing", "user1", "marketing 3");

        verify(gateway).send("user1", "marketing 1");
        verify(gateway).send("user1", "marketing 2");
        verify(gateway).send("user1", "marketing 3");
    }

    @Test
    public void testSendMarketingNotificationsExceedingLimit() {
        service.send("marketing", "user1", "marketing 1");
        service.send("marketing", "user1", "marketing 2");
        service.send("marketing", "user1", "marketing 3");
        service.send("marketing", "user1", "marketing 4");

        verify(gateway).send("user1", "marketing 1");
        verify(gateway).send("user1", "marketing 2");
        verify(gateway).send("user1", "marketing 3");
        verify(gateway, never()).send("user1", "marketing 4");
    }
}
