package fr.SOA.shopping3000.flows;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by user on 29/10/2015.
 */
public class ProductList extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:getProductList")
                .setBody(simple("Hello world !"));

        restConfiguration().component("servlet");

        rest("/product")
                .get()
                .to("direct:getProductList");

    }
}
