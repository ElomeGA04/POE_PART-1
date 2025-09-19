package loginapp;

import java.util.Objects;
import java.util.regex.Pattern;

public class Login {

    // ===== Stored registration details =====
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;

    // ===== State for login result =====
    private boolean isAuthenticated = false;

    public Login() {
    }

    public Login(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }

    // ---------- SPEC CHECKS ----------
    // Username contains underscore and is no more than five characters
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }

        boolean hasUnderscore = username.contains("_");
        if (username.contains("_") && username.length() <= 5) {
            return true;
        }

        boolean lengthOk = username.length() <= 5;
        return hasUnderscore && lengthOk;
    }

    // Password must be at least 8 chars, contain a capital letter, a number, and a special char
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }

        boolean longEnough = password.length() >= 8;
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasNumber = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^A-Za-z0-9]").matcher(password).find();

        return longEnough && hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cell) {
        if (cell == null) {
            return false;
        }
        // ^\+ : must start with '+'
        // \d{1,3} : 1–3 digit country code
        // \d{1,10} : 1–10 digit subscriber number (<=10 as required)
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        return Pattern.compile(regex).matcher(cell).matches();
    }

    // ---------- REGISTRATION FLOW ----------
    // Returns the exact messages required by the spec tables
    public String registerUser(String firstName, String lastName, String username, String password, String cell) {
        // Validate username
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        // Validate password
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        // Validate cell number via regex requirement (AI-assisted)
        if (!checkCellPhoneNumber(cell)) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }

        // All good – persist details
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cell;

        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }

    // ---------- LOGIN FLOW ----------
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        boolean ok = Objects.equals(this.username, enteredUsername)
                && Objects.equals(this.password, enteredPassword);
        this.isAuthenticated = ok;
        return ok;
    }

    public String returnLoginStatus() {
        if (isAuthenticated) {
            // Exact success message (note space/comma as per brief)
            return "Welcome " + firstName + " ," + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Convenience getters for testing
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    /*
     * References (AI attribution for regex idea)
     * ChatGPT. (2025, September 19). Regular-expression pattern guidance for international phone formats with country codes.
     * OpenAI. Prompt: “Create a regex that validates a phone number beginning with +<country code> and up to 10-digit subscriber number.”
     */
}
