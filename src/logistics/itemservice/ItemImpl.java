package logistics.itemservice;


/**
 * This class represents the Item in the inventory of a given Facility.
 * 
 * The class provides methods to set as well as return a given Item's 
 * name and price.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.NullParameterException;
import java.text.DecimalFormat;

public class ItemImpl implements Item
{
    private String id;
    private Double price;
    
    /**
     * Constructor that creates a new Item given
     * an item ID and price.
     */
    public ItemImpl(String id, Double price) throws NullParameterException {
        setId(id);
        setPrice(price);
    }
    
    /**
     * Helper method that returns an Item's ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Helper method that returns an Item's price.
     */
    public Double getPrice() {
        return price;
    }

   /**
    * Helper method that is used for displaying
    * an Item's information.
    */
    public String toString() {
        DecimalFormat inputFormatter = new DecimalFormat("$#,###");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(id);
        stringBuffer.append(" : ");
        stringBuffer.append(inputFormatter.format(price));
        return stringBuffer.toString();
    }

    /**
     * Helper method that sets an Item's name.
     */
    private void setId(String itemId) throws NullParameterException {
        validateId(itemId);
        id = itemId;
    }
    
    /**
     * Helper method that sets an Item's price.
     */
    private void setPrice(Double itemPrice) throws NullParameterException {
        validatePrice(itemPrice);
        price = itemPrice;
    }

    /**
     * Validates that an Item's name is not Empty or Null.
     */
    private void validateId(String id) throws NullParameterException {
        if (id == null | id.equals("")){
            throw new NullParameterException("Item ID cannot be null or empty");
        }
    }

    /**
     * Validates that an Item's price is not Null.
     */
    private void validatePrice(Double price) throws NullParameterException {
        if (price == null){
            throw new NullParameterException("Price cannot be null");
        }
    }
}
