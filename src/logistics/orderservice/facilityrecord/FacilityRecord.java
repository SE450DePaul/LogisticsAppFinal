package logistics.orderservice.facilityrecord;

import logistics.utilities.exceptions.IllegalParameterException;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public interface FacilityRecord {

    public void setSource(String source) throws IllegalParameterException;
    public void setNoOfItems(int quantity) throws IllegalParameterException;
    public void setProcessingEndDay(int processingEndDay) throws IllegalParameterException;
    public void setTravelTime(int travelTime) throws IllegalParameterException;
    public void setArrivalDay(int arrivalDay) throws IllegalParameterException;
    public void setTotalCost(double cost) throws IllegalParameterException;
    public String getSource();
    public int getNoOfItems();
    public int getProcessingEndDay();
    public int getTravelTime();
    public int getArrivalDay();
    public double getCostPerDay();
    public double getTotalCost();
    public double getRate();
    public double getItemPrice();
    

}
