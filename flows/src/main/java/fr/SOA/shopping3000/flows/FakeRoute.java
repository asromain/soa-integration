package fr.SOA.shopping3000.flows;

import org.apache.camel.builder.RouteBuilder;


/**
 * Created by zeibetsu on 09/11/2015.
 */
public class FakeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:customshirt")
                .log("Item redirected in customshirt")
                .end()
        ;

        from("direct:customshoes")
                .log("Item redirected in customshoes")
                .end()
        ;
        from("direct:artinprovence")
                .log("Item redirected in artinprovence")
                .end()
        ;
    }
}
