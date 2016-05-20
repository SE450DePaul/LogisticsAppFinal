package logistics.utilities.loader.interfaces;

import logistics.utilities.exceptions.LoaderConfigFilePathException;
import logistics.facilityservice.Facility;

import java.util.Collection;

/**
 * Created by uchennafokoye on 4/26/16.
 */
public interface FacilityLoader extends Loader<Facility>
{
    Collection<Facility> load() throws LoaderConfigFilePathException;
}
