package phone_store.business;

import phone_store.model.Invoice;

import java.util.List;

public interface IInvoiceService {
    boolean addInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();
}