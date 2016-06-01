package logistics.utilities.loader.implementation;


import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.LoaderConfigFilePathException;
import logistics.itemservice.Item;
import logistics.itemservice.ItemFactory;
import logistics.utilities.loader.interfaces.ItemLoader;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by uchennafokoye on 4/22/16.
 */
public class ItemXmlLoaderImpl implements ItemLoader {

    private String filepath;

    public ItemXmlLoaderImpl(String itemFilepath){
        filepath = itemFilepath;
    }

    public ArrayList<Item> load() throws LoaderConfigFilePathException {

        ArrayList<Item> items = new ArrayList<Item>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File xml = new File(filepath);
            if (!xml.exists()) {
                throw new LoaderConfigFilePathException();
            }

            Document doc = db.parse(xml);
            Element documentElement = doc.getDocumentElement();
            documentElement.normalize();

            NodeList itemEntries = documentElement.getChildNodes();
            for (int i = 0; i < itemEntries.getLength(); i++) {
                Node node = itemEntries.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                String entryName = node.getNodeName();
                if (!entryName.equals("item")) {
                    continue;
//                    Or perhaps throw an error
                }

                NamedNodeMap attributes = node.getAttributes();
                Node namedItem = attributes.getNamedItem("id");
                String id = namedItem.getNodeValue();
                Element element = (Element) itemEntries.item(i);
                NodeList priceNode = element.getElementsByTagName("price");
                Double price = Double.parseDouble(priceNode.item(0).getTextContent());

                Item item = ItemFactory.build(id, price);

//                System.out.println("No " + i + ": Item price: " + price + " Item Id: " + id);
                items.add(item);

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullParameterException e) {
            e.printStackTrace();
        }


        return items;
    }



    public static void main(String[] args){

        ItemXmlLoaderImpl xmlLoader =  new ItemXmlLoaderImpl("data/item_catalog.xml");
        try {
            xmlLoader.load();
        } catch (LoaderConfigFilePathException e) {
            e.printStackTrace();
        }


    }
}
