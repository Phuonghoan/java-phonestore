package phone_store.presentation;

import phone_store.business.IProductService;
import phone_store.business.impl.ProductServiceImpl;
import phone_store.utils.InputUtil;
import phone_store.model.Product;

import java.util.List;

public class ProductView {
    private final IProductService productService = new ProductServiceImpl();

    public void showProductMenu() {
        do {
            System.out.println("========== GIAO DIỆN QUẢN LÝ ĐIỆN THOẠI ==========");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Cập nhật sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Hiển thị danh sách sản phẩm");
            System.out.println("5. Tìm kiếm điện thoại theo brand");
            System.out.println("0. Quay về menu chính");
            System.out.println("==================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    displayAllProducts();
                    break;
                case 5:
                    searchProductByBrand();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }

        } while (true);
    }

    private void addProduct() {
        System.out.println("========== THÊM MỚI SẢN PHẨM ==========");

        String name = InputUtil.inputString("Nhập tên sản phẩm: ");
        String brand = InputUtil.inputString("Nhập nhãn hàng: ");
        double price = InputUtil.inputDouble("Nhập giá bán: ");
        int stock = InputUtil.inputInt("Nhập số lượng tồn kho: ");

        Product product = new Product(name, brand, price, stock);

        boolean result = productService.addProduct(product);

        if (result) {
            System.out.println("Thêm sản phẩm thành công!");
        } else {
            System.out.println("Thêm sản phẩm thất bại!");
        }
    }

    private void updateProduct() {
        System.out.println();
        System.out.println("========== CẬP NHẬT SẢN PHẨM ==========");

        Product oldProduct;

        while (true) {
            int id = InputUtil.inputInt("Nhập id sản phẩm cần cập nhật, nhập 0 để quay lại: ");

            if (id == 0) {
                return;
            }

            oldProduct = productService.getProductById(id);

            if (oldProduct == null) {
                System.out.println("Không tìm thấy sản phẩm có id = " + id + ". Vui lòng nhập lại!");
            } else {
                break;
            }
        }

        System.out.println("Thông tin sản phẩm hiện tại:");
        displayProductDetail(oldProduct);

        System.out.println("Nhập thông tin mới cho sản phẩm:");

        String newName = InputUtil.inputString("Nhập tên sản phẩm mới: ");
        String newBrand = InputUtil.inputString("Nhập nhãn hàng mới: ");
        double newPrice = InputUtil.inputDouble("Nhập giá bán mới: ");
        int newStock = InputUtil.inputInt("Nhập số lượng tồn kho mới: ");

        Product newProduct = new Product();
        newProduct.setId(oldProduct.getId());
        newProduct.setName(newName);
        newProduct.setBrand(newBrand);
        newProduct.setPrice(newPrice);
        newProduct.setStock(newStock);

        boolean result = productService.updateProduct(newProduct);

        if (result) {
            System.out.println("Cập nhật sản phẩm thành công!");
        } else {
            System.out.println("Cập nhật sản phẩm thất bại!");
        }
    }

    private void deleteProduct() {
        System.out.println();
        System.out.println("========== XÓA SẢN PHẨM ==========");

        Product product;

        while (true) {
            int id = InputUtil.inputInt("Nhập id sản phẩm muốn xóa, nhập 0 để quay lại: ");

            if (id == 0) {
                return;
            }

            product = productService.getProductById(id);

            if (product == null) {
                System.out.println("Id sản phẩm không tồn tại. Vui lòng nhập lại!");
            } else {
                break;
            }
        }

        System.out.println("Thông tin sản phẩm muốn xóa:");
        displayProductDetail(product);

        boolean confirm = InputUtil.inputYesNo("Bạn có chắc chắn muốn xóa sản phẩm này không? (Y/N): ");

        if (!confirm) {
            System.out.println("Đã hủy thao tác xóa.");
            return;
        }

        boolean result = productService.deleteProduct(product.getId());

        if (result) {
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("Xóa sản phẩm thất bại!");
        }
    }

    private void displayAllProducts() {
        System.out.println();
        System.out.println("========== DANH SÁCH SẢN PHẨM ==========");

        List<Product> products = productService.getAllProducts();

        displayProductList(products);
    }

    private void searchProductByBrand() {
        System.out.println();
        System.out.println("========== TÌM KIẾM ĐIỆN THOẠI THEO BRAND ==========");

        String keyword = InputUtil.inputString("Nhập từ khóa brand cần tìm: ");

        List<Product> products = productService.searchProductsByBrand(keyword);

        displayProductList(products);
    }

    private void displayProductList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("Không có sản phẩm nào!");
            return;
        }

        System.out.printf("%-5s | %-25s | %-15s | %-15s | %-10s%n",
                "ID", "Tên sản phẩm", "Brand", "Giá bán", "Tồn kho");
        System.out.println("----------------------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d | %-25s | %-15s | %-15.2f | %-10d%n",
                    product.getId(),
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getStock());
        }
    }

    private void displayProductDetail(Product product) {
        System.out.println("--------------------------------------------");
        System.out.println("ID: " + product.getId());
        System.out.println("Tên sản phẩm: " + product.getName());
        System.out.println("Brand: " + product.getBrand());
        System.out.println("Giá bán: " + product.getPrice());
        System.out.println("Tồn kho: " + product.getStock());
        System.out.println("--------------------------------------------");
    }
}