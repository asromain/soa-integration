package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static fr.SOA.shopping3000.flows.utils.Endpoints.CSV_OUTPUT_DIRECTORY;


/**
 * Created by user on 29/10/2015.
 */
public class ClientRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");


        rest("/client/create/{client_name}/{client_address}")
                .get()
                .to("direct:clientCreate")
        ;

        rest("/client/update/{client_name}/{client_address}")
                .get()
                .to("direct:clientCreate") // TODO client update
        ;

        from("direct:clientCreate")
                .log("CommandRest user from rest")
                .process(createClient)
        ;
    }


    private static Processor createClient = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String clientId = Database.genUID();
            String clientName = (String) exchange.getIn().getHeader("client_name");
            String clientAddress = (String) exchange.getIn().getHeader("client_address");

            //fill database
            Database.createClient(clientId, clientName, clientAddress);

            exchange.getIn().setBody(clientId);
        }

    };

}
