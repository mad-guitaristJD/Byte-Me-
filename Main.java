import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------ByteMe--------");
        System.out.println();
        while(true){
//            try{
                System.out.println("1.TO ENTER :) \n2.EXIT :( ");
                int choice;
                choice = scanner.nextInt();
                
                if(choice==1){
                    controller.login();
                }
                else if(choice==2 || choice == -1){
                    break;
                }
                else{
                    System.out.println("NOT A VALID CHOICE");
                }
//            }
//            catch (Exception e){
//                System.out.println("ENTER A VALID CHOICE");
//                scanner.nextLine();
//            }
        }
    }
}