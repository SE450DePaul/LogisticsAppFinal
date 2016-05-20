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
    public int quantity;

    public FacilityWithItemDTO(String facilityName, int itemQuantity){
        name = facilityName;
        quantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof FacilityWithItemDTO)) return false;

        FacilityWithItemDTO facilityWithItemDTO = (FacilityWithItemDTO) o;

        if (!name.equals(facilityWithItemDTO.name)) return false;
        if (! (quantity == facilityWithItemDTO.quantity) ) return false;

        return true;
    }

}
