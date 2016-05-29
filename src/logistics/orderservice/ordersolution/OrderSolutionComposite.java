package logistics.orderservice.ordersolution;

import logistics.orderservice.dtos.OrderItemRequestDTO;
import logistics.orderservice.dtos.OrderRequestDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public class OrderSolutionComposite implements OrderSolutionComponent{


    private Collection<OrderSolutionLeaf> orderSolutions;
    private OrderRequestDTO mOrderRequestDTO;

    public OrderSolutionComposite(OrderRequestDTO orderRequestDTO){
        mOrderRequestDTO = orderRequestDTO;
        orderSolutions = new ArrayList<>();
    }

    public void addSolution(OrderSolutionLeaf orderSolution){
        orderSolutions.add(orderSolution);
    }


    @Override
    public int getTotalCost() {
        int totalCost = 0;
        for (OrderSolutionLeaf orderSolution : orderSolutions) {
            totalCost += orderSolution.getTotalCost();
        }
        return totalCost;
    }

    @Override
    public int getFirstDeliveryDay() {
        int firstDeliveryDay = Integer.MAX_VALUE;
        for (OrderSolutionLeaf orderSolution : orderSolutions){
            if (firstDeliveryDay > orderSolution.getFirstDeliveryDay()){
                firstDeliveryDay = orderSolution.getFirstDeliveryDay();
            }
        }
        return firstDeliveryDay;
    }

    @Override
    public int getLastDeliveryDay() {
        int lastDeliveryDay = Integer.MIN_VALUE;
        for (OrderSolutionLeaf orderSolution : orderSolutions){
            if (lastDeliveryDay < orderSolution.getLastDeliveryDay()){
                lastDeliveryDay = orderSolution.getLastDeliveryDay();
            }
        }
        return lastDeliveryDay;
    }

    @Override
    public int getNoOfSourcesUsed() {
        int noOfSourcesUsed = 0;
        for (OrderSolutionLeaf orderSolution : orderSolutions) {
            noOfSourcesUsed += orderSolution.getNoOfSourcesUsed();
        }
        return noOfSourcesUsed;
    }

    @Override
    public void printOutput() {
        System.out.format("\t%-16s%-16s%n", "Order Id:", mOrderRequestDTO.orderId);
        System.out.format("\t%-16s%-16s%n", "Order Time:", "Day " + mOrderRequestDTO.startTime);
        System.out.format("\t%-16s%-16s%n", "Destination:", mOrderRequestDTO.destination);
        System.out.format("\t%-16s%n", "List of Order Items:");
        int i = 1;
        for (OrderItemRequestDTO orderItemRequestDTO : mOrderRequestDTO.orderItemRequestDTOs){
            System.out.format("\t\t%-16s%-10s%-10s%n", i + ") Item ID", orderItemRequestDTO.itemId + ",", "Quantity: " + orderItemRequestDTO.quantityNeeded);
            i++;
        }


        System.out.println("\nProcessing Solution:");
        System.out.format("\t%-20s%-16s%n", "Total Cost:", getTotalCost());
        System.out.format("\t%-20s%-16s%n", "1st Delivery Day:", getFirstDeliveryDay());
        System.out.format("\t%-20s%-16s%n%n", "Last Delivery Day:", getLastDeliveryDay());
        System.out.format("\t%-20s%n", "Order Items:");
        System.out.format("\t%-10s%-10s%-10s%-16s%-16s%-16s%n", "Item ID", "Quantity", "Cost", "Sources Used", "First Day", "Last Day");
        for (OrderSolutionLeaf orderSolution : orderSolutions){
            orderSolution.printOutput();
        }
    }




}
