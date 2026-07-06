package phone_store.presentation;

import phone_store.business.IInvoiceService;
import phone_store.business.impl.InvoiceServiceImpl;
import phone_store.model.Invoice;
import phone_store.utils.InputUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class InvoiceView {
    private final IInvoiceService invoiceService = new InvoiceServiceImpl();

    public void showInvoiceMenu() {
        do {
            System.out.println();
            System.out.println("========== GIAO DIỆN QUẢN LÝ THÔNG TIN MUA BÁN ==========");
            System.out.println("1. Thêm mới đơn hàng");
            System.out.println("2. Hiển thị danh sách hóa đơn");
            System.out.println("0. Quay về menu chính");
            System.out.println("==========================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    addInvoice();
                    break;
                case 2:
                    displayAllInvoices();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }

        } while (true);
    }

    private void addInvoice() {
        System.out.println();
        System.out.println("========== THÊM MỚI ĐƠN HÀNG / HÓA ĐƠN ==========");

        int customerId = InputUtil.inputInt("Nhập mã khách hàng: ");
        double totalAmount = InputUtil.inputDouble("Nhập tổng tiền hóa đơn: ");

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setTotalAmount(totalAmount);

        boolean result = invoiceService.addInvoice(invoice);

        if (result) {
            System.out.println("Thêm hóa đơn thành công!");
        } else {
            System.out.println("Thêm hóa đơn thất bại!");
        }
    }

    private void displayAllInvoices() {
        System.out.println();
        System.out.println("========== DANH SÁCH HÓA ĐƠN ==========");

        List<Invoice> invoices = invoiceService.getAllInvoices();

        if (invoices == null || invoices.isEmpty()) {
            System.out.println("Không có hóa đơn nào!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.printf("%-5s | %-12s | %-25s | %-20s | %-15s%n",
                "ID", "Mã KH", "Tên khách hàng", "Ngày tạo", "Tổng tiền");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Invoice invoice : invoices) {
            System.out.printf("%-5d | %-12d | %-25s | %-20s | %-15.2f%n",
                    invoice.getId(),
                    invoice.getCustomerId(),
                    invoice.getCustomerName(),
                    invoice.getCreatedAt().format(formatter),
                    invoice.getTotalAmount());
        }
    }
}