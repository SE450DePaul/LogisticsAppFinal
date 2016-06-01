package logistics.reportservice;

import logistics.facilityservice.FacilityService;
import logistics.networkservice.NetworkService;
import logistics.reportservice.services.FacilityStatusService;
import logistics.utilities.exceptions.FacilityNotFoundException;
import logistics.utilities.exceptions.FacilityNotFoundInNetworkException;
import logistics.utilities.exceptions.IllegalParameterException;

import java.util.Set;


public final class FacilityStatusReporter implements Reporter {


    private volatile static FacilityStatusReporter instance;
    NetworkService networkService;
    FacilityService facilityService;
    FacilityStatusService facilityStatusService;

    private FacilityStatusReporter() {

        networkService = NetworkService.getInstance();
        facilityService = FacilityService.getInstance();
        facilityStatusService = facilityStatusService.getInstance();
    }

    public static FacilityStatusReporter getInstance()
    {
        if (instance == null)
        {
            synchronized (FacilityStatusReporter.class)
            {
                if (instance == null)
                {
                    instance = new FacilityStatusReporter();
                }
            }
        }
        return instance;
    }


    public void printOutput() {
        StringBuffer str = new StringBuffer();
        Set<String> facilityNames = facilityService.getFacilityNames();


        for (String facilityName : facilityNames){
            try {
                str.append(facilityStatusService.getOutput(facilityName));
            } catch (FacilityNotFoundInNetworkException e) {
                e.printStackTrace();
            } catch (IllegalParameterException e) {
                e.printStackTrace();
            } catch (FacilityNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println(str);
    }




}
