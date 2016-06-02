package logistics.utilities.loader.interfaces;

/**
 * This is an Item Loader Interface which provides common behaviors 
 * every ItemLoader implementation should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.itemservice.Item;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import java.util.Collection;

public interface ItemLoader extends Loader<Item>
{
    Collection<Item> load() throws LoaderFileNotFoundException;
}
