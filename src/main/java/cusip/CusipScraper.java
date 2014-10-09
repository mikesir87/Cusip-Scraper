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

/**
 * Defines a service that is used to scrape data for CUSIPs.
 *
 * @author Michael Irwin
 */
public interface CusipScraper {

  /**
   * Search for all of the provided cusips.  It is expected that this method
   * will be blocking until all search results have done completed
   * @param cusips The values to search
   * @return The search results
   */
  List<CusipSearchResult> scrapeCusips(List<String> cusips);
}
