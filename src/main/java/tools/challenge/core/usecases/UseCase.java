package tools.challenge.core.usecases;


import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;

public interface UseCase<IN extends ValueInput, OUT extends ValueOutput> {

    Either<Error, OUT> execute(final IN input);
}