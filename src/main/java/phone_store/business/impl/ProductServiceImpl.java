package phone_store.business.impl;

import phone_store.business.IProductService;
import phone_store.dao.IProductDAO;
import phone_store.dao.impl.ProductDAOImpl;
import phone_store.model.Product;

import java.util.List;

public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO = new ProductDAOImpl();

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public Product getProductById(int id) {
        if (id <= 0) {
            return null;
        }

        return productDAO.findById(id);
    }


    @Override
    public boolean addProduct(Product product) {
        if (!validateProduct(product)) {
            return false;
        }

        return productDAO.save(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        if (product.getId() <= 0) {
            System.out.println("Id sản phẩm không hợp lệ!");
            return false;
        }

        if (!validateProduct(product)) {
            return false;
        }

        return productDAO.update(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        if (id <= 0) {
            System.out.println("Id sản phẩm không hợp lệ!");
            return false;
        }

        return productDAO.deleteById(id);
    }

    @Override
    public List<Product> searchProductsByBrand(String keyword) {
        return productDAO.searchByBrand(keyword);
    }

    private boolean validateProduct(Product product) {
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

        return true;
    }

    @Override
    public List<Product> searchProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            System.out.println("Khoảng giá không được âm!");
            return List.of();
        }

        if (minPrice > maxPrice) {
            System.out.println("Giá bắt đầu không được lớn hơn giá kết thúc!");
            return List.of();
        }

        return productDAO.searchByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> searchProductsByNameAndStockAvailable(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            System.out.println("Từ khóa tìm kiếm không được để trống!");
            return List.of();
        }

        return productDAO.searchByNameAndStockAvailable(keyword);
    }
}