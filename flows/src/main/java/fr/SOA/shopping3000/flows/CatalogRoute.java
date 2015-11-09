package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.utils.Database;
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
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                        // Envoi requete GET sur les boutiques
                .multicast()
                .parallelProcessing()
                .to("activdmq:getRequestShoes")
                .to("activdmq:getRequestArts")
                .to("activdmq:getRequestShirt")
        ;

        // Ici les routes qui menent aux catalogues de nos boutiques

        from("activemq:getRequestShoes")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShoes")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/stock" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShoesTranslation")
        ;

        from("activemq:getRequestArts")
                .log(LoggingLevel.INFO, "Envoi du GET a Arts")
                .to(Endpoints.BASE_URL + Endpoints.BASE_ART + "/products" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processArtsTranslation")
        ;

        from("activemq:getRequestShirt")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShirt")
//                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/products" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShirtTranslation")
        ;

        // Ici les translators de chaque boutiques

        from("direct:processShoesTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Shoes")
                        // Ici on fait quelque chose sur le body qui contient la reponse du GET de la boutique
                        // cad on transforme la reponse en une liste de Product business
                .to("direct:addProductListToDatabase")
        ;

        from("direct:processArtsTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Arts")
                        // Ici on fait quelque chose sur le body qui contient la reponse du GET de la boutique
                        // cad on transforme la reponse en une liste de Product business
                .to("direct:addProductListToDatabase")
        ;

        from("direct:processShirtTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Shirt")
                        // Ici on fait quelque chose sur le body qui contient la reponse du GET de la boutique
                        // cad on transforme la reponse en une liste de Product business
                .to("direct:addProductListToDatabase")
        ;

        // Ici on ajoute les reponses traitees ( liste de Product ) a la DB
        from("direct:addProductListToDatabase")
                .log(LoggingLevel.INFO, "Ajout d'une liste de Product a la DB")
                .split(body())
                .bean(Database.class, "addProduct(${body})")
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
