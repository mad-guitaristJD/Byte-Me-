import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------ByteMe--------");
        System.out.println();
        while(true){
                System.out.println("1.TO ENTER :) \n2.EXIT :( ");
                int choice;
                choice = scanner.nextInt();
                
                if(choice==1){
                    System.out.println("1.REGISTER\n2.LOGIN");
                    choice = scanner.nextInt();
//                    scanner.next();
                    if(choice==1) controller.register();
                    else if(choice==2) controller.login();
                }
                else if(choice==2 || choice == -1){
                    break;
                }
                else{
                    System.out.println("NOT A VALID CHOICE");
                }
        }
    }
}