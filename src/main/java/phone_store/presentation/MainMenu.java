package phone_store.presentation;

import phone_store.utils.InputUtil;

public class MainMenu {
    private final ProductView productView = new ProductView();

    public void showMenu() {
        do {
            System.out.println("========== GIAO DIỆN BẮT ĐẦU CHƯƠNG TRÌNH ==========");
            System.out.println("1. Quản lý điện thoại");
            System.out.println("0. Thoát chương trình");
            System.out.println("====================================================");

            int choice = InputUtil.inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    productView.showProductMenu();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }

        } while (true);
    }
}