package phone_store.business.impl;

import phone_store.business.IInvoiceService;
import phone_store.dao.IInvoiceDAO;
import phone_store.dao.impl.InvoiceDAOImpl;
import phone_store.model.Invoice;

import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceDAO invoiceDAO = new InvoiceDAOImpl();

    @Override
    public boolean addInvoice(Invoice invoice) {
        if (invoice.getCustomerId() <= 0) {
            System.out.println("Mã khách hàng không hợp lệ!");
            return false;
        }

        if (invoice.getTotalAmount() <= 0) {
            System.out.println("Tổng tiền hóa đơn phải lớn hơn 0!");
            return false;
        }

        return invoiceDAO.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }
}
