package fr.SOA.shopping3000.flows.utils;

public class Endpoints {

    public static final String CSV_INPUT_DIRECTORY = "file:camel/input";

    public static final String CSV_OUTPUT_DIRECTORY = "file:camel/output";

    public static final String HANDLE_ITEM = "activemq:handleItem";

    public static final String BRIDGE = "?bridgeEndpoint=true";

    public static final String BASE_URL = "http://localhost:8181/cxf";

    public static final String BASE_SHIRT = "/customshirt";

    public static final String BASE_SHOES = "/customshoes";

    public static final String BASE_ART = "/arts";
}
