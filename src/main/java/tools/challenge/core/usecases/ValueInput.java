package tools.challenge.core.usecases;

public interface ValueInput {

    default ValueInput validate() {
        return this;
    }
}
