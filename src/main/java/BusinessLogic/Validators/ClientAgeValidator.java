package BusinessLogic.Validators;

import Model.Client;

/**
 * Validator used to verify if a client's age falls within the desired range.
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;

    /**
     * Validates the age of the specified client.
     * @param t The client object to be validated.
     * @throws IllegalArgumentException if the client's age is outside the [18, 100] range.
     */
    @Override
    public void validate(Client t) {
        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Student Age limit is not respected!");
        }
    }
}