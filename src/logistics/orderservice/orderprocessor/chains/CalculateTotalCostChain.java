package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.facilityrecord.FacilityRecord;
import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.utilities.exceptions.IllegalParameterException;

import java.util.Collection;

import static logistics.ApplicationConfig.TRANSPORT_COST;

/**
 * Creates a sorted Facility Record Collection
 * Created by uchennafokoye on 5/20/16.
 */
public class CalculateTotalCostChain extends ProcessChain {

    private OrderItemRequestDTO orderItemRequestDTO;
    private double itemPrice;

    public CalculateTotalCostChain(OrderItemRequestDTO orderItemRequestDTO){
        this.orderItemRequestDTO = orderItemRequestDTO;
        this.itemPrice = itemCatalogService.getItem(orderItemRequestDTO.itemId).price;
    }

    @Override
    protected Collection<FacilityRecord> buildFacilityRecord()
    {
        for (FacilityRecord facilityRec : facilityRecords)
        {
            double totalCost = calculateCost(facilityRec);
            try {
				facilityRec.setTotalCost(totalCost);
			} catch (IllegalParameterException e) {
			
				e.printStackTrace();
			}
        }
        return facilityRecords;
        
    }

    private double calculateCost(FacilityRecord facilityRecord) {
        int noOfItemsUsed = facilityRecord.getNoOfItems();
        double processingDays = noOfItemsUsed / facilityRecord.getRate();
        int travelDays = facilityRecord.getArrivalDay() - facilityRecord.getProcessingEndDay();
        return (itemPrice * noOfItemsUsed) + (processingDays * facilityRecord.getCostPerDay()) + (travelDays * TRANSPORT_COST);

    }

}
