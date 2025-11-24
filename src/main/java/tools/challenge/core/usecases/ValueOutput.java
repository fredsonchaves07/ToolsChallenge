package tools.challenge.core.usecases;

public interface ValueOutput {

    default ValueOutput validate() {
        return this;
    }
}
