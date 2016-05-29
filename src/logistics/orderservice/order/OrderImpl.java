package logistics.orderservice.order;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.dtos.OrderRequestDTO;
import logistics.orderservice.order.orderitem.OrderItem;
import logistics.orderservice.order.orderitem.OrderItemFactory;
import logistics.utilities.exceptions.IllegalParameterException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @authors David Olundare and Uchenna F. Okoye
 */
public class OrderImpl implements Order {

    private String orderId;
    private int orderTime;
    private String orderDestination;
    private Collection<OrderItem> orderItems;

    public OrderImpl(OrderRequestDTO orderRequestDTO) throws IllegalParameterException {
        validateOrderRequestDTO(orderRequestDTO);
        setOrderId(orderRequestDTO.orderId);
        setOrderTime(orderRequestDTO.startTime);
        setOrderDestination(orderRequestDTO.destination);
        setOrderItems(orderRequestDTO.orderItemRequestDTOs);
    }

    /*
     * Creates a new Order Implementation object given an OrderRequestDTO.
     */
    public OrderImpl(Order order) throws IllegalParameterException {
        validateOrder(order);
        setOrderId(order.getOrderId());
        setOrderTime(order.getOrderTime());
        setOrderDestination(order.getDestination());
        setOrderItems(order.getOrderItems());
    }



    /*
	* Helper method that sets the order ID.
	*/
    public void setOrderId(String id) throws NullParameterException, IllegalParameterException {
        validateOrderId(id);
        orderId = id;
    }

    /*
     * Helper method that sets the order time.
     */
    public void setOrderTime(int time) throws NullParameterException, NegativeOrZeroParameterException {
        validateOrderTime(time);
        orderTime = time;
    }

    /*
     * Helper method that sets the order destination.
     */
    public void setOrderDestination(String destination) throws IllegalParameterException {
        validateOrderDestination(destination);
        orderDestination = destination;
    }



    public void addOrderItem(OrderItem orderItem) throws IllegalParameterException{
        if (orderItem == null) { throw new NullParameterException("Order Item cannot be null"); }
        orderItems.add(orderItem);
    }

    /*
     * Returns the Order Time.
     */
    public int getOrderTime() {
        return orderTime;
    }

    /**
     * Returns the Order's ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Returns the Order Destination.
     */
    public String getDestination() {
        return orderDestination;
    }

    /*
     * Returns a list of Order Items
     */
    public Iterator<OrderItem> getOrderItems(){

        ArrayList<OrderItem> orderItemCopy = new ArrayList<>();
        for (OrderItem mOrderItem : orderItems){
            try {
                OrderItem orderItem = OrderItemFactory.build(mOrderItem);
                orderItemCopy.add(orderItem);
            } catch (IllegalParameterException e) {
                e.printStackTrace();
            }
        }

        return orderItemCopy.iterator();

    }

    /*
   * Helper method that sets the order items.
   */
    private void setOrderItems(Iterator<OrderItem> iterator) throws IllegalParameterException {
        orderItems = new ArrayList<>();
        if (!iterator.hasNext()) { throw new IllegalParameterException("No of Order Items cannot be empty");}
        while (iterator.hasNext()){
            orderItems.add(iterator.next());
        }
    }

    private void setOrderItems(Collection<OrderItemRequestDTO> orderItemRequestDTOs) throws IllegalParameterException {
        orderItems = new ArrayList<>();
        if (orderItemRequestDTOs == null || orderItemRequestDTOs.isEmpty()){
            throw new IllegalParameterException("Order Items cannot be null or empty");
        }
        for (OrderItemRequestDTO orderItemRequestDTO : orderItemRequestDTOs){
           orderItems.add(OrderItemFactory.build(orderItemRequestDTO.itemId, orderItemRequestDTO.quantityNeeded));
       }
    }

    private void validateOrder(Order order) throws NullParameterException {
        if (order == null){
            throw new NullParameterException("Order cannot be null");
        }
    }

    private void validateOrderRequestDTO(OrderRequestDTO order) throws NullParameterException {
        if (order == null){
            throw new NullParameterException("Order cannot be null");
        }
    }

    /**
     * Helper method that validates that an order's destination is not null or empty.
     */
    private void validateOrderDestination(String orderDestination) throws NullParameterException, IllegalParameterException {
        if (orderDestination == null)
            throw new NullParameterException("Order Destination cannot be Null");
        if (orderDestination.isEmpty())
            throw new IllegalParameterException("The Order Destination cannot be Empty");
    }

    /**
     * Helper method that validates that an order time is not Empty or Null.
     */
    private void validateOrderTime(int orderTime) throws NegativeOrZeroParameterException {
        if (orderTime <= 0)
            throw new NegativeOrZeroParameterException("Order Time cannot be a Negative value or 0");
    }

    /**
     * Helper method that validates that an order ID is not Null or Empty.
     */
    private void validateOrderId(String orderId) throws IllegalParameterException {
        if (orderId == null)
            throw new NullParameterException("Order ID cannot be Null");
        if (orderId.isEmpty())
            throw new IllegalParameterException("The Order ID cannot be Empty");
    }


}