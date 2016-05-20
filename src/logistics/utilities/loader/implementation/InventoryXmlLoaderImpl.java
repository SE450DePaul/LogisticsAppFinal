package logistics.utilities.loader.implementation;

import logistics.inventoryservice.Inventory;
import logistics.inventoryservice.InventoryFactory;
import logistics.inventoryservice.inventoryitem.InventoryItemDTO;
import logistics.utilities.exceptions.LoaderConfigFilePathException;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.loader.interfaces.InventoryLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author David Olorundare
 *
 */

public class InventoryXmlLoaderImpl implements InventoryLoader
{

	private String name;
	private String itemId;
	private int itemQty;
	private String filepath;
	private String itemQuantity;

	public InventoryXmlLoaderImpl(String path)
	{
		filepath = path;
	}

	public ArrayList<Inventory> load() throws LoaderConfigFilePathException
	{


		ArrayList<Inventory> inventories = new ArrayList<>();

		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(filepath);
			if (!xml.exists())
			{
				System.out.println("File does not exist"); /*throw new LoaderFileNotFoundException();*/
			}

			Document doc = db.parse(xml);
			Element documentElement = doc.getDocumentElement();
			documentElement.normalize();

			NodeList facilityInvEntries = documentElement.getChildNodes();
			for (int i = 0; i < facilityInvEntries.getLength(); i++)
			{
				Node node = facilityInvEntries.item(i);
				if (node.getNodeType() == Node.TEXT_NODE)
				{
					continue;
				}

				String entryName = node.getNodeName();
				if (!entryName.equals("facility"))
				{
					continue;
					//Or perhaps throw an error
				}
	             /*
	               NamedNodeMap attributes = node.getAttributes();
	              Node namedItem = attributes.getNamedItem("id");
	               String id = namedItem.getNodeValue();
	               */

				// Get a named nodes
				Element element = (Element) facilityInvEntries.item(i);
				NodeList nameNode = element.getElementsByTagName("name");
				name = nameNode.item(0).getTextContent();

				Inventory inventory = InventoryFactory.build(name);


				ArrayList<String> itemDescriptions = new ArrayList<>();
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

					// Get some named nodes
					element = (Element) itemList.item(j);
					itemId = element.getElementsByTagName("id").item(0).getTextContent();
					itemQuantity = element.getElementsByTagName("quantity").item(0).getTextContent();
					itemQty = Integer.parseInt(itemQuantity);
					InventoryItemDTO inventoryItemDTO = new InventoryItemDTO(itemId, itemQty);
					inventory.addInventoryItem(itemId, itemQty);

					// Create a string summary of the item
					itemDescriptions.add(itemId + "with Quantity " + itemQuantity);
				}


				inventories.add(inventory);
//				System.out.println("Facility " + i + " : " + name + "Items: " + itemId + " Quantity " + itemQty);
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
		} catch (NullParameterException e) {
			e.printStackTrace();
		} catch (NegativeOrZeroParameterException e) {
			e.printStackTrace();
		}

		return inventories;
	}



	public static void main(String[] args){

		InventoryXmlLoaderImpl xmlLoader =  new InventoryXmlLoaderImpl("src/data/facility_inventory.xml");
		try
		{
			xmlLoader.load();
		}
		catch (LoaderConfigFilePathException e)
		{
			e.printStackTrace();
		}
	}

}
