package fr.SOA.shopping3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static fr.SOA.shopping3000.flows.utils.Endpoints.GEN_SERVICE;

/**
 * Created by user on 29/10/2015.
 */
public class ProductList extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");
//
        rest("product")
                .get()
                .to("direct:getProductList");
//
//        from("direct:getProductList")
//                .setBody(simple("Hello world !"));
//
//        rest("/products")
//                .get()
//                .to("direct:getProductList2");
//
//        from("direct:getProductList2")
//                .setBody(simple("Hello world two 2 !"));

        from("direct:getProductList")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/services/stock?bridgeEndpoint=true")
                .process(readResponseStream)
                ;
    }

    private static Processor readResponseStream = new Processor() {
        public void process(Exchange exchange) throws Exception {
            InputStream response = (InputStream) exchange.getIn().getBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) { out.append(line); }
            reader.close();
            exchange.getIn().setBody(out.toString());
        }
    };
}
