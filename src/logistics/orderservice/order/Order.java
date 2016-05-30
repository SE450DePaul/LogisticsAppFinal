package logistics.orderservice.order;

import logistics.orderservice.order.orderitem.OrderItem;
import logistics.reportservice.Reporter;

import java.util.Iterator;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public interface Order extends Reporter {
    String getOrderId();
    int getOrderTime();
    String getDestination();
    Iterator<OrderItem> getOrderItems();

}
