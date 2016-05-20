package logistics.inventoryservice;

/**
 * This is a Facility Inventory Interface which provides common behaviors 
 * every Facility Inventory should be able to perform.
 * 
 * @author David Olorundare
 */

import logistics.utilities.exceptions.ItemNotFoundInActiveInventoryException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.QuantityExceedsAvailabilityException;

import java.util.Iterator;

public interface Inventory
{
    void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException;
    void addInventoryItem(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException;
    Iterator<String> getInventoryItems();
    Integer getQuantity(String itemId);
    String getFacilityName();
    String getInventoryOutput();
}
