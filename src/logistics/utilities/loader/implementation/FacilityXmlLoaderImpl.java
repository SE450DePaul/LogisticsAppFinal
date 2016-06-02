package logistics.utilities.loader.implementation;

import logistics.facilityservice.Facility;
import logistics.facilityservice.FacilityFactory;
import logistics.utilities.exceptions.LoaderFileNotFoundException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.loader.interfaces.FacilityLoader;
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
public class FacilityXmlLoaderImpl implements FacilityLoader
{

	 private String filepath;
	 public FacilityXmlLoaderImpl(String path)
	 {
	        filepath = path;
	 }
	
	    public ArrayList<Facility> load() throws LoaderFileNotFoundException
	    {

	        ArrayList<Facility> facilities = new ArrayList<>();

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

	            NodeList facilityEntries = documentElement.getChildNodes();
	            for (int i = 0; i < facilityEntries.getLength(); i++) 
	            {
	                Node node = facilityEntries.item(i);
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


	                Element element = (Element) facilityEntries.item(i);
	                NodeList nameNode = element.getElementsByTagName("name");
	                NodeList rateNode = element.getElementsByTagName("rate");
	                NodeList costNode = element.getElementsByTagName("cost");
	                
	                String name = nameNode.item(0).getTextContent();
	                Integer rate = Integer.parseInt(rateNode.item(0).getTextContent());
	                Double cost = Double.parseDouble(costNode.item(0).getTextContent());
	                
	                Facility facility = FacilityFactory.build(name, rate, cost);
					facilities.add(facility);
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

			return facilities;
	    }


	    public static void main(String[] args)
	    {
	        FacilityXmlLoaderImpl xmlLoader =  new FacilityXmlLoaderImpl("data/facilities.xml");
			ArrayList<Facility> facilities;
			try 
			{
				facilities = xmlLoader.load();
				for (Facility f : facilities)
				{

					System.out.println(f.getName());
					System.out.println("Cost: " + f.getCost());
					System.out.println("Rate: " + f.getRate());
					System.out.println("");

				}
			} 
			catch (LoaderFileNotFoundException e) 
			{
				e.printStackTrace();
			}
	    }
}
