
package loginapp;




import java.nio.file.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private static final Random RNG = new Random();

    // --------- Message fields (per spec) ---------
    private final String messageId;       // 10-digit numeric
    private final int messageNumber;      // auto-incremented per session
    private final String recipient;       // "+<code><number>", <= 10 digits after '+'
    private final String message;         // <= 250 chars (see note)
    private final String messageHash;     // e.g. "00:0:HITONIGHT"
    private final String status;          // "Sent" | "Stored" | "Disregarded"

    // --------- Constructor does validation ---------
    public Message(int messageNumber, String recipient, String message, String status) {
        this.messageId = generateTenDigitId();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.status = status;
        this.messageHash = createMessageHash();
    }

    // ===== Spec checks =====
    public static boolean checkMessageID(String id) {
        return id != null && id.matches("^\\d{1,10}$"); // "not more than ten characters"
    }

    // Recipient: “no more than ten characters and contains international code”
    // We’ll require a '+' then 1–10 digits (reuse Part 1 phone concept).
    public static boolean checkRecipientCell(String recipient) {
    if (recipient == null) return false;

    // Accepts numbers that start with + followed by 8–13 digits
    // e.g. +27718693002
    return recipient.matches("^\\+\\d{8,13}$");
}

    // 250-char limit (brief’s Part 2 tests use 250, but see note below)
    public static String checkMessageLengthFeedback(String msg) {
        int max = 250;
        if (msg == null) msg = "";
        if (msg.length() <= max) return "Message ready to send.";
        int over = msg.length() - max;
        return "Message exceeds 250 characters by " + over + ", please reduce size.";
    }

    private static String generateTenDigitId() {
        // random 10-digit numeric (first digit can be 0, which helps create "00:" in hashes)
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) sb.append(RNG.nextInt(10));
        return sb.toString();
    }

    // HASH = first two numbers of ID + ":" + messageNumber + ":" + FIRST+LAST words (upper, no spaces)
    public String createMessageHash() {
        String head2 = messageId.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last  = words.length > 0 ? words[words.length - 1] : "";
        String wordPair = (first + last).replaceAll("[^A-Za-z0-9]", "");
        return (head2 + ":" + messageNumber + ":" + wordPair).toUpperCase(Locale.ROOT);
    }

    // Store ONE message as a JSON line (NDJSON) so Part 3 can read them back easily.
    // No external libs needed; each line is a valid JSON object.
    public void storeMessageToJson(String fileName) throws IOException {
        String json = toJsonObject();
        Path p = Paths.get(fileName);
        Files.write(p, (json + System.lineSeparator()).getBytes(), 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String toJsonObject() {
        return "{"
            + "\"messageId\":\"" + esc(messageId) + "\","
            + "\"messageNumber\":" + messageNumber + ","
            + "\"recipient\":\"" + esc(recipient) + "\","
            + "\"message\":\"" + esc(message) + "\","
            + "\"messageHash\":\"" + esc(messageHash) + "\","
            + "\"status\":\"" + esc(status) + "\""
            + "}";
    }

    // Getters
    public String getMessageId()    { return messageId; }
    public int getMessageNumber()   { return messageNumber; }
    public String getRecipient()    { return recipient; }
    public String getMessage()      { return message; }
    public String getMessageHash()  { return messageHash; }
    public String getStatus()       { return status; }
}
