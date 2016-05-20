package logistics.inventoryservice.inventoryitem;

/**
 * This class represents an Inventory Factory, which handles object creation 
 * of new Inventory implementation classes.
 * 
 * @author David Olorundare
 */

import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;

public class InventoryItemFactory
{
    /*
     * Returns a newly created Facility Inventory given a Facility name.
     */
	public static InventoryItem build(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
        return new InventoryItemImpl(itemId, quantity);
    }
}
