package logistics.utilities.loader.interfaces;

import logistics.utilities.exceptions.LoaderFileNotFoundException;

import java.util.ArrayList;

/**
 * Created by uchennafokoye on 4/23/16.
 */
public interface Loader<Type> {
    ArrayList<Type> load() throws LoaderFileNotFoundException;

}