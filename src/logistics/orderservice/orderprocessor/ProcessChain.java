package logistics.orderservice.orderprocessor;

import logistics.facilityservice.FacilityService;
import logistics.itemservice.ItemCatalogService;
import logistics.orderservice.facilityrecord.FacilityRecord;
import logistics.utilities.exceptions.*;

import java.util.Collection;

/**
 * Created by uchennafokoye on 5/20/16.
 */
public abstract class ProcessChain {

    private ProcessChain chain;
    protected Collection<FacilityRecord> facilityRecords;
    protected FacilityService facilityService = FacilityService.getInstance();
    protected ItemCatalogService itemCatalogService = ItemCatalogService.getInstance();

    protected void setNextChain(ProcessChain chain) {
        this.chain = chain;
    }

    public Collection<FacilityRecord> process() throws FacilityNotFoundInNetworkException, IllegalParameterException, NeighborNotFoundInNetworkException, FacilityNotFoundException {
        facilityRecords = buildFacilityRecord();
        if (chain != null){
            chain.setFacilityRecord(facilityRecords);
            facilityRecords = chain.process();
        }
        return facilityRecords;
    }

    protected void setFacilityRecord(Collection<FacilityRecord> facilityRecord) throws NullParameterException {
        validateFacilityRecord(facilityRecord);
        this.facilityRecords = facilityRecord;
    };

    protected void validateFacilityRecord(Collection<FacilityRecord> facilityRecord) throws NullParameterException {
        if (facilityRecord == null){
            throw new NullParameterException("Facility Record  cannot be null unless overridden");
        }
    }

    protected abstract Collection<FacilityRecord> buildFacilityRecord() throws NeighborNotFoundInNetworkException, IllegalParameterException, FacilityNotFoundInNetworkException, FacilityNotFoundException;

    protected int calculateArrivalDay(int processingEndDay, int travelTime){
        return processingEndDay + travelTime;
    }


}
