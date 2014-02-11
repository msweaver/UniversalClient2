/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.xdsb;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import java.util.Date;
import java.util.List;

/**
 * The Interface XDSbAdhocQueryRequestHelper.
 * 
 * @author msw
 */
public interface XDSbAdhocQueryRequestHelper {

    /**
     * Creates a slot with the given name and a single value. If a slot already exists, its value will be replaced with
     * the passed in value, even in cases where there are multiple existing values. For example, if a slot exists with 3
     * values, and this method is invoked the resulting slot will have a single value.
     * 
     * @param slotName the slot name
     * @param value the value
     * @param message the message
     */
    public void createOrReplaceSlotValue(XDSbConstants.RegistryStoredQueryParameter slotName, String value,
            AdhocQueryRequest message);
    
    /**
     * Format date in UTC representation with no trailing zeros.
     *
     * @param date the date
     * @return the string
     */
    public String formatXDSbDate(Date date);
    
    /**
     * Creates the single quote delimited value.
     *
     * @param value the value
     * @return the string
     */
    public String createSingleQuoteDelimitedValue(String value);
    
    /**
     * Creates the single quote delimited list.
     *
     * @param values the values
     * @return the list
     */
    public String createSingleQuoteDelimitedListValue(List<String> values);

    /**
     * @param documentTypeCode
     * @param string
     * @return
     */
    public String createCodeSchemeValue(String documentTypeCode, String string);

}
