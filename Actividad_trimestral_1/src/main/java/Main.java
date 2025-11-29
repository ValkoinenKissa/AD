import controller.Controller;
import dao.ProductDAOImp;
import utils.ScannerUtil;
import java.util.Scanner;

/*
Se pide la realización de una aplicación que:
•	Crea una base de datos llamada almacén con las siguientes tablas y campos ✅
o	Productos: id (pk), nombre, descripción, cantidad, precio. ✅
o	Productos_Fav: id (pk), id_producto (fk) ✅
•	Agregar todos los productos que están ubicados en el siguiente JSON dentro de la tabla productos: https://dummyjson.com/products ✅
•	Agregar una serie empleados y pedidos mediante statement. Tener en cuenta que los pedidos tienen una fk sobre la tabla productos ✅
•	Muestra por consola mediante la ejecución de querys – statement: ✅
o	Todos los productos ✅
o	Todos los productos favoritos con sus datos ✅
•	Muestra por consola todos los productos de la base de datos que tengan un precio inferior a 600€ ✅
•	Inserta en la tabla productos_fav aquellos productos que tengan un valor superior a 1000€ ✅

 */
public class Main {
    private static final Scanner scanner = ScannerUtil.getScanner();

    public static void main(String[] args) {
        Controller c = new Controller();
        ProductDAOImp pdi = new ProductDAOImp();

        boolean stop = false;

        do {

            System.out.println("====================================");
            System.out.println("              MENÚ                 ");
            System.out.println("====================================");
            System.out.println("0. Poblar la base de datos");
            System.out.println("1. Añadir empleado");
            System.out.println("2. Añadir pedido");
            System.out.println("3. Mostrar todos los productos");
            System.out.println("4. Mostrar productos favoritos");
            System.out.println("5. Mostrar productos con precio < 600");
            System.out.println("6. Insertar productos con precio > 1000");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); //Consumir carcater \n

            switch (option){
                case 0 -> c.loadProductsIntoDB();
                case 1 -> c.createEmployee();
                case 2 -> c.createOrder();
                case 3 -> pdi.showAllProducts();
                case 4 -> pdi.showAllFavouriteProducts();
                case 5 -> pdi.showProductsUnder600();
                case 6 -> pdi.insertProductsAbove1000();
                case 7 -> stop = true;
                default -> System.out.println("Error: Introduce solo números del 0 al 7");
            }

        }while (!stop);

        System.out.println("¡Hasta pronto!");
        ScannerUtil.getScanner().close();

    }
}
