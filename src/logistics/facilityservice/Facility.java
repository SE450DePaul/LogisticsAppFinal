package logistics.facilityservice;

import logistics.utilities.exceptions.ItemNotFoundInActiveInventoryException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.QuantityExceedsAvailabilityException;

/**
 * This class represents a Facility Factory, which handles object creation 
 * of new Facility Implementation classes.
 * 
 * @author David Olorundare
 * @author Uchenna F. Okoye
 */

public interface Facility
{
    String getName();
    Integer getRate();
    Double getCost();
    String getInventoryOutput();
    void addInventoryItem(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException;
    Integer getQuantity(String itemId);
    void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException;
}
