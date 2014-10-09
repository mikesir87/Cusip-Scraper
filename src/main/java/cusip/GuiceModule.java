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
package cusip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import cusip.scraper.ConcreteCusipTaskFactory;
import cusip.scraper.ConcurrentCusipScraper;
import cusip.scraper.CusipTaskFactory;
import cusip.scraper.EmmaSessionObtainer;
import cusip.scraper.HttpClientEmmaSessionObtainer;

/**
 * Module definitions for setting up the project for Guice.
 *
 * @author Michael Irwin
 */
public class GuiceModule extends AbstractModule {
  
  public static final String COOKIE_NAME = "sessionCookieValue";
  
  private static final Integer THREADPOOL_SIZE = 10;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    bind(CusipRetriever.class).to(FileBasedCusipRetriever.class).asEagerSingleton();
    bind(CusipScraper.class).to(ConcurrentCusipScraper.class).asEagerSingleton();
    bind(CusipTaskFactory.class).to(ConcreteCusipTaskFactory.class).asEagerSingleton();
    bind(EmmaSessionObtainer.class).to(HttpClientEmmaSessionObtainer.class).asEagerSingleton();
    bind(CusipReporter.class).to(CsvCusipReporter.class).asEagerSingleton();
  }
  
  /**
   * Definition for an executorService that is shared amongst all injection
   * points
   */
  @Provides @Singleton
  private ExecutorService executorService() {
    return Executors.newFixedThreadPool(THREADPOOL_SIZE);
  }
  
  /**
   * Definition for the HttpClient. This configures the client to work in a
   * multi-threaded environment.
   */
  @Provides @Singleton
  public static CloseableHttpClient httpClient() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(100);
    return HttpClients.custom().setConnectionManager(cm).build();
  }

}
