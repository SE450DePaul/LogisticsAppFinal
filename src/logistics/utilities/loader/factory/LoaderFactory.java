package logistics.utilities.loader.factory;

/**
 * This class represents a Loader Factory, which handles creation 
 * of Facility, Item, Inventory, and Network Loader Factories.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.loader.interfaces.Loader;

public class LoaderFactory {

    public static Loader build(String type){
        if (type == "item"){
            return ItemLoaderFactory.build();
        } else if (type == "facility"){
            return FacilityLoaderFactory.build();
        } else if (type == "inventory"){
            return InventoryLoaderFactory.build();
        } else if (type == "network"){
            return NetworkLoaderFactory.build();
        }
        return null;
    }
}
