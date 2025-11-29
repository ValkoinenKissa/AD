package dao;
/*
Agregar todos los productos que están ubicados en el siguiente JSON dentro de la tabla productos:
https://dummyjson.com/products ✅
•	Agregar una serie empleados y pedidos mediante statement. Tener en cuenta que los pedidos tienen una fk sobre la tabla productos
•	Muestra por consola mediante la ejecución de querys – statement:
o	Todos los productos
o	Todos los productos favoritos con sus datos
•	Muestra por consola todos los productos de la base de datos que tengan un precio inferior a 600€
•	Inserta en la tabla productos_fav aquellos productos que tengan un valor superior a 1000€
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import database.DBConnection;
import database.DBScheme;
import model.ProductJSON;
import model.ProductResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

                if (affectedRowsCounter > 0){
                    System.out.println("Base de datos poblada con exito");
                    System.out.println("Filas afectadas: " + affectedRowsCounter);
                } else{
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
            System.out.println("Error al ejecutar la consulta");
        }

        return affectedRowsCounter;
    }

    @Override
    public void addEmployee() {

    }

    @Override
    public void addOrder() {

    }

    @Override
    public void showAllProducts() {

    }

    @Override
    public void showAllFavouriteProducts() {

    }

    @Override
    public void showProductsUnder600() {

    }

    @Override
    public void insertProductsAbove1000() {

    }
}
