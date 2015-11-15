package fr.SOA.shopping3000.flows;

import org.apache.camel.builder.RouteBuilder;

import static fr.SOA.shopping3000.flows.utils.Endpoints.HANDLE_ITEM;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class HandleItem extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Route to handle a given item
        from(HANDLE_ITEM)
                .log("    Routing product id ${body.id}")
                .log("      Calling the shop route")
                .choice()
                .when(simple("${body.shop} == 'customshirt'"))
                .to("direct:customshirt")
                .when(simple("${body.shop} == 'customshoes'"))
                .to("direct:customshoes")
                .when(simple("${body.shop} == 'artinprovence'"))
                .to("direct:artinprovence")
                .otherwise()
                .to("direct:shopError") // stopping the route for bad items
                .end() // End of the content-based-router
        ;

        // bad information about a given item
        from("direct:shopError")
                .log("    Bad information for item ${body.id}, shop not available.")
                .end()
        ;

    }
}
