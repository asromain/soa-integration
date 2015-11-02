package fr.SOA.shopping3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

/**
 * Created by user on 29/10/2015.
 */
public class ProductList extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");
//
        rest("/product")
                .get()
                .to("direct:getProductList");

        rest("/artproducts")
                .get()
                .to("direct:artproducts");

        from("direct:getProductList")
                .setBody(simple("Hello world !"));
//
//        rest("/products")
//                .get()
//                .to("direct:getProductList2");
//
//        from("direct:getProductList2")
//                .setBody(simple("Hello world two 2 !"));

        from("direct:artproducts")
                .log("issy")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to(GEN_SERVICE + "/cxf/arts/products?bridgeEndpoint=true")
                ;
    }

}
