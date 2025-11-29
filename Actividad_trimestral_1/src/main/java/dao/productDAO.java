package dao;

public interface productDAO {
    void populateDatabase();

    boolean checkIfDbIsEmpty();

    void addEmployee();

    void addOrder();

    void showAllProducts();

    void showAllFavouriteProducts();

    void showProductsUnder600();

    void insertProductsAbove1000();

}
