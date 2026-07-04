package phone_store.presentation;

import phone_store.business.ICustomerService;
import phone_store.business.impl.CustomerServiceImpl;
import phone_store.model.Customer;
import phone_store.utils.InputUtil;

import java.util.List;

public class CustomerView {
    private final ICustomerService customerService = new CustomerServiceImpl();

    public void showCustomerMenu() {
        do {
            System.out.println();
            System.out.println("========== GIAO DIỆN QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Thêm mới khách hàng");
            System.out.println("2. Cập nhật khách hàng");
            System.out.println("3. Xóa khách hàng");
            System.out.println("4. Hiển thị danh sách khách hàng");
            System.out.println("0. Quay về menu chính");
            System.out.println("===================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    deleteCustomer();
                    break;
                case 4:
                    displayAllCustomers();
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

    private void updateCustomer() {
        System.out.println();
        System.out.println("========== CẬP NHẬT KHÁCH HÀNG ==========");

        Customer oldCustomer;

        while (true) {
            int id = InputUtil.inputInt("Nhập id khách hàng cần cập nhật, nhập 0 để quay lại: ");

            if (id == 0) {
                return;
            }

            oldCustomer = customerService.getCustomerById(id);

            if (oldCustomer == null) {
                System.out.println("Không tìm thấy khách hàng có id = " + id + ". Vui lòng nhập lại!");
            } else {
                break;
            }
        }

        System.out.println("Thông tin khách hàng hiện tại:");
        displayCustomerDetail(oldCustomer);

        System.out.println("Nhập thông tin mới cho khách hàng:");

        String newName = InputUtil.inputString("Nhập họ tên mới: ");
        String newPhone = InputUtil.inputString("Nhập số điện thoại mới: ");
        String newEmail = InputUtil.inputString("Nhập email mới: ");
        String newPassword = InputUtil.inputString("Nhập mật khẩu mới: ");
        String newAddress = InputUtil.inputString("Nhập địa chỉ mới: ");

        Customer newCustomer = new Customer();
        newCustomer.setId(oldCustomer.getId());
        newCustomer.setName(newName);
        newCustomer.setPhone(newPhone);
        newCustomer.setEmail(newEmail);
        newCustomer.setPassword(newPassword);
        newCustomer.setRole(oldCustomer.getRole());
        newCustomer.setAddress(newAddress);

        boolean result = customerService.updateCustomer(newCustomer);

        if (result) {
            System.out.println("Cập nhật khách hàng thành công!");
        } else {
            System.out.println("Cập nhật khách hàng thất bại!");
        }
    }

    private void deleteCustomer() {
        System.out.println();
        System.out.println("========== XÓA KHÁCH HÀNG ==========");

        Customer customer;

        while (true) {
            int id = InputUtil.inputInt("Nhập id khách hàng muốn xóa, nhập 0 để quay lại: ");

            if (id == 0) {
                return;
            }

            customer = customerService.getCustomerById(id);

            if (customer == null) {
                System.out.println("Id khách hàng không tồn tại. Vui lòng nhập lại!");
            } else {
                break;
            }
        }

        System.out.println("Thông tin khách hàng muốn xóa:");
        displayCustomerDetail(customer);

        boolean confirm = InputUtil.inputYesNo("Bạn có chắc chắn muốn xóa khách hàng này không? (Y/N): ");

        if (!confirm) {
            System.out.println("Đã hủy thao tác xóa.");
            return;
        }

        boolean result = customerService.deleteCustomer(customer.getId());

        if (result) {
            System.out.println("Xóa khách hàng thành công!");
        } else {
            System.out.println("Xóa khách hàng thất bại!");
        }
    }

    private void displayAllCustomers() {
        System.out.println();
        System.out.println("========== DANH SÁCH KHÁCH HÀNG ==========");

        List<Customer> customers = customerService.getAllCustomers();

        displayCustomerList(customers);
    }

    private void displayCustomerList(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("Không có khách hàng nào!");
            return;
        }

        System.out.printf("%-5s | %-25s | %-15s | %-25s | %-12s | %-30s%n",
                "ID", "Họ tên", "SĐT", "Email", "Role", "Địa chỉ");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (Customer customer : customers) {
            System.out.printf("%-5d | %-25s | %-15s | %-25s | %-12s | %-30s%n",
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getRole(),
                    customer.getAddress());
        }
    }

    private void displayCustomerDetail(Customer customer) {
        System.out.println("--------------------------------------------");
        System.out.println("ID: " + customer.getId());
        System.out.println("Họ tên: " + customer.getName());
        System.out.println("Số điện thoại: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Role: " + customer.getRole());
        System.out.println("Địa chỉ: " + customer.getAddress());
        System.out.println("--------------------------------------------");
    }
}
