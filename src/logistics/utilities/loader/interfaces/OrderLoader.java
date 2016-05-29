package logistics.utilities.loader.interfaces;

import logistics.orderservice.order.Order;
import logistics.utilities.exceptions.LoaderFileNotFoundException;

import java.util.Collection;

/**
 * Created by uchennafokoye on 4/22/16.
 */
public interface OrderLoader extends Loader<Order> {
    Collection<Order> load() throws LoaderFileNotFoundException;
}
