package fr.SOA.shopping3000.flows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CatalogRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Jackson ObjectMapper configuration
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);



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
                .to(Endpoints.BASE_URL + Endpoints.BASE_ART + "/products" + Endpoints.BRIDGE)
                .marshal()
                .json(JsonLibrary.Jackson, Product.class);

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
