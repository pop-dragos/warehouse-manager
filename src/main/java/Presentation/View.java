    package Presentation;

    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.ActionListener;

    public class View extends JFrame {
        private JTabbedPane tabbedPane = new JTabbedPane();

        // Tabele principale
        private JTable clientTable = new JTable();
        private JTable productTable = new JTable();

        // Butoane
        private JButton btnAddClient = new JButton("Add Client");
        private JButton btnEditClient = new JButton("Edit Client");
        private JButton btnDeleteClient = new JButton("Delete Client");
        private JButton btnAddProduct = new JButton("Add Product");
        private JButton btnEditProduct = new JButton("Edit Product");
        private JButton btnDeleteProduct = new JButton("Delete Product");

        // Componente Comenzi
        private JComboBox<String> clientCombo = new JComboBox<>();
        private JComboBox<String> productCombo = new JComboBox<>();
        private JTextField tfOrderQuantity = new JTextField(10);
        private JButton btnPlaceOrder = new JButton("Place Order");
        private JButton btnShowOrders = new JButton("Show All Orders"); // Butonul nou

        public View() {
            this.setTitle("Orders Management System");
            this.setSize(900, 600);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            tabbedPane.addTab("Clients", createPanel(clientTable, btnAddClient, btnEditClient, btnDeleteClient));
            tabbedPane.addTab("Products", createPanel(productTable, btnAddProduct, btnEditProduct, btnDeleteProduct));
            tabbedPane.addTab("Orders", createOrderPanel());

            this.add(tabbedPane);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }

        private JPanel createPanel(JTable table, JButton add, JButton edit, JButton del) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
            JPanel bp = new JPanel();
            bp.add(add); bp.add(edit); bp.add(del);
            panel.add(bp, BorderLayout.SOUTH);
            return panel;
        }

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
            g.gridy = 4; panel.add(btnShowOrders, g); // Plasăm butonul sub "Place Order"

            return panel;
        }

        // Metodă pentru a deschide fereastra de istoric
        public void showOrdersWindow(DefaultTableModel model) {
            JFrame historyFrame = new JFrame("Orders History");
            historyFrame.setSize(600, 400);
            JTable table = new JTable(model);
            historyFrame.add(new JScrollPane(table));
            historyFrame.setLocationRelativeTo(this);
            historyFrame.setVisible(true);
        }

        // Getters & Setters
        public void setClientTable(DefaultTableModel m) { clientTable.setModel(m); }
        public void setProductTable(DefaultTableModel m) { productTable.setModel(m); }
        public void setClientCombo(String[] c) { clientCombo.setModel(new DefaultComboBoxModel<>(c)); }
        public void setProductCombo(String[] p) { productCombo.setModel(new DefaultComboBoxModel<>(p)); }

        public JTable getClientTable() { return clientTable; }
        public JTable getProductTable() { return productTable; }
        public int getSelectedClientIndex() { return clientCombo.getSelectedIndex(); }
        public int getSelectedProductIndex() { return productCombo.getSelectedIndex(); }
        public String getOrderQty() { return tfOrderQuantity.getText(); }

        // Listeners
        public void addClientL(ActionListener a) { btnAddClient.addActionListener(a); }
        public void editClientL(ActionListener a) { btnEditClient.addActionListener(a); }
        public void delClientL(ActionListener a) { btnDeleteClient.addActionListener(a); }
        public void addProdL(ActionListener a) { btnAddProduct.addActionListener(a); }
        public void editProdL(ActionListener a) { btnEditProduct.addActionListener(a); }
        public void delProdL(ActionListener a) { btnDeleteProduct.addActionListener(a); }
        public void addOrderL(ActionListener a) { btnPlaceOrder.addActionListener(a); }
        public void showOrdersL(ActionListener a) { btnShowOrders.addActionListener(a); }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new Controller(new View()));
        }
    }