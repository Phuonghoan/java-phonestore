package phone_store.dao;

import phone_store.model.Product;
import java.util.List;


public interface IProductDAO {
    List<Product> findAll();
    Product findById(int id);
    boolean save(Product product);
    boolean update(Product product);
    boolean deleteById(int id);
    List<Product> searchByBrand(String keyword);
    List<Product> searchByPriceRange(double minPrice, double maxPrice);
    List<Product> searchByNameAndStockAvailable(String keyword);
}
