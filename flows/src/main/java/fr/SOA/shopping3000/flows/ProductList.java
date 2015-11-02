package fr.SOA.shopping3000.flows;

import org.apache.camel.builder.RouteBuilder;

public class ProductList extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        rest("/product")
                .get()
                .to("direct:getProductList");

        from("direct:getProductList")
                .setBody(simple("Hello world !"));

        rest("/products")
                .get()
                .to("direct:getProductList2");

        from("direct:getProductList2")
                .setBody(simple("Hello world two 2 !"));


    }
}
