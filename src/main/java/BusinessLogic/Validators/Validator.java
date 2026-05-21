package BusinessLogic.Validators;

/**
 * Generic interface for validating objects of type T.
 * @param <T> The type of the object to be validated.
 */
public interface Validator<T> {

    /**
     * Validates the provided object.
     * @param t The object instance to be checked.
     * @throws IllegalArgumentException or a custom RuntimeException if validation fails.
     */
    public void validate(T t);
}