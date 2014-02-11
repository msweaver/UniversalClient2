/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.services.impl;

import javax.inject.Inject;
import javax.inject.Named;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.commons.lang.StringUtils;
import org.connectopensource.client.DocumentRegistryClient;
import org.connectopensource.messaging.builder.AdhocQueryRequestBuilder;
import org.connectopensource.model.DocumentMetadata;
import org.connectopensource.model.DocumentMetadataResults;
import org.connectopensource.model.builder.DocumentMetadataResultsModelBuilder;
import org.connectopensource.services.DocumentMetadataService;
import org.connectopensource.services.exception.DocumentMetadataException;

/**
 * The Class DocumentMetadataServiceImpl.
 */
@Named
public class DocumentMetadataServiceImpl implements DocumentMetadataService {

    /** The registry client. */
    @Inject
    private DocumentRegistryClient registryClient;

    /** The request builder. */
    @Inject
    private AdhocQueryRequestBuilder requestBuilder;

    /** The response builder. */
    @Inject
    private DocumentMetadataResultsModelBuilder responseBuilder;

    /**
     * Instantiates a new document metadata service impl.
     */
    public DocumentMetadataServiceImpl() {

    }

    /**
     * Instantiates a new document metadata service impl.
     * 
     * @param registryClient the registry client
     * @param requestBuilder the request builder
     * @param responseBuilder the response builder
     */
    DocumentMetadataServiceImpl(DocumentRegistryClient registryClient, AdhocQueryRequestBuilder requestBuilder,
            DocumentMetadataResultsModelBuilder responseBuilder) {
        this.registryClient = registryClient;
        this.requestBuilder = requestBuilder;
        this.responseBuilder = responseBuilder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.services.DocumentMetadataService#queryForDocuments(org.connectopensource.model.DocumentMetadata)
     */
    @Override
    public DocumentMetadataResults queryForDocuments(DocumentMetadata query) throws DocumentMetadataException {
        AdhocQueryRequest request = createAdhocQueryRequest(query);
        AdhocQueryResponse response = registryClient.findDocuments(request);
        return createDocumentMetadataResult(response);
    }

    /**
     * Creates the document metadata result.
     * 
     * @param response the response
     * @return the document metadata results
     */
    private DocumentMetadataResults createDocumentMetadataResult(AdhocQueryResponse response) {
        responseBuilder.setAdhocQueryResponse(response);
        responseBuilder.build();
        return responseBuilder.getResults();
    }

    /**
     * Creates the adhoc query request.
     * 
     * @param query the query
     * @return the adhoc query request
     */
    private AdhocQueryRequest createAdhocQueryRequest(DocumentMetadata query) {
        if (!StringUtils.isBlank(query.getPatientId())) {
            requestBuilder.setPatientId(query.getPatientId());
        }
        if (!StringUtils.isBlank(query.getPatientIdRoot())) {
            requestBuilder.setPatientIdRoot(query.getPatientIdRoot());
        }

        requestBuilder.setDocumentTypeCode(query.getDocumentType());

        requestBuilder.setCreationTimeFrom(query.getStartTime());

        requestBuilder.setCreationTimeTo(query.getEndTime());

        requestBuilder.build();
        return requestBuilder.getMessage();
    }

}
