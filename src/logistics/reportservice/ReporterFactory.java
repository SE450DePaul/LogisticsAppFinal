package logistics.reportservice;


/**
 * Created by uchennafokoye on 4/23/16.
 */
public class ReporterFactory {

    public static Reporter build(String type){
        type = type.toLowerCase();
        if (type.equals("item")){
            return ItemCatalogReporter.getInstance();
        } else if (type.equals("facility") || type.equals( "facility status")){
            return FacilityStatusReporter.getInstance();
        } else if (type.equals("shortest path")){
            return TravelGuideReporter.getInstance();
        } else if (type.equals("orders")){
            return OrderReporter.getInstance();
        } else if (type.equals("everything")) {
            return ReportService.getInstance();
        }

        return ReportService.getInstance();
    }

}
