package dao;

import model.Employee;
import model.Order;

public interface productDAO {
    void populateDatabase();

    boolean checkIfDbIsEmpty();

    void addEmployee(Employee e);

    void addOrder(Order o);

    boolean existsEmployee(int id);

    boolean existsProduct(int idProducto);

    void showAllProducts();

    void showAllFavouriteProducts();

    void showProductsUnder600();

    void insertProductsAbove1000();



}
