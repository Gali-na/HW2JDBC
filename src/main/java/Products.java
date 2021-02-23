import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Products {
    static final String DB_CONECTION = "jdbc:mysql://localhost:3306/agency?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_Password = "password";

    public static Map<Integer, String> productsList() {
        Map<Integer, String> products = new HashMap<>(0);
        Integer curentIdProduct = 0;
        String curentNamePrice = "";
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Id, name, price  FROM order_database.products");
            while (resultSet.next()) {
                curentIdProduct = resultSet.getInt(1);
                curentNamePrice = resultSet.getString(2) + " " + resultSet.getString(3) +"грн";
                products.put(curentIdProduct, curentNamePrice);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }
}
