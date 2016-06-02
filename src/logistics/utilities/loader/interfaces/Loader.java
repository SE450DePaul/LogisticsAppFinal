package logistics.utilities.loader.interfaces;

/**
 * This is a Loader Interface which provides common behaviors 
 * every Loader implementation should be able to perform.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.utilities.exceptions.LoaderFileNotFoundException;
import java.util.Collection;

public interface Loader<Type> {
    Collection<Type> load() throws LoaderFileNotFoundException;
}
