package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.services.ClientService;
import org.apache.camel.builder.RouteBuilder;


/**
 * Created by user on 29/10/2015.
 */
public class ClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        rest("/client").description("Client rest service")
                .consumes("application/json").produces("application/json")

                .post("/{client_name}/{client_address}").description("Create client")
                .to("direct:clientCreate")

                //.put("{client_id}/{client_name}/{client_address}").description("Updates client")
                //.to("direct:clientUpdate")
        ;

        from("direct:clientCreate")
                .log("Create client")
                .bean(ClientService.class, "createClient(${header.client_name},${header.client_address})")
        ;
    }

}
