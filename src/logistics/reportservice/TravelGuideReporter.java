package logistics.reportservice;

import logistics.Main;
import logistics.reportservice.services.TravelGuideService;
import logistics.utilities.exceptions.FacilityNotFoundInNetworkException;
import logistics.utilities.exceptions.IllegalParameterException;
import logistics.utilities.exceptions.NeighborNotFoundInNetworkException;


public class TravelGuideReporter implements Reporter {


    private volatile static TravelGuideReporter instance;
    TravelGuideService service;
    private TravelGuideReporter() {

    }

    public static TravelGuideReporter getInstance()
    {
        if (instance == null)
        {
            synchronized (TravelGuideReporter.class)
            {
                if (instance == null)
                {
                    instance = new TravelGuideReporter();
                }
            }
        }


        return instance;
    }

    public void printOutput() {
        String[][] shortestPathTests =  Main.SHORTEST_PATH_TEST;

        for (int i = 0; i < shortestPathTests.length; i++){
            String facility = shortestPathTests[i][0];
            String destination = shortestPathTests[i][1];
            try {
                System.out.println(TravelGuideService.printItinerary(facility, destination));
            } catch (FacilityNotFoundInNetworkException e) {
                e.printStackTrace();
            } catch (IllegalParameterException e) {
                e.printStackTrace();
            } catch (NeighborNotFoundInNetworkException e) {
                e.printStackTrace();
            }
        }

    }

}
