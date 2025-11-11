
package loginapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class MessageStore {

    static String[] sendMessage = {
        "Did you get the cake?",
        "Where are you? You are late! I have asked you to be on time.",
        "Yohoooo, I am at your gate.",
        "It is dinner time!"
    };
    static String[] recipients = {
        "+27834557896",
        "+27838884567",
        "+27834484567",
        "0838884567"
    };
    static String[] messageFlags = {"Sent", "Stored", "Disregard", "Sent"};
    static String[] disregardedMessages = {"Yohoooo, I am at your gate."};
    static String[] messageIDs = {"MSG001", "MSG002", "MSG003", "MSG004"};
    static String[] messageHashes = new String[sendMessage.length];

    public static void main(String[] args) throws Exception {
        generateMessageHashes();
        displaySenderAndRecipient();
        displayLongestMessage();
        searchMessageByID("MSG002");
        searchMessageByRecipient("+27838884567");
        deleteMessageByHash(messageHashes[1]);
        displayReport();
        readFromJSON();  
        displayReport(); 
    }

    public static void generateMessageHashes() throws NoSuchAlgorithmException {
        for (int i = 0; i < sendMessage.length; i++) {
            messageHashes[i] = hash(sendMessage[i]);
        }
    }

    public static String hash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(message.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static void displaySenderAndRecipient() {
        System.out.println("\n+++ Sent Messages +++");
        for (int i = 0; i < sendMessage.length; i++) {
            System.out.println("Recipient: " + recipients[i]);
            System.out.println("Message: " + sendMessage[i]);
            System.out.println("Flag: " + messageFlags[i]);
        }
    }

    public static void displayLongestMessage() {
        String longest = sendMessage[0];
        for (String msg : sendMessage)
            if (msg.length() > longest.length())
                longest = msg;
        System.out.println("\nLongest message: " + longest);
    }

    public static void searchMessageByID(String messageID) {
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i].equals(messageID)) {
                System.out.println("\nSearch result for ID: " + messageID + ":");
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + sendMessage[i]);
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    public static void searchMessageByRecipient(String recipient) {
        System.out.println("\nMessage sent to " + recipient + ":");
        boolean found = false;
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].equals(recipient)) {
                System.out.println(sendMessage[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No message found by this recipient.");
    }

    public static void deleteMessageByHash(String hash) {
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i].equals(hash)) {
                System.out.println("\nDeleting message: " + sendMessage[i]);
                sendMessage[i] = "[DELETED]";
                return;
            }
        }
        System.out.println("Message not found by Hash");
    }

    public static void displayReport() {
        System.out.println("\n+++ REPORT OF ALL SENT MESSAGES +++");
        for (int i = 0; i < sendMessage.length; i++) {
            System.out.println("Message ID: " + (i < messageIDs.length ? messageIDs[i] : "N/A"));
            System.out.println("Hash: " + (i < messageHashes.length ? messageHashes[i] : "N/A"));
            System.out.println("Message: " + sendMessage[i]);
            System.out.println("----------------------------");
        }
    }

    public static void  readFromJSON() {
        String jsonData = "[{\"message\":\"Hi\"},{\"message\":\"Bye\"}]";
        System.out.println("\nReading JSON messages: " + jsonData);
        // Example only: in real case, use org.json or Gson to parse JSON.
    
       
    }
  
   
}

//Jackson ObjectMapper – FasterXML Project. (n.d.). Retrieved from https://github.com/FasterXML/jackson