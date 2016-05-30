package logistics.orderservice.order.orderitem;

import logistics.utilities.exceptions.IllegalParameterException;
import logistics.utilities.exceptions.NullParameterException;

/**
 * @authors David Olundare and Uchenna F. Okoye
 */
public class OrderItemImpl implements OrderItem {

    private String itemId;
    int quantity;

    public OrderItemImpl(String itemId, int quantity) throws IllegalParameterException {
        validateItem(itemId);
        validateQuantity(quantity);
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public OrderItemImpl(OrderItem orderItem) throws IllegalParameterException {
        this(orderItem.getItemId(), orderItem.getQuantity());
    }



    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    /*
	 * Validates that a Facility's inventory quantity is not less than zero.
	 */
    private void validateQuantity(int quantity) throws NullParameterException {
        if (quantity < 0){
            throw new NullParameterException();
        }
    }

    /*
     * Validates that a Facility's inventory-item name is not Null.
     */
    private void validateItem(String itemId) throws IllegalParameterException {
        if (itemId == null){
            throw new NullParameterException("Item cannot be null");
        }
        if (itemId.isEmpty()){
            throw new IllegalParameterException("Item cannot be blank");
        }
    }

	@Override
	public void printOutput() {

        System.out.format("%-16s%-10s%-10s%n", "Item ID", itemId + ",", "Quantity: " + quantity);

		
	}
}