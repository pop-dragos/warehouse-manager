package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main Graphical User Interface (GUI) class for the Orders Management System.
 */
public class View extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTable clientTable = new JTable();
    private JTable productTable = new JTable();
    private JTable billTable = new JTable();

    private JButton btnAddClient = new JButton("Add Client");
    private JButton btnEditClient = new JButton("Edit Client");
    private JButton btnDeleteClient = new JButton("Delete Client");
    private JButton btnAddProduct = new JButton("Add Product");
    private JButton btnEditProduct = new JButton("Edit Product");
    private JButton btnDeleteProduct = new JButton("Delete Product");

    private JComboBox<String> clientCombo = new JComboBox<>();
    private JComboBox<String> productCombo = new JComboBox<>();
    private JTextField tfOrderQuantity = new JTextField(10);
    private JButton btnPlaceOrder = new JButton("Place Order");
    private JButton btnShowOrders = new JButton("Show All Orders");

    /**
     * Initializes the main window, sets up dimensions, and constructs the tabbed pane layout containing all sub-panels.
     */
    public View() {
        this.setTitle("Orders Management System");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane.addTab("Clients", createPanel(clientTable, btnAddClient, btnEditClient, btnDeleteClient));
        tabbedPane.addTab("Products", createPanel(productTable, btnAddProduct, btnEditProduct, btnDeleteProduct));
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Bills Log", new JScrollPane(billTable));

        this.add(tabbedPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Creates a standardized CRUD panel with a table at the center
     * and a set of action buttons at the bottom.
     * * @param table the JTable displaying the corresponding records
     * @param add the button triggering record creation
     * @param edit the button triggering record updates
     * @param del the button triggering record deletion
     * @return a fully configured JPanel ready to be appended as a tab
     */
    private JPanel createPanel(JTable table, JButton add, JButton edit, JButton del) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bp = new JPanel();
        bp.add(add); bp.add(edit); bp.add(del);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Builds the order placement panel.
     * * @return a structured JPanel containing the order placement forms
     */
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10); g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; panel.add(new JLabel("Client:"), g);
        g.gridx = 1; panel.add(clientCombo, g);
        g.gridx = 0; g.gridy = 1; panel.add(new JLabel("Product:"), g);
        g.gridx = 1; panel.add(productCombo, g);
        g.gridx = 0; g.gridy = 2; panel.add(new JLabel("Quantity:"), g);
        g.gridx = 1; panel.add(tfOrderQuantity, g);

        g.gridx = 1; g.gridy = 3; panel.add(btnPlaceOrder, g);
        g.gridy = 4; panel.add(btnShowOrders, g);

        return panel;
    }

    /**
     * Spawns a secondary internal window showing the history log of all submitted orders.
     * * @param model the table model containing historical orders data to display
     */
    public void showOrdersWindow(DefaultTableModel model) {
        JFrame historyFrame = new JFrame("Orders History");
        historyFrame.setSize(600, 400);
        JTable table = new JTable(model);
        historyFrame.add(new JScrollPane(table));
        historyFrame.setLocationRelativeTo(this);
        historyFrame.setVisible(true);
    }

    public void setClientTable(DefaultTableModel m) { clientTable.setModel(m); }
    public void setProductTable(DefaultTableModel m) { productTable.setModel(m); }
    public void setBillTable(DefaultTableModel m) { billTable.setModel(m); } //
    public void setClientCombo(String[] c) { clientCombo.setModel(new DefaultComboBoxModel<>(c)); }
    public void setProductCombo(String[] p) { productCombo.setModel(new DefaultComboBoxModel<>(p)); }

    public JTable getClientTable() { return clientTable; }
    public JTable getProductTable() { return productTable; }
    public int getSelectedClientIndex() { return clientCombo.getSelectedIndex(); }
    public int getSelectedProductIndex() { return productCombo.getSelectedIndex(); }
    public String getOrderQty() { return tfOrderQuantity.getText(); }

    /** @param a the listener handling client creation events */
    public void addClientL(ActionListener a) { btnAddClient.addActionListener(a); }

    /** @param a the listener handling client update events */
    public void editClientL(ActionListener a) { btnEditClient.addActionListener(a); }

    /** @param a the listener handling client deletion events */
    public void delClientL(ActionListener a) { btnDeleteClient.addActionListener(a); }

    /** @param a the listener handling product creation events */
    public void addProdL(ActionListener a) { btnAddProduct.addActionListener(a); }

    /** @param a the listener handling product update events */
    public void editProdL(ActionListener a) { btnEditProduct.addActionListener(a); }

    /** @param a the listener handling product deletion events */
    public void delProdL(ActionListener a) { btnDeleteProduct.addActionListener(a); }

    /** @param a the listener handling transaction creation events */
    public void addOrderL(ActionListener a) { btnPlaceOrder.addActionListener(a); }

    /** @param a the listener handling logs window triggers */
    public void showOrdersL(ActionListener a) { btnShowOrders.addActionListener(a); }

    /**
     * Application entry point. Initializes the interface and hooks up the Controller.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Controller(new View()));
    }
}
