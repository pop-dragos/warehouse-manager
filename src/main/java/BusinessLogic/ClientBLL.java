package BusinessLogic;

import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();

        validators = new ArrayList<>();
        validators.add(new EmailValidator());
    }

    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }

    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    public void insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.insert(client);
    }

    public void updateClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.update(client);
    }

    public void deleteClient(Client client) {
        clientDAO.delete(client);
    }
}
