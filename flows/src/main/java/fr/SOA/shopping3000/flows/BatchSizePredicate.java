package fr.SOA.shopping3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

public class BatchSizePredicate implements Predicate {

    public int size;

    public BatchSizePredicate(int size) {
        this.size = size;
    }

    public boolean matches(Exchange exchange) {
        if (exchange != null) {
            ArrayList list = exchange.getIn().getBody(ArrayList.class);
            if (CollectionUtils.isNotEmpty(list) && list.size() == size) {
                return true;
            }
        }
        return false;
    }

}