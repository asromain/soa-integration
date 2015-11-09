package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class CatalogRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // region BACKGROUND DEFINITIONS
        // Scheduler to generate the catalog : HIDDEN
        from("timer:customTimer?period=30s")
                .log(LoggingLevel.INFO, "Passe dans le timer.")
                .to("direct:createCatalog");

        // Route to generate the catalog : HIDDEN
        from("direct:createCatalog")
                .log(LoggingLevel.INFO, "Passe dans la création du catalogue.")
                ;

        // Intern definition of getCatalog : HIDDEN
        from("direct:getCatalog")
                .log(LoggingLevel.INFO, "Passe dans getCatalog.")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to(Endpoints.BASE_URL + Endpoints.BASE_ART + "/products" + Endpoints.BRIDGE);

        // endregion

        // region FOREGROUND DEFINITIONS

        // REST Configuration
        restConfiguration().component("servlet");

        // Route to get the catalog : EXPOSED
        rest("/products")
                .get()
                .to("direct:getCatalog");


        // endregion
    }

}
