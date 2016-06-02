package logistics.utilities.loader.implementation;

/**
* This class represents the implementation of an Order XML Loader
* which loads in XML data, containing details of various Orders, into
* the Logistics application.
*
* @author David Olorundare
*
*/

import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;
import logistics.utilities.loader.interfaces.OrderLoader;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.itemservice.Item;
import logistics.itemservice.ItemFactory;
import logistics.orderservice.Order;
import logistics.orderservice.OrderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderXmlLoaderImpl implements OrderLoader
{
	private String orderStartDay;
	private String orderId;
	private String orderDestination;
	private String itemId;
	private Double itemQty;
	private String itemQuantity;
	private ArrayList<Item> orderItems;
	private String filepath;

    /*
	 * Takes as input the filesystem path to the XML data. 
	 */
    public OrderXmlLoaderImpl(String orderFilepath)
    {
        filepath = orderFilepath;
    }
	
    /*
 	 * Returns a list of Items loaded from the XML data.
 	 */
    public ArrayList<Order> load() throws LoaderFileNotFoundException 
    {
    	ArrayList<Order> orderList = new ArrayList<Order>();

        try 
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File xml = new File(filepath);
            if (!xml.exists()) 
            {
                throw new LoaderFileNotFoundException();
            }

            Document doc = db.parse(xml);
            Element documentElement = doc.getDocumentElement();
            documentElement.normalize();
            
            NodeList orderEntries = documentElement.getChildNodes();
            for (int i = 0; i < orderEntries.getLength(); i++) 
            {
                Node node = orderEntries.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) 
                {
                    continue;
                }

                String entryName = node.getNodeName();
                if (!entryName.equals("order")) 
                {
                    continue;
                }
                
                NamedNodeMap attributes = node.getAttributes();
                Node orderName = attributes.getNamedItem("id");
                orderId = orderName.getNodeValue();
                
                Element element = (Element) orderEntries.item(i);
                NodeList timeNode = element.getElementsByTagName("time");
                orderStartDay = timeNode.item(0).getTextContent();
                
                NodeList destinationNode = element.getElementsByTagName("destination");
                orderDestination = destinationNode.item(0).getTextContent();
                
                orderItems = new ArrayList<Item>();
                NodeList itemList = element.getElementsByTagName("item");
                
				for (int j = 0; j < itemList.getLength(); j++)
				{
					if (itemList.item(j).getNodeType() == Node.TEXT_NODE)
					{
						continue;
					}

					entryName = itemList.item(j).getNodeName();
					if (!entryName.equals("item"))
					{
						System.err.println("Unexpected node found: " + entryName);

					}

					element = (Element) itemList.item(j);
					itemId = element.getElementsByTagName("id").item(0).getTextContent();
					itemQuantity = element.getElementsByTagName("quantity").item(0).getTextContent();
					itemQty = Double.parseDouble(itemQuantity);
					
					Item item = ItemFactory.build(itemId, itemQty);
					orderItems.add(item);
				}
				
				Order orders = OrderFactory.build(orderId, orderDestination, orderStartDay, orderItems);
				
				// Must be uncommented if this class is to be run from its main() method.
				//System.out.println("Order Id: " + orderId + "  Destination: " + orderDestination + "  Start Day: " + orderStartDay);
				
				orderList.add(orders);	
            }
        }
        catch (ParserConfigurationException e) 
        {
            e.printStackTrace();
        } 
        catch (SAXException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        catch (NullParameterException e) 
        {
            e.printStackTrace();
        }

        return orderList;
    }
    
    // Test that the class works.
    public static void main(String[] args)
    {
    	Loader xmlLoader = LoaderFactory.build("order"); 
        try 
        {
            xmlLoader.load();
        } 
        catch (LoaderFileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
