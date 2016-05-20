package logistics.facilityservice;

/**
 * This class represents a Facility implementation.
 * 
 * @authors David Olorundare and Uchenna F. okoye
 */

import logistics.inventoryservice.Inventory;
import logistics.inventoryservice.InventoryFactory;
import logistics.scheduleservice.Schedule;
import logistics.scheduleservice.ScheduleFactory;
import logistics.utilities.exceptions.*;

import java.util.Iterator;

public class FacilityImpl implements Facility
{
    private String name;
    private Integer rate;
    private Double cost;
	private Inventory inventory;
	private Schedule schedule;

    public FacilityImpl(String name, Integer rate, Double cost) throws IllegalParameterException {
        setName(name);
        setRate(rate);
        setCost(cost);
		inventory = InventoryFactory.build(name);
		schedule = ScheduleFactory.build(name, rate);
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


	/* Inventory */
	@Override
	public String getInventoryOutput() {
		return inventory.getInventoryOutput();
	}

	@Override
	public void addInventoryItem(String itemId, int quantity) throws NullParameterException, NegativeOrZeroParameterException {
		inventory.addInventoryItem(itemId, quantity);
	}

	@Override
	public Iterator<String> getInventoryItems() {
		return inventory.getInventoryItems();
	}

	@Override
	public Integer getQuantity(String itemId) {
		return inventory.getQuantity(itemId);
	}

	@Override
	public String getFacilityName() {
		return inventory.getFacilityName();
	}

	@Override
	public void reduceFromInventory(String itemId, int quantity) throws NullParameterException, QuantityExceedsAvailabilityException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException {
		inventory.reduceFromInventory(itemId, quantity);
	}

	/* End Inventory */

	/* Schedule */

	@Override
	public int bookFacility(int processItemNum, int startDay) throws NegativeOrZeroParameterException {
		return schedule.bookFacility(processItemNum, startDay);
	}

	@Override
	public String getScheduleOutput() {
		return schedule.getScheduleOutput();
	}

	@Override
	public int getProcessDaysNeeded(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException {
		return schedule.getProcessDaysNeeded(noOfItemsToProcess, startDay);
	}

	/* End Schedule */

	/*
	 * Helper method used to assembly a Facility's Name, Rate, and Cost, for output.
	 */
	public String getFacilityOutput() {
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
		if (name == null | name.equals("")){
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
