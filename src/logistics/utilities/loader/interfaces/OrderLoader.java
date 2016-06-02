package logistics.utilities.loader.interfaces;

/**
 * This is an Order Loader Interface which provides common behaviors 
 * every OrderLoader implementation should be able to perform.
 * 
 * @author David Olorundare
 */

import logistics.orderservice.Order;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import java.util.Collection;

public interface OrderLoader extends Loader<Order>
{	
	Collection<Order> load() throws LoaderFileNotFoundException;
}
