package loginapp;

import java.util.Scanner;

public class LoginApp {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Login login = new Login();

        //Variable declaration
        String fn, ln, un, pw, cn;
        System.out.println("========== REGISTRATION ============================");

        System.out.print("Enter First name: ");
        fn = in.nextLine();

        System.out.print("Enter Last name : ");
        ln = in.nextLine();

        System.out.print("Enter Username  : ");
        un = in.nextLine();

        System.out.print("Enter Password  : ");
        pw = in.nextLine();

        System.out.print("Enter Cell (+..): ");
        cn = in.nextLine();

        //Invoking 
        String reg = login.registerUser(fn, ln, un, pw, cn);
        System.out.println(reg);

        if (!reg.contains("Cell number successfully captured.")) {
            return;
        }

        System.out.println("\n=== LOGIN ===");

        System.out.print("Enter Username: ");
        String lu = in.nextLine();

        System.out.print("Password: ");
        String lp = in.nextLine();

        login.loginUser(lu, lp);

        System.out.println(login.returnLoginStatus());
    }

}
