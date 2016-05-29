package logistics.inventoryservice;

/**
 * This is a Facility Inventory Interface which provides common behaviors 
 * every Facility Inventory should be able to perform.
 * 
 * @author David Olorundare
 */

import logistics.utilities.exceptions.*;

public interface Inventory
{
    void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException;
    void addInventoryItem(String itemId, int quantity) throws IllegalParameterException;
    Integer getQuantity(String itemId);
    String getFacilityName();
    String getInventoryOutput();
}
