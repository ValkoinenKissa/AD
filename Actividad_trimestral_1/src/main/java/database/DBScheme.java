package database;

public interface DBScheme {
    //tabla de productos
    String TAB_PRODUCTS_NAME = "productos";
    String PRODUCT_ID = "id_producto";
    String PRODUCT_NAME = "nombre_producto";
    String PRODUCT_DESCRIPTION = "descripcion_producto";
    String PRODUCT_STOCK = "cantidad";
    String PRODUCT_PRICE = "precio";

    //Join prov_fav productos

    String ID_PRODUCTS_FAV = "id_producto_fav";


}
