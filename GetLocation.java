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

public class GetLocation {

	public static void getLoca(String id, String state) throws Exception {

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
			NodeList n2 = docEle.getElementsByTagName("latitude");
			NodeList n3 = docEle.getElementsByTagName("longitude");
			String[] name = new String[n1.getLength()];
			String[] loc = new String[n2.getLength()];

			for (int i = 0; i < n1.getLength(); i++) {
				name[i] = n1.item(i).getTextContent();
				loc[i] = n2.item(i).getTextContent() + ", " + n3.item(i).getTextContent();
				System.out.println("County: " + name[i] + " \n\tLocation: " + loc[i]);
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();

		}

	}
}
