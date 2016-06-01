package logistics.utilities.loader.interfaces;

import logistics.orderservice.order.Order;
import logistics.utilities.exceptions.LoaderConfigFilePathException;

import java.util.Collection;

/**
 * Created by uchennafokoye on 4/22/16.
 */
public interface OrderLoader extends Loader<Order> {
    Collection<Order> load() throws LoaderConfigFilePathException;
}
