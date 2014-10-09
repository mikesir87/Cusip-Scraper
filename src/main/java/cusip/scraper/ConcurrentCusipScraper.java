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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

import cusip.CusipScraper;
import cusip.CusipSearchResult;

/**
 * An implementation of the {@link CusipScraper} that uses a threadpool to farm
 * out the actual work.
 *
 * @author Michael Irwin
 */
public class ConcurrentCusipScraper implements CusipScraper {

  @Inject
  private ExecutorService executorService;
  
  @Inject
  private CusipTaskFactory taskFactory;
  
  /**
   * {@inheritDoc}
   */
  public List<CusipSearchResult> scrapeCusips(List<String> cusips) {
    List<Callable<CusipSearchResult>> tasks = new ArrayList<Callable<CusipSearchResult>>();
    for (String cusip : cusips) {
      tasks.add(taskFactory.createCusipTask(cusip));
    }
    
    // Now that we have a bunch of tasks, hand them off to a thread pool to do
    // the work. Then, take out the results, combine them into our own list and
    // return them. We aren't expecting any exceptions, so wrap them and rethrow
    try {
      List<Future<CusipSearchResult>> resultFutures = executorService.invokeAll(tasks);
      List<CusipSearchResult> results = new ArrayList<CusipSearchResult>();
      for (Future<CusipSearchResult> future : resultFutures) {
        results.add(future.get());
      }
      return results;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
}
