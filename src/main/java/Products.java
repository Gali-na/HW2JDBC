import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Products {
    static final String DB_CONECTION = "jdbc:mysql://localhost:3306/agency?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_Password = "password";

    public static Map<Integer, Product> productsList() {
        Map<Integer, Product> IdAndProducts = new HashMap<>(0);
        Integer curentIdProduct = 0;

        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Id, name, price, quantity FROM order_database.products");
            while (resultSet.next()) {
                curentIdProduct = resultSet.getInt(1);
                Product product = new Product();
                product.setName( resultSet.getString(2));
                product.setPrice( resultSet.getInt(3));
                product.setQuantity( resultSet.getInt(4));
                IdAndProducts.put(curentIdProduct, product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return IdAndProducts;
    }
}
