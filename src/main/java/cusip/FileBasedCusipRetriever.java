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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the {@link CusipRetriever} that uses a local 
 * line-delimited file to provide the cusips.
 *
 * @author Michael Irwin
 */
public class FileBasedCusipRetriever implements CusipRetriever {

  protected static final String FILE_NAME = "cusips.txt";
  protected String fileName = FILE_NAME;
  
  /**
   * {@inheritDoc}
   */
  public List<String> getCusipsToFind() {
    List<String> cusips = new ArrayList<String>();
    
    try {
      InputStream inputStream = 
          getClass().getClassLoader().getResourceAsStream(fileName);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      String line = null;
      while ((line = br.readLine()) != null) {
        if (!line.trim().isEmpty())
          cusips.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException("Problem reading " + FILE_NAME, e);
    }
    return cusips;
  }
  
  protected void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
}
