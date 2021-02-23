import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WorkWithClient {
    static final String DB_CONECTION = "jdbc:mysql://localhost:3306/agency?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_Password = "password";

    public static void checkingUserRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Введите ваш пароль регистрации");
        String password = scanner.nextLine();
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT clients.name FROM order_database.clients WHERE order_database.clients.password =?");
            preparedStatement.setString(1, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            if (result == false) {
                System.out.println(" Пользователя с данным паролем не найдено, зарегестрируйтесь пожалуйста");
                registeringClientDatabase();
            }
            if (result == true) {
                orderMenu(password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void registeringClientDatabase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваше имя ");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию");
        String surname = scanner.nextLine();
        System.out.println("Введите номер телефона ");
        String namberPhone = scanner.nextLine();
        System.out.println("Введите пароль ");
        String password = scanner.nextLine();
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_database.clients (Id, name, surname, phone_number, password) VALUES (NULL, ?, ?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, namberPhone);
            preparedStatement.setString(4, password);
            preparedStatement.execute();
            System.out.println(" Вы зарегестрировались");
            orderMenu(password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void orderMenu(String password) {
        Map<Integer, Integer> idQuantityGoods = new HashMap<>(0);
        Set<Integer> keys = Products.productsList().keySet();
        Scanner scanner = new Scanner(System.in);
        int numberGoods = 0;
        System.out.println("Если вы хотите добавить товар в таблицу укажите количество товара ");
        System.out.println("Если товар вас не интересует - 0");
        System.out.println("");
        System.out.println("Выберите товары ");
        System.out.println("");
        for (Integer key : keys) {
            System.out.println(Products.productsList().get(key));
            numberGoods = scanner.nextInt();
            if (numberGoods != 0) {
                idQuantityGoods.put(key, numberGoods);
            }
        }
        recordingIdOrder(idQuantityGoods, password);
    }

    public static void recordingIdOrder(Map<Integer, Integer> idQuantityGoods, String password) {
        int idCurentOrder = 0;
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            connection.setAutoCommit(false);
            CallableStatement callableStatementOne = connection.prepareCall("{CALL creating_new_record_in_orders_table(?,?)}");
            callableStatementOne.setString(2, password);
            callableStatementOne.execute();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT order_database.orders.Id  FROM order_database.orders WHERE order_database.orders.sum_order IS NULL;");
            while (resultSet.next()) {
                idCurentOrder = resultSet.getInt(1);
            }
            connection.commit();
            recordingOrder(idQuantityGoods, idCurentOrder);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void recordingOrder(Map<Integer, Integer> idQuantityGoods, int idCurentOrder) {
        String questionClience = " Заказ оформлен, спасибо";
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO order_database.orders_goods(id, id_order, id_product, number_products, price_products, purchase_amount)" +
                    "VALUE(NULL, ?,?,?,?,?)");
            Set<Integer> idProductInOrder = idQuantityGoods.keySet();
            for (Integer idProduct : idProductInOrder) {
                int quantityGoods = idQuantityGoods.get(idProduct);
                if (checkinQuantityGoods(idProduct, quantityGoods) < 0) {
                    questionClience = questionClience + " " + pductNameSearch(idProduct) + " отсудствует в указаном количестве, товар не был добавленн в заказ";
                    continue;
                }
                preparedStatement.setInt(1, idCurentOrder);
                preparedStatement.setInt(2, idProduct);
                preparedStatement.setInt(3, quantityGoods);
                CallableStatement callableStatement2 = connection.prepareCall("{CALL retutn_price_product_new(?,?)}");
                callableStatement2.setInt(1, idProduct);
                callableStatement2.registerOutParameter(2, Types.INTEGER);
                callableStatement2.execute();
                int priceProduct = callableStatement2.getInt(2);
                preparedStatement.setInt(4, priceProduct);
                preparedStatement.setInt(5, priceProduct * quantityGoods);
                preparedStatement.executeUpdate();
                updatingRemainsCreature(idProduct, quantityGoods);
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(questionClience);
    }

    public static int checkinQuantityGoods(int idProduct, int quantityGoods) {
        int rezult = 0;
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            connection.setAutoCommit(false);
            CallableStatement callableStatement = connection.prepareCall("{CALL checking_number_goods_warehouse(?,?,?)}");
            callableStatement.setInt(1, idProduct);
            callableStatement.setInt(2, quantityGoods);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();
            rezult = callableStatement.getInt(3);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezult;
    }

    public static String pductNameSearch(int idProduct) {
        String nameProduct = "";
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {

            CallableStatement callableStatement4 = connection.prepareCall("{CALL product_name_search(?,?)}");
            callableStatement4.setInt(1, idProduct);
            callableStatement4.registerOutParameter(2, Types.VARCHAR);
            callableStatement4.execute();
            nameProduct = callableStatement4.getString("name_product");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nameProduct;
    }

    public static void updatingRemainsCreature(int idProduct, int quantityGoods) {
        try (Connection connection = DriverManager.getConnection(DB_CONECTION, DB_USER, DB_Password)) {
            connection.setAutoCommit(false);
            CallableStatement callableStatement5 = connection.prepareCall("{CALL updating_quantity_goods_updated(?,?)}");
            callableStatement5.setInt(1, idProduct);
            callableStatement5.setInt(2, quantityGoods);
            callableStatement5.execute();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
