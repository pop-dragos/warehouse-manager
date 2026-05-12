package BusinessLogic;

import BusinessLogic.Validators.OrderQuantityValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.BillDAO;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Order;
import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private BillDAO billDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        billDAO = new BillDAO();

        validators = new ArrayList<>();
        validators.add(new OrderQuantityValidator());
    }

    public Order findOrderById(int id) {
        Order o = orderDAO.findById(id);
        if (o == null) {
            throw new NoSuchElementException("Order with id=" + id + " was not found!");
        }
        return o;
    }

    public List<Order> findAllOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            return new ArrayList<>();
        }
        return orders;
    }

    public void insertOrder(Order order) throws Exception {
        for (Validator<Order> v : validators) {
            v.validate(order);
        }

        Product product = productDAO.findById(order.getId());
        if (product == null) {
            throw new NoSuchElementException("Product with id= " + order.getId() + " doesn't exist!");
        }

        if (product.getQuantity() < order.getQuantity()) {
            throw new Exception("Understock! Available stock: " + product.getQuantity() +
                    ". You requested: " + order.getQuantity());
        }

        int newQuantity = product.getQuantity() - order.getQuantity();
        product.setQuantity(newQuantity);
        productDAO.update(product);

        orderDAO.insert(order);
    }
}
