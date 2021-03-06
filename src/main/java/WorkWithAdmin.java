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
        Product product = new Product();
        System.out.println("Добавьте пожалуйста товар ");
        System.out.println();
        System.out.println("Введите название товара ");
        String nameProduct = scanner.nextLine();
        product.setName(nameProduct);
        System.out.println("Введите стоимость товара ");
       int priceProduct = scanner.nextInt();
        product.setPrice(priceProduct);
        System.out.println("Введите количество товара ");
        int quantity = scanner.nextInt();
        product.setQuantity(quantity);
        System.out.println("Подтвердите внесенные данные -1");
        int rezult = scanner.nextInt();
        if (rezult == 1) {
            try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_database.products (Id, name, price,quantity) VALUES (NULL, ?, ?,?)");
                preparedStatement.setString(1, product.getName());
                preparedStatement.setInt(2, product.getPrice());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Товар внесен в базу данных");
        } else {
            System.out.println("Товар не был внесен");
        }
    }
}
