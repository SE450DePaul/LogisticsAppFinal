package logistics.reportservice;


/**
 * Created by uchennafokoye on 4/23/16.
 */
public class ReporterFactory {

    public static Reporter build(String type){
        type = type.toLowerCase();
        if (type == "item"){
            return ItemCatalogReporter.getInstance();
        } else if (type == "facility" || type == "facility status"){
            return FacilityStatusReporter.getInstance();
        } else if (type == "shortest path"){
            return TravelGuideReporter.getInstance();
        } else if (type == "everything") {
            return ReportService.getInstance();
        }

        return null;
    }

}
