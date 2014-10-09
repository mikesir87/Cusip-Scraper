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

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * An implementation of the {@link CusipReporter} that saves the results to
 * a CSV.
 *
 * @author Michael Irwin
 */
public class CsvCusipReporter implements CusipReporter {
  
  private static final String FILE_NAME = "output.csv";
  
  protected String fileName = FILE_NAME;

  /**
   * {@inheritDoc}
   */
  public void report(List<CusipSearchResult> results) {
    try {
      CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));
      List<String[]> data  = toStringArray(results);
      csvWriter.writeAll(data);
      csvWriter.close();
      
      System.out.println("--- Data has been successfully saved to " + fileName);
    } catch (Exception e) {
      throw new RuntimeException("Unable to save report", e);
    }
  }

  private List<String[]> toStringArray(List<CusipSearchResult> results) {
    List<String[]> array = new ArrayList<String[]>();
    array.add(new String[] { 
        "CUSIP", "Entity", "State", "Initial Offering Price", "Initial Offering Yield" 
    });
    
    for (CusipSearchResult result : results) {
      if (result == null)
        System.out.println("HUH");
      array.add(new String[] { 
          result.getCusip(), 
          result.getEntityName(),
          result.getState(),
          result.getInitialOfferingPrice(), 
          result.getInitialOfferingYield() 
      });
    }
    return array;
  }
}
