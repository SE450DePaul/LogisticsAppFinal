package logistics.facilityservice;

/**
 * This class represents a Facility Manager that keeps track of all Facilities.
 * It provides methods for creating a Facility (using a Facility Factory), returning
 * a Facility's information to a requesting client, as well as display the 
 * list of all available Facilities.
 * 
 * @author Uchenna F. Okoye
 */

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

    /* 
     * Returns a FacilityDTO given the name of the Facility.
     */
    public FacilityDTO getFacility(String name) throws IllegalParameterException {
        validateFacilityName(name);
    	Facility facility = facilityHashMap.get(name);
        if (facility == null) return null;
        return new FacilityDTO(facility.getName(), facility.getCost(), facility.getRate());
    }

    public Collection<FacilityDTO> getFacilityDTOs() {
        Collection<String> facilityNames = facilityHashMap.keySet();
        Collection<FacilityDTO> facilityDTOs = new ArrayList<FacilityDTO>();
        for (String facilityName : facilityNames){
            Facility facility = facilityHashMap.get(facilityName);
            FacilityDTO facilityDTO = new FacilityDTO(facility.getName(), facility.getCost(), facility.getRate());
            facilityDTOs.add(facilityDTO);
        }
        return facilityDTOs;
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
        return facility.toString();
    }

    public String getInventoryOutput(String name) throws FacilityNotFoundException {
        validateFacilityExists(name);
        Facility facility = facilityHashMap.get(name);
        return facility.getInventoryOutput();
    }


    private void validateFacilityName(String name) throws IllegalParameterException {
        if (name == null) {
            throw new NullParameterException("Facility name cannot be null");
        }
        if (name.equals("")){
            throw new IllegalParameterException("Facility name cannot be empty string");
        }
    }



    private void validateFacilityExists(String name) throws FacilityNotFoundException {
        if (!facilityHashMap.containsKey(name)){
            throw new FacilityNotFoundException("Facility " + name + "not found");
        }
    }
}
