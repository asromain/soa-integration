package fr.SOA.shopping3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.SOA.shopping3000.flows.utils.Endpoints.HANDLE_ITEM;
/**
 * Created by zeibetsu on 09/11/2015.
 */
public class HandleItem extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Route to handle a given Person
        from(HANDLE_ITEM)
                .log("    Routing ${body.name}")
                .log("      Storing the Person as an exchange property")
                        //.setProperty("person", body())
                        //.setProperty("orderId", "12345")
                .log("      Calling an existing generator")
                .choice()
                .when(simple("${body.color}"))
                .to("direct:coucou")
                .when(simple("${body.income} >= 42000"))
                .when(simple("${body.income} >= 42000"))
                .otherwise()
                .to("direct:badItem").stop() // stopping the route for bad citizens
                .end() // End of the content-based-router
        //.to(HANDLE_ORDER)
        ;

        // bad information about a given citizen
        from("direct:badItem")
                .log("    Bad information for otem ${body.name}, ending here.")
        ;


        /*from("direct:generateLetter")
                .bean(LetterWriter.class, "write(${property.person}, ${body}, ${property.tax_computation_method})")
                .to(CSV_OUTPUT_DIRECTORY + "?fileName=${property.p_uuid}.txt")
        ;*/
    }
}
