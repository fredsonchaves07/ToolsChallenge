package tools.challenge.core.valueobject;

public interface ValueObject {

    default ValueObject validate() {
        return this;
    }
}
