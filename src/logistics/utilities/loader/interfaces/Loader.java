package logistics.utilities.loader.interfaces;

import logistics.utilities.exceptions.LoaderConfigFilePathException;

import java.util.Collection;

/**
 * Created by uchennafokoye on 4/23/16.
 */
public interface Loader<Type> {
    Collection<Type> load() throws LoaderConfigFilePathException;

}
