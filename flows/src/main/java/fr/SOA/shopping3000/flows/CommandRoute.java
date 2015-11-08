/*package fr.SOA.shopping3000.flows;


public class CommandRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        rest("/command")
                .get()
                .to("direct:createCommand");

        from("direct:createCommand")
                .setBody(simple("Hello world !"));
    }
}*/
