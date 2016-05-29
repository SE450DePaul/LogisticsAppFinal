package logistics.orderservice.order;

/**
 * This class represents a Facility Factory, which handles object creation 
 * of new Facility Implementation classes.
 * 
 * @author Uchenna F.okoye
 */

import logistics.orderservice.dtos.OrderRequestDTO;
import logistics.utilities.exceptions.IllegalParameterException;

public class OrderFactory {
    public static Order build(OrderRequestDTO orderRequestDTO) throws IllegalParameterException {
        return new OrderImpl(orderRequestDTO);
    }

    public static Order build(Order order) throws IllegalParameterException {
        return new OrderImpl(order);
    }

}
