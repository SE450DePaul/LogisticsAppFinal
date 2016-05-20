package logistics.utilities.loader;

/**
 * This class represents a Facility Manager that keeps track of all Facilities.
 * It provides methods for creating a Facility (using a Facility Factory), returning
 * a Facility's information to a requesting client, as well as display the 
 * list of all available Facilities.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.facilityservice.Facility;
import logistics.inventoryservice.Inventory;
import logistics.itemservice.Item;
import logistics.utilities.exceptions.LoaderConfigFilePathException;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;

import java.util.Collection;

public final class LoaderService
{
    private volatile static LoaderService instance;


    private LoaderService() {

    }
    
    /*
     * Returns an instance of the Facility Service.
     */
    public static LoaderService getInstance() {
        if (instance == null)
        {
            synchronized (LoaderService.class)
            {
                if (instance == null)
                {
                    instance = new LoaderService();
                }
            }
        }
        return instance;
    }

    public Collection<Facility> loadFacilities() throws LoaderConfigFilePathException {
        Collection<Facility> facilities = null;
        Loader<Facility> loader = LoaderFactory.build("facility");
        facilities = loader.load();

        return facilities;
    }

    public Collection<Item> loadItems() throws LoaderConfigFilePathException {
        Collection<Item> items = null;
        Loader<Item> loader = LoaderFactory.build("item");
        items = loader.load();

        return items;
    }

    public Collection<Inventory> loadInventory() throws LoaderConfigFilePathException {
        Collection<Inventory> inventories = null;
        Loader<Inventory> loader = LoaderFactory.build("inventory");
        inventories = loader.load();

        return inventories;
    }



}
