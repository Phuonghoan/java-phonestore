package phone_store.dao.impl;

import phone_store.dao.IProductDAO;
import phone_store.model.Product;
import phone_store.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductDAOImpl implements IProductDAO {

    @Override
    public boolean save(Product product) {
        String sql = """
                INSERT INTO product(name, brand, price, stock)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }
}