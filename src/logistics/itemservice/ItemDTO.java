package logistics.itemservice;

/**
 * This class represents an Item Data Transfer Object,
 * which is used by the Item Service manager when communicating
 * with other subsystems of the Logistics application.
 * 
 * @author Uchenna F. Okoye
 */
public class ItemDTO {

    public String id;
    public Double price;
    
    /**
     * Constructor that creates a new ItemDTO given
     * an item's ID and price.
     */
    public ItemDTO(String itemId, Double itemPrice){
        id = itemId;
        price = itemPrice;
    }
}
