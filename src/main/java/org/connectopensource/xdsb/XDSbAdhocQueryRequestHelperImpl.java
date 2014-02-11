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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.xdsb.XDSbConstants.RegistryStoredQueryParameter;

/**
 * The Class XDSbAdhocQueryRequestHelperImpl.
 * 
 * @author msw
 */
@Named
public class XDSbAdhocQueryRequestHelperImpl implements XDSbAdhocQueryRequestHelper {

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper#createOrSetSlotValue(org.connectopensource.xdsb.XDSbConstants.
     * RegistryStoredQueryParameter, java.lang.String, oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest)
     */
    @Override
    public void createOrReplaceSlotValue(RegistryStoredQueryParameter slotName, String value, AdhocQueryRequest message) {

        if (message != null && message.getAdhocQuery() != null && message.getAdhocQuery().getSlot() != null) {
            for (SlotType1 slot : message.getAdhocQuery().getSlot()) {
                if (slot != null) {
                    if (StringUtils.equalsIgnoreCase(slot.getName(), slotName.toString())) {
                        message.getAdhocQuery().getSlot().remove(slot);
                        break;
                    }
                }
            }
            message.getAdhocQuery().getSlot().add(createSlot(slotName, value));
        }
    }

    /**
     * Creates a slot.
     * 
     * @param slotName the slot name
     * @param value the value
     * @return the slot type1
     */
    protected SlotType1 createSlot(RegistryStoredQueryParameter slotName, String value) {
        SlotType1 slot = new SlotType1();
        slot.setName(slotName.toString());
        slot.setValueList(new ValueListType());
        slot.getValueList().getValue().add(value);
        return slot;
    }

    /**
     * Creates a slot.
     * 
     * @param slotName the slot name
     * @param value the value
     * @return the slot type1
     */
    protected SlotType1 createSlot(RegistryStoredQueryParameter slotName, List<String> values) {
        SlotType1 slot = new SlotType1();
        slot.setName(slotName.toString());
        slot.setValueList(new ValueListType());
        slot.getValueList().getValue().addAll(values);
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper#formatXDSbDate(java.util.Date)
     */
    @Override
    public String formatXDSbDate(Date date) {
        String sFormattedDate = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            sFormattedDate = sdf.format(date);
            sFormattedDate = StringUtils.stripEnd(sFormattedDate, "0000000000");
            sFormattedDate = StringUtils.stripEnd(sFormattedDate, "00000000");
            sFormattedDate = StringUtils.stripEnd(sFormattedDate, "000000");
            sFormattedDate = StringUtils.stripEnd(sFormattedDate, "0000");
            sFormattedDate = StringUtils.stripEnd(sFormattedDate, "00");
        }
        return sFormattedDate;
    }

    /* (non-Javadoc)
     * @see org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper#createSingleQuoteDelimitedValue(java.lang.String)
     */
    @Override
    public String createSingleQuoteDelimitedValue(String value) {
        if (!StringUtils.startsWith(value, "'")) {
            value = "'".concat(value);
        }
        if (!StringUtils.endsWith(value, "'")) {
            value = value.concat("'");
        }
        return value;
    }

    /* (non-Javadoc)
     * @see org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper#createSingleQuoteDelimitedList(java.util.List)
     */
    @Override
    public String createSingleQuoteDelimitedListValue(List<String> values) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (String s : values) {
            builder.append(createSingleQuoteDelimitedValue(s));
        }
        builder.append(")");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper#createCodeSchemeValue(java.lang.String, java.lang.String)
     */
    @Override
    public String createCodeSchemeValue(String documentTypeCode, String scheme) {
        StringBuilder builder = new StringBuilder();
        builder.append("('");
        builder.append(documentTypeCode);
        builder.append("^^");
        builder.append(scheme);
        builder.append("')");
        return builder.toString();
    }

}
