import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML {
	private String mid_term = "http://www.weather.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108";
	private String term1 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=1111061500";
	private String term2 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=2647065000";
	private String term3 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=3017063000";
	private String term4 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=2711051700";
	private String term5 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=3114051000";
	private String term6 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=2914074500";
	private String term7 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=2820051000";
	private String term8 = "http://www.weather.go.kr/wid/queryDFSRSS.jsp?zone=5011054000";
	protected String d0_temp;
	protected String d0_wfKor;
	protected String d0_ws;
	protected String d0_wdKor;
	protected String d1_wf;
	protected String d1_tmn;
	protected String d1_tmx;
	protected String d2_wf;
	protected String d2_tmn;
	protected String d2_tmx;
	protected String d3_wf;
	protected String d3_tmn;
	protected String d3_tmx;
	protected String d4_wf;
	protected String d4_tmn;
	protected String d4_tmx;
	protected String d5_wf;
	protected String d5_tmn;
	protected String d5_tmx;
	protected String d6_wf;
	protected String d6_tmn;
	protected String d6_tmx;
	protected String d7_wf;
	protected String d7_tmn;
	protected String d7_tmx;
	protected int LocID;

	public XML(int LocID) {
		this.LocID = LocID;
		try {
			getMidWeather(LocID);
			getNowWeather(LocID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			WeatherDAO.refreshWeather(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getMidWeather(int LocID) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(mid_term);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("location");
		
		String Loc = null;
		switch(LocID) {
			case 1 : Loc = "서울"; break;
			case 2 : Loc = "부산"; break;
			case 3 : Loc = "대전"; break;
			case 4 : Loc = "대구"; break;
			case 5 : Loc = "울산"; break;
			case 6 : Loc = "광주"; break;
			case 7 : Loc = "인천"; break;
			case 8 : Loc = "제주"; break;
		}

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			NodeList xmlNodeList = nNode.getChildNodes();

			if (xmlNodeList.item(3).getTextContent().equals(Loc)) {
				Node d3 = xmlNodeList.item(7);
				NodeList dl3 = d3.getChildNodes();
				Node d4 = xmlNodeList.item(11);
				NodeList dl4 = d4.getChildNodes();
				Node d5 = xmlNodeList.item(15);
				NodeList dl5 = d5.getChildNodes();
				Node d6 = xmlNodeList.item(19);
				NodeList dl6 = d6.getChildNodes();
				Node d7 = xmlNodeList.item(23);
				NodeList dl7 = d7.getChildNodes();

				for (int nodeInt = 0; nodeInt < dl3.getLength(); nodeInt++) {
					if (dl3.item(nodeInt).getNodeName().equals("wf")) {
						d3_wf = dl3.item(nodeInt).getTextContent();
					}
					if (dl3.item(nodeInt).getNodeName().equals("tmn")) {
						d3_tmn = dl3.item(nodeInt).getTextContent();
					}
					if (dl3.item(nodeInt).getNodeName().equals("tmx")) {
						d3_tmx = dl3.item(nodeInt).getTextContent();
					}
				}
				for (int nodeInt = 0; nodeInt < dl4.getLength(); nodeInt++) {
					if (dl4.item(nodeInt).getNodeName().equals("wf")) {
						d4_wf = dl4.item(nodeInt).getTextContent();
					}
					if (dl4.item(nodeInt).getNodeName().equals("tmn")) {
						d4_tmn = dl4.item(nodeInt).getTextContent();
					}
					if (dl4.item(nodeInt).getNodeName().equals("tmx")) {
						d4_tmx = dl4.item(nodeInt).getTextContent();
					}
				}
				for (int nodeInt = 0; nodeInt < dl5.getLength(); nodeInt++) {
					if (dl5.item(nodeInt).getNodeName().equals("wf")) {
						d5_wf = dl5.item(nodeInt).getTextContent();
					}
					if (dl5.item(nodeInt).getNodeName().equals("tmn")) {
						d5_tmn = dl5.item(nodeInt).getTextContent();
					}
					if (dl5.item(nodeInt).getNodeName().equals("tmx")) {
						d5_tmx = dl5.item(nodeInt).getTextContent();
					}
				}
				for (int nodeInt = 0; nodeInt < dl6.getLength(); nodeInt++) {
					if (dl6.item(nodeInt).getNodeName().equals("wf")) {
						d6_wf = dl6.item(nodeInt).getTextContent();
					}
					if (dl6.item(nodeInt).getNodeName().equals("tmn")) {
						d6_tmn = dl6.item(nodeInt).getTextContent();
					}
					if (dl6.item(nodeInt).getNodeName().equals("tmx")) {
						d6_tmx = dl6.item(nodeInt).getTextContent();
					}
				}
				for (int nodeInt = 0; nodeInt < dl7.getLength(); nodeInt++) {
					if (dl7.item(nodeInt).getNodeName().equals("wf")) {
						d7_wf = dl7.item(nodeInt).getTextContent();
					}
					if (dl7.item(nodeInt).getNodeName().equals("tmn")) {
						d7_tmn = dl7.item(nodeInt).getTextContent();
					}
					if (dl7.item(nodeInt).getNodeName().equals("tmx")) {
						d7_tmx = dl7.item(nodeInt).getTextContent();
					}
				}
				break;
			}
		}

	}

	private void getNowWeather(int LocID) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		String URL = null;
		switch(LocID) {
			case 1 : URL = term1; break;
			case 2 : URL = term2; break;
			case 3 : URL = term3; break;
			case 4 : URL = term4; break;
			case 5 : URL = term5; break;
			case 6 : URL = term6; break;
			case 7 : URL = term7; break;
			case 8 : URL = term8; break;
		}

		Document doc = dBuilder.parse(URL);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("body");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			NodeList xmlNodeList = nNode.getChildNodes();

			int check = 0;
			int[] index = new int[3];
			for (int nodeInt = 0; nodeInt < xmlNodeList.getLength(); nodeInt++) {
				if (xmlNodeList.item(nodeInt).getNodeType() == Node.ELEMENT_NODE) {
					Node tempNode = xmlNodeList.item(nodeInt);
					NodeList tempList = tempNode.getChildNodes();
					if (tempList.item(1).getTextContent().equals("12")) {
						index[check++] = nodeInt;
					}
				}
			}

			Node d0 = xmlNodeList.item(1);
			NodeList dl0 = d0.getChildNodes();
			Node d1 = null;
			NodeList dl1 = null;
			Node d2 = null;
			NodeList dl2 = null;

			if (check == 2) {
				d1 = xmlNodeList.item(index[0]);
				dl1 = d1.getChildNodes();
				d2 = xmlNodeList.item(index[1]);
				dl2 = d2.getChildNodes();
			} else {
				d1 = xmlNodeList.item(index[1]);
				dl1 = d1.getChildNodes();
				d2 = xmlNodeList.item(index[2]);
				dl2 = d2.getChildNodes();
			}

			for (int nodeInt = 0; nodeInt < dl0.getLength(); nodeInt++) {
				if (dl0.item(nodeInt).getNodeName().equals("temp")) {
					d0_temp = dl0.item(nodeInt).getTextContent();
				}
				if (dl0.item(nodeInt).getNodeName().equals("wfKor")) {
					d0_wfKor = dl0.item(nodeInt).getTextContent();
				}
				if (dl0.item(nodeInt).getNodeName().equals("wdKor")) {
					d0_wdKor = dl0.item(nodeInt).getTextContent();
				}
				if (dl0.item(nodeInt).getNodeName().equals("ws")) {
					d0_ws = String.format("%.1f", Double.parseDouble(dl0.item(nodeInt).getTextContent()));
				}
			}
			for (int nodeInt = 0; nodeInt < dl1.getLength(); nodeInt++) {
				if (dl1.item(nodeInt).getNodeName().equals("wfKor")) {
					d1_wf = dl1.item(nodeInt).getTextContent();
				}
				if (dl1.item(nodeInt).getNodeName().equals("tmn")) {
					d1_tmn = dl1.item(nodeInt).getTextContent();
				}
				if (dl1.item(nodeInt).getNodeName().equals("tmx")) {
					d1_tmx = dl1.item(nodeInt).getTextContent();
				}
			}
			for (int nodeInt = 0; nodeInt < dl2.getLength(); nodeInt++) {
				if (dl2.item(nodeInt).getNodeName().equals("wfKor")) {
					d2_wf = dl2.item(nodeInt).getTextContent();
				}
				if (dl2.item(nodeInt).getNodeName().equals("tmn")) {
					d2_tmn = dl2.item(nodeInt).getTextContent();
				}
				if (dl2.item(nodeInt).getNodeName().equals("tmx")) {
					d2_tmx = dl2.item(nodeInt).getTextContent();
				}
			}
		}
	}
}
