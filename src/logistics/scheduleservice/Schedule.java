package logistics.scheduleservice;

import logistics.utilities.exceptions.NegativeOrZeroParameterException;

/**
 * This is a Schedule Interface which provides common behaviors 
 * every Facility Schedule should be able to perform.
 * 
 * @author David Olorundare
 */
public interface Schedule {

    // Returns the processing end day after booking
    public int bookFacility(int processItemNum, int startDay) throws NegativeOrZeroParameterException;

    // Returns a string with schedule output
    public String getScheduleOutput();

    // returns the processing end day needed without actually booking
    public int getProcessDaysNeeded(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException;
}
