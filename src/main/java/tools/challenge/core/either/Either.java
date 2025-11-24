package tools.challenge.core.either;

import tools.challenge.core.error.Error;
import tools.challenge.core.error.InternalError;
import tools.challenge.core.usecases.ValueOutput;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;


public abstract class Either<E extends Error, S extends ValueOutput> {

    private Either() {
    }

    public abstract boolean isError();

    public abstract boolean isSuccess();

    public abstract E getError();

    public abstract S getSuccess();

    public static <S extends ValueOutput> Either<Error, S> attempt(Supplier<S> supplier) {
        return attempt(supplier, throwable ->
                throwable instanceof Error
                        ? (Error) throwable
                        : InternalError.error(throwable));
    }

    private static <E extends Error, S extends ValueOutput> Either<E, S> attempt(
            Supplier<S> supplier, Function<Throwable, E> errorMapper
    ) {
        try {
            return success(supplier.get());
        } catch (Throwable throwable) {
            return error(errorMapper.apply(throwable));
        }
    }

    private static <E extends Error, S extends ValueOutput> Either<E, S> error(final E value) {
        return new EitherError<>(value);
    }

    private static <E extends Error, S extends ValueOutput> Either<E, S> success(final S value) {
        return new Success<>(value);
    }

    private static class EitherError<E extends Error, S extends ValueOutput> extends Either<E, S> {

        private final E value;

        private EitherError(final E value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public E getError() {
            return value;
        }

        @Override
        public S getSuccess() {
            return null;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private static class Success<E extends Error, S extends ValueOutput> extends Either<E, S> {

        private final S value;

        private Success(final S value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public S getSuccess() {
            return value;
        }

        @Override
        public E getError() {
            return null;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
