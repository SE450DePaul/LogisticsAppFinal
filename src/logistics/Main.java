package logistics;

/**
 * This is the Main class of the Logistics Application.
 * Running the code in this class initiates all the application's services
 * and display a printout of each Facility and its corresponding Inventory
 * and Schedule details, as well as the Item Catalog.
 * Output from the Shortest Path tests is also displayed.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.reportservice.Reporter;
import logistics.reportservice.ReporterFactory;


public class Main {

    public static String[][] SHORTEST_PATH_TEST = {{"Santa Fe, NM", "Chicago, IL"},
        {"Atlanta, GA", "St. Louis, MO"},
            {"Seattle, WA", "Nashville, TN"},
                {"New York City, NY", "Phoenix, AZ"},
                    {"Fargo, ND", "Austin, TX"},
                        {"Denver, CO", "Miami, FL"},
                            {"Austin, TX", "Norfolk, VA"},
                                {"Miami, FL", "Seattle, WA"},
                                    {"Los Angeles, CA", "Chicago, IL"},
                                        {"Detroit, MI", "Nashville, TN"}};


    public static void main(String args[]){
        Reporter reporter = ReporterFactory.build("everything");
        reporter.printOutput();
    }


}

