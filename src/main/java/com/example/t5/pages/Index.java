package com.example.t5.pages;


import com.example.t5.data.Units;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.entities.view.Storage;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
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
public class Index
{
  @Inject
  private Logger logger;

  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;

  @Property
  @Inject
  @Symbol(SymbolConstants.TAPESTRY_VERSION)
  private String tapestryVersion;

  @InjectPage
  private About about;

  @Inject
  private Block block;

  @Inject
  private Session session;

  @Property
  private Storage storage;

  public List<Storage> getListStorage() {
    List<SourceSorageEntity> list = session.createCriteria(SourceSorageEntity.class).list();
    Map<String, Double> map = new HashMap<String, Double>();
    List<String> nameList = new ArrayList<String>();
    List<Storage> storageList = new ArrayList<Storage>();
    for (SourceSorageEntity storageEntity : list){
    Double count =  map.get(storageEntity.getSource().getName());
      if(count == null) {
        map.put(storageEntity.getSource().getName(), storageEntity.getCount());
        nameList.add(storageEntity.getSource().getName());
      } else {
        map.put(storageEntity.getSource().getName(), storageEntity.getCount()+count);
      }
    }
    for (String name : nameList){
    storageList.add(new Storage(name, "0001",  map.get(name), Units.KG));
  }
   return storageList;
  }

  // Handle call with an unwanted context
  Object onActivate(EventContext eventContext)
  {
    return eventContext.getCount() > 0 ?
        new HttpError(404, "Resource not found") :
        null;
  }


  Object onActionFromLearnMore()
  {
    about.setLearn("LearnMore");

    return about;
  }

  @Log
  void onComplete()
  {
    logger.info("Complete call on Index page");
  }

  @Log
  void onAjax()
  {
    logger.info("Ajax call on Index page");

    ajaxResponseRenderer.addRender("middlezone", block);
  }


  public Date getCurrentTime()
  {
    return new Date();
  }
}
