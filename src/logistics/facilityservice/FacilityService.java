package logistics.facilityservice;

/**
 * This class represents a Facility Manager that keeps track of all Facilities.
 * It provides methods for creating a Facility (using a Facility Factory), returning
 * a Facility's information to a requesting client, as well as display the 
 * list of all available Facilities.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.facilityservice.dtos.FacilityWithItemDTO;
import logistics.inventoryservice.Inventory;
import logistics.utilities.exceptions.*;
import logistics.utilities.loader.LoaderService;

import java.util.*;

public final class FacilityService
{
    private volatile static FacilityService instance;
    private HashMap<String, Facility> facilityHashMap = new HashMap<>();
    private LoaderService loaderService;

    private FacilityService() {
        loaderService = LoaderService.getInstance();
        buildFacilities();
    }
    
    /*
     * Returns an instance of the Facility Service.
     */
    public static FacilityService getInstance() {
        if (instance == null)
        {
            synchronized (FacilityService.class)
            {
                if (instance == null)
                {
                    instance = new FacilityService();
                }
            }
        }
        return instance;
    }

    /*
     * Returns a list of all Facilities
     */
    public Set<String> getFacilityNames(){
        return new TreeSet<String>(facilityHashMap.keySet());
    }

    /*
     * Returns information about a Facility given its name.
     */
    public String getOutput(String name) throws FacilityNotFoundException {
        validateFacilityExists(name);
        Facility facility = facilityHashMap.get(name);
        return facility.getFacilityOutput();
    }

    public String getInventoryOutput(String name) throws FacilityNotFoundException {
        validateFacilityExists(name);
        Facility facility = facilityHashMap.get(name);
        return facility.getInventoryOutput();
    }

    public String getScheduleOutput(String name) throws FacilityNotFoundException {
        validateFacilityExists(name);
        Facility facility = facilityHashMap.get(name);
        return facility.getScheduleOutput();
    }


    public Integer getProcessDaysNeeded(String facilityName, int noOfItemsToProcess, int startDay) throws FacilityNotFoundException, NegativeOrZeroParameterException {
        validateFacilityExists(facilityName);
        Facility facility = facilityHashMap.get(facilityName);
        return facility.getProcessDaysNeeded(noOfItemsToProcess, startDay);
    }

    public boolean reduceFromInventory(String facilityName, String itemId, int quantity) throws QuantityExceedsAvailabilityException, NullParameterException, ItemNotFoundInActiveInventoryException, NegativeOrZeroParameterException, FacilityNotFoundException {
        validateFacilityExists(facilityName);
        Facility facility = facilityHashMap.get(facilityName);
        facility.reduceFromInventory(itemId, quantity);
        return true;
    }

    /*
     * Returns FacilitiesWithItemDTO
     * which provides a list of all the facilities with an item and the quantity
     */
    public Collection<FacilityWithItemDTO> getFacilityWithItemDTOs(String itemId){

        Collection<Facility> facilities = facilityHashMap.values();
        ArrayList<FacilityWithItemDTO> facilityWithItemDTOs = new ArrayList<>();

        for (Facility facility : facilities) {
            Integer quantity = facility.getQuantity(itemId);
            if (quantity != null && quantity > 0){
                facilityWithItemDTOs.add(new FacilityWithItemDTO(facility.getFacilityName(), quantity));
            }
        }

        return facilityWithItemDTOs;
    }

    /**
    /* Processes item with default day of Day 1 and returns processing end day
     */
    public Integer bookFacility(String facility, int noOfItemsToProcess) throws NegativeOrZeroParameterException, FacilityNotFoundException {
        return bookFacility(facility, noOfItemsToProcess, 1);
    }


    /**
     *  Updates schedule given a number of items to process and returns processing end day
     */
    public Integer bookFacility(String facilityName, int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException, FacilityNotFoundException {
        validateFacilityExists(facilityName);
        Facility facility = facilityHashMap.get(facilityName);
        return facility.bookFacility(noOfItemsToProcess, startDay);
    }

    private void buildFacilities() {
        loadFacilities();
        buildInventories();
    }

    private void loadFacilities() {
        try {
            Collection<Facility> facilities = loaderService.loadFacilities();
            for (Facility facility : facilities){
                facilityHashMap.put(facility.getName(), facility);
            }
        } catch (LoaderConfigFilePathException e) {
            e.printStackTrace();
        }
    }

    private void buildInventories() {
        try {
            Collection<Inventory> inventories = loaderService.loadInventory();
            for (Inventory inventory : inventories){
                Facility facility = facilityHashMap.get(inventory.getFacilityName());
                Iterator<String> iterator = inventory.getInventoryItems();
                while (iterator.hasNext()){
                    String itemId = iterator.next();
                    int quantity = inventory.getQuantity(itemId);
                    facility.addInventoryItem(itemId, quantity);
                }

            }
        } catch (LoaderConfigFilePathException e) {
            e.printStackTrace();
        } catch (NegativeOrZeroParameterException e) {
            e.printStackTrace();
        } catch (NullParameterException e) {
            e.printStackTrace();
        }
    }


    private void validateFacilityExists(String name) throws FacilityNotFoundException {
        if (!facilityHashMap.containsKey(name)){
            throw new FacilityNotFoundException("Facility " + name + "not found");
        }
    }
}
