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

import logistics.inventoryservice.inventoryitem.InventoryItem;
import logistics.inventoryservice.inventoryitem.InventoryItemFactory;
import logistics.utilities.exceptions.ItemNotFoundInActiveInventoryException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.QuantityExceedsAvailabilityException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class InventoryImpl implements Inventory
{

	private HashMap<String, InventoryItem> activeItemHash;
	private HashMap<String, InventoryItem> depletedItemHash;
	private String facilityName;

	public InventoryImpl(String facilityName) throws NullParameterException {
		setFacilityName(facilityName);
		activeItemHash = new HashMap<>();
		depletedItemHash = new HashMap<>();
	}

	/**
	 * Adds a new Item to a Facility's Inventory given
	 * an item ID and quantity.
	 */
	public void addInventoryItem(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		updateInventoryHelper(itemId, quantity);
	}

	/**
	 * Returns an Iterator of Item Ids in the Inventory
	 */
	public Iterator<String> getInventoryItems(){
		Set<String> inventoryItems = new HashSet<>(activeItemHash.keySet());
		inventoryItems.addAll(depletedItemHash.keySet());
		return inventoryItems.iterator();
	}

	/**
	 * Updates a Facility's Inventory with a new Item and its quantity.
	 *
	 */
	public void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException {
		validateQuantityNeeded(itemId, quantity);
		InventoryItem inventoryItem = activeItemHash.get(itemId);
		int quantityAvailable = inventoryItem.getQuantity();
		quantityAvailable -= quantity;
		updateInventoryHelper(itemId, quantityAvailable);
	}

	/**
	 * Helper method that returns a Facility Inventory's item quantity
	 * given an item ID.
	 */
	public Integer getQuantity(String itemId) {
		InventoryItem inventoryItem = activeItemHash.get(itemId);
		if (inventoryItem == null) return null;
		return inventoryItem.getQuantity();
	}

	/**
	 * Helper method that returns a Facility's Name.
	 */
	public String getFacilityName(){
		return facilityName;
	}

	/**
	 * Helper method that sets a Facility's Name.
	 */
	private void setFacilityName(String nameOfFacility) throws NullParameterException {
		validateFacility(nameOfFacility);
		facilityName = nameOfFacility;
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
		stringBuffer.append("\n");
		stringBuffer.append("Active Inventory: ");
		stringBuffer.append("\n");
		stringBuffer.append("\tItem Id\t\tQuantity");

		if (activeItems.isEmpty()){
			stringBuffer.append("None");
		}

		for (String item : activeItems){
			int quantity = activeItemHash.get(item).getQuantity();
			stringBuffer.append("\n");
			stringBuffer.append("\t" + item + "\t\t" + quantity);
		}

		stringBuffer.append("\n\n");
		stringBuffer.append("Depleted (Used-Up) Inventory: ");

		Set<String> depletedItems = depletedItemHash.keySet();
		if (depletedItems.isEmpty()){
			stringBuffer.append("None");
		}

		for (String item : depletedItems){
			stringBuffer.append("\n");
			stringBuffer.append("\t" + item);
		}
		
		return stringBuffer.toString();
	}

	/*
	 * Updates the Active Inventory of a Facility with a given Item and its Quantity.
	 */
	private void updateInventoryHelper(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		if (quantity == 0) {
			InventoryItem inventoryItem = popOrBuildInventoryItemUpdateHelper(activeItemHash, itemId, quantity);
			depletedItemHash.put(itemId, inventoryItem);
		} else {
			InventoryItem inventoryItem = popOrBuildInventoryItemUpdateHelper(depletedItemHash, itemId, quantity);
			activeItemHash.put(itemId, inventoryItem);
		}
	}

	private InventoryItem popOrBuildInventoryItemUpdateHelper(HashMap<String, InventoryItem> hashMap, String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		InventoryItem inventoryItem = hashMap.remove(itemId);
		if (inventoryItem == null){
			inventoryItem = InventoryItemFactory.build(itemId, quantity);
		}
		return inventoryItem;
	}

	private void validateQuantityNeeded(String itemId, int quantityNeeded) throws ItemNotFoundInActiveInventoryException, QuantityExceedsAvailabilityException, NegativeOrZeroParameterException {
		InventoryItem inventoryItem = activeItemHash.get(itemId);
		if (inventoryItem == null) {
			throw new ItemNotFoundInActiveInventoryException("Item with id: " + itemId + "is not in active inventory");
		}

		int quantityAvailable = inventoryItem.getQuantity();
		if (quantityAvailable > quantityNeeded){
			throw new QuantityExceedsAvailabilityException("Quantity requested: " + quantityNeeded + "exceeds availability of " + inventoryItem);
		}

		if (quantityNeeded < 0){
			throw new NegativeOrZeroParameterException("Please provide requested quantity greater than 0");
		}
		
	}

	/*
	 * Validates that a Facility's name is not Null.
	 */
	private void validateFacility(String facilityName) throws NullParameterException {
		if (facilityName == null){
			throw new NullParameterException();
		}
	}


}
