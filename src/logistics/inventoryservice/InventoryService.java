package logistics.inventoryservice;

/**
 * This class represents an Inventory Manager that keeps track of the Inventories
 * of all the Facilities.
 * The class provides methods for returning the Inventory of a given Facility, as well
 * as displaying the Inventories of all Facilities.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.inventoryservice.dtos.FacilityWithItemDTO;
import logistics.utilities.exceptions.*;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

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
        } catch (LoaderConfigFilePathException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Returns an instance of the Inventory Service 
     */
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
    
    public boolean reduceFromInventory(String facility, String itemId, int quantity) throws QuantityExceedsAvailabilityException, NullParameterException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException {
        Inventory inventory = inventoryHashMap.get(facility);
        if (inventory == null) { return false; }
        inventory.reduceFromInventory(itemId, quantity);
        return true;
    }

    /*
     * Returns FacilitiesWithItemDTO
     * which provides a list of all the facilities with an item and the quantity
     */
    public Collection<FacilityWithItemDTO> getFacilityWithItemDTOs(String itemId){

        Set<String> facilities = inventoryHashMap.keySet();
        ArrayList<FacilityWithItemDTO> facilityWithItemDTOs = new ArrayList<>();

        for (String facility : facilities) {
            Integer quantity = inventoryHashMap.get(facility).getQuantity(itemId);
            if (quantity != null && quantity > 0){
                facilityWithItemDTOs.add(new FacilityWithItemDTO(facility, quantity));
            }
        }

        return facilityWithItemDTOs;
    }

    /*
     * Returns the Inventory of a given Facility.
     */
    public String getOutput(String facilityName){
        Inventory inventory = inventoryHashMap.get(facilityName);
        if (inventory == null) { return ""; }
        return inventory.getInventoryOutput();

    }
}

