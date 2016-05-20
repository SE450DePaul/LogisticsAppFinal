package logistics.utilities.loader.implementation;


import logistics.networkservice.factory.FacilityVertexFactory;
import logistics.networkservice.interfaces.FacilityVertex;
import logistics.utilities.exceptions.NegativeOrZeroParameterException;
import logistics.utilities.exceptions.NullParameterException;
import logistics.utilities.exceptions.LoaderConfigFilePathException;
import logistics.utilities.exceptions.NeighborNotFoundInNetworkException;
import logistics.utilities.loader.factory.LoaderFactory;
import logistics.utilities.loader.interfaces.Loader;
import logistics.utilities.loader.interfaces.NetworkLoader;
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
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by uchennafokoye on 4/22/16.
 */
public class NetworkXmlLoaderImpl implements NetworkLoader {

	private String filepath;

	public NetworkXmlLoaderImpl(String networkFilepath){
		filepath = networkFilepath;
	}

	public ArrayList<FacilityVertex> load() throws LoaderConfigFilePathException {

		ArrayList<FacilityVertex> facilityVertices = new ArrayList<>();

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

			NodeList networkEntries = documentElement.getChildNodes();
			for (int i = 0; i < networkEntries.getLength(); i++) {
				Node node = networkEntries.item(i);
				if (node.getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				String entryName = node.getNodeName();
				if (!entryName.equals("facility")) {
					continue;
//                    Or perhaps throw an error
				}

				// Get some named nodes
				Element element = (Element) networkEntries.item(i);
				String facilityName = element.getElementsByTagName("name").item(0).getTextContent();

				FacilityVertex facilityVertex = FacilityVertexFactory.build(facilityName);

				NodeList linkNodes = element.getElementsByTagName("link");

				for (int j = 0; j < linkNodes.getLength(); j++){

					node = linkNodes.item(j);
					if (node.getNodeType() == Node.TEXT_NODE){
						continue;
					}

					entryName = node.getNodeName();
					if (!entryName.equals("link")){
						continue;
					}

					element = (Element) linkNodes.item(j);
					String neighborName = element.getElementsByTagName("name").item(0).getTextContent();
					String distanceString = element.getElementsByTagName("distance").item(0).getTextContent();
					int distance = Integer.parseInt(distanceString);
					facilityVertex.addNeighbor(neighborName, distance);

				}

				facilityVertices.add(facilityVertex);

			}


		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullParameterException e) {
			e.printStackTrace();
		} catch (NegativeOrZeroParameterException e) {
			e.printStackTrace();
		}


		return facilityVertices;
	}



	public static void main(String[] args){

		Loader loader = LoaderFactory.build("network");
		try {
			Collection<FacilityVertex> facilityVertexCollection = loader.load();

			for (FacilityVertex facilityVertex : facilityVertexCollection){
				System.out.println(facilityVertex.getFacilityName());
				Iterator<String> iterator = facilityVertex.neighbors();

				System.out.println("Neighbors: ");
				while (iterator.hasNext()){
					String current = iterator.next();
					System.out.println(current);
					System.out.println("distance: " + facilityVertex.distanceTo(current));
				}

			}

		} catch (LoaderConfigFilePathException e) {
			e.printStackTrace();
		} catch (NeighborNotFoundInNetworkException e) {
			e.printStackTrace();
		}


	}
}
