package tools.challenge.core.usecases;

public class EmptyValueOutput implements ValueOutput {

    private final static String VALUE = "";

    private EmptyValueOutput() {
    }

    public static EmptyValueOutput create() {
        return new EmptyValueOutput();
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
