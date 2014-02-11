/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.messaging.builder.impl.dq;

import java.util.Collections;
import java.util.Date;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.xdsb.XDSbAdhocQueryRequestHelper;
import org.connectopensource.xdsb.XDSbAdhocQueryRequestHelperImpl;
import org.connectopensource.xdsb.XDSbConstants;
import org.connectopensource.xdsb.XDSbConstants.RegistryStoredQueryParameter;
import org.connectopensource.xdsb.XDSbConstants.XDSQueryStatus;
import org.connectopensource.xdsb.XDSbConstants.XDSbStoredQuery;

/**
 * The Class FindDocumentsAdhocQueryRequestBuilder. Creates an AdhocQueryRequest message, required values are patient id
 * and query status. Status will default to Approved.
 * 
 * @author msw
 */
public class FindDocumentsAdhocQueryRequestBuilder extends AbstractAdhocQueryRequestBuilder {

    /** The helper. */
    private XDSbAdhocQueryRequestHelper helper = new XDSbAdhocQueryRequestHelperImpl();

    /** The request. */
    private AdhocQueryRequest request = null;

    /** The query id. */
    private XDSbStoredQuery queryId = XDSbStoredQuery.FindDocuments;

    /** The patient id. */
    private String patientId = null;

    private String patientIdRoot = null;

    /** The status. */
    private XDSQueryStatus status = XDSQueryStatus.APPROVED;

    /** The creation time from. */
    private Date creationTimeFrom = null;

    /** The creation time to. */
    private Date creationTimeTo = null;

    /** The document type code. */
    private String documentTypeCode = null;

    /**
     * Instantiates a new find documents adhoc query request builder.
     */
    FindDocumentsAdhocQueryRequestBuilder() {

    }

    /**
     * Instantiates a new find documents adhoc query request builder.
     * 
     * @param helper the helper
     */
    FindDocumentsAdhocQueryRequestBuilder(XDSbAdhocQueryRequestHelper helper) {
        this.helper = helper;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.dq.AdhocQueryRequestBuilder#build()
     */
    @Override
    public void build() {
        if (StringUtils.isBlank(patientId)) {
            throw new IllegalArgumentException("Patient Id is a required value.");
        }

        super.build();
        request = super.getMessage();

        // required parameters
        StringBuilder builder = new StringBuilder();
        builder.append(patientId);
        builder.append("^^^&");
        builder.append(patientIdRoot);
        builder.append("&ISO");
        String qualifiedPatientId = builder.toString();
        String delimitedPatientId = helper.createSingleQuoteDelimitedValue(qualifiedPatientId);
        helper.createOrReplaceSlotValue(RegistryStoredQueryParameter.$XDSDocumentEntryPatientId, delimitedPatientId,
                request);
        String delimitedStatuses = helper.createSingleQuoteDelimitedListValue(Collections.singletonList(status
                .toString()));
        helper.createOrReplaceSlotValue(RegistryStoredQueryParameter.$XDSDocumentEntryStatus, delimitedStatuses,
                request);

        // optional parameters
        if (creationTimeFrom != null) {
            helper.createOrReplaceSlotValue(RegistryStoredQueryParameter.$XDSDocumentEntryCreationTimeFrom,
                    helper.formatXDSbDate(creationTimeFrom), request);
        }
        if (creationTimeTo != null) {
            helper.createOrReplaceSlotValue(RegistryStoredQueryParameter.$XDSDocumentEntryCreationTimeTo,
                    helper.formatXDSbDate(creationTimeTo), request);
        }
        if (!StringUtils.isBlank(documentTypeCode)) {
            helper.createOrReplaceSlotValue(RegistryStoredQueryParameter.$XDSDocumentEntryTypeCode,
                    helper.createCodeSchemeValue(documentTypeCode, "LOINC"), request);
        }

        /*
         * TODO add any additional parameters to the request message. Also add methods to also the builder's director to
         * set those parameters prior to invoking the build method.
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.dq.AdhocQueryRequestBuilder#getMessage()
     */
    @Override
    public AdhocQueryRequest getMessage() {
        return request;
    }

    /**
     * Sets the patient id.
     * 
     * @param patientId the new patient id
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Sets the query status.
     * 
     * @param status the new query status
     */
    public void setQueryStatus(XDSbConstants.XDSQueryStatus status) {
        this.status = status;
    }

    /**
     * Sets the creation time from.
     * 
     * @param creationTimeFrom the new creation time from
     */
    public void setCreationTimeFrom(Date creationTimeFrom) {
        this.creationTimeFrom = creationTimeFrom;
    }

    /**
     * Sets the creation time to.
     * 
     * @param creationTimeTo the new creation time to
     */
    public void setCreationTimeTo(Date creationTimeTo) {
        this.creationTimeTo = creationTimeTo;
    }

    /**
     * Sets the document type code.
     * 
     * @param documentTypeCode the new document type code
     */
    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.dq.AdhocQueryRequestBuilder#getQueryType()
     */
    @Override
    public XDSbStoredQuery getQueryId() {
        return queryId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AdhocQueryRequestBuilder#setPatientIdRoot(java.lang.String)
     */
    @Override
    public void setPatientIdRoot(String patientIdRoot) {
        this.patientIdRoot = patientIdRoot;
    }

}
