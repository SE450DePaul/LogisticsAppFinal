package logistics.inventoryservice.inventoryitem;

/**
 * This class represents an Inventory-item Data Transfer Object,
 * which is used by the Inventory Service manager when communicating
 * with other subsystems of the Logistics application.
 * 
 * @author David Olorundare
 */
public class InventoryItemDTO
{

    public String id;
    public int quantity;
    
    /*
     * Constructor that creates a new Inventory-item DTO given an 
     * item's ID and quantity.
     */
    public InventoryItemDTO(String itemId, int itemQuantity)
    {
        id = itemId;
        quantity = itemQuantity;
    }
}
