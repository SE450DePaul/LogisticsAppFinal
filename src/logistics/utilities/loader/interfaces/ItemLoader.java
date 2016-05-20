package logistics.utilities.loader.interfaces;

import logistics.itemservice.Item;
import logistics.utilities.exceptions.LoaderConfigFilePathException;

import java.util.Collection;

/**
 * Created by uchennafokoye on 4/22/16.
 */
public interface ItemLoader extends Loader<Item>
{
    Collection<Item> load() throws LoaderConfigFilePathException;
}
