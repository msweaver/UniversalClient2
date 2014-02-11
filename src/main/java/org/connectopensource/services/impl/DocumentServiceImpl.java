/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.services.impl;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.client.DocumentRepositoryClient;
import org.connectopensource.client.impl.DocumentRepositoryClientImpl;
import org.connectopensource.messaging.builder.DocumentRequestBuilder;
import org.connectopensource.messaging.builder.impl.dr.DocumentRequestBuilderImpl;
import org.connectopensource.model.DocumentResults;
import org.connectopensource.model.DocumentSearch;
import org.connectopensource.model.builder.DocumentResultsModelBuilder;
import org.connectopensource.model.builder.impl.dr.DocumentResultsModelBuilderImpl;
import org.connectopensource.services.DocumentService;
import org.connectopensource.services.exception.DocumentException;

public class DocumentServiceImpl implements DocumentService {

    private DocumentRepositoryClient repoClient;

    private DocumentRequestBuilder requestBuilder;

    private DocumentResultsModelBuilder responseBuilder;

    public DocumentServiceImpl() {
        repoClient = new DocumentRepositoryClientImpl();
        requestBuilder = new DocumentRequestBuilderImpl();
        responseBuilder = new DocumentResultsModelBuilderImpl();
    }

    @Override
    public DocumentResults retrieveDocument(DocumentSearch query) throws DocumentException {
        RetrieveDocumentSetRequestType request = createRetrieveDocumentSetRequest(query);
        RetrieveDocumentSetResponseType response = repoClient.retrieveDocument(request);
        return createDocumentResults(response);
    }

    /**
     * @param query
     * @return
     */
    private RetrieveDocumentSetRequestType createRetrieveDocumentSetRequest(DocumentSearch query) {
        if (!StringUtils.isBlank(query.getHcid())) {
            requestBuilder.setHomeCommunityId(query.getHcid());
        }
        if (!StringUtils.isBlank(query.getRepoId())) {
            requestBuilder.setRepositoryId(query.getRepoId());
        }
        if (!StringUtils.isBlank(query.getDocumentId())) {
            requestBuilder.setDocumentId(query.getDocumentId());
        }

        requestBuilder.build();
        return requestBuilder.getMessage();
    }

    /**
     * @param response
     * @return
     */
    private DocumentResults createDocumentResults(RetrieveDocumentSetResponseType response) {
        responseBuilder.setRetrieveDocumentSetResponseType(response);
        responseBuilder.build();
        return responseBuilder.getResults();
    }

}
