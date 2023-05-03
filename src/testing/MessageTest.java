package testing;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import blackjack.*;

public class MessageTest {

  @Test
  public void testMessageTypeGetter() {
    Message message = new Message(MessageType.LOGIN, "connect", "login", 0);
    assertEquals(message.getType(), MessageType.LOGIN);
  }

  @Test
  public void testTextGetter() {
    Message message = new Message(MessageType.LOGIN, "connect", "login", 0);
    assertEquals(message.getText(), "login");
  }

  @Test
  public void testSpecialDataGetter() {
    Message message = new Message(MessageType.LOGIN, "connect", "login", 123);
    assertEquals(message.getspecialData(), 123);
  }

  @Test
  public void testActionGetter() {
    Message message = new Message(MessageType.LOGIN, "connect", "login", 0);
    assertEquals(message.getAction(), "connect");
  }

  @Test
  public void testMessageprint() {
    Message message = new Message(MessageType.LOGIN, "connect", "login", 0);
    message.Messageprint();
  }
}

