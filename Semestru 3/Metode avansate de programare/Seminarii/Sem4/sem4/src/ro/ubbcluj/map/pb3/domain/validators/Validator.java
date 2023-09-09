package ro.ubbcluj.map.pb3.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}