import java.io.*;
import java.util.*;
public class ATM {

    static ArrayList<String> PinCodes = new ArrayList<>();
    static ArrayList<String> Names = new ArrayList<>();
    static ArrayList<String> Balance = new ArrayList<>();
    static ArrayList<String> Transactions = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    static int pinInput;
    static int attempts = 3;
    static int index = 0;

    public static void main(String[] args) {
        String Accounts = "BankAccounts.txt";
        splitInfo(Accounts);
//		System.out.println(PinCodes);
//		System.out.println(Names);
//		System.out.println(Balance);

        //boolean mayAccount;
        while(true) {

            System.out.print("Enter Pin Code: ");
            pinInput = scan.nextInt();

            boolean hasAccount = inputPin(Accounts);

            if(hasAccount) {
                MainMenu();
                break;
            }
            else {
                attempts--;
                if (attempts == 0) {
                    System.out.println("Account has been locked.");
                }
                else{
                    System.out.println(attempts+" attempts remaining.");
                }
                index = 0;//if none of the conditions were met, index resets to zero

            }

        }
    }


    public static void CheckBalance() {
        System.out.println("Balance is: ₱" + Long.parseLong(Balance.get(index)));
    }

    public static void Deposit() {
        System.out.print("Enter amount to deposit: ");
        int deposit = scan.nextInt();

        //main part of the function
        long newBalance = deposit + Long.parseLong(Balance.get(index));
        System.out.println("Current balance: ₱" + newBalance);
        Balance.set(index, String.valueOf(newBalance));

        Transactions.add("Deposit: "+ "\t₱"+ deposit +"\t\t" + newBalance);
    }

    public static void Withdraw() {
        System.out.print("Enter amount: ");
        long withdraw = scan.nextLong();

        if(withdraw %500==0) {
            if(withdraw > Long.parseLong(Balance.get(index))) {
                System.out.println("Insufficient balance. Please try again or deposit.");
                Transactions.add("Withdraw: " + "\t₱"+ withdraw +"\t\tInsufficient funds.");
            }
            else {
                long newBalance = Long.parseLong(Balance.get(index)) - withdraw;
                System.out.println("Withdrew Successfully!\nCurrent balance: " + newBalance);
                Balance.set(index, String.valueOf(newBalance));
                Transactions.add("Withdraw: " + "\t₱"+ withdraw +"\t\t₱"+newBalance);
            }
        }

        else if(withdraw > Long.parseLong(Balance.get(index))) {
            System.out.println("Insufficient balance. Please try again or deposit.");
            Transactions.add("Withdraw: " + "\t₱"+ withdraw +"\t\tInsufficient funds.");
        }

        else {
            System.out.println("This ATM only gives out cash in divisible of 500.");
        }
    }

    public static void showTransactionHistory(){
        System.out.println("-----------------------------------------\n"+
                "\tTRANSACTION HISTORY\n"+
                "-----------------------------------------\n"+
                "#   Type        Amount       Balance\n"+
                "-----------------------------------------");
        for (int i = 0; i < Transactions.size(); i++) {
            System.out.println((i + 1) + ".  " + Transactions.get(i));
        }
        System.out.println("-----------------------------------------");
    }

    public static void splitInfo(String Accounts) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Accounts));
            String line;
            while((line = reader.readLine()) != null) {
                String[] infoNindo = line.split(",");
                PinCodes.add(infoNindo[0]);
                Names.add(infoNindo[1]);
                Balance.add(infoNindo[2]);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean inputPin(String Accounts) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Accounts));
            String line;
            while((line = reader.readLine()) != null) {
                for(int i = 0; i < PinCodes.size(); i++, index++) {
                    if(pinInput == Integer.parseInt(PinCodes.get(i))){
                        System.out.println("Welcome, " +Names.get(i));
                        return true;//may account
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;//mayo account
    }
    public static void MainMenu() {
        while(true) {
            System.out.println("     BANK\n" +
                    "[1] Check balance\n" +
                    "[2] Deposit\n" +
                    "[3] Withdraw\n" +
                    "[4] Transaction History\n" +
                    "[5] Exit bank\n");
            System.out.print("Enter choice: ");
            int choice = scan.nextInt();

            switch(choice) {
                case 1:
                    CheckBalance();
                    break;
                case 2:
                    Deposit();
                    break;
                case 3:
                    Withdraw();
                    break;
                case 4:
                    showTransactionHistory();
                    break;
                default:
                    System.out.println("Have you called it yet?");
            }//switch
            if(choice == 4) {
                System.out.println("Thank you for using our bank.");
                return;
            }
        }//loop
    }
}