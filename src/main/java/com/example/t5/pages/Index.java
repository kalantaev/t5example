package com.example.t5.pages;


import com.example.t5.data.Units;
import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.SourceInProductEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.entities.view.Storage;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;
import org.slf4j.Logger;

import java.util.*;

/**
 * Start page of application t5.
 */
public class Index {

    @Inject
    private Session session;

    // Handle call with an unwanted context
    Object onActivate(EventContext eventContext) {
        return eventContext.getCount() > 0 ?
                new HttpError(404, "Resource not found") :
                null;
    }

    public String getCountFromStorage() {
        return ((List<Long>) session.createQuery("select count(*) from ProductEntity where dateShipment = null and template = false and deleted != true ").list()).get(0).toString();
    }

    public String getCount() {
        return ((List<Long>) session.createQuery("select count(*) from ProductEntity where  template = false and deleted != true ").list()).get(0).toString();
    }

}
