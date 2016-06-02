package logistics.itemservice;

/**
 * This class represents an Item Factory, which handles object creation 
 * of new Items implementation classes.
 * Created by uchennafokoye on 4/23/16.
 */

import logistics.utilities.exceptions.NullParameterException;

public class ItemFactory {

	/**
	 * Returns a newly created item object given
	 * an item ID and price.
	 */
    public static Item build(String id, Double price) throws NullParameterException {
        return new ItemImpl(id, price);
    }
}
