package phone_store.dao;

import phone_store.model.Customer;
import java.util.List;

public interface ICustomerDAO {
    List<Customer> findAll();
    Customer findById(int id);
    boolean update(Customer customer);
    boolean save(Customer customer);
    boolean deleteById(int id);
}
