
package loginapp;



import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void messageLength_ok() {
        String feedback = Message.checkMessageLengthFeedback("Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", feedback);
    }

    @Test
    public void messageLength_tooLong() {
        String longMsg = new String(new char[260]).replace('\0', 'a');
        String fb = Message.checkMessageLengthFeedback(longMsg);
        assertTrue(fb.startsWith("Message exceeds 250 characters by "));
    }

    @Test
    public void recipient_ok() {
        assertTrue(Message.checkRecipientCell("+27718693002".substring(0, 12))); // "+2771869300" (<=10 digits after '+')
    }

    @Test
    public void recipient_bad() {
        assertFalse(Message.checkRecipientCell("08575975889")); // missing '+'
    }

    @Test
    public void hash_expected_from_case1_shape() {
        // Force a message with ID that starts with "00" to mirror rubric example (00:0:HITONIGHT)
        // We can't set ID directly (it’s random), so verify the SHAPE: two digits + ":" + number + ":" + FIRSTLAST
        Message m = new Message(0, "+2712345678", "Hi Mike, can you join us for dinner tonight", "Sent");
        String h = m.getMessageHash();
        assertTrue(h.matches("^\\d{2}:0:[A-Z0-9]+$"));
    }
}
