package logistics.reportservice;

import logistics.orderservice.OrderService;


public class OrderReporter implements Reporter {


    private volatile static OrderReporter instance;
    private OrderService orderService;

    private OrderReporter() {

        orderService = OrderService.getInstance();
    }

    public static OrderReporter getInstance()
    {
        if (instance == null)
        {
            synchronized (OrderReporter.class)
            {
                if (instance == null)
                {
                    instance = new OrderReporter();
                }
            }
        }
        return instance;
    }


    public void printOutput() {
        orderService.printOutput();
    }




}
