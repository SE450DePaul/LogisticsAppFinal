package logistics.orderservice.ordersolution;

import logistics.orderservice.dtos.OrderRequestDTO;

import java.text.NumberFormat;
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
    public int getNoOfBackloggedItems() {
        int noOfBackLoggedItems = 0;
        for (OrderSolutionLeaf orderSolution : orderSolutions) {
            noOfBackLoggedItems += orderSolution.getNoOfBackloggedItems();
        }
        return noOfBackLoggedItems;
    }

    @Override
    public void printOutput() {
        System.out.println("\nProcessing Solution:");
        System.out.format("\t%-20s%-16s%n", "Total Cost:", currencyFormater());
        System.out.format("\t%-20s%-16s%n", "1st Delivery Day:", getFirstDeliveryDay());
        System.out.format("\t%-20s%-16s%n%n", "Last Delivery Day:", getLastDeliveryDay());
        System.out.format("\t%-20s%n", "Order Items:");
        System.out.format("\t%-13s%-13s%-13s%-16s%-13s%-13s%-13s%n", "Item ID", "Quantity", "Cost", "Sources Used", "First Day", "Last Day", "BackLog");
        for (OrderSolutionLeaf orderSolution : orderSolutions){
            orderSolution.printOutput();
        }
        System.out.println("\n");
    }



    private String currencyFormater() {
        return NumberFormat.getCurrencyInstance().format(getTotalCost()).replaceAll("\\.00", "");
    }




}
