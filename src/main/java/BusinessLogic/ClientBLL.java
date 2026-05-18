package BusinessLogic;

import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Class for handling client-related operations.
 */
public class ClientBLL {
    private ClientDAO clientDAO;
    private List<Validator<Client>> validators;

    /**
     * Initializes the ClientBLL with necessary validators and the corresponding DAO.
     */
    public ClientBLL() {
        clientDAO = new ClientDAO();
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
    }

    /**
     * Retrieves a client by their unique identifier.
     * @param id The ID of the searched client.
     * @return The found {@link Client} object.
     * @throws NoSuchElementException if no client is found with the given ID.
     */
    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id= " + id + " was not found!");
        }
        return c;
    }

    /**
     * Fetches all client records from the database.
     * @return A list of all {@link Client} objects.
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Validates and inserts a new client into the system.
     * @param client The client object to be inserted.
     */
    public void insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.insert(client);
    }

    /**
     * Validates and updates an existing client record.
     * @param client The client object containing updated information.
     */
    public void updateClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.update(client);
    }

    /**
     * Removes a client record from the system.
     * @param client The client object to be deleted.
     */
    public void deleteClient(Client client) {
        clientDAO.delete(client);
    }
}