package XML_Parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GetZid {

	public static void getZid(String id, String add, String csz) {

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id="
					+ id + "&address=" + add + "&citystatezip=" + csz).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuffer response = new StringBuffer();
		String line;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Document document = null;

		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(response.toString())));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element docEle = document.getDocumentElement();
		NodeList n1 = docEle.getElementsByTagName("zpid");

		for (int i = 0; i < n1.getLength(); i++) {

			System.out.println(n1.item(i).getTextContent());
		}
	}
}
