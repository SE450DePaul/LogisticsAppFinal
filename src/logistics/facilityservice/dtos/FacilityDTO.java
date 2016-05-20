package logistics.facilityservice.dtos;

/**
 * This class represents a Facility Data Transfer Object that is 
 * passed, as the return data, to clients who request any of 
 * the Facility Manager's services. 
 * 
 * @author David Olorundare
 */

public class FacilityDTO
{
    public String name;
    public Double cost;
    public Integer rate;

    public FacilityDTO(String facilityName, Double facilityCost, Integer facilityRate)
    {
        name = facilityName;
        cost = facilityCost;
        rate = facilityRate;
    }
}
