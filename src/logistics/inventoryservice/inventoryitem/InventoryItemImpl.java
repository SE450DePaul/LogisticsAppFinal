package logistics.inventoryservice.inventoryitem;

/**
 * This class represents the Inventory Item for an Inventory
 *
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;


public class InventoryItemImpl implements InventoryItem {

	private String itemId;
	private int quantity;

	public InventoryItemImpl(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		validateItem(itemId);
		validateQuantity(quantity);
		this.itemId = itemId;
		this.quantity = quantity;
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
	private void validateQuantity(int quantity) throws NegativeOrZeroParameterException {
		if (quantity < 0){
			throw new NegativeOrZeroParameterException("Please provide a number greater than or equal to 0");
		}
	}

	/*
	 * Validates that a Facility's inventory-item name is not Null.
	 */
	private void validateItem(String itemId) throws NullParameterException {
		if (itemId == null){
			throw new NullParameterException();
		}
	}


}
