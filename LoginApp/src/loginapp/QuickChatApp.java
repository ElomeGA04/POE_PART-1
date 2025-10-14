
package loginapp;

import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import javax.swing.JOptionPane;

public class QuickChatApp {

    public static void main(String[] args) {
        // 1) Login gate
        Login login = new Login();
        login.registerUser("Keegan", "Miller", "kg_n", "Abcdef1!", "+27718693002");
        if (!login.loginUser("kg_n", "Abcdef1!")) {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            return;
        }

        // 2) Welcome + menu loop
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat."); // required welcome
        int totalSent = 0;
        int messageCount = askInt("How many messages do you want to enter?");

        int created = 0; // messageNumber seed
        boolean running = true;
        while (running) {
            String opt = JOptionPane.showInputDialog(
                "Choose an option:\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            if (opt == null) break; // cancelled
            switch (opt.trim()) {
                case "1":
                    for (int i = 0; i < messageCount; i++) {
                        int msgNo = created; // use running counter as messageNumber
                        String recipient = JOptionPane.showInputDialog("Enter recipient (+<code><number>, <=10 digits total):");
                        if (recipient == null) break;
                        if (!Message.checkRecipientCell(recipient)) {
                            JOptionPane.showMessageDialog(null,
                                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            continue;
                        }

                        String msg = JOptionPane.showInputDialog("Enter message (<= 250 characters):");
                        if (msg == null) break;

                        String lenFeedback = Message.checkMessageLengthFeedback(msg);
                        if (!"Message ready to send.".equals(lenFeedback)) {
                            JOptionPane.showMessageDialog(null, lenFeedback);
                            continue;
                        }

                        String[] choose = {"Send Message", "Disregard Message", "Store Message"};
                        int choice = JOptionPane.showOptionDialog(null, "Choose what to do with this message:",
                                "Send/Discard/Store", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choose, choose[0]);

                        String status;
                        switch (choice) {
                            case 0: status = "Sent"; break;
                            case 1: status = "Disregarded"; break;
                            case 2: status = "Stored"; break;
                            default: status = "Disregarded";
                        }

                        Message m = new Message(msgNo, recipient, msg, status);

                        // Display details after action (required order)
                        JOptionPane.showMessageDialog(null,
                                "MessageID: " + m.getMessageId()
                                + "\nMessage Hash: " + m.getMessageHash()
                                + "\nRecipient: " + m.getRecipient()
                                + "\nMessage: " + m.getMessage());

                        // Count sent + JSON store when asked
                        if ("Sent".equals(status)) {
                            totalSent++;
                            JOptionPane.showMessageDialog(null, "Message successfully sent.");
                        } else if ("Stored".equals(status)) {
                            try {
                                m.storeMessageToJson("messages.json"); // ChatGPT-assisted JSON
                                JOptionPane.showMessageDialog(null, "Message successfully stored.");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                        }

                        created++; // increment messageNumber seed
                    }
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + totalSent);
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon."); // as required
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Please choose 1–3.");
            }
        }
    }

    private static int askInt(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(prompt);
            if (s == null) return 0;
            try { return Integer.parseInt(s.trim()); }
            catch (Exception e) { JOptionPane.showMessageDialog(null, "Enter a valid number."); }
        }
    }
}
