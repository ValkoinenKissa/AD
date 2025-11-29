/*
Se pide la realización de una aplicación que:
•	Crea una base de datos llamada almacén con las siguientes tablas y campos ✅
o	Productos: id (pk), nombre, descripción, cantidad, precio.
o	Productos_Fav: id (pk), id_producto (fk)
•	Agregar todos los productos que están ubicados en el siguiente JSON dentro de la tabla productos: https://dummyjson.com/products
•	Agregar una serie empleados y pedidos mediante statement. Tener en cuenta que los pedidos tienen una fk sobre la tabla productos
•	Muestra por consola mediante la ejecución de querys – statement:
o	Todos los productos
o	Todos los productos favoritos con sus datos
•	Muestra por consola todos los productos de la base de datos que tengan un precio inferior a 600€
•	Inserta en la tabla productos_fav aquellos productos que tengan un valor superior a 1000€

 */

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ProductJSON;
import model.ProductResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URI uri = new URI("https://dummyjson.com/products");
            URL url = uri.toURL();
            try (InputStream is = url.openStream()) {
                ProductResponse response = mapper.readValue(is, ProductResponse.class);
                for (ProductJSON item : response.getProducts()) {
                    System.out.println(item);
                }
            }

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("Error al parsear la URL");

        } catch (IOException e) {
            System.out.println("Error al consultar la URL");
        }

    }
}
