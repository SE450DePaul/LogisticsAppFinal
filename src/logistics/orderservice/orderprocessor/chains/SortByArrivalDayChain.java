package logistics.orderservice.orderprocessor.chains;

import logistics.orderservice.orderprocessor.ProcessChain;
import logistics.orderservice.facilityrecord.FacilityRecord;

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
    protected Collection<FacilityRecord> buildFacilityRecord() {
        return sort(facilityRecords);
    }

    public Collection<FacilityRecord> sort(Collection<FacilityRecord> facilityRecords) {
        TreeSet<FacilityRecord> facilityRecordsTreeSet = new TreeSet<>(new FacilityRecordComparator());
        facilityRecordsTreeSet.addAll(facilityRecords);
        return facilityRecordsTreeSet;
    }

    private class FacilityRecordComparator implements Comparator<FacilityRecord>{
        @Override
        public int compare(FacilityRecord o1, FacilityRecord o2) {
                if (o1.getArrivalDay() > o2.getArrivalDay()) {
                    return 1;
                } else if (o1.getArrivalDay() < o2.getArrivalDay()) {
                    return -1;
                }
                return 1;
        }
    }

}
