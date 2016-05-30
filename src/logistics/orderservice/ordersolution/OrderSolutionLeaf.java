package logistics.orderservice.ordersolution;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.facilityrecord.FacilityRecord;

import java.text.NumberFormat;
import java.util.Collection;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public class OrderSolutionLeaf implements OrderSolutionComponent{


    private int totalCost = 0;
    private int noOfSourcesUsed = 0;
    private int firstDeliveryDay = -1;
    private int lastDeliveryDay = -1;
    private Collection<FacilityRecord> facilityRecords;
    private OrderItemRequestDTO orderItemRequestDTO;

    public OrderSolutionLeaf(OrderItemRequestDTO orderItemRequestDTO, Collection<FacilityRecord> facilityRecords){
        //validate facility records
        // validate order item
        this.facilityRecords = facilityRecords;
        this.orderItemRequestDTO = orderItemRequestDTO;

        for (FacilityRecord facilityRecord : facilityRecords){
            totalCost += facilityRecord.getTotalCost();
            noOfSourcesUsed++;
            int arrivalDay = facilityRecord.getArrivalDay();
            if (firstDeliveryDay == -1){
                firstDeliveryDay =  arrivalDay;
                lastDeliveryDay = arrivalDay;
            } else {
                if (firstDeliveryDay > arrivalDay){
                    firstDeliveryDay = arrivalDay;
                }
                if (lastDeliveryDay < arrivalDay){
                    lastDeliveryDay = arrivalDay;
                }
            }

        }

    }


    @Override
    public int getTotalCost(){
        return totalCost;
    }

    @Override
    public int getFirstDeliveryDay() {
        return firstDeliveryDay;
    }

    @Override
    public int getLastDeliveryDay() {
        return lastDeliveryDay;
    }

    @Override
    public int getNoOfSourcesUsed() {
        return noOfSourcesUsed;
    }

    @Override
    public void printOutput() {
        System.out.format("\t%-10s%-10d%-10s%-16s%-16s%-16s%n", orderItemRequestDTO.itemId, orderItemRequestDTO.quantityNeeded, currencyFormater(), getNoOfSourcesUsed(), getFirstDeliveryDay(), getLastDeliveryDay());
    }
    
    private String currencyFormater() {
        return NumberFormat.getCurrencyInstance().format(getTotalCost()).replaceAll("\\.00", "");
    }

    
}
