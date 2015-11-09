package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;
import java.util.Map;

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
                .to("activemq:getRequestShoes")
                .to("activemq:getRequestArts")
                .to("activemq:getRequestShirt")
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
//                .to("direct:addProductListToDatabase")
        ;

        from("direct:processArtsTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Arts")
                .unmarshal()
                .json(JsonLibrary.Jackson, ArrayList.class)
                .process(processArtsTranslation)
                .to("direct:addProductListToDatabase")
        ;

        from("direct:processShirtTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Shirt")
                        // Ici on fait quelque chose sur le body qui contient la reponse du GET de la boutique
                        // cad on transforme la reponse en une liste de Product business
//                .to("direct:addProductListToDatabase")
        ;

        // Ici on ajoute les reponses traitees ( liste de Product ) a la DB
        from("direct:addProductListToDatabase")
                .log(LoggingLevel.INFO, "Ajout d'une liste de Product a la DB")
                .split(body())
                .bean(Database.class, "addProduct(${body})")
//                .bean(Database.class, "TESTaddProduct()")
        ;



        // Intern definition of getCatalog : HIDDEN
        from("direct:getCatalog")
            .log(LoggingLevel.INFO, "Passe dans getCatalog.")
            .bean(Database.class,"getAllProducts()")
                .marshal()
                .json(JsonLibrary.Jackson);
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


    // ArtsInProvence Processor
    private static Processor processArtsTranslation = new Processor() {

        public void process(Exchange exchange) throws Exception {
            ArrayList<Map<String, Object>> input = (ArrayList<Map<String,Object>>) exchange.getIn().getBody();
            ArrayList<Product> output = translater(input);
            exchange.getIn().setBody(output);
        }

        private ArrayList<Product> translater(ArrayList<Map<String, Object>> input) {
            ArrayList<Product> output = new ArrayList<Product>();

            for (Map map : input) {
                String name = (String)map.get("description");
                Double prix = Double.valueOf(map.get("price").toString());
                String shop = "Arts In Provence";
                String url = (String)map.get("url");
                int indexLastSlash = url.lastIndexOf("/");
                String id = url.substring(indexLastSlash + 1);

                Product product = new Product(id, name, shop, prix);
                // Champs supplémentaires
                product.setSpecializedAttribute("url", url);
                product.setSpecializedAttribute("available", ((Boolean)map.get("available")).toString());

                output.add(product);
            }
            return output;
        }

       /* private Person builder(Map<String, Object> data) {
            Person p = new Person();
            // name
            String name =  (String) data.get("Navn");
            p.setFirstName((name.split(",")[1].trim()));
            p.setLastName((name.split(",")[0].trim()));
            // zip code
            p.setZipCode(Integer.parseInt((String) data.get("Postnummer")));
            // address
            p.setAddress((String) data.get("Postaddressen"));
            // email
            p.setEmail((String) data.get("Epost"));
            // Unique identifier
            p.setUid((String) data.get("Fodselsnummer"));
            // Money
            p.setIncome(getMoneyValue(data, "Inntekt"));
            p.setAssets(getMoneyValue(data, "Formue"));
            return p;
        }

        private int getMoneyValue(Map<String, Object> data, String field) {
            String rawIncome = (String) data.get(field);
            return Integer.parseInt(rawIncome.replace(",", "").substring(0, rawIncome.length() - 3));
        }*/
    };

}
