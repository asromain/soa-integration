package fr.SOA.shopping3000.flows;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;



/**
 * Created by user on 29/10/2015.
 */
public class Command extends RouteBuilder {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");


        rest("/command/valid/{command_id}")
                .get()
                .to("direct:commandUser")
        ;
        from("direct:commandUser")
                .log("Command user from rest")
                .process(commandToCsv)
                .log("drop csv in the command file")
                .to(CSV_OUTPUT_DIRECTORY + "?fileName=${header.command_id}.csv")
        ;
    }


    private static Processor commandToCsv = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String commandId = (String) exchange.getIn().getHeader("command_id");

            //fill database for test
            Database.createOrder("5", "paris", "234");

            //get order from database
            Order order = Database.getOrder(commandId);
            String csvHeader = order.getCSVHeader();
            String csvOrder = order.toCSV();
            String csvContent = csvHeader+"\n"+csvOrder;


            // retrieving the body of the exchanged message
            //Map<String, Object> input = (Map<String, Object>) exchange.getIn().getBody();
            // transforming the input into a person
            //Person output =  builder(input);
            // Putting the person inside the body of the message
            exchange.getIn().setBody(csvContent);
        }

        //private void builder() {

        //}


    };


}
