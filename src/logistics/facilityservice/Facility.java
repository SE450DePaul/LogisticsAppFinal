package logistics.facilityservice;

/**
 * This is a Facility Interface which provides common behaviors 
 * every Facility Implementation should be able to perform.
 * 
 * @author David Olorundare
 */

public interface Facility
{
    String getName();
    Integer getRate();
    Double getCost();
    String toString();
}
