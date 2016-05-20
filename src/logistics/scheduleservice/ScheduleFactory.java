package logistics.scheduleservice;

/**
 * This class represents a Schedule Factory, which handles object creation 
 * of new Schedule Implementation classes.
 * 
 * @author Uchenna F.Okoye
 */

import logistics.facilityservice.dtos.FacilityDTO;
import logistics.utilities.exceptions.IllegalParameterException;

public class ScheduleFactory
{
	/*
	 * Returns a newly created Facility Schedule implementation.
	 */
    public static Schedule build(FacilityDTO facility) throws IllegalParameterException {
        return new ScheduleImpl(facility);
    }

    public static Schedule build(String facilityName, int rate) throws IllegalParameterException {
        return new ScheduleImpl(facilityName, rate);
    }


}
