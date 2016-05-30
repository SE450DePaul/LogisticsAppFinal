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

import logistics.facilityservice.FacilityDTO;
import logistics.facilityservice.FacilityService;
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
    private final int NO_TO_SHOW = 20;

    public String getFacilityName() {
        return facilityName;
    }

    public ScheduleImpl(FacilityDTO facility) throws NullParameterException {
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
        int howManyToBuild = (noOfItemsToProcess/pacePerDay) + 1;
        buildHashMapValues(startDay, howManyToBuild);

        int pointer = startDay;
        while (noOfItemsToProcess > 0){
            Integer availability = dayAvailability.get(pointer);
            if (availability == null){
                buildHashMapValues(pointer, 20);
                availability = dayAvailability.get(pointer);
            }
            noOfItemsToProcess -= availability;
            if (noOfItemsToProcess > 0) {
                pointer++;
            }
        }

        return pointer;
    }

    /*
    /* Processes items
     */
    public boolean bookFacility(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException {
        validateProcessItemNum(noOfItemsToProcess);
        validateStartDay(startDay);
        int howManyToBuild = (noOfItemsToProcess/pacePerDay) + 1;
        buildHashMapValues(startDay, howManyToBuild);

        int pointer = startDay;
        while (noOfItemsToProcess > 0){
            Integer availability = dayAvailability.get(pointer);
            if (availability == null){
                buildHashMapValues(pointer, 20);
                availability = dayAvailability.get(pointer);
            }
            if (availability > noOfItemsToProcess){
                dayAvailability.put(pointer, availability - noOfItemsToProcess);
            } else {
                dayAvailability.put(pointer, 0);
            }

            noOfItemsToProcess -= availability;
            pointer++;
        }

        return true;
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
        for (int day= 1; day <= NO_TO_SHOW; day++){
            str.append(day + "\t" );
        }

        str.append("\nAvailable:\t");
        for (int day= 1; day <= NO_TO_SHOW; day++){
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

    private void validateFacilityDTO(FacilityDTO facilityDTO) throws NullParameterException {
        if (facilityDTO == null)
            throw new NullParameterException("Facility cannot be null");
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
    public static void main(String[] args)
    {
		FacilityService instance = FacilityService.getInstance();

		ScheduleImpl schedule;
		try
		{
			schedule = new ScheduleImpl(instance.getFacility("San Francisco, CA"));
			System.out.println("-----------Initial Schedule for San Francisco Facility ------------------------------------------");
			System.out.println(schedule.getScheduleOutput());
            System.out.println("Days needed to process RL123A for 40 items on Day 2: " + schedule.getProcessDaysNeeded(40, 2));
			schedule.bookFacility(40, 2);
			System.out.println("-----------New Schedule After Processing 40 Items ------------------------------------------");
			System.out.println(schedule.getScheduleOutput());
			System.out.println("-----------New Schedule After Processing another 33 Items------------------------------------------");
			schedule.bookFacility(33, 1);
			System.out.println(schedule.getScheduleOutput());
			System.out.println("-----------------New Schedule After Processing 7 more Items------------------------------------");
			schedule.bookFacility(7, 1);
			System.out.println(schedule.getScheduleOutput());
		}
		catch (NullParameterException e)
		{
			e.printStackTrace();
		}
		catch (NegativeOrZeroParameterException e)
		{
			e.printStackTrace();
		} catch (IllegalParameterException e) {
            e.printStackTrace();
        }

    }
}









