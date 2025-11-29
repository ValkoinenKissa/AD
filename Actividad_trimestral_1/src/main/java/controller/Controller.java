package controller;

import dao.ProductDAOImp;
import model.Employee;
import model.Order;
import utils.ScannerUtil;

import java.time.LocalDate;
import java.util.Scanner;

public class Controller {
    private final Scanner scanner;
    ProductDAOImp pdi = new ProductDAOImp();

    public Controller() {
        scanner = ScannerUtil.getScanner();
    }


    public void loadProductsIntoDB() {
        if (pdi.checkIfDbIsEmpty()) {
            pdi.populateDatabase();
        } else {
            System.out.println("La base de datos ya ha sido poblada, borra los registros de la tabla productos para " +
                    "ejecutar esta opcion");
        }
    }

    public void createEmployee() {

        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Puesto: ");
        String puesto = scanner.nextLine();

        Employee e = new Employee(nombre, puesto);

        pdi.addEmployee(e);

        System.out.println("Empleado añadido correctamente.");
    }

    public void createOrder() {
        System.out.println("ID del producto: ");
        int idProducto = scanner.nextInt();

        if (!pdi.existsProduct(idProducto)) {
            System.out.println("El producto no existe en la BD.");
            return;
        }

        System.out.println("ID del empleado: ");
        int idEmpleado = scanner.nextInt();

        if (!pdi.existsEmployee(idEmpleado)) {
            System.out.println("El empleado no existe.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        //Obtener la fecha actual con la función localDate.now
        Order o = new Order(idProducto, idEmpleado, cantidad, LocalDate.now().toString());

        pdi.addOrder(o);

        System.out.println("Pedido añadido correctamente.");

    }
}
