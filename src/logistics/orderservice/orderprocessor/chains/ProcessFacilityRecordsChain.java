package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecord;
import logistics.utilities.exceptions.*;

import java.util.ArrayList;
import java.util.Collection;

/** Processes Facility Records
 * Created by uchennafokoye on 5/20/16.
 */
public class ProcessFacilityRecordsChain extends ProcessChain {

    private OrderItemRequestDTO orderItemRequestDTO;

    public ProcessFacilityRecordsChain(OrderItemRequestDTO orderItemRequestDTO){
        this.orderItemRequestDTO = orderItemRequestDTO;
    }

    protected Collection<FacilityRecord> buildFacilityRecord() throws IllegalParameterException, FacilityNotFoundException {
        return processFacilityRecords(facilityRecords);
    }

    private Collection<FacilityRecord> processFacilityRecords(Collection<FacilityRecord> facilityRecordCollection) throws IllegalParameterException, FacilityNotFoundException {
        int requiredQuantity = orderItemRequestDTO.quantityNeeded;
        Collection<FacilityRecord> facilityRecordUsed = new ArrayList<>();
        for (FacilityRecord facilityRecord : facilityRecordCollection){
            if(requiredQuantity <= 0) {
                break;
            }
            int noOfItemsToRetrieve = requiredQuantity;
            int noOfItemsAtFacility = facilityRecord.getNoOfItems();
            if (noOfItemsToRetrieve > noOfItemsAtFacility){
                noOfItemsToRetrieve = noOfItemsAtFacility;
            }
            requiredQuantity -= noOfItemsToRetrieve;
            processFromFacility(facilityRecord, noOfItemsToRetrieve);
            facilityRecordUsed.add(facilityRecord);
        }
        return facilityRecordUsed;
    }

    private void processFromFacility(FacilityRecord facilityRecord, int quantity) throws IllegalParameterException, FacilityNotFoundException {
        String facility = facilityRecord.getSource();
        int startTime = orderItemRequestDTO.startTime;
        int processingEndDay = scheduleService.getProcessDaysNeeded(facility, quantity, startTime);
        inventoryService.reduceFromInventory(facility, orderItemRequestDTO.itemId, quantity);
        scheduleService.bookFacility(facility, quantity, startTime);
        if (processingEndDay != facilityRecord.getProcessingEndDay()) {
            int arrivalDay = calculateArrivalDay(processingEndDay, facilityRecord.getTravelTime());
            facilityRecord.setProcessingEndDay(processingEndDay);
            facilityRecord.setArrivalDay(arrivalDay);
            facilityRecord.setNoOfItems(quantity);
        }
    }


}
