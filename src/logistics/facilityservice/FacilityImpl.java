package logistics.facilityservice;

/**
 * This class represents a Facility implementation.
 * 
 * @authors David Olorundare and Uchenna F. okoye
 */

import logistics.inventoryservice.Inventory;
import logistics.inventoryservice.InventoryFactory;
import logistics.utilities.exceptions.ItemNotFoundInActiveInventoryException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.QuantityExceedsAvailabilityException;

public class FacilityImpl implements Facility
{
    private String name;
    private Integer rate;
    private Double cost;
	private Inventory inventory;

    public FacilityImpl(String name, Integer rate, Double cost) throws NullParameterException {
        setName(name);
        setRate(rate);
        setCost(cost);
		inventory = InventoryFactory.build(name);
    }


	public String getName() {
		return name;
	}
	public Integer getRate()
	{
		return rate;
	}
	public Double getCost()
	{
		return cost;
	}

	@Override
	public String getInventoryOutput() {
		return inventory.getInventoryOutput();
	}

	@Override
	public void addInventoryItem(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		inventory.addInventoryItem(itemId, quantity);
	}

	@Override
	public Integer getQuantity(String itemId) {
		return inventory.getQuantity(itemId);
	}

	@Override
	public void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException {
		inventory.reduceFromInventory(itemId, quantity);
	}
	/*
	 * Helper method used to assembly a Facility's Name, Rate, and Cost, for output.
	 */
	public String toStr() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(name);
		stringBuffer.append("\n");
		stringBuffer.append(generateDashedLine(name.length()));
		stringBuffer.append("\n");
		stringBuffer.append("Rate per Day: " + rate);
		stringBuffer.append("\n");
		stringBuffer.append("Cost per day: " + cost);
		return stringBuffer.toString();
	}



	/*
	 * Helper method used to set a Facility's Name.
	 */
	private void setName(String facilityName) throws NullParameterException
	{
		validateName(facilityName);
		name = facilityName;
	}

	/*
	 * Helper method used to set a Facility's Rate.
	 */
	private void setRate(Integer facilityRate) throws NullParameterException
	{
		validateRate(facilityRate);
		rate = facilityRate;
	}

	/*
	 * Helper method used to set a Facility's Cost.
	 */
	private void setCost(Double facilityCost) throws NullParameterException
	{
		validateCost(facilityCost);
		cost = facilityCost;
	}

	/*
	 * Validates that a given Facility's Name is not Null.
	 */
	private void validateName(String name) throws NullParameterException {
		if (name == null | name == ""){
			throw new NullParameterException("Facility Name cannot be Null or Empty");	
		}
	}

	/*
	 * Validates that a given Facility's Rate is not Null.
	 */
	private void validateRate(Integer rate) throws NullParameterException {
		if (rate == null){
			throw new NullParameterException("Facility Rate cannot be Null");
		}
	}
	
	/*
	 * Validates that a given Facility's Cost is not Null.
	 */
	private void validateCost(Double cost) throws NullParameterException {
		if (cost == null){
			throw new NullParameterException("Facility Cost cannot be Null");
		}
	}
	
	/*
	 * Helper method that is used to generate dashed lines.
	 */
	private String generateDashedLine(int length) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++){
			str.append("-");
		}
		return str.toString();
	}
}
