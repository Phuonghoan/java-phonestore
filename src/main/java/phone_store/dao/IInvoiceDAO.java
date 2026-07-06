package phone_store.dao;

import phone_store.model.Invoice;

import java.util.List;

public interface IInvoiceDAO {
    boolean save(Invoice invoice);

    List<Invoice> findAll();
}
