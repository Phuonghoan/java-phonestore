package phone_store.dao.impl;

import phone_store.dao.ICustomerDAO;
import phone_store.model.Customer;
import phone_store.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        String sql = """
                SELECT id, name, phone, email, password, role, address
                FROM customer
                ORDER BY id ASC
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Customer customer = mapResultSetToCustomer(rs);
                customers.add(customer);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        }

        return customers;
    }

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

    @Override
    public Customer findById(int id) {
        String sql = """
                SELECT id, name, phone, email, password, role, address
                FROM customer
                WHERE id = ?
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi tìm khách hàng theo id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean update(Customer customer) {
        String sql = """
                UPDATE customer
                SET name = ?, phone = ?, email = ?, password = ?, role = ?, address = ?
                WHERE id = ?
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
            ps.setInt(7, customer.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật khách hàng: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi xóa khách hàng: " + e.getMessage());
            return false;
        }
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws Exception {
        Customer customer = new Customer();

        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setRole(rs.getString("role"));
        customer.setAddress(rs.getString("address"));

        return customer;
    }
}