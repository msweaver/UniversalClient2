/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.messaging.builder;

import java.util.Date;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;

import org.connectopensource.xdsb.XDSbConstants.ReturnType;
import org.connectopensource.xdsb.XDSbConstants.XDSbStoredQuery;

/**
 * The Interface AdhocQueryRequestBuilder. This interface is a super set of all query types. It is expected that
 * individual builders throw exceptions for values which do no correspond to that specific builder implementation.
 * 
 * @author msw
 */
public interface AdhocQueryRequestBuilder extends Builder {

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public AdhocQueryRequest getMessage();

    /**
     * Gets the query type.
     * 
     * @return the query type
     */
    public XDSbStoredQuery getQueryId();

    /**
     * Sets the return composed objects.
     * 
     * @param bool the new return composed objects
     */
    public void setReturnComposedObjects(boolean bool);

    /**
     * Sets the return type.
     * 
     * @param returnType the new return type
     */
    public void setReturnType(ReturnType returnType);

    /**
     * @param patientId
     */
    public void setPatientId(String patientId);

    /**
     * @param documentType
     */
    public void setDocumentTypeCode(String documentTypeCode);

    /**
     * @param startTime
     */
    public void setCreationTimeFrom(Date startTime);

    /**
     * @param endTime
     */
    public void setCreationTimeTo(Date endTime);

    /**
     * @param patientIdRoot
     */
    public void setPatientIdRoot(String patientIdRoot);

}
