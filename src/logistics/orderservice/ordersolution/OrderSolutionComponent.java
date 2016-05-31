package logistics.orderservice.ordersolution;

/**
 * Created by uchennafokoye on 5/21/16.
 */
public interface OrderSolutionComponent {
    int getTotalCost();
    int getFirstDeliveryDay();
    int getLastDeliveryDay();
    int getNoOfSourcesUsed();
    void printOutput();
    void printBackOrdered();
    int getNoOfBackloggedItems();
}
