package controller;

import dao.ProductDAOImp;
public class Controller {
    ProductDAOImp pdi = new ProductDAOImp();

    public void loadProductsIntoDB() {
        if (!pdi.checkIfDbIsEmpty()) {
            pdi.populateDatabase();
        } else {
            System.out.println("La base de datos ya ha sido poblada, borra los registros de la tabla productos para " +
                    "ejecutar esta opcion");
        }
    }

    public void createEmployee() {
    }

    public void createOrder() {

    }
}
