package logistics.reportservice.services;

import logistics.facilityservice.FacilityService;
import logistics.networkservice.NetworkService;
import logistics.utilities.exceptions.FacilityNotFoundException;
import logistics.utilities.exceptions.FacilityNotFoundInNetworkException;
import logistics.utilities.exceptions.IllegalParameterException;


public final class FacilityStatusService {


    private volatile static FacilityStatusService instance;
    NetworkService networkService;
    FacilityService facilityService;

    private FacilityStatusService() {

        networkService = NetworkService.getInstance();
        facilityService = FacilityService.getInstance();

    }

    public static FacilityStatusService getInstance()
    {
        if (instance == null)
        {
            synchronized (FacilityStatusService.class)
            {
                if (instance == null)
                {
                    instance = new FacilityStatusService();
                }
            }
        }
        return instance;
    }


    public String getOutput(String facilityName) throws FacilityNotFoundInNetworkException, IllegalParameterException, FacilityNotFoundException {

        StringBuffer str = new StringBuffer();
        str.append("\n");
        str.append(facilityService.getOutput(facilityName));
        str.append("\n");
        str.append(networkService.getDirectLinksOutput(facilityName));
        str.append("\n");
        str.append(facilityService.getInventoryOutput(facilityName));
        str.append("\n");
        str.append(facilityService.getScheduleOutput(facilityName));

        str.append("\n");
        str.append(generateDashedLine(100));

        return str.toString();

    }

    private String generateDashedLine(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++){
            str.append("-");
        }
        return str.toString();
    }


}
