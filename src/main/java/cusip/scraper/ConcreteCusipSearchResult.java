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

import cusip.CusipSearchResult;

/**
 * A simple implementation of the {@link CusipSearchResult}.
 *
 * @author Michael Irwin
 */
public class ConcreteCusipSearchResult implements CusipSearchResult {

  private String cusip;
  private String entityName;
  private String state;
  private String initialOfferingPrice;
  private String initialOfferingYield;
  
  public ConcreteCusipSearchResult(String cusip) {
    this.cusip = cusip;
  }
  
  /**
   * {@inheritDoc}
   */
  public String getCusip() {
    return cusip;
  }

  /**
   * {@inheritDoc}
   */
  public String getEntityName() {
    return entityName;
  }
  
  /**
   * Sets the {@code entityName} property.
   */
  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  /**
   * {@inheritDoc}
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the {@code state} property.
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * {@inheritDoc}
   */
  public String getInitialOfferingPrice() {
    return initialOfferingPrice;
  }
  
  /**
   * Sets the {@code initialOfferingPrice} property.
   */
  public void setInitialOfferingPrice(String initialOfferingPrice) {
    this.initialOfferingPrice = initialOfferingPrice;
  }

  /**
   * {@inheritDoc}
   */
  public String getInitialOfferingYield() {
    return initialOfferingYield;
  }
  
  /**
   * Sets the {@code initialOfferingYield} property.
   */
  public void setInitialOfferingYield(String initialOfferingYield) {
    this.initialOfferingYield = initialOfferingYield;
  }

  @Override
  public String toString() {
    return new StringBuilder("CUSIP: ")
        .append(cusip)
        .append("; Entity: ")
        .append(entityName)
        .append("; State: ")
        .append(state)
        .append("; Initial Price: ")
        .append(initialOfferingPrice)
        .append("; Initial Yield: ")
        .append(initialOfferingYield)
        .toString();
  }
}
