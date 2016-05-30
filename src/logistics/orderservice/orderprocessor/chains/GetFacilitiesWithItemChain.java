package logistics.orderservice.orderprocessor.chains;

import logistics.facilityservice.FacilityDTO;
import logistics.inventoryservice.dtos.FacilityWithItemDTO;
import logistics.networkservice.NetworkService;
import logistics.networkservice.travelguide.TravelGuideDTO;
import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecord;
import logistics.orderservice.facilityrecord.FacilityRecordDTO;
import logistics.orderservice.facilityrecord.FacilityRecordFactory;
import logistics.utilities.exceptions.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Creates a sorted Facility Record Collection
 * Created by uchennafokoye on 5/20/16.
 */
public class GetFacilitiesWithItemChain extends ProcessChain {

    private OrderItemRequestDTO orderItemRequestDTO;

    public GetFacilitiesWithItemChain(OrderItemRequestDTO orderItemRequestDTO){
        this.orderItemRequestDTO = orderItemRequestDTO;
    }

    @Override
    protected void validateFacilityRecord(Collection<FacilityRecord> facilityRecords){
        return;
    }

    @Override
    public Collection<FacilityRecord> buildFacilityRecord() throws NeighborNotFoundInNetworkException, IllegalParameterException, FacilityNotFoundInNetworkException, FacilityNotFoundException {
        return getFacilityRecords();
    }

    public Collection<FacilityRecord> getFacilityRecords() throws NeighborNotFoundInNetworkException, IllegalParameterException, FacilityNotFoundInNetworkException {
        Collection<FacilityWithItemDTO> facilitiesWithItemDTO = inventoryService.getFacilitiesWithItemDTO(orderItemRequestDTO.itemId);
        Collection<FacilityRecord> facilityRecords = new ArrayList<>();
        for (FacilityWithItemDTO facilityWithItemDTO : facilitiesWithItemDTO) {
            if (!facilityWithItemDTO.name.equals(orderItemRequestDTO.destination)){
                facilityRecords.add(buildFacilityRecord(facilityWithItemDTO));
            }
        }

        return facilityRecords;
    }

    private FacilityRecord buildFacilityRecord(FacilityWithItemDTO facility) throws NeighborNotFoundInNetworkException, IllegalParameterException, FacilityNotFoundInNetworkException {
        String source = facility.name;
        int noOfItems = facility.quantity;
        int travelTime = getTravelTime(source, orderItemRequestDTO.destination);
        int processingEndDay = getProcessDaysNeeded(source, noOfItems, orderItemRequestDTO.startTime);
        int arrivalDay = calculateArrivalDay(processingEndDay, travelTime);
        double itemPrice = itemCatalogService.getItem(orderItemRequestDTO.itemId).price;
        FacilityDTO facilityDTO = facilityService.getFacility(source);
        double costPerDay = facilityDTO.cost;
        int rate = facilityDTO.rate;
        FacilityRecordDTO facilityRecordDTO = new FacilityRecordDTO(source, noOfItems, itemPrice, processingEndDay, travelTime, arrivalDay, costPerDay, rate); 
        FacilityRecord facilityRecord = FacilityRecordFactory.build(facilityRecordDTO);

        return facilityRecord;
    }


    private int getTravelTime(String source, String destination) throws FacilityNotFoundInNetworkException, NullParameterException, NeighborNotFoundInNetworkException {
        TravelGuideDTO travelGuideDTO = NetworkService.getInstance().getTravelGuideDTO(source, destination);
        int travelTime = (int) Math.ceil(travelGuideDTO.timeInDays);
        return travelTime;
    }
    private int getProcessDaysNeeded(String source, int noOfItems, int startDay) throws IllegalParameterException {
        return scheduleService.getProcessDaysNeeded(source, noOfItems, startDay);
    }

}
