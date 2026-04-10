import java.util.Scanner;
public class Reverse {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String sentence = scan.nextLine();
        String[] parts = sentence.split(" ");
        for (int i = parts.length-1; i >= 0; i--) {
            System.out.print(parts[i]+" ");
        }
    }
}
