package logistics.inventoryservice.inventoryitem;

/**
 * This is a Item Interface which provides common behaviors 
 * every Item object should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */
public interface InventoryItem
{
    String getItemId();
    int getQuantity();
}
