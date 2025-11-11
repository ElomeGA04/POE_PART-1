
package loginapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageStoreTest {

    @Test
    public void testSentMessagesArrayPopulated() {
        assertEquals("Did you get the cake?", MessageStore.sendMessage[0]);
        assertEquals("Where are you? You are late! I have asked you to be on time.", MessageStore.sendMessage[1]);
    }

    @Test
    public void testLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        String actual = getLongestMessage();
        assertEquals(expected, actual);
    }

    private String getLongestMessage() {
        String longest = MessageStore.sendMessage[0];
        for (String msg : MessageStore.sendMessage) {
            if (msg.length() > longest.length()) longest = msg;
        }
        return longest;
    }

    @Test
    public void testSearchMessageByID() {
        String expected = "It is dinner time!";
        String result = MessageStore.sendMessage[3]; 
        assertEquals(expected, result);
    }
}

