package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecordDTO;

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
    protected Collection<FacilityRecordDTO> buildFacilityRecordDTOs(){
        for (FacilityRecordDTO facilityRecordDTO : facilityRecordDTOs){
            double totalCost = calculateCost(facilityRecordDTO);
            facilityRecordDTO.totalCost = totalCost;
        }
        return facilityRecordDTOs;
    }

    private double calculateCost(FacilityRecordDTO facilityRecordDTO) {
        int noOfItemsUsed = facilityRecordDTO.noOfItems;
        int processingDays = (int) Math.ceil(noOfItemsUsed / facilityRecordDTO.rate);
        int travelDays = facilityRecordDTO.arrivalDay - facilityRecordDTO.processingEndDay;
        return (itemPrice * noOfItemsUsed) + (processingDays * facilityRecordDTO.costPerDay) + (travelDays * TRANSPORT_COST);

    }

}
