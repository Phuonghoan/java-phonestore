package phone_store.presentation;

import phone_store.business.ICustomerService;
import phone_store.business.impl.CustomerServiceImpl;
import phone_store.model.Customer;
import phone_store.utils.InputUtil;

public class CustomerView {
    private final ICustomerService customerService = new CustomerServiceImpl();

    public void showCustomerMenu() {
        do {
            System.out.println();
            System.out.println("========== GIAO DIỆN QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Thêm mới khách hàng");
            System.out.println("0. Quay về menu chính");
            System.out.println("===================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }

        } while (true);
    }

    private void addCustomer() {
        System.out.println();
        System.out.println("========== THÊM MỚI KHÁCH HÀNG ==========");

        String name = InputUtil.inputString("Nhập họ tên khách hàng: ");
        String phone = InputUtil.inputString("Nhập số điện thoại: ");
        String email = InputUtil.inputString("Nhập email: ");
        String password = InputUtil.inputString("Nhập mật khẩu: ");
        String address = InputUtil.inputString("Nhập địa chỉ: ");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setRole("CUSTOMER");
        customer.setAddress(address);

        boolean result = customerService.addCustomer(customer);

        if (result) {
            System.out.println("Thêm khách hàng thành công!");
        } else {
            System.out.println("Thêm khách hàng thất bại!");
        }
    }
}
