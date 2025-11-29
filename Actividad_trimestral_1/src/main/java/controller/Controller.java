package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ProductJSON;
import model.ProductResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {


    public void jsonFetch() {
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
