package logistics.inventoryservice;

/**
 * This class represents an Inventory Factory, which handles object creation 
 * of new Inventory implementation classes.
 * 
 * @author David Olorundare
 */

import logistics.utilities.exceptions.NullParameterException;

public class InventoryFactory
{
    /**
     * Returns a newly created Facility Inventory given a Facility name.
     */
	public static Inventory build(String facilityName) throws NullParameterException 
	{
        return new InventoryImpl(facilityName);
    }
}
