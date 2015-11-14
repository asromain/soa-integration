package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.BadItemWriter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;

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
                //.process(addShop2Product)
                .choice()
                .when(simple("${body.shop} == 'customshirt'"))
                .to("direct:customshirt")
                //.when(simple("${body.income} == customshoes"))
                .when(simple("${body.shop} == 'customshoes'"))
                .to("direct:customshoes")
                .when(simple("${body.shop} == 'artinprovence'"))
                //.when(simple("${body.income} == artinprovence"))
                .to("direct:artinprovence")
                .otherwise()
                .to("direct:shopError") // stopping the route for bad items
                //.bean(BadItemWriter.class, "writeBadItem($(body})")
                .end() // End of the content-based-router
        ;

        // bad information about a given item
        from("direct:shopError")
                .log("    Bad information for item ${body.id}, shop not available.")
                .bean(BadItemWriter.class, "writeBadItem($(body})")
                .end()
        ;

    }

    private static Processor addShop2Product = new Processor() {
        public void process(Exchange exchange) throws Exception {
            Product product = (Product)exchange.getIn().getBody();
            //get from bd
            //for each id, his shop
            product.setShop("");
            exchange.getIn().setBody(product);
        }
    };
}
