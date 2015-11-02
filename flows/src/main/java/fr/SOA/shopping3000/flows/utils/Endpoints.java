package fr.SOA.shopping3000.flows.utils;

/**
 * Created by user on 29/10/2015.
 */
public class Endpoints {
    public static final String CSV_INPUT_DIRECTORY = "file:camel/input";
    public static final String CSV_OUTPUT_DIRECTORY = "file:camel/output";
    public static final String HANDLE_CITIZEN = "activemq:handleACitizen";
    public static final String GEN_SERVICE = "http://localhost:8181";
    public static final String STORE_TAX_FORM = "activemq:storeTaxForm";
    public static final String TAX_COMPUTATION_SERVICE = "spring-ws://http://localhost:8181/cxf/TaxComputation";
}
