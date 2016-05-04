package logistics.networkservice.implementation;

import logistics.networkservice.interfaces.FacilityVertex;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.NeighborNotFoundInNetworkException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by uchennafokoye on 4/29/16.
 */
public class FacilityVertexImpl implements FacilityVertex {

    private String facility;
    private HashMap<String, Integer> weights;

    public FacilityVertexImpl(String fac) throws NullParameterException {
        setFacility(fac);
        weights = new HashMap<>();
    }

    @Override
    public void addNeighbor(String facility, int distance) throws NullParameterException, NegativeOrZeroParameterException {
        validateFacility(facility);
        validateDistance(distance);
        weights.put(facility, distance);
    }

    @Override
    public int distanceTo(String facility) throws NeighborNotFoundInNetworkException {
        Integer distance = weights.get(facility);
        if (distance == null){
            throw new NeighborNotFoundInNetworkException("No distance found for neighbor");
        }
        return distance;
    }

    @Override
    public Iterator<String> neighbors() {
        return new TreeSet<String>(weights.keySet()).iterator();
    }

    @Override
    public String getFacilityName() {
        return facility;
    }



    private void setFacility(String fac) throws NullParameterException {
        validateFacility(fac);
        facility = fac;
    }


    private void validateFacility(String fac) throws NullParameterException {
        if (fac == null){
            throw new NullParameterException("Facility cannot be null");
        }
    }

    private void validateDistance(int distance) throws NegativeOrZeroParameterException {
        if (distance < 0){
            throw new NegativeOrZeroParameterException("Distance cannot be negative");
        }
    }
}
