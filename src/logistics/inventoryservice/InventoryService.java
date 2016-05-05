package logistics.inventoryservice;

import logistics.inventoryservice.inventoryitem.InventoryItemDTO;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author uchenna f. okoye
 */
public final class InventoryService
{

    private volatile static InventoryService instance;
    private HashMap<String, Inventory> inventoryHashMap = new HashMap<>();
    private Loader<Inventory> loader;


    private InventoryService() {
        loader = LoaderFactory.build("inventory");

        try {
            Collection<Inventory> inventories = loader.load();
            for (Inventory inventory : inventories){
                inventoryHashMap.put(inventory.getFacilityName(), inventory);
            }
        } catch (LoaderFileNotFoundException e) {
            e.printStackTrace();
        }

    }
    
    public static InventoryService getInstance() {
        if (instance == null)
        {
            synchronized (InventoryService.class)
            {
                if (instance == null)
                {
                    instance = new InventoryService();
                }
            }
        }
        return instance;
    }

    public InventoryItemDTO getInventoryItem(String facilityName, String itemId) {

        Inventory inventory = inventoryHashMap.get(facilityName);
        if (inventory == null) { return null; }
        Integer quantity = inventory.getQuantity(itemId);
        if (quantity == null) { return null; }
        return new InventoryItemDTO(itemId, quantity);

    }


    public String getOutput(String facilityName){
        Inventory inventory = inventoryHashMap.get(facilityName);
        if (inventory == null) { return ""; }
        return inventory.getInventoryOutput();

    }
}

