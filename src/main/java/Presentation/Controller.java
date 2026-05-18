package Presentation;

import BusinessLogic.*;
import DataAccess.BillDAO;
import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Main Controller class in the layered architecture that orchestrates the application flow.
 */
public class Controller {
    private View view;
    private ClientBLL cBLL = new ClientBLL();
    private ProductBLL pBLL = new ProductBLL();
    private OrderBLL oBLL = new OrderBLL();
    private BillDAO billDAO = new BillDAO();

    private List<Client> currentClients;
    private List<Product> currentProducts;

    /**
     * Constructs the Controller and hooks it up with the core application view.
     * * @param view the main UI window frame container instance
     */
    public Controller(View view) {
        this.view = view;
        refresh();

        view.addClientL(e -> handleAddClient());
        view.editClientL(e -> handleEditClient());
        view.delClientL(e -> handleDeleteClient());

        view.addProdL(e -> handleAddProduct());
        view.editProdL(e -> handleEditProduct());
        view.delProdL(e -> handleDeleteProduct());

        view.addOrderL(e -> handleAddOrder());

        view.showOrdersL(e -> handleShowOrders());
    }

    /**
     * Spawns an input dialog form to capture data for a new Client record and submits it to the validation layer.
     */
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

    /**
     * Extracts the currently selected Client row from the UI, pre-populates an input
     * dialog with existing values, and pushes updates down to the database layers.
     */
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

    /**
     * Identifies the selected Client record and dispatches a deletion request.
     */
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

    /**
     * Spawns an input dialog form to capture stock data for a new Product record and forwards it to the database.
     */
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

    /**
     * Extracts the currently selected Product row from the UI, pre-populates an input
     * dialog with existing values, and pushes updates down to the database layers.
     */
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

    /**
     * Identifies the selected Product record and dispatches a deletion request.
     */
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

    /**
     * Extracts order details from the user interface and initiates a transaction placement.
     */
    private void handleAddOrder() {
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    /**
     * Fetches the entire order history and triggers the log view pop-up panel.
     */
    private void handleShowOrders() {
        List<Orders> allOrders = oBLL.findAllOrders();
        DefaultTableModel model = CreateTables.generateTable(allOrders);
        view.showOrdersWindow(model);
    }

    /**
     * Refreshes the user interface state by pulling fresh datasets from the database layers.
     */
    private void refresh() {
        currentClients = cBLL.findAllClients();
        currentProducts = pBLL.findAllProducts();
        List<Bill> currentBills = billDAO.findAll();

        view.setClientTable(CreateTables.generateTable(currentClients));
        view.setProductTable(CreateTables.generateTable(currentProducts));
        view.setBillTable(CreateTables.generateTable(currentBills));

        String[] clientComboItems = currentClients.stream()
                .map(client -> client.getId() + " - " + client.getName())
                .toArray(String[]::new);
        view.setClientCombo(clientComboItems);

        String[] productComboItems = currentProducts.stream()
                .filter(product -> product.getQuantity() > 0)
                .map(product -> product.getName() + " - " + product.getQuantity() + " pcs.")
                .toArray(String[]::new);
        view.setProductCombo(productComboItems);
    }
}