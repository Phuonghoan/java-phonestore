package phone_store.business.impl;

import phone_store.business.IProductService;
import phone_store.dao.IProductDAO;
import phone_store.dao.impl.ProductDAOImpl;
import phone_store.model.Product;

public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO = new ProductDAOImpl();

    @Override
    public boolean addProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            System.out.println("Tên sản phẩm không được để trống!");
            return false;
        }

        if (product.getBrand() == null || product.getBrand().isBlank()) {
            System.out.println("Nhãn hàng không được để trống!");
            return false;
        }

        if (product.getPrice() <= 0) {
            System.out.println("Giá bán phải lớn hơn 0!");
            return false;
        }

        if (product.getStock() < 0) {
            System.out.println("Số lượng tồn kho không được âm!");
            return false;
        }

        return productDAO.save(product);
    }
}