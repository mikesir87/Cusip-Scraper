/*
 * File created on Oct 8, 2014 
 *
 * Copyright 2013-2014 Nerdwin15, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cusip.scraper;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cusip.CusipSearchResult;

/**
 * A callable task that is able to go scrape the details for a specific cusip
 * using the emma.msrg.org website.
 *
 * @author Michael Irwin
 */
public class EmmaCusipTask implements Callable<CusipSearchResult> {
  
  protected static final String URI = "http://emma.msrb.org/SecurityView/SecurityDetails.aspx?cusip=";
  
  protected static final Pattern STATE_PATTERN = Pattern.compile(".*\\((\\w\\w)\\).*");
  
  protected static final String ENTITY_SELECTOR = "a[id$=topLevelIssueDataLink]";
  protected static final String OFFERING_PRICE_SELECTOR = 
      "span[id$=nitialOfferingPriceDataLabel]";
  protected static final String OFFERING_YIELD_SELECTOR = 
      "span[id$=nitialOfferingYieldDataLabel]";

  @Inject
  protected CloseableHttpClient httpClient;
  
  @Inject
  protected EmmaSessionObtainer sessionObtainer;
  
  @Inject
  protected ProgressIndicator progressIndicator;

  private String cusip;
  
  /**
   * {@inheritDoc}
   */
  public CusipSearchResult call() throws Exception {
    ConcreteCusipSearchResult result = new ConcreteCusipSearchResult(cusip);
    HttpGet getRequest = new HttpGet(URI + cusip);
    getRequest.addHeader("Cookie", sessionObtainer.getSessionCookie());
    CloseableHttpResponse response = httpClient.execute(getRequest);
    try {
      String responseBody = EntityUtils.toString(response.getEntity());
      Document doc = Jsoup.parse(responseBody);
      
      Element entity = doc.select(ENTITY_SELECTOR).first();
      if (entity != null) {
        String cusipEntity = entity.text();
        result.setEntityName(cusipEntity);
        
        Matcher matcher = STATE_PATTERN.matcher(cusipEntity);
        if (matcher.matches())
          result.setState(matcher.group(1));
      }
      
      Element initialOfferingPrice = doc.select(OFFERING_PRICE_SELECTOR).first();
      if (initialOfferingPrice != null)
        result.setInitialOfferingPrice(initialOfferingPrice.text());
      
      Element initialOfferingYield = doc.select(OFFERING_YIELD_SELECTOR).first();
      if (initialOfferingYield != null)
        result.setInitialOfferingYield(initialOfferingYield.text());
      return result;
    }
    finally {
      response.close();
      progressIndicator.incrementCompletedCount();
    }
  }

  protected void setCusip(String cusip) {
    this.cusip = cusip;
  }

}
