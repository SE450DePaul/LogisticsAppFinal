package logistics.facilityservice.dtos;

/**
 * This class represents a Facility With Item Data Transfer Object,
 * which is used by the Inventory Service manager when communicating
 * with other subsystems of the Logistics application.
 *
 * Created by uchennafokoye on 5/13/16.
 */

public class FacilityWithItemDTO {

    public String name;
    public String itemId;
    public int quantity;
    public int rate;
    public double costPerDay;

    public FacilityWithItemDTO(String facilityName, String itemNoId, int itemQuantity, int facilityRate, double facilityCostPerDay){
        name = facilityName;
        itemId = itemNoId;
        quantity = itemQuantity;
        rate = facilityRate;
        costPerDay = facilityCostPerDay;
    }

}
