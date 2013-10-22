package resource;

public class ResourceType {

	public final static String ROOT = "/osmanvlastic/rest",
			DESTINATION = "/destination", FLIGHT = "/flight",
			RESERVATION = FLIGHT + "/{flightId}/reservation",

			FULL_DESTINATION = ROOT + DESTINATION, FULL_FLIGHT = ROOT + FLIGHT,
			FULL_RESERVATION = ROOT + RESERVATION;
}
