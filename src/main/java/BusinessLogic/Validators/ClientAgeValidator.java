package BusinessLogic.Validators;

import Model.Client;

/**
 * Validator used to verify if a client's age falls within the desired range.
 */
public class ClientAgeValidator implements Validator<Client> {

    /**
     * Validates the age of the specified client.
     * @param t The client object to be validated.
     * @throws IllegalArgumentException if the client's age is outside the [18, 100] range.
     */
    @Override
    public void validate(Client t) {
        if (t.getAge() < 18 || t.getAge() > 100) {
            throw new IllegalArgumentException("The Student Age limit is not respected!");
        }
    }
}