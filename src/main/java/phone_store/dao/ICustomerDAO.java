package phone_store.dao;

import phone_store.model.Customer;

public interface ICustomerDAO {
    boolean save(Customer customer);
}
