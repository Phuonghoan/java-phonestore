package phone_store.dao.impl;

import phone_store.dao.IProductDAO;
import phone_store.model.Product;
import phone_store.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO {

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT id, name, brand, price, stock FROM product ORDER BY id ASC";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Product product = mapResultSetToProduct(rs);
                products.add(product);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }

        return products;
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT id, name, brand, price, stock FROM product WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi tìm sản phẩm theo id: " + e.getMessage());
        }

        return null;
    }

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

    @Override
    public boolean update(Product product) {
        String sql = """
                UPDATE product
                SET name = ?, brand = ?, price = ?, stock = ?
                WHERE id = ?
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getBrand());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM product WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Product> searchByBrand(String keyword) {
        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT id, name, brand, price, stock
                FROM product
                WHERE brand ILIKE ?
                ORDER BY id ASC
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = mapResultSetToProduct(rs);
                    products.add(product);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm sản phẩm theo brand: " + e.getMessage());
        }

        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws Exception {
        Product product = new Product();

        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setBrand(rs.getString("brand"));
        product.setPrice(rs.getDouble("price"));
        product.setStock(rs.getInt("stock"));

        return product;
    }

}