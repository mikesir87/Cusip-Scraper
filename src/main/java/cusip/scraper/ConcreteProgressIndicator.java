/*
 * File created on Oct 9, 2014 
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

/**
 * A pretty simple implementation of the {@link ProgressIndicator}.
 *
 * @author Michael Irwin
 */
public class ConcreteProgressIndicator implements ProgressIndicator {

  private static final String LOG_MESSAGE = "---- Completed %d of %d (%d%%)";
  
  private Integer totalCount;
  private Integer numCompleted = 0;
  
  /**
   * {@inheritDoc}
   */
  public void setTotalCount(int totalCount) {
    if (this.totalCount != null)
      throw new IllegalStateException("The total count has already been set!");
    this.totalCount = totalCount;
    System.out.println("--- Will be fetching details for " + totalCount + " cusips");
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void incrementCompletedCount() {
    numCompleted++;
    if (numCompleted % 50 == 0) {
      System.out.println(String.format(LOG_MESSAGE, numCompleted, totalCount, 
          numCompleted / totalCount * 100)); 
    }
  }
  
}
