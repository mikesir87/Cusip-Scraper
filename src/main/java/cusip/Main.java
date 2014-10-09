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

import java.util.List;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cusip.scraper.ProgressIndicator;

/**
 * The main class that runs the program.
 *
 * @author Michael Irwin
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Injector injector = Guice.createInjector(new GuiceModule());
    Main main = injector.getInstance(Main.class);
    main.run();
  }
  
  @Inject
  private CusipRetriever retriever;
  
  @Inject
  private CusipScraper scraper;
  
  @Inject
  private CusipReporter reporter;
  
  @Inject
  private ProgressIndicator progressIndicator;
  
  public void run() {
    List<String> cusips = retriever.getCusipsToFind();
    progressIndicator.setTotalCount(cusips.size());
    List<CusipSearchResult> results = scraper.scrapeCusips(cusips);
    System.out.println("--- Completed fetching details for each cusip");
    reporter.report(results);
    
    System.out.println("--- Here's another output:");
    for (CusipSearchResult result : results) {
      System.out.println(result);
    }
  }
}
