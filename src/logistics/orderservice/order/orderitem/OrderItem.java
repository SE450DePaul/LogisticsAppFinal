package logistics.orderservice.order.orderitem;

import logistics.reportservice.Reporter;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public interface OrderItem extends Reporter {
    String getItemId();
    int getQuantity();
}
