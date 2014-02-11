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

import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.LocalizedStringType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.apache.commons.lang.StringUtils;
import org.connectopensource.xdsb.XDSbConstants.ClassificationScheme;
import org.connectopensource.xdsb.XDSbConstants.IdentificationScheme;
import org.connectopensource.xdsb.XDSbConstants.ResponseSlotName;

/**
 * @author msw
 * 
 */
public class XDSbAdhocQueryResponseHelperImpl implements XDSbAdhocQueryResponseHelper {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getSingleSlotValue(org.connectopensource.xdsb.XDSbConstants.ResponseSlotName,
     * javax.xml.registry.infomodel.ExtrinsicObject)
     */
    @Override
    public String getSingleSlotValue(ResponseSlotName slotName, IdentifiableType identifiableType) {
        return getSingleSlotValue(slotName.toString(), identifiableType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getSingleSlotValue(java.lang.String,
     * javax.xml.registry.infomodel.ExtrinsicObject)
     */
    @Override
    public String getSingleSlotValue(String customSlotName, IdentifiableType identifiableType) {
        List<SlotType1> slots = identifiableType.getSlot();
        for (SlotType1 s : slots) {
            if (StringUtils.equalsIgnoreCase(customSlotName, s.getName()) && s.getValueList() != null) {
                ValueListType vlt = s.getValueList();
                for (String string : vlt.getValue()) {
                    return string;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getStatus(oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public String getStatus(ExtrinsicObjectType extrinsicObject) {
        String status = null;
        if (extrinsicObject != null) {
            status = extrinsicObject.getStatus();
        }
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getTitle(oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public String getTitle(ExtrinsicObjectType extrinsicObject) {
        String title = null;
        if (extrinsicObject != null && extrinsicObject.getName() != null
                && extrinsicObject.getName().getLocalizedString() != null) {
            List<LocalizedStringType> names = extrinsicObject.getName().getLocalizedString();
            if (names.size() >= 1 && extrinsicObject.getName().getLocalizedString().get(0) != null) {
                title = extrinsicObject.getName().getLocalizedString().get(0).getValue();
            }
            
        }
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getClassificationValue(org.connectopensource.xdsb.XDSbConstants.
     * ResponseClassification, oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public String getClassificationValue(ClassificationScheme classification, ExtrinsicObjectType extrinsicObject) {
        RegistryObjectType registryObjectType = getClassification(classification, extrinsicObject);
        ClassificationType classType = null;
        if (registryObjectType instanceof ClassificationType) {
            classType = (ClassificationType) registryObjectType;
        }
        return classType.getNodeRepresentation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getExternalIdentifierValue(org.connectopensource.xdsb.XDSbConstants.
     * IdentificationScheme, oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public String getExternalIdentifierValue(IdentificationScheme idScheme, ExtrinsicObjectType extrinsicObject) {
        String value = null;
        List<ExternalIdentifierType> externalIds = extrinsicObject.getExternalIdentifier();
        for (ExternalIdentifierType id : externalIds) {
            if (StringUtils.equalsIgnoreCase(idScheme.toString(), id.getIdentificationScheme())) {
                value = id.getValue();
            }
        }
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper#getClassification(org.connectopensource.xdsb.XDSbConstants.ClassificationScheme,
     * oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public RegistryObjectType getClassification(ClassificationScheme classification, ExtrinsicObjectType extrinsicObject) {
        RegistryObjectType registryObject = null;
        if (extrinsicObject != null && classification != null) {
            List<ClassificationType> classifications = extrinsicObject.getClassification();
            for (ClassificationType c : classifications) {
                if (StringUtils.equalsIgnoreCase(c.getClassificationScheme(), classification.toString())) {
                    registryObject = c;
                }
            }
        }
        return registryObject;
    }

}
