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

import org.connectopensource.xdsb.XDSbConstants.ClassificationScheme;
import org.connectopensource.xdsb.XDSbConstants.IdentificationScheme;

import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectType;

/**
 * The Interface XDSbAdhocQueryResponseHelper.
 *
 * @author msw
 */
public interface XDSbAdhocQueryResponseHelper {

    /**
     * Gets the single slot value.
     *
     * @param slotName the slot name
     * @param identifiableType the identifiable type
     * @return the single slot value
     */
    public String getSingleSlotValue(XDSbConstants.ResponseSlotName slotName, IdentifiableType identifiableType);

    /**
     * Gets the single slot value.
     *
     * @param customSlotName the custom slot name
     * @param identifiableType the identifiable type
     * @return the single slot value
     */
    public String getSingleSlotValue(String customSlotName, IdentifiableType identifiableType);

    /**
     * Gets the status.
     *
     * @param extrinsicObject the extrinsic object
     * @return the status
     */
    public String getStatus(ExtrinsicObjectType extrinsicObject);

    /**
     * Gets the title.
     *
     * @param extrinsicObject the extrinsic object
     * @return the title
     */
    public String getTitle(ExtrinsicObjectType extrinsicObject);

    /**
     * Gets the classification value.
     *
     * @param classification the classification
     * @param extrinsicObject the extrinsic object
     * @return the classification value
     */
    public String getClassificationValue(ClassificationScheme classification, ExtrinsicObjectType extrinsicObject);
    
    /**
     * Gets the classification.
     *
     * @param classification the classification
     * @param extrinsicObject the extrinsic object
     * @return the classification
     */
    public RegistryObjectType getClassification(ClassificationScheme classification, ExtrinsicObjectType extrinsicObject);

    /**
     * Gets the external identifier value.
     *
     * @param patientid the patientid
     * @param extrinsicObject the extrinsic object
     * @return the external identifier value
     */
    public String getExternalIdentifierValue(IdentificationScheme patientid, ExtrinsicObjectType extrinsicObject);

}
