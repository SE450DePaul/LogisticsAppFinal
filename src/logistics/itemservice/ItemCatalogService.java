package logistics.itemservice;

import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.utilities.loader.interfaces.Loader;
import logistics.utilities.loader.factory.LoaderFactory;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by uchennafokoye on 4/22/16.
 */
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

    public String getOutput(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Item Catalog: ");
        stringBuffer.append("\n");
        int i = 1;
        Collection<Item> items = itemsHash.values();
        for (Item item : items){
            stringBuffer.append(item.toString());
            if (i % 4 == 0){
                stringBuffer.append("\n");
            } else {
                stringBuffer.append("\t");
            }
            i++;
        }
        return stringBuffer.toString();
    }


    public ItemDTO getItem(String itemId){
        Item item = itemsHash.get(itemId);
        if (item == null) return null;
        return new ItemDTO(item.getId(), item.getPrice());
    }

    public static void main(String[] args){

        ItemCatalogService itemCatalogService = ItemCatalogService.getInstance();
        ItemDTO itemDTO = itemCatalogService.getItem("ABC123");
        System.out.println("Please get item");
        System.out.println(" Item id: " + itemDTO.id + " Item price: " + itemDTO.price);

    }

}
