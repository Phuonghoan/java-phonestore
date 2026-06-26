package phone_store.presentation;

import phone_store.business.IProductService;
import phone_store.business.impl.ProductServiceImpl;
import phone_store.utils.InputUtil;
import phone_store.model.Product;

public class ProductView {
    private final IProductService productService = new ProductServiceImpl();

    public void showProductMenu() {
        do {
            System.out.println("========== GIAO DIỆN QUẢN LÝ ĐIỆN THOẠI ==========");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("0. Quay về menu chính");
            System.out.println("==================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    addProduct();
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
}