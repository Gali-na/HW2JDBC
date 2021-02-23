import java.sql.*;
import java.util.Scanner;

public class WorkWithAdmin {

    static final String DB_CONECTION = "jdbc:mysql://localhost:3306/agency?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_Password = "password";

    public static void checkingAdminRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Введите ваш пароль администратора ");
        String password = scanner.nextLine();
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT administrator.name FROM order_database.administrator WHERE order_database.administrator.password =?");
            preparedStatement.setString(1, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            if (result == false) {
                System.out.println(" Aдминистратор с данным паролем не найдено, вы не можете вносить новые товары в базу данных");
            }
            if (result == true) {
                addingProductsDatabase();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addingProductsDatabase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добавьте пожалуйста товар ");
        System.out.println();
        System.out.println("Введите название товара ");
        String nameProduct = scanner.nextLine();
        System.out.println("Введите стоимость товара ");
        String priceProduct = scanner.nextLine();
        System.out.println("Подтвердите внесенные данные -1");
        int rezult = scanner.nextInt();
        if (rezult == 1) {
            try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_database.products (Id, name, price) VALUES (NULL, ?, ?)");
                preparedStatement.setString(1, nameProduct);
                preparedStatement.setString(2, priceProduct);
                preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Товар внесен в базу данных");
        } else {
            System.out.println("Товар не был внесен");
        }
    }
}
