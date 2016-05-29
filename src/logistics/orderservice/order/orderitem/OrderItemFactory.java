package logistics.orderservice.order.orderitem;

/**
 * This class represents a Facility Factory, which handles object creation 
 * of new Facility Implementation classes.
 * 
 * @author Uchenna F.okoye
 */

import logistics.utilities.exceptions.IllegalParameterException;

public class OrderItemFactory {
    public static OrderItemImpl build(String itemId, int quantity) throws IllegalParameterException {
        return new OrderItemImpl(itemId, quantity);
    }

    public static OrderItemImpl build(OrderItem orderItem) throws IllegalParameterException {
        return new OrderItemImpl(orderItem);
    }
}
