package logistics.orderservice.facilityrecord;

/**
 * This class represents a Facility Factory, which handles object creation 
 * of new Facility Implementation classes.
 * 
 * @author Uchenna F.okoye
 */

import logistics.utilities.exceptions.IllegalParameterException;

public class FacilityRecordFactory {
    public static FacilityRecord build(FacilityRecordDTO facilityRecordDTO) throws IllegalParameterException  {
        return new FacilityRecordImpl(facilityRecordDTO);
    }
}
