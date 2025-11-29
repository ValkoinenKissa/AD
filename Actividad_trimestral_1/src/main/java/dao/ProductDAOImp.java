package dao;
/*
Agregar todos los productos que están ubicados en el siguiente JSON dentro de la tabla productos:
https://dummyjson.com/products ✅
•	Agregar una serie empleados y pedidos mediante statement. Tener en cuenta que los pedidos tienen una fk sobre la tabla productos ✅
•	Muestra por consola mediante la ejecución de querys – statement:
o	Todos los productos ✅
o	Todos los productos favoritos con sus datos ✅
•	Muestra por consola todos los productos de la base de datos que tengan un precio inferior a 600€ ✅
•	Inserta en la tabla productos_fav aquellos productos que tengan un valor superior a 1000€ ✅
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import database.DBConnection;
import database.DBScheme;
import model.Employee;
import model.Order;
import model.ProductJSON;
import model.ProductResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;

public class ProductDAOImp implements productDAO {
    private final Connection connection;

    public ProductDAOImp() {
        connection = DBConnection.getConnection();
    }


    @Override
    public void populateDatabase() {
        ObjectMapper mapper = new ObjectMapper();
        int affectedRowsCounter = 0;
        try {
            URI uri = new URI("https://dummyjson.com/products");
            URL url = uri.toURL();
            try (InputStream is = url.openStream()) {
                ProductResponse response = mapper.readValue(is, ProductResponse.class);
                for (ProductJSON item : response.getProducts()) {
                    String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)"
                            , DBScheme.TAB_PRODUCTS_NAME, DBScheme.PRODUCT_ID, DBScheme.PRODUCT_NAME, DBScheme.PRODUCT_DESCRIPTION,
                            DBScheme.PRODUCT_STOCK, DBScheme.PRODUCT_PRICE);
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, item.getId());
                        preparedStatement.setString(2, item.getTitle());
                        preparedStatement.setString(3, item.getDescription());
                        preparedStatement.setInt(4, item.getStock());
                        preparedStatement.setDouble(5, item.getPrice());
                        int affectedRows = preparedStatement.executeUpdate();
                        affectedRowsCounter += affectedRows;

                    } catch (SQLException e) {
                        System.out.println("Error en la consulta SQL");
                        System.out.println(e.getMessage());
                    }
                }

                if (affectedRowsCounter > 0) {
                    System.out.println("Base de datos poblada con exito");
                    System.out.println("Filas afectadas: " + affectedRowsCounter);
                } else {
                    System.out.println("Error al poblar la base de datos, posiblemente esta ya contenga registros");
                    System.out.println("Filas afectadas: " + affectedRowsCounter);
                }
            }

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("Error al parsear la URL");

        } catch (IOException e) {
            System.out.println("Error al consultar la URL");
        }
    }

    @Override
    public boolean checkIfDbIsEmpty() {
        boolean affectedRowsCounter = false;
        String query = String.format("SELECT * FROM %s;", DBScheme.TAB_PRODUCTS_NAME);
        try {
            Statement statement = connection.prepareStatement(query);
            affectedRowsCounter = statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        return affectedRowsCounter;
    }

    @Override
    public void addEmployee(Employee e) {
        String sql = "INSERT INTO empleados (nombre, puesto) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getPosition());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta: " + ex.getMessage());
        }
    }

    @Override
    public void addOrder(Order o) {
        String sql = "INSERT INTO pedidos (id_producto, id_empleado, cantidad, fecha) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, o.getProductID());
            ps.setInt(2, o.getEmployeeID());
            ps.setInt(3, o.getProductQuantity());
            ps.setDate(4, Date.valueOf(o.getDate()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta: " + ex.getMessage());
        }
    }


    @Override
    public boolean existsEmployee(int id) {
        String sql = "SELECT id FROM empleados WHERE id = ?";

        return searchCoincidences(id, sql);
    }

    @Override
    public boolean existsProduct(int idProducto) {
        String sql = "SELECT id_producto FROM productos WHERE id_producto = ?";

        return searchCoincidences(idProducto, sql);
    }

    private boolean searchCoincidences(int id, String sql) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Error al comprobar si existe el empleado: " + ex.getMessage());
            return false;
        }
    }

    private void executeSelectOverProductsTable(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt(DBScheme.PRODUCT_ID) + " | " +
                                resultSet.getString(DBScheme.PRODUCT_NAME) + " | " +
                                resultSet.getString(DBScheme.PRODUCT_DESCRIPTION) + " | " +
                                resultSet.getInt(DBScheme.PRODUCT_STOCK) + " | " +
                                resultSet.getDouble(DBScheme.PRODUCT_PRICE)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    @Override
    public void showAllProducts() {
        String query = String.format("SELECT * FROM %s;", DBScheme.TAB_PRODUCTS_NAME);
        executeSelectOverProductsTable(query);

    }

    @Override
    public void showAllFavouriteProducts() {
        String query = "SELECT pf.id_producto_fav, p.* FROM productos_fav pf\n" +
                "LEFT JOIN productos p ON pf.id_producto = p.id_producto;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt(DBScheme.ID_PRODUCTS_FAV) + " | " +
                                resultSet.getInt(DBScheme.PRODUCT_ID) + " | " +
                                resultSet.getString(DBScheme.PRODUCT_NAME) + " | " +
                                resultSet.getString(DBScheme.PRODUCT_DESCRIPTION) + " | " +
                                resultSet.getInt(DBScheme.PRODUCT_STOCK) + " | " +
                                resultSet.getDouble(DBScheme.PRODUCT_PRICE)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

    }

    @Override
    public void showProductsUnder600() {
        String query = "SELECT * FROM productos WHERE precio < 600;";

        executeSelectOverProductsTable(query);

    }


    @Override
    public void insertProductsAbove1000() {
        String query = "INSERT INTO productos_fav (id_producto) SELECT productos.id_producto \n" +
                "FROM productos WHERE precio > 1000;";

        try {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(query);

            System.out.println(affectedRows + " productos insertados como favoritos");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

    }
}
