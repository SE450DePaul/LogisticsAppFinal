package logistics.scheduleservice;

/**
 * This class represents a Schedule Manager that keeps track of the schedules
 * of all the Facilities.
 * The class provides methods for creating a Schedule (given a Facility), updating a
 * Facility's Schedule, as well as display the list of all available Facility Schedules.
 *  
 * @author David Olorundare
 *
 */

import logistics.facilityservice.FacilityDTO;
import logistics.facilityservice.FacilityService;
import logistics.utilities.exceptions.IllegalParameterException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;

import java.util.Collection;
import java.util.HashMap;


public final class ScheduleService
{
    private volatile static ScheduleService instance;
    private HashMap<String, Schedule> scheduleHashMap;

    private ScheduleService() {
        FacilityService facilityService = FacilityService.getInstance();
        scheduleHashMap = new HashMap<>();
        Collection<FacilityDTO> facilityDTOCollection = facilityService.getFacilityDTOs();
        for (FacilityDTO facilityDTO : facilityDTOCollection){
            try {
                Schedule schedule = ScheduleFactory.build(facilityDTO);
                scheduleHashMap.put(facilityDTO.name, schedule);
            } catch (NullParameterException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     *  Returns a static instance of the Schedule Service
     */
    public static ScheduleService getInstance(){
        if (instance == null)
        {
            synchronized (ScheduleService.class)
            {
                if (instance == null)
                {
                    instance = new ScheduleService();
                }
            }
        }
        return instance;
    }


    /*
     *  Returns string output of a Schedule of a given Facility name
     */
    public String getOutput(String facilityName) throws IllegalParameterException {
        validateFacilityName(facilityName);
        Schedule schedule = scheduleHashMap.get(facilityName);
        if (schedule == null) { return null; }
        return schedule.getScheduleOutput();
    }


    public Integer getProcessDaysNeeded(String facilityName, int noOfItemsToProcess, int startDay) throws IllegalParameterException {
        validateFacilityName(facilityName);
        Schedule schedule = scheduleHashMap.get(facilityName);
        if (schedule == null) { return null; }
        return schedule.getProcessDaysNeeded(noOfItemsToProcess, startDay);
    }

    /*
    /* Processes item with default day of Day 1 and returns processing end day
     */
    public Integer bookFacility(String facility, int noOfItemsToProcess) throws NegativeOrZeroParameterException {
        return bookFacility(facility, noOfItemsToProcess, 1);
    }


    /*
     *  Updates schedule given a number of items to process and returns processing end day
     */
    public Integer bookFacility(String facility, int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException {
        Schedule schedule = scheduleHashMap.get(facility);
        if (schedule == null) { return null; }
        return schedule.bookFacility(noOfItemsToProcess, startDay);
    }

    private void validateFacilityName(String name) throws IllegalParameterException {
        if (name == null) {
            throw new NullParameterException("Facility name cannot be null");
        }
        if (name.equals("")){
            throw new IllegalParameterException("Facility name cannot be empty string");
        }
    }

    public static void main(String[] args) {
	    ScheduleService instance = ScheduleService.getInstance();
        String facility = "Chicago, IL";
        try {
            System.out.println("-------------------------Initial Schedule-----------------------------------------------\n");
            System.out.println(instance.getOutput(facility));
            System.out.println("--------------------New Schedule when 26 items processed--------------------------------\n");
            instance.bookFacility(facility, 26, 5);
            System.out.println(instance.getOutput("Chicago, IL"));
            System.out.println("--------------------New Schedule when another 33 items are processed--------------------\n");
            instance.bookFacility(facility, 33);
            System.out.println(instance.getOutput("Chicago, IL"));
            System.out.println("--------------------New Schedule when 10 more items processed----------------------------\n");
            instance.bookFacility(facility, 10);
            System.out.println(instance.getOutput("Chicago, IL"));
        } catch (IllegalParameterException e) {
            e.printStackTrace();
        }
    }
}
