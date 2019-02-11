package Controller;

import javax.xml.datatype.XMLGregorianCalendar;


import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.BigInteger;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import Entities.Budget;
import Entities.Day;
import Entities.Days;
import Entities.Itinerary;
import Entities.POI;
import Entities.Periode;
import Entities.Step;
import Entities.Steps;
import Entities.Themes;


//@XmlType(namespace = "http://www.example.org/itinerary")
public class ItineraryController {
	static String xmlFile = "itinerary.xml";
	static File file = new File(xmlFile);
	public static String getElement;
	static int counterElement = 0;

//	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException {
//
//
//		String themesTitle = "Humour";
//		String poiTitle = "Sub of Paris";
//		String description = "Came to know the catacombes of Paris";
//		String duration = "3";
//		int ratingValue = 5;
//
//
//		insertItinararyDay(poiTitle,themesTitle,description,duration,ratingValue);
//	}
	public void insertItinararyDay(String poiTitle,
			String themesTitle,
			String description,
			String duration,
			int ratingValue) throws JAXBException, DatatypeConfigurationException {
		Itinerary itinerary = new Itinerary();
		Day day = new Day();
		Steps steps = new Steps();
		Step step = new Step();
		Themes themes = new Themes();
		//check number of existing day elements and then add 1
		//		getElement = "POI";
		//		CounterElement(xmlFile);
		String theDay = null;
		//		System.out.println(counterElement);
		int c = 0;
		try {
			c=count("POI");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (c == 0) {
			theDay = String.valueOf(counterElement+1);
			day.setId(theDay);
			themes.getTheme().add(themesTitle);
			POI poi = insertPOI(themes, poiTitle,description , duration,ratingValue);
			step.setPOI(poi);
			steps.getStep().add(step);
			day.setSteps(steps);
			itinerary = createItinerary(day,"100",8,10,2019);
			setItinerary(itinerary);

		} else {
			//insert after oldest element
			try {
				createNewElementDay(poiTitle, themesTitle, description, duration,ratingValue);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	private static POI insertPOI(Themes themes,String poiTitle, String description,String duration, int rating) {
		POI poi = new POI();
		poi.setDescription(description);
		poi.setDuration(new BigInteger(duration));
		poi.setName(poiTitle);
		poi.setThemes(themes);
		poi.setHandicap(true);

		//check how many elements exist and then set id as the size/lenght + 1
		//getElement = "POI";
		//CounterElement(xmlFile);
		int c = 0;
		try {
			c= count("POI");

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c);
		poi.setId(String.valueOf(c+1));
		poi.setRating(new BigDecimal(rating));

		return poi;

	}
	private static Itinerary createItinerary(
			Day day,
			String budgetValue,
			int startDayValue,
			int startMonthValue,
			int startYearValue
			) throws JAXBException, DatatypeConfigurationException {

		Itinerary itinerary = new Itinerary();
		Budget budget = new Budget();

		Days days = new Days();
		Periode periode = new Periode();



		days.setDay(day);

		// assign values to itinerary day 1
		budget.setValue(new BigInteger(budgetValue));	
		itinerary.getDays().add(days);

		itinerary.setBudget(budget);
		// Set XML calendar

		//XMLGregorianCalendar calendarStart = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(startYearValue,startMonthValue,startDayValue,0);
		XMLGregorianCalendar calendarStart = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		calendarStart.setDay(startDayValue);
		calendarStart.setMonth(startMonthValue);
		calendarStart.setYear(startYearValue);
		calendarStart.setSecond(0);
		calendarStart.setMinute(0);
		calendarStart.setHour(12);

		XMLGregorianCalendar calendarEnd = DatatypeFactory.newInstance().newXMLGregorianCalendar();

		calendarEnd.setDay(startDayValue);
		calendarEnd.setMonth(startMonthValue);
		calendarEnd.setYear(startYearValue);
		calendarEnd.setSecond(0);
		calendarEnd.setMinute(0);
		calendarEnd.setHour(12);


		periode.setStart(calendarStart);
		periode.setEnd(calendarEnd);

		itinerary.setPeriode(periode);


		return itinerary;

	}

	private static void setItinerary(Itinerary itinerary) throws JAXBException {
		// TODO Auto-generated method stub
		JAXBContext context = JAXBContext.newInstance(Itinerary.class);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.encoding", "UTF-8");
		marshaller.setProperty("jaxb.formatted.output", true);
		//marshaller.marshal(itinerary, System.out);
		marshaller.marshal(itinerary,file);


	}


	public static void createNewElementDay(String poiTitle, String themesTitle, String descriptionValue, String duration, int ratingValue) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		

		//recuperer une instance de factory qui se chargera  de fournir un parseur
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//créer le parseur à travers factory
		DocumentBuilder builder = factory.newDocumentBuilder();

		//parser le fichier .xml via l'objet builder et recuperer un objet document representant
		//la hierarchie d'objet crée  pendant le parsing
		Document document = builder.parse(file);
		//Manipulation des noueds
		Element root = document.getDocumentElement();

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath path = xpathFactory.newXPath();
		String expressionDays = "//days";

		NodeList nlistOfDays = (NodeList)path.evaluate(expressionDays, root,XPathConstants.NODESET);

		System.out.println(nlistOfDays.getLength());
		for (int i = 0; i < nlistOfDays.getLength(); i++) {
			//String title = 
			//System.out.println(nlistOfDays.item(i).getChildNodes().item(1).getChildNodes());
			try {
				String elementDayname = nlistOfDays.item(i).getChildNodes().item(1).getNodeName();
				Element day = document.createElement("day");
				Element steps = document.createElement("steps");
				Element step = document.createElement("step");
				Element poi = document.createElement("POI");
				//poi fixe by day, because have no time to build a system in 24h...
				poi.setAttribute("id", String.valueOf(count(elementDayname)+1));

				Element name = document.createElement("name");
				Text nameText = document.createTextNode(poiTitle);
				name.appendChild(nameText);

				Element rating = document.createElement("rating");
				Text ratingInt = document.createTextNode(String.valueOf(ratingValue));
				rating.appendChild(ratingInt);

				Element handicap = document.createElement("handicap");
				Text handicapBool = document.createTextNode(String.valueOf(true));
				handicap.appendChild(handicapBool);

				Element description = document.createElement("description");
				Text descriptionText = document.createTextNode(descriptionValue);
				description.appendChild(descriptionText);

				Element themes = document.createElement("themes");
				Element theme = document.createElement("theme");
				Text themeText = document.createTextNode(themesTitle);
				theme.appendChild(themeText);
				themes.appendChild(theme);

				Element dur = document.createElement("duration");
				Text durationText = document.createTextNode(duration);
				dur.appendChild(durationText);

				poi.appendChild(name);
				poi.appendChild(rating);
				poi.appendChild(handicap);
				poi.appendChild(description);
				poi.appendChild(themes);
				poi.appendChild(dur);

				step.appendChild(poi);
				steps.appendChild(step);

				day.setAttribute("id", String.valueOf(count(elementDayname)+1));
				day.appendChild(steps);
				//System.out.println(nlistOfDays.item(i).getChildNodes().item(1).getNodeName());

				nlistOfDays.item(i).getChildNodes().item(1).getParentNode().insertBefore(day, nlistOfDays.item(i).getChildNodes().item(1));
			} catch (NullPointerException e) {
				e.getStackTrace();
			}

		}

		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result =  new StreamResult(file);
		//StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		try {

			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//String xmlOutput = result.getWriter().toString();
		//System.out.println(xmlOutput);
	}

	private static int count(String element) throws ParserConfigurationException, SAXException, IOException {
		//recuperer une instance de factory qui se chargera  de fournir un parseur
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//créer le parseur à travers factory
		DocumentBuilder builder = factory.newDocumentBuilder();

		//parser le fichier .xml via l'objet builder et recuperer un objet document representant
		//la hierarchie d'objet crée  pendant le parsing
		Document document = builder.parse(file);
		// Returns a NodeList of all the Elements in document order with a given tag name and are contained in the document.
		NodeList nodes = document.getElementsByTagName(element);
		//System.out.println(nodes.item(1));
		System.out.println("\nHere you go => Total # of Elements: " + nodes.getLength());
		return nodes.getLength();

	}


}




