package logistics.orderservice;

/**
 * This class represents a Facility Manager that keeps track of all Facilities.
 * It provides methods for creating a Facility (using a Facility Factory), returning
 * a Facility's information to a requesting client, as well as display the 
 * list of all available Facilities.
 * 
 * @author Uchenna F. Okoye
 */

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.dtos.OrderRequestDTO;
import logistics.orderservice.order.Order;
import logistics.orderservice.order.orderitem.OrderItem;
import logistics.orderservice.ordersolution.OrderSolutionComponent;
import logistics.utilities.exceptions.*;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;

import java.util.*;

public final class OrderService {
    private volatile static OrderService instance;
    private Loader<Order> loader;
    private Collection<Order> orders;
    private HashMap<String, Order> orderHashMap;
    private HashMap<String, OrderSolutionComponent> orderSolutionComponentHashMap;

    private OrderService() {
        loader = LoaderFactory.build("orders");
        orderSolutionComponentHashMap = new LinkedHashMap<>();
        orderHashMap = new LinkedHashMap<>();
        try {
            Collection<Order> orders = loader.load();
            for (Order order: orders){
                Collection<OrderItemRequestDTO> orderItemRequestDTOs = new ArrayList<>();
                Iterator<OrderItem> iterator = order.getOrderItems();
                while (iterator.hasNext()){
                    OrderItem orderItem = iterator.next();
                    OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO(order.getDestination(), orderItem.getItemId(), order.getOrderTime(), orderItem.getQuantity());
                    orderItemRequestDTOs.add(orderItemRequestDTO);
                }
                OrderRequestDTO orderRequestDTO = new OrderRequestDTO(order.getOrderId(), order.getDestination(), order.getOrderTime(), orderItemRequestDTOs);
                OrderSolutionComponent orderSolutionComponent = OrderProcessor.process(orderRequestDTO);
                orderHashMap.put(orderRequestDTO.orderId, order);
                orderSolutionComponentHashMap.put(orderRequestDTO.orderId, orderSolutionComponent);
            }

        } catch (LoaderFileNotFoundException e) {
            e.printStackTrace();
        } catch (NeighborNotFoundInNetworkException e) {
            e.printStackTrace();
        } catch (IllegalParameterException e) {
            e.printStackTrace();
        } catch (FacilityNotFoundException e) {
            e.printStackTrace();
        } catch (FacilityNotFoundInNetworkException e) {
            e.printStackTrace();
        }


    }


    public void printOutput(){
        Collection<String> keySets = orderSolutionComponentHashMap.keySet();

        int i = 1;
        for (String key : keySets){
            generateDashedLine(100);
            System.out.println("Order #" + i);
            OrderSolutionComponent orderSolutionComponent = orderSolutionComponentHashMap.get(key);
        	Order order = orderHashMap.get(key); 
        	order.printOutput();
            orderSolutionComponent.printOutput();
            if (keySets.size() == i){
                generateDashedLine(100);
            }
            i++;
        }
    }

    
    /*
     * Returns an instance of the Facility Service.
     */
    public static OrderService getInstance() {
        if (instance == null)
        {
            synchronized (OrderService.class)
            {
                if (instance == null)
                {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    private void generateDashedLine(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++){
            str.append("-");
        }
        System.out.println(str);
    }


    public static void main(String[] args){

        OrderService orderService = OrderService.getInstance();

        orderService.printOutput();


    }
}
