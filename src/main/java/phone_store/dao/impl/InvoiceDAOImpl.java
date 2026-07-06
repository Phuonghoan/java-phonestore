package phone_store.dao.impl;

import phone_store.dao.IInvoiceDAO;
import phone_store.model.Invoice;
import phone_store.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements IInvoiceDAO {

    @Override
    public boolean save(Invoice invoice) {
        String sql = """
                INSERT INTO invoice(customer_id, total_amount)
                VALUES (?, ?)
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, invoice.getCustomerId());
            ps.setDouble(2, invoice.getTotalAmount());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();

        String sql = """
                SELECT 
                    i.id,
                    i.customer_id,
                    c.name AS customer_name,
                    i.created_at,
                    i.total_amount
                FROM invoice i
                JOIN customer c ON i.customer_id = c.id
                ORDER BY i.id ASC
                """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Invoice invoice = new Invoice();

                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCustomerName(rs.getString("customer_name"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));

                invoices.add(invoice);
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }

        return invoices;
    }
}