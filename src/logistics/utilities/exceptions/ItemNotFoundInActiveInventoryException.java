package logistics.utilities.exceptions;

/**
 * @author Uchenna F. Okoye
 */
public class ItemNotFoundInActiveInventoryException extends IllegalParameterException {

    public ItemNotFoundInActiveInventoryException() {super();}
    public ItemNotFoundInActiveInventoryException(String message){
        super(message);
    }
}
