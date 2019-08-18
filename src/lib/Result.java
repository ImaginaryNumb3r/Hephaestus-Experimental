package lib;

import essentials.functional.exception.ConsumerEx;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author Patrick Plieschnegger
 *
 * A result is used to return data from a method and signal one of three exist states. The result can then be interpreted differently, depending on the result type.
 * The possible result types are:
 *  - OK: The method executed as expected and all imposed constraints were satisfied.
 *  - FAILURE: The method failed in an expected way.
 *  - FAIL: The method failed in an unexpected way or a serious constraint was violated.
 *
 * @apiNote It is up to use-site of this class to define the meaning of each type when each type of result is returned.
 * @implNote The implementation of this class may add further types of Results in the future.
 */
public class Result<Data> { // TODO: up to 3 different generics, one for each result type.
    /*
     * 3 Different Types:
     *  - Single: Result
     *  - OK, Fail: Assertion
     *  - OK, Error:
     *  - Fail, Error:
     *  - OK, Fail, Error: EitherResult
     */
    private final Data _data;
    private final Type _type;

    private Result(Data data, Type type) {
        _data = data;
        _type = type;
    }

    /**
     * @return the data of the result. Any value is permitted, even null.
     */
    public Data getData() {
        return _data;
    }

    /**
     * Signals that the returning method finished as expected.
     * @return true if the result can be used for the main execution path.
     */
    public boolean isOk() {
        return _type == Type.OK;
    }

    /**
     * Signals that the returning method finished as expected and the return value.
     * This is the preferred method for checking whether a method did not satisfy all imposed constraints.
     *
     * @return true if the result can be used for the main execution path.
     */
    public boolean isNotOk() {
        return _type == Type.OK;
    }

    /**
     * Signals that the returning method was interrupted in an expected way.
     * @return true if the result can be used for the main execution path.
     */
    public boolean isFailure() {
        return _type == Type.FAILURE;
    }

    /**
     * Signals that the returning method was interrupted in an unexpected or serious way.
     * @return true if the result can be used for the main execution path.
     */
    public boolean isError() {
        return _type == Type.ERROR;
    }

    /**
     * Performs an action on the data only if the instance's type is OK.
     * @param consumer will also be called if the data is null.
     */
    public void ifOk(@NotNull Consumer<Data> consumer) {
        if (_type == Type.OK) {
            consumer.accept(_data);
        }
    }

    /**
     * Performs an action on the data only if the instance's type is FAIL or FAILURE.
     * @param consumer will also be called if the data is null.
     */
    public void ifNotOK(@NotNull Consumer<Data> consumer) {
        if (_type != Type.OK) {
            consumer.accept(_data);
        }
    }

    /**
     * Performs an action on the data only if the instance's type is FAILURE.
     * @param consumer will also be called if the data is null.
     */
    public void ifFailure(@NotNull Consumer<Data> consumer) {
        if (_type == Type.FAILURE) {
            consumer.accept(_data);
        }
    }

    /**
     * Performs an action on the data only if the instance's type is FAIL.
     * @param consumer will also be called if the data is null.
     */
    public void ifError(@NotNull Consumer<Data> consumer) {
        if (_type == Type.ERROR) {
            consumer.accept(_data);
        }
    }

    /**
     * Performs an action on the data only if the instance's type is FAIL.
     * @param consumer will also be called if the data is null.
     */
    public <X extends Exception> void ifError(@NotNull ConsumerEx<Data, X> consumer) throws X {
        if (_type == Type.ERROR) {
            consumer.accept(_data);
        }
    }

    /**
     * Factory method to create a result of type OK. Construction will never fail.
     *
     * @param data the returned value of the method.
     * @return Result instance of type OK containing the provided data.
     */
    public static <Data> Result<Data> ok(Data data) {
        return new Result<>(data, Type.OK);
    }

    /**
     * Factory method to create an empty result of type OK. Construction will never fail.
     * Method {@code getData} will return null.
     *
     * @return Result instance of type OK containing the provided data.
     */
    public static <Data> Result<Data> ok() {
        return new Result<>(null, Type.OK);
    }

    /**
     * Factory method to create a result of type FAILURE. Construction will never fail.
     *
     * @param data to serve as a value for the main execution path or null as a signal for no value.
     * @return Result instance of type FAILURE containing the provided data.
     */
    public static <Data> Result<Data> failure(Data data) {
        return new Result<>(data, Type.FAILURE);
    }

    /**
     * Factory method to create an empty result of type FAILURE. Construction will never fail.
     * Method {@code getData} will return null.
     *
     * @return Result instance of type FAILURE containing the provided data.
     */
    public static <Data> Result<Data> failure() {
        return new Result<>(null, Type.FAILURE);
    }

    /**
     * Factory method to create a result of type FAIL. Construction will never fail.
     *
     * @param data is not expected to be value that can be used for the main execution path. <br>
     *             It should carry information about the nature of this error or used to throw an exception.
     * @return Result instance of type FAIL containing the provided data.
     */
    public static <Data> Result<Data> error(Data data) {
        return new Result<>(data, Type.ERROR);
    }

    /**
     * Factory method to create an empty result of type FAIL. Construction will never fail.
     * Method {@code getData} will return null.
     * However, it is preferred to return something that informs the caller about the nature of the error.
     *
     * @return Result instance of type FAIL containing the provided data.
     */
    public static <Data> Result<Data> error() {
        return new Result<>(null, Type.ERROR);
    }

    /**
     * The instances for each type.
     * Do not expose this enum so new types can be added more freely in the future.
     */
    private enum Type {
        OK, ERROR, FAILURE
    }
}
