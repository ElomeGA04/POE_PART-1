package loginapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private Login login;

    @Before
    public void setup() {
        login = new Login();
    }

    // -------- assertEquals tests (messages) --------
    @Test
    public void testUsernameCorrectlyFormatted_Message() {
        String msg = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(msg.contains("Username successfully captured."));
        assertTrue(msg.contains("Password successfully captured."));
        assertTrue(msg.contains("Cell number successfully captured."));
    }

    @Test
    public void testUsernameIncorrectlyFormatted_Message() {
        String msg = login.registerUser("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
                msg
        );
    }

    @Test
    public void testPasswordMeetsComplexity_Message() {
        String msg = login.registerUser("Amy", "Jones", "am_y", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(msg.contains("Password successfully captured."));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity_Message() {
        String msg = login.registerUser("Amy", "Jones", "am_y", "password", "+27838968976");
        assertEquals(
                "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                msg
        );
    }

    @Test
    public void testCellNumberCorrectlyFormatted_Message() {
        String msg = login.registerUser("Kayla", "Reed", "ky_l", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(msg.contains("Cell number successfully captured."));
    }

    @Test
    public void testCellNumberIncorrectlyFormatted_Message() {
        String msg = login.registerUser("Kayla", "Reed", "ky_l", "Ch&&sec@ke99!", "08966553");
        assertEquals(
                "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.",
                msg
        );
    }

    // -------- assertTrue / assertFalse tests --------
    @Test
    public void testLoginSuccessful_true() {
        login.registerUser("Keegan", "Miller", "kg_n", "Abcdef1!", "+27718693002");
        boolean ok = login.loginUser("kg_n", "Abcdef1!");
        assertTrue(ok);
        assertEquals("Welcome Keegan ,Miller it is great to see you again.", login.returnLoginStatus());
    }

    @Test
    public void testLoginFailed_false() {
        login.registerUser("Keegan", "Miller", "kg_n", "Abcdef1!", "+27718693002");
        boolean ok = login.loginUser("kg_n", "Wrong123!");
        assertFalse(ok);
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus());
    }

    @Test
    public void testUsernameCorrect_true() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrect_false() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordComplex_true() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordComplex_false() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellCorrect_true() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellIncorrect_false() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}
