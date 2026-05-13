package Presentation;

import BusinessLogic.*;
import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Controller {
    private View view;
    private ClientBLL cBLL = new ClientBLL();
    private ProductBLL pBLL = new ProductBLL();
    private OrderBLL oBLL = new OrderBLL();

    private List<Client> currentClients;
    private List<Product> currentProducts;

    public Controller(View view) {
        this.view = view;
        refresh();

        // --- CLIENT LISTENERS ---
        view.addClientL(e -> handleAddClient());
        view.editClientL(e -> handleEditClient()); // Lipsa apel
        view.delClientL(e -> handleDeleteClient()); // Lipsa apel

        // --- PRODUCT LISTENERS ---
        view.addProdL(e -> handleAddProduct());
        view.editProdL(e -> handleEditProduct()); // Lipsa apel
        view.delProdL(e -> handleDeleteProduct()); // Lipsa apel

        // --- ORDER LISTENERS ---
        view.addOrderL(e -> {
            try {
                int clientIdx = view.getSelectedClientIndex();
                int productIdx = view.getSelectedProductIndex();
                int qty = Integer.parseInt(view.getOrderQty());

                if (clientIdx != -1 && productIdx != -1) {
                    Client sc = currentClients.get(clientIdx);
                    Product sp = currentProducts.get(productIdx);

                    oBLL.insertOrder(new Orders(sc.getId(), sp.getId(), qty));
                    refresh();
                    JOptionPane.showMessageDialog(null, "Order placed!");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage()); }
        });

        view.showOrdersL(e -> {
            List<Orders> allOrders = oBLL.findAllOrders();
            DefaultTableModel model = TableGenerator.generateTable(allOrders);
            view.showOrdersWindow(model);
        });
    }

    // --- LOGICĂ CLIENȚI ---

    private void handleAddClient() {
        JTextField name = new JTextField(); JTextField addr = new JTextField();
        JTextField mail = new JTextField(); JTextField age = new JTextField();
        Object[] msg = {"Name:", name, "Address:", addr, "Email:", mail, "Age:", age};
        if (JOptionPane.showConfirmDialog(null, msg, "Add Client", JOptionPane.OK_CANCEL_OPTION) == 0) {
            try {
                cBLL.insertClient(new Client(name.getText(), addr.getText(), mail.getText(), Integer.parseInt(age.getText())));
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
        }
    }

    private void handleEditClient() {
        int row = view.getClientTable().getSelectedRow();
        if (row != -1) {
            int id = (int) view.getClientTable().getValueAt(row, 0);
            JTextField name = new JTextField(view.getClientTable().getValueAt(row, 1).toString());
            JTextField addr = new JTextField(view.getClientTable().getValueAt(row, 2).toString());
            JTextField mail = new JTextField(view.getClientTable().getValueAt(row, 3).toString());
            JTextField age = new JTextField(view.getClientTable().getValueAt(row, 4).toString());
            Object[] msg = {"Name:", name, "Address:", addr, "Email:", mail, "Age:", age};

            if (JOptionPane.showConfirmDialog(null, msg, "Edit Client", JOptionPane.OK_CANCEL_OPTION) == 0) {
                try {
                    Client c = new Client(name.getText(), addr.getText(), mail.getText(), Integer.parseInt(age.getText()));
                    c.setId(id);
                    cBLL.updateClient(c);
                    refresh();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
            }
        } else { JOptionPane.showMessageDialog(null, "Select a client to edit!"); }
    }

    private void handleDeleteClient() {
        int row = view.getClientTable().getSelectedRow();
        if (row != -1) {
            int id = (int) view.getClientTable().getValueAt(row, 0);
            Client c = new Client();
            c.setId(id);
            try {
                cBLL.deleteClient(c);
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Cannot delete: " + ex.getMessage()); }
        } else { JOptionPane.showMessageDialog(null, "Select a client to delete!"); }
    }

    // --- LOGICĂ PRODUSE ---

    private void handleAddProduct() {
        JTextField name = new JTextField(); JTextField qty = new JTextField();
        Object[] msg = {"Name:", name, "Quantity:", qty};
        if (JOptionPane.showConfirmDialog(null, msg, "Add Product", JOptionPane.OK_CANCEL_OPTION) == 0) {
            try {
                pBLL.insertProduct(new Product(name.getText(), Integer.parseInt(qty.getText())));
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
        }
    }

    private void handleEditProduct() {
        int row = view.getProductTable().getSelectedRow();
        if (row != -1) {
            int id = (int) view.getProductTable().getValueAt(row, 0);
            JTextField name = new JTextField(view.getProductTable().getValueAt(row, 1).toString());
            JTextField qty = new JTextField(view.getProductTable().getValueAt(row, 2).toString());
            Object[] msg = {"Name:", name, "Quantity:", qty};

            if (JOptionPane.showConfirmDialog(null, msg, "Edit Product", JOptionPane.OK_CANCEL_OPTION) == 0) {
                try {
                    Product p = new Product(name.getText(), Integer.parseInt(qty.getText()));
                    p.setId(id);
                    pBLL.updateProduct(p);
                    refresh();
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
            }
        } else { JOptionPane.showMessageDialog(null, "Select a product to edit!"); }
    }

    private void handleDeleteProduct() {
        int row = view.getProductTable().getSelectedRow();
        if (row != -1) {
            int id = (int) view.getProductTable().getValueAt(row, 0);
            Product p = new Product();
            p.setId(id);
            try {
                pBLL.deleteProduct(p);
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Cannot delete: " + ex.getMessage()); }
        } else { JOptionPane.showMessageDialog(null, "Select a product to delete!"); }
    }

    private void refresh() {
        currentClients = cBLL.findAllClients();
        currentProducts = pBLL.findAllProducts();
        view.setClientTable(TableGenerator.generateTable(currentClients));
        view.setProductTable(TableGenerator.generateTable(currentProducts));
        view.setClientCombo(currentClients.stream().map(Client::getName).toArray(String[]::new));
        view.setProductCombo(currentProducts.stream().map(Product::getName).toArray(String[]::new));
    }
}