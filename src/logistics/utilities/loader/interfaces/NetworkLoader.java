package logistics.utilities.loader.interfaces;

/**
 * This is a Network Loader Interface which provides common behaviors 
 * every NetworkLoader implementation should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.networkservice.interfaces.FacilityVertex;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import java.util.Collection;

public interface NetworkLoader extends Loader<FacilityVertex>
{
    Collection<FacilityVertex> load() throws LoaderFileNotFoundException;
}
