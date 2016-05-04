package XML_Parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GetCountyinfoVa {

	String name;
	String val;

	public static void getCountyInfo(String id, String state) throws Exception {

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL("http://www.zillow.com/webservice/GetRegionChildren.htm?zws-id="
					+ id + "&state=" + state + "&childtype=county").openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(connection.toString());

		StringBuffer response = new StringBuffer();
		String line;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response.toString());

		Document dom = null;

		try {
			dom = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(response.toString())));

			Element docEle = dom.getDocumentElement();


			NodeList n1 = docEle.getElementsByTagName("name");
			NodeList n2 = docEle.getElementsByTagName("zindex");

			String[] name = new String[n2.getLength()];
			String[] value = new String[n2.getLength()];

			for (int i = 0; i < n2.getLength(); i++) {

				if (n1.item(i).getTextContent().contains("Fairfax")
						|| n1.item(i).getTextContent().contains("Virginia Beach City")|| n1.item(i).getTextContent().contains("Prince William")
						|| n1.item(i).getTextContent().contains("Chesterfield") || n1.item(i).getTextContent().contains("Loudoun")
						|| n1.item(i).getTextContent().contains("Henrico") || n1.item(i).getTextContent().contains("Chesapeake City")
						|| n1.item(i).getTextContent().contains("Arlington")|| n1.item(i).getTextContent().contains("Richmond City")
						|| n1.item(i).getTextContent().contains("Newport News City")|| n1.item(i).getTextContent().contains("Hampton City")
						|| n1.item(i).getTextContent().contains("Alexandria City")|| n1.item(i).getTextContent().contains("Stafford")
						|| n1.item(i).getTextContent().contains("Spotsylvania") || n1.item(i).getTextContent().contains("Hanover")
						|| n1.item(i).getTextContent().contains("Portsmouth City") || n1.item(i).getTextContent().contains("Roanoke City")
						|| n1.item(i).getTextContent().contains("Frederick") || n1.item(i).getTextContent().contains("Fauquier")
						|| n1.item(i).getTextContent().contains("York") || n1.item(i).getTextContent().contains("James City")
						|| n1.item(i).getTextContent().contains("Franklin") || n1.item(i).getTextContent().contains("Culpeper")
						|| n1.item(i).getTextContent().contains("Shenandoah") || n1.item(i).getTextContent().contains("Manassas City")
						|| n1.item(i).getTextContent().contains("Warren") || n1.item(i).getTextContent().contains("Orange")
						|| n1.item(i).getTextContent().contains("Louisa") || n1.item(i).getTextContent().contains("Caroline")
						|| n1.item(i).getTextContent().contains("Winchester City")|| n1.item(i).getTextContent().contains("Fairfax City")
						|| n1.item(i).getTextContent().contains("King George") || n1.item(i).getTextContent().contains("Fredericksburg City")
						|| n1.item(i).getTextContent().contains("Manassas Park City") || n1.item(i).getTextContent().contains("Falls Church City")) {
					
					name[i] = n1.item(i).getTextContent();
				    value[i]= n2.item(i).getTextContent();
					System.out.println("County: " + n1.item(i).getTextContent() + " \nAverage Value: "
							+ n2.item(i).getTextContent());

				}

			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

}
