package tools.challenge.core.validator;

import tools.challenge.core.error.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Validator {

    private final List<Runnable> rules;

    private final Function<String, Error> errorFunction;

    public Validator(Function<String, Error> errorFunction) {
        this.rules = new ArrayList<>();
        this.errorFunction = errorFunction;
    }

    public static Validator create(Function<String, Error> errorFunction) {
        return new Validator(errorFunction);
    }

    public Validator rule(BooleanSupplier condition, String message) {
        rules.add(() -> {
            if (condition.getAsBoolean())
                throw errorFunction.apply(message);
        });
        return this;
    }

    public void validate() {
        for (Runnable rule : rules) rule.run();
    }

    @FunctionalInterface
    public interface BooleanSupplier {
        boolean getAsBoolean();
    }
}
