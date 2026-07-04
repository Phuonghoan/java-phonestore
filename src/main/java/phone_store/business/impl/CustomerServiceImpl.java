package phone_store.business.impl;

import phone_store.business.ICustomerService;
import phone_store.dao.ICustomerDAO;
import phone_store.dao.impl.CustomerDAOImpl;
import phone_store.model.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer getCustomerById(int id) {
        if (id <= 0) {
            return null;
        }

        return customerDAO.findById(id);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            return false;
        }

        return customerDAO.save(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if (customer.getId() <= 0) {
            System.out.println("Id khách hàng không hợp lệ!");
            return false;
        }

        if (!validateCustomer(customer)) {
            return false;
        }

        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteCustomer(int id) {
        if (id <= 0) {
            System.out.println("Id khách hàng không hợp lệ!");
            return false;
        }

        return customerDAO.deleteById(id);
    }

    private boolean validateCustomer(Customer customer) {
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

        return true;
    }
}