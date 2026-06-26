package phone_store.dao;

import phone_store.model.Product;

public interface IProductDAO {
    boolean save(Product product);
}
