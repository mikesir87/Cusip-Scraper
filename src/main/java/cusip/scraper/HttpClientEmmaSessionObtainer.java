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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * An implementation of the {@link EmmaSessionObtainer} that uses a 
 * {@link HttpClient} to do its dirty work.
 *
 * @author Michael Irwin
 */
public class HttpClientEmmaSessionObtainer implements EmmaSessionObtainer {

  private static final String FORM_ID = "aspnetForm";
  private static final String URI = "http://emma.msrb.org/SecurityView/SecurityDetails.aspx?cusip=430047BK";
  private static final String SUBMIT_URI = "http://emma.msrb.org/Disclaimer.aspx?cusip=430047BK";
  private static final String YES_BUTTON = "ctl00$mainContentArea$disclaimerContent$yesButton";
  private static final String NO_BUTTON = "ctl00$mainContentArea$disclaimerContent$noButton";
  
  private static final Lock lock = new ReentrantLock();
  
  private static String cookieValue;
  
  @Inject
  protected CloseableHttpClient httpClient;
  
  /**
   * {@inheritDoc}
   */
  public String getSessionCookie() {
    lock.lock();
    try {
      if (cookieValue == null)
        cookieValue = fetchSessionCookie();
      return cookieValue;
    } finally {
      lock.unlock();
    }
  }
  
  private String fetchSessionCookie() {
    try {
      String initialBody = getBody();
      List<NameValuePair> formParams = getFormParams(initialBody);
      HttpEntity entity = EntityBuilder.create().setParameters(formParams).build();
      HttpPost post = new HttpPost(SUBMIT_URI);
      post.setEntity(entity);
      CloseableHttpResponse response = httpClient.execute(post);
      try {
        String cookieValue = response.getFirstHeader("Set-Cookie").getValue();
        return cookieValue.substring(0, cookieValue.indexOf(";"));
      } finally {
        response.close();
      }
    } catch (Exception e) {
      throw new RuntimeException("Problem starting session", e);
    }
  }

  private String getBody() throws Exception {
    HttpResponse response = httpClient.execute(new HttpGet(URI));
    return EntityUtils.toString(response.getEntity());
  }
  
  private List<NameValuePair> getFormParams(String responseData) {
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    Document doc = Jsoup.parse(responseData);

    Element loginform = doc.getElementById(FORM_ID);
    Elements inputElements = loginform.getElementsByTag("input");
   
    for (Element inputElement : inputElements) {
      String key = inputElement.attr("name");
      String value = inputElement.attr("value");
      if (key.equals(NO_BUTTON))
        continue;
      if (!key.equals(YES_BUTTON)) {
        params.add(new BasicNameValuePair(key, value));
      }
      else {
        params.add(new BasicNameValuePair(YES_BUTTON + ".x", "0"));
        params.add(new BasicNameValuePair(YES_BUTTON + ".y", "0"));
      }
    }
    
    return params;
  }
}
