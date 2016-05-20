package logistics.facilityservice;

import logistics.inventoryservice.Inventory;
import logistics.scheduleservice.Schedule;

/**
 * This class represents a Facility Factory, which handles object creation 
 * of new Facility Implementation classes.
 * 
 * @author David Olorundare
 * @author Uchenna F. Okoye
 */

public interface Facility extends Schedule, Inventory {
    String getName();
    Integer getRate();
    Double getCost();
    String getFacilityOutput();

}
