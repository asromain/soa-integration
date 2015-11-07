package fr.SOA.shopping3000.flows;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import java.lang.reflect.Array;
import java.util.List;


/**
 * Created by user on 29/10/2015.
 */
public class CommandRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");


        rest("/command/valid/{command_id}")
                .get()
                .to("direct:commandUser")
        ;

        from("direct:commandUser")
                .log("CommandRest user from rest")
                .process(commandToCsv)
                .log("drop csv in the command file")
                .to(CSV_INPUT_DIRECTORY + "?fileName=${header.command_id}.csv")
        ;
    }


    private static Processor commandToCsv = new Processor() {

        private static final String COMMA_DELIMITER = ",";
        private static final String NEW_LINE_SEPARATOR = "\n";

        public void process(Exchange exchange) throws Exception {

            String commandId = (String) exchange.getIn().getHeader("command_id");

            //fill database for test
            Database.createOrder("5", "paris", 234.25);

            //get order from database
            Order order = Database.getOrder(commandId);

            String csvContent = buildCSV(order);

            exchange.getIn().setBody(csvContent);
        }

        private String buildCSV(Order order) {
            return order.toCSV();
        }


    };


}
