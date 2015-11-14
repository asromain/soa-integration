package fr.SOA.shopping3000.flows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.*;

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

        /*******************************************
         *  Route to generate the catalog : HIDDEN
         *******************************************/
        from("direct:createCatalog")
                .log(LoggingLevel.INFO, "Passe dans la création du catalogue.")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                        // Envoi requete GET sur les boutiques
                .multicast()
                .parallelProcessing()
                .to("activemq:getRequestShoes")
                .to("activemq:getRequestArts")
                .to("activemq:getRequestShirtcolors")
                .to("activemq:getRequestShirttypes")
                .to("activemq:getRequestShirtsymbols")
        ;

        /***********************************************************
         * Ici les routes qui menent aux catalogues de nos boutiques
         ***********************************************************/
        // Custom Shoes
        from("activemq:getRequestShoes")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShoes")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/stock" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShoesTranslation")
        ;
        // Arts In Provence
        from("activemq:getRequestArts")
                .log(LoggingLevel.INFO, "Envoi du GET a Arts")
                .to(Endpoints.BASE_URL + Endpoints.BASE_ART + "/products" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processArtsTranslation")
        ;
        // Custom Shirts
        from("activemq:getRequestShirtcolors")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShirt colors")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/colors" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShirtTranslation")
        ;
        from("activemq:getRequestShirttypes")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShirt types")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/types" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShirtTranslation")
        ;
        from("activemq:getRequestShirtsymbols")
                .log(LoggingLevel.INFO, "Envoi du GET a CustomShirt symbols")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/symbols" + Endpoints.BRIDGE)
                        // On redirige la reponse de la boutique vers SON translator
                .to("direct:processShirtTranslation")
        ;


        /******************************************
         * Ici les translators de chaque boutiques
         ******************************************/
        // Custom Shoes
        from("direct:processShoesTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Shoes")
                .unmarshal().json(JsonLibrary.Jackson)
                //.log(LoggingLevel.INFO, "####### AVANT #######")
                //.log(LoggingLevel.INFO, "${body}")
                .process(processShoesTranslation)
                //.log(LoggingLevel.INFO, "####### APRES #######")
                //.log(LoggingLevel.INFO, "${body}")
                        // Ici on fait quelque chose sur le body qui contient la reponse du GET de la boutique
                        // cad on transforme la reponse en une liste de Product business
                .to("direct:addProductListToDatabase")
        ;
        // Arts In Provence
        from("direct:processArtsTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Arts")
                .unmarshal()
                .json(JsonLibrary.Jackson, ArrayList.class)
                .process(processArtsTranslation)
                .to("direct:addProductListToDatabase")
        ;
        // Custom Shirts
        from("direct:processShirtTranslation")
                .log(LoggingLevel.INFO, "Translator boutique Shirt")
                .unmarshal().json(JsonLibrary.Jackson)
                .process(shirtTranslation)
                .to("direct:addProductListToDatabase")
        ;

        /******************************************************************
         * Ici on ajoute les reponses traitees ( liste de Product ) a la DB
         ******************************************************************/
        from("direct:addProductListToDatabase")
                .log(LoggingLevel.INFO, "Ajout d'une liste de Product a la DB")
                .split(body())
                .bean(Database.class, "addProduct(${body})")
                //.bean(Database.class, "TESTaddProduct()")
        ;

        /******************************************
         * Intern definition of getCatalog : HIDDEN
         ******************************************/
        from("direct:getCatalog")
            .log(LoggingLevel.INFO, "Passe dans getCatalog.")
            .bean(Database.class,"getAllProducts()")
                .marshal()
                .json(JsonLibrary.Jackson);

        // endregion
        // region FOREGROUND DEFINITIONS

        // REST Configuration
        restConfiguration().component("servlet");

        /*************************************
         * Route to get the catalog : EXPOSED
         *************************************/
        rest("/products")
                .get()
                .to("direct:getCatalog");
        // endregion
    }


    /********************
     *  Les processors
     ********************/

    // Custom Shoes Processor
    private static Processor processShoesTranslation = new Processor() {

        public void process(Exchange exchange) throws Exception
        {
            HashMap<String, ArrayList<Map <String, Object>>> input = (HashMap<String, ArrayList<Map <String, Object>>>) exchange.getIn().getBody();
            ArrayList<Map<String, Object>> tmp = input.get("product");

            ArrayList<Product> output = translater(tmp);
            exchange.getIn().setBody(output);
        }

        private ArrayList<Product> translater(ArrayList<Map<String, Object>> input)
        {
            ArrayList<Product> output = new ArrayList<Product>();

            for (Map map : input)
            {
                Double prix = Double.valueOf(map.get("price").toString());
                String name = "shoes";
                String shop = "custom shoes";
                Integer idtmp = (Integer) map.get("refProduct");
                String id = idtmp.toString();

                Product product = new Product(id, name, shop, prix);

                Double sizetmp = Double.valueOf(map.get("size").toString());
                String size = sizetmp.toString();

                product.setSpecializedAttribute("size", size);
                product.setSpecializedAttribute("cleats", (String) map.get("cleats"));
                product.setSpecializedAttribute("color", (String) map.get("color"));

                output.add(product);
            }
            return output;
        }
    };

    // ArtsInProvence Processor
    private static Processor processArtsTranslation = new Processor() {

        public void process(Exchange exchange) throws Exception {
            ArrayList<Map<String, Object>> input = (ArrayList<Map<String,Object>>) exchange.getIn().getBody();
            ArrayList<Product> output = translater(input);
            exchange.getIn().setBody(output);
        }

        private ArrayList<Product> translater(ArrayList<Map<String, Object>> input) {
            ArrayList<Product> output = new ArrayList<Product>();

            List<String> artsCustomNeed = new ArrayList<String>() {
                {
                 add("url/exemple/image.jpg");
                }
            };

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
                product.setPersonalisation("personalisations",artsCustomNeed );

                output.add(product);
            }
            return output;
        }
    };

    // CustomShirt Processor
    private static Processor shirtTranslation = new Processor() {

        public void process(Exchange exchange) throws Exception {
            HashMap<String, ArrayList<HashMap<String, String>>> input = (HashMap<String, ArrayList<HashMap<String, String>>>) exchange.getIn().getBody();

            ArrayList<Product> output = translater(input);
            exchange.getIn().setBody(output);
        }

        private ArrayList<Product> translater(HashMap<String, ArrayList<HashMap<String, String>>> input) {
            ArrayList<Product> output = new ArrayList<Product>();
            Map<String, List<String>> persos = new HashMap<String, List<String>>();

            Product product = Database.getProduct("customshirt-1");

            if (product == null) {

                String id = "customshirt-1";
                String name = "Tshirt Personnalisable";
                Double prix = (double) 101;
                String shop = "customshirt";

                product = new Product(id, name, shop, prix);
            }
            ArrayList<String> persosForCatalog = new ArrayList<String>();
            for (HashMap.Entry<String, ArrayList<HashMap<String, String>>> entry : input.entrySet()) {
                for (String lhm : entry.getValue().get(0).values()) {
                    persosForCatalog.add(lhm);
                }
                // c'est un hashmap donc ca remplace l'ancien si deja existant
                product.setPersonalisation(entry.getKey(), persosForCatalog);
            }

            //product.setPersonalisation("color", persosForCatalog);
            output.add(product);
            return output;
        }

    };

}
