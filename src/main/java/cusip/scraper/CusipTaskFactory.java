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

/**
 * A factory object that creates new {@link EmmaCusipTask} objects.  This is used
 * to allow easier injections into the task objects.
 *
 * @author Michael Irwin
 */
public interface CusipTaskFactory {

  /**
   * Create a new task
   * @param cusip The cusip for the task
   * @return The newly created task
   */
  EmmaCusipTask createCusipTask(String cusip);
}
