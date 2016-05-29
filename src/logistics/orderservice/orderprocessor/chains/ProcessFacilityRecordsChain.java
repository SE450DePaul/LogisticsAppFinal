package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecordDTO;
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

    protected Collection<FacilityRecordDTO> buildFacilityRecordDTOs() throws IllegalParameterException, FacilityNotFoundException {
        return processFacilityRecords(facilityRecordDTOs);
    }

    private Collection<FacilityRecordDTO> processFacilityRecords(Collection<FacilityRecordDTO> facilityRecordDTOCollection) throws IllegalParameterException, FacilityNotFoundException {
        int requiredQuantity = orderItemRequestDTO.quantityNeeded;
        Collection<FacilityRecordDTO> facilityRecordDTOsUsed = new ArrayList<>();
        for (FacilityRecordDTO facilityRecordDTO : facilityRecordDTOCollection){
            if(requiredQuantity <= 0) {
                break;
            }
            int noOfItemsToRetrieve = requiredQuantity;
            int noOfItemsAtFacility = facilityRecordDTO.noOfItems;
            if (noOfItemsToRetrieve > noOfItemsAtFacility){
                noOfItemsToRetrieve = noOfItemsAtFacility;
            }
            requiredQuantity -= noOfItemsToRetrieve;
            processFromFacility(facilityRecordDTO, noOfItemsToRetrieve);
            facilityRecordDTOsUsed.add(facilityRecordDTO);
        }

        return facilityRecordDTOsUsed;
    }

    private void processFromFacility(FacilityRecordDTO facilityRecordDTO, int quantity) throws IllegalParameterException, FacilityNotFoundException {
        String facility = facilityRecordDTO.source;
        int startTime = orderItemRequestDTO.startTime;
        int processingEndDay = scheduleService.getProcessDaysNeeded(facility, quantity, startTime);
        inventoryService.reduceFromInventory(facility, orderItemRequestDTO.itemId, quantity);
        scheduleService.bookFacility(facility, quantity, startTime);
        if (processingEndDay != facilityRecordDTO.processingEndDay) {
            int arrivalDay = calculateArrivalDay(processingEndDay, facilityRecordDTO.travelTime);
            facilityRecordDTO.processingEndDay = processingEndDay;
            facilityRecordDTO.arrivalDay = arrivalDay;
            facilityRecordDTO.noOfItems = quantity;
        }
    }


}
