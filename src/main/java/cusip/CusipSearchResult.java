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

/**
 * A definition for a search result while searching for a CUSIP value.
 *
 * @author Michael Irwin
 */
public interface CusipSearchResult {

  /**
   * Get the cusip value being searched for
   * @return The cusip value
   */
  String getCusip();
  
  /**
   * Get the entity name
   * @return The entity name
   */
  String getEntityName();
  
  /**
   * Get the state
   * @return The state
   */
  String getState();
  
  /**
   * Get the initial offering price
   * @return The initial offering price. Can be null
   */
  String getInitialOfferingPrice();
  
  /**
   * Get the initial offering yield
   * @return The initial offering yield. Can be null
   */
  String getInitialOfferingYield();
}
