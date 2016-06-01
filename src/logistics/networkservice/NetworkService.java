package logistics.networkservice;

import logistics.networkservice.factory.NetworkGraphFactory;
import logistics.networkservice.interfaces.FacilityVertex;
import logistics.networkservice.interfaces.NetworkGraph;
import logistics.networkservice.travelguide.TravelGuide;
import logistics.networkservice.travelguide.TravelGuideDTO;
import logistics.networkservice.travelguide.TravelGuideFactory;
import logistics.utilities.exceptions.*;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This class represents the Network Service manager and provides
 * methods determining the location details between Facilities.
 * 
 * @author Uchenna F. Okoye
 */
public final class NetworkService {

    private volatile static NetworkService instance;
    private HashSet<String> facilities;
    private Loader loader;
    private TravelGuide travelGuide;


    private NetworkGraph networkGraph;

    private NetworkService() {
            loader = LoaderFactory.build("network");
            facilities = new HashSet<>();
            networkGraph = NetworkGraphFactory.build();
            buildGraph();
            buildTravelGuide();
    }

    public static NetworkService getInstance() {
        if (instance == null){
            synchronized (NetworkService.class){
                if (instance == null){
                    instance = new NetworkService();
                }
            }
        }

        return instance;
    }

    public String getDirectLinksOutput(String facility) throws FacilityNotFoundInNetworkException{
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = networkGraph.neighbors(facility);
        stringBuffer.append("\n");
        stringBuffer.append("Direct Links: ");
        stringBuffer.append("\n");
        while (iterator.hasNext()) {

            String neighbor = iterator.next();
            stringBuffer.append(neighbor);

            try {
                TravelGuideDTO travelItinerary = getTravelGuideDTO(facility, neighbor);
                stringBuffer.append(" (" + String.format("%1.1f", travelItinerary.timeInDays ) + "d)");
                stringBuffer.append("; ");
            } catch (NullParameterException e) {
                e.printStackTrace();
            } catch (NeighborNotFoundInNetworkException e) {
                e.printStackTrace();
            }

        }

        return stringBuffer.toString();
    }

    /*
     * Helper method that provides Travel Guide DTO. This DTO provides the path, distance as well as the travel
     */
    public TravelGuideDTO getTravelGuideDTO(String facility, String destination) throws NullParameterException, FacilityNotFoundInNetworkException, NeighborNotFoundInNetworkException {

        return travelGuide.getTravelGuideDTO(facility, destination);

    }

    /*
     * Helper method that builds a network graph of Facilities and their neighbors.
     */
    private void buildGraph() {
        try {
            Collection<FacilityVertex> facilityVertices = loader.load();
            for (FacilityVertex facilityVertex : facilityVertices){
                String facilityName = facilityVertex.getFacilityName();
                networkGraph.addFacility(facilityName);
                facilities.add(facilityName);

                Iterator<String> iterator = facilityVertex.neighbors();
                while (iterator.hasNext()){
                    String neighbor = iterator.next();
                    int distance = facilityVertex.distanceTo(neighbor);
                    networkGraph.addNeighbor(facilityName, neighbor, distance);
                }

            }
        } catch (LoaderConfigFilePathException e) {
            e.printStackTrace();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (FacilityNotFoundInNetworkException e) {
            e.printStackTrace();
        } catch (SelfLoopNetworkException e) {
            e.printStackTrace();
        } catch (NeighborNotFoundInNetworkException e) {
            e.printStackTrace();
        } catch (NegativeOrZeroParameterException e) {
            e.printStackTrace();
        }
    }

    /*
     * Helper method that builds a network travel guide.
     */
    private void buildTravelGuide() {
        try {
            travelGuide = TravelGuideFactory.build(networkGraph);
        } catch (NullParameterException e) {
            e.printStackTrace();
        }
    }
}
