package logistics.utilities.loader.interfaces;

/**
 * This is an Inventory Loader Interface which provides common behaviors 
 * every InventoryLoader implementation should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.inventoryservice.Inventory;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import java.util.Collection;

public interface InventoryLoader extends Loader<Inventory>
{
    Collection<Inventory> load() throws LoaderFileNotFoundException;
}
