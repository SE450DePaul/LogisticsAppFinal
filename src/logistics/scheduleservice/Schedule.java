package logistics.scheduleservice;

import logistics.utilities.exceptions.NegativeOrZeroParameterException;

/**
 * This is a Schedule Interface which provides common behaviors 
 * every Facility Schedule should be able to perform.
 * 
 * @author David Olorundare
 */
public interface Schedule
{
    public int bookFacility(int processItemNum, int startDay) throws NegativeOrZeroParameterException;
    public String getScheduleOutput();
    public int getProcessDaysNeeded(int noOfItemsToProcess, int startDay) throws NegativeOrZeroParameterException;
}
