package phone_store.dao.impl;

import phone_store.dao.ICustomerDAO;
import phone_store.model.Customer;
import phone_store.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public boolean save(Customer customer) {
        String sql = """
                INSERT INTO customer(name, phone, email, password, role, address)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getRole());
            ps.setString(6, customer.getAddress());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
            return false;
        }
    }
}