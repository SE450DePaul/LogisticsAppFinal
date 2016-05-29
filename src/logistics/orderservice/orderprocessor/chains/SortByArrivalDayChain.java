package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecordDTO;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Creates a sorted Facility Record Collection
 * Created by uchennafokoye on 5/20/16.
 */
public class SortByArrivalDayChain extends ProcessChain {

    public SortByArrivalDayChain(){
    }

    @Override
    protected Collection<FacilityRecordDTO> buildFacilityRecordDTOs() {
        return sort(facilityRecordDTOs);
    }

    public Collection<FacilityRecordDTO> sort(Collection<FacilityRecordDTO> facilityRecordDTOs) {
        TreeSet<FacilityRecordDTO> facilityRecordDTOsTreeSet = new TreeSet<>(new FacilityRecordComparator());
        facilityRecordDTOsTreeSet.addAll(facilityRecordDTOs);
        return facilityRecordDTOsTreeSet;
    }

    private class FacilityRecordComparator implements Comparator<FacilityRecordDTO>{
        @Override
        public int compare(FacilityRecordDTO o1, FacilityRecordDTO o2) {
                if (o1.arrivalDay > o2.arrivalDay) {
                    return 1;
                } else if (o1.arrivalDay < o2.arrivalDay) {
                    return -1;
                }
                return 1;
        }
    }

}
