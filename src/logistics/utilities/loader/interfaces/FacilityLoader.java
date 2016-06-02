package logistics.utilities.loader.interfaces;

/**
 * This is a Facility Loader Interface which provides common behaviors 
 * every FacilityLoader implementation should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.facilityservice.Facility;
import java.util.Collection;

public interface FacilityLoader extends Loader<Facility>
{
    Collection<Facility> load() throws LoaderFileNotFoundException;
}
