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
 * Defines a service that is used as a progress indicator.  It is loaded 
 * initially with the number of scrapes to be performed and is expected to be
 * called each time a scrape has been completed.
 *
 * @author Michael Irwin
 */
public interface ProgressIndicator {

  /**
   * Set the total count of scrapes that will be performed
   * @param totalCount The total count
   */
  void setTotalCount(int totalCount);
  
  /**
   * Indicate that another scrape has been completed.  It is expected that this
   * method will be exxecuted in a threadsafe manner. ;)
   */
  void incrementCompletedCount();
}
