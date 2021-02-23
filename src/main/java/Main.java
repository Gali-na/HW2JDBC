import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Зоомагазин ");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Меню для клиентов -1 ");
        System.out.println("Меню для администраторов -2 ");
        int menu = scanner.nextInt();
        if (menu == 1) {
            WorkWithClient.checkingUserRegistration();
        }
        if (menu == 2) {
            WorkWithAdmin.checkingAdminRegistration();
        }
    }
}
