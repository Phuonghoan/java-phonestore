package phone_store.business.impl;

import phone_store.business.ICustomerService;
import phone_store.dao.ICustomerDAO;
import phone_store.dao.impl.CustomerDAOImpl;
import phone_store.model.Customer;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean addCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().isBlank()) {
            System.out.println("Tên khách hàng không được để trống!");
            return false;
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            System.out.println("Email không được để trống!");
            return false;
        }

        if (!customer.getEmail().contains("@") || !customer.getEmail().contains(".")) {
            System.out.println("Email không đúng định dạng!");
            return false;
        }

        if (customer.getPassword() == null || customer.getPassword().isBlank()) {
            System.out.println("Mật khẩu không được để trống!");
            return false;
        }

        if (customer.getRole() == null || customer.getRole().isBlank()) {
            customer.setRole("CUSTOMER");
        }

        return customerDAO.save(customer);
    }
}