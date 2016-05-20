package logistics.scheduleservice;

/**
 * This class represents the Schedule of a given Facility.
 * It uses a Java HashMap to keep track of the workdays a Facility uses to 
 * process a given item. The HashMap's key-value representation is mapped to 
 * Facility work-days and available processing rate for each day, respectively. 
 * 
 * The class provides methods to create schedules as well as change existing ones.
 * The HashMap dynamically increases to add more workdays whenever the initial
 * set days are exhausted.
 * 
 * @author David Olorundare and uchenna f. okoye
 */

import logistics.facilityservice.dtos.FacilityDTO;
import logistics.facilityservice.FacilityService;
import logistics.utilities.exceptions.FacilityNotFoundException;
import logistics.utilities.exceptions.IllegalParameterException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;

import java.util.HashMap;
import java.util.Set;


public class ScheduleImpl implements Schedule
{
    public int pointerToNextAvailableDay;
    private String facilityName;
    private int pacePerDay;
    private HashMap<Integer, Integer> dayAvailability;

    public String getFacilityName() {
        return facilityName;
    }

    public ScheduleImpl(FacilityDTO facility) throws IllegalParameterException {
        dtoConstructor(facility);
    }

    public ScheduleImpl(String facilityName, int rate) throws IllegalParameterException {
        FacilityDTO facilityDTO = new FacilityDTO(facilityName, 0.0, rate);
        dtoConstructor(facilityDTO);
    }

    private void dtoConstructor(FacilityDTO facility) throws IllegalParameterException {
        validateFacilityDTO(facility);
        facilityName = facility.name;
        pacePerDay = facility.rate;
        dayAvailability = new HashMap<>();
        pointerToNextAvailableDay = 1;
        buildHashMapValues(pointerToNextAvailableDay, 20);
    }



    /*
    /* Determines the days needed to process items located at the name.
     */
    public int getProcessDaysNeeded(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException {
        validateProcessItemNum(noOfItemsToProcess);
        validateStartDay(startDay);
        buildHashMapValues(startDay, 20);

        int pointer = startDay;
        while (noOfItemsToProcess > 0){
            Integer availability = dayAvailability.get(pointer);
            noOfItemsToProcess -= availability;
            if (noOfItemsToProcess > 0) {
                pointer++;
            }
        }

        return pointer;
    }

    /*
    /* Processes items and return processing end day
     */
    public int bookFacility(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException {
        validateProcessItemNum(noOfItemsToProcess);
        validateStartDay(startDay);
        buildHashMapValues(startDay, 20);

        int pointer = startDay;
        while (noOfItemsToProcess > 0){
            Integer availability = dayAvailability.get(pointer);
            if (availability > noOfItemsToProcess){
                dayAvailability.put(pointer, availability - noOfItemsToProcess);
            } else {
                dayAvailability.put(pointer, 0);
            }

            noOfItemsToProcess -= availability;
            pointer++;
        }

        return pointer;
   }

    /*
     *  Returns the schedule of the associated name
     */
    public String getScheduleOutput()
    {
        StringBuffer str = new StringBuffer();
        str.append("\n");
        str.append("Schedule: ");
        str.append("\n");
        str.append("Days:\t\t");

        Set<Integer> days = dayAvailability.keySet();
        for(Integer day: days){
            str.append(day + "\t" );
        }
        str.append("\nAvailable:\t");
        for(Integer day: dayAvailability.keySet()) {
            str.append(dayAvailability.get(day) + "\t");
        }
        str.append("\n");

        return str.toString();
    }

    /*
    * Builds Hashmap values from the day given
    */
    private void buildHashMapValues(int startDay, int noToBuild){
        int endDay = startDay + noToBuild;
        for (int i = startDay; i < endDay; i++){
            if (!dayAvailability.containsKey(i)){
                dayAvailability.put(i, pacePerDay);
            }
        }
    }

    private void validateRateAndName(String facilityName, int rate) throws IllegalParameterException {
        if (facilityName == null) {
            throw new NullParameterException("Facility cannot be null");
        }
        if (facilityName.equals("")) {
            throw new IllegalParameterException("Facility cannot be blank");
        }
        if (rate <= 0){
            throw new NegativeOrZeroParameterException("Rate cannot be a negative or 0");
        }
    }

    private void validateFacilityDTO(FacilityDTO facilityDTO) throws IllegalParameterException {
        if (facilityDTO == null)
            throw new NullParameterException("Facility cannot be null");
        validateRateAndName(facilityDTO.name, facilityDTO.rate);
    }

    private void validateProcessItemNum(int noOfItemsToProcess) throws NegativeOrZeroParameterException {
        if (noOfItemsToProcess <= 0)
            throw new NegativeOrZeroParameterException("Number of items to process cannot be less than or zero");
    }

    private void validateStartDay(int startDay) throws NegativeOrZeroParameterException {
        if (startDay <= 0){
            throw new NegativeOrZeroParameterException("Start Day cannot be less than or equal to zero");
        }
    }

    // Test that this class works
    public static void main(String[] args) {
        FacilityService instance = FacilityService.getInstance();
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
        } catch (FacilityNotFoundException e) {
            e.printStackTrace();
        }

    }
}









