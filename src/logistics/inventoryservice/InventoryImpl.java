package logistics.inventoryservice;

/**
 * This class represents the Inventory of a given Facility.
 * 
 * The class provides methods to add and remove items to the
 * a Facility's inventory, as well as list out all the inventory
 * in the Facility. 
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.*;

import java.util.HashMap;
import java.util.Set;


public class InventoryImpl implements Inventory
{

	private HashMap<String, Integer> activeItemHash;
	private HashMap<String, Integer> depletedItemHash;
	private String facilityName;

	public InventoryImpl(String facilityName) throws NullParameterException {
		setFacilityName(facilityName);
		activeItemHash = new HashMap<>();
		depletedItemHash = new HashMap<>();
	}

	/**
	 * Helper method that sets a Facility's Name.
	 */
	private void setFacilityName(String nameOfFacility) throws NullParameterException {
		validateFacility(nameOfFacility);
		facilityName = nameOfFacility;
	}

	public void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException {
		validateQuantityNeeded(itemId, quantity);
		int quantityAvailable = activeItemHash.get(itemId);;
		quantityAvailable -= quantity;
		updateInventoryHelper(itemId, quantityAvailable);
	}

	/**
	 * Adds a new Item to a Facility's Inventory given
	 * an item ID and quantity.
	 */
	public void addInventoryItem(String itemId, int quantity) throws IllegalParameterException {
		validateItem(itemId);
		validateQuantity(quantity);
		updateInventoryHelper(itemId, quantity);
	}

	/**
	 * Helper method that returns a Facility Inventory's item quantity
	 * given an item ID.
	 */
	public Integer getQuantity(String itemId) {
		return activeItemHash.get(itemId);
	}

	/**
	 * Helper method that returns a Facility's Name.
	 */
	public String getFacilityName() {
		return facilityName;
	}

	/**
	 * Displays the Active Inventory details of a Facility,
	 * showing the Items currently present in the inventory, 
	 * its Quantity, as well as the total quantity of Items
	 *  that have already been used up.
	 */
	public String getInventoryOutput() {

		Set<String> activeItems = activeItemHash.keySet();
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("\nActive Inventory: \n\tItem Id\t\tQuantity");
		stringBuffer.append("\n");

		if (activeItems.isEmpty()){
			stringBuffer.append("None");
		}

		for (String item : activeItems){
			int quantity = activeItemHash.get(item);

			stringBuffer.append(String.format("\t%-8s\t%-4d%n", item, quantity));
		}

		stringBuffer.append("\n\n");
		stringBuffer.append("Depleted (Used-Up) Inventory: ");

		Set<String> depletedItems = depletedItemHash.keySet();
		if (depletedItems.isEmpty()){
			stringBuffer.append("None");
		}

		for (String item : depletedItems){
			stringBuffer.append("\t" + item);
		}

		return stringBuffer.toString();
	}

	/**
	 * Updates the Active Inventory of a Facility with a given Item and its Quantity.
	 */
	private void updateInventoryHelper(String itemId, int quantity) {
		if (quantity == 0) {
			activeItemHash.remove(itemId);
			depletedItemHash.put(itemId, quantity);
		} else {
			activeItemHash.put(itemId, quantity);
			depletedItemHash.remove(itemId);
		}
	}

	private void validateQuantityNeeded(String itemId, int quantityNeeded) throws ItemNotFoundInActiveInventoryException, QuantityExceedsAvailabilityException, NegativeOrZeroParameterException {
		Integer quantityAvailable  = activeItemHash.get(itemId);
		if (quantityAvailable == null) {
			throw new ItemNotFoundInActiveInventoryException("Item with id: " + itemId + "is not in active inventory");
		}

		if (quantityNeeded > quantityAvailable){
			throw new QuantityExceedsAvailabilityException("Quantity requested: " + quantityNeeded + "exceeds availability of " + quantityAvailable);
		}

		if (quantityNeeded < 0){
			throw new NegativeOrZeroParameterException("Please provide requested quantity greater than 0");
		}

	}

	/**
	 * Validates that a Facility's name is not Null.
	 */
	private void validateFacility(String facilityName) throws NullParameterException {
		if (facilityName == null){
			throw new NullParameterException();
		}
	}

	/**
	 * Validates that a Facility's inventory quantity is not less than zero.
	 */
	private void validateQuantity(int quantity) throws NullParameterException {
		if (quantity < 0){
			throw new NullParameterException();
		}
	}

	/**
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
}
