package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class CatalogRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // REST Configuration
        restConfiguration().component("servlet");

        // Route to get the catalog : EXPOSED
        rest("/products")
                .get()
                .to("direct:getCatalog");

        // Intern definition of getCatalog : HIDDEN
        from("direct:getCatalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to(Endpoints.BASE_URL + Endpoints.BASE_ART + "/products" + Endpoints.BRIDGE);



        // Route to generate the catalog : HIDDEN
        from("timer:dataBaseTimer?period=5s")
            .to("log:timer");
    }

}
