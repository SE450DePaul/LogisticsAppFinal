package logistics.orderservice.order;

import logistics.orderservice.order.orderitem.OrderItem;

import java.util.Iterator;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public interface Order {
    String getOrderId();
    int getOrderTime();
    String getDestination();
    Iterator<OrderItem> getOrderItems();

}
