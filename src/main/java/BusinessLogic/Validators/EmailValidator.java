package BusinessLogic.Validators;

import Model.Client;

/**
 * Validator used to verify the format of a client's email address.
 * It ensures the email string contains the "@" character using a regular expression.
 */
public class EmailValidator implements Validator<Client> {

    /**
     * Validates the email format of the specified client.
     * @param t The client object to be validated.
     * @throws IllegalArgumentException if the email does not match the required pattern.
     */
    @Override
    public void validate(Client t) {
        if(!t.getEmail().matches("^(.+)@(.+)$")) {
            throw new IllegalArgumentException("Invalid Email");
        }
    }
}