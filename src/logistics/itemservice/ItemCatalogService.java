package logistics.itemservice;

/**
 * This class represents an Item Catalog Manager that keeps track of the Items
 * in the Inventories of all Facilities.
 * The class provides methods for displaying the Items in a given Facility's
 * Inventory, as well as the Items in all Facility Inventories.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public final class ItemCatalogService {

    private volatile static ItemCatalogService instance;
    private Loader loader;
    private HashMap<String, Item> itemsHash = new HashMap<>();

    private ItemCatalogService() {
            loader = LoaderFactory.build("item");

            try {
                Collection<Item> items = loader.load();
                for (Item item : items){
                    itemsHash.put(item.getId(), item);
                }
            } catch (LoaderFileNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    /**
     * Returns an instance of the Item Catalog service.
     */
    public static ItemCatalogService getInstance() {
        if (instance == null){
            synchronized (ItemCatalogService.class){
                if (instance == null){
                    instance = new ItemCatalogService();
                }
            }
        }

        return instance;
    }
    
    /**
     * Returns information about all Items in the Facilities.
     */
    public String getOutput(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\nItem Catalog:  \n");

        int i = 1;
        Collection<String> itemIds = new TreeSet<>(itemsHash.keySet());
        for (String itemId : itemIds) {
            Item item = itemsHash.get(itemId);
            stringBuffer.append(String.format("%-9s: $%,-5.0f\t", item.getId(), item.getPrice()));
            if (i % 4 == 0) {
                stringBuffer.append("\n");
            }
            i++;
        }
        return stringBuffer.toString();
    }

    /**
     * Returns an ItemDTO given an item's name.
     */
    public ItemDTO getItem(String itemId){
        Item item = itemsHash.get(itemId);
        if (item == null) return null;
        return new ItemDTO(item.getId(), item.getPrice());
    }

    // Test that the service works.
    public static void main(String[] args){

        ItemCatalogService itemCatalogService = ItemCatalogService.getInstance();
        ItemDTO itemDTO = itemCatalogService.getItem("ABC123");
        System.out.println("Please get item");
        System.out.println(" Item id: " + itemDTO.id + " Item price: " + itemDTO.price);
    }
}
