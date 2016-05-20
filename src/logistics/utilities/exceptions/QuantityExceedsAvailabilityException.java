package logistics.utilities.exceptions;

/**
 * @author Uchenna F. Okoye
 */
public class QuantityExceedsAvailabilityException extends IllegalParameterException {

    public QuantityExceedsAvailabilityException() {super();}
    public QuantityExceedsAvailabilityException(String message){
        super(message);
    }
}
