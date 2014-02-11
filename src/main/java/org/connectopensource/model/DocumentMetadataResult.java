/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.model;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class DocumentMetadataResult.
 */
public class DocumentMetadataResult {

    /** The document id. */
    private String documentId;

    /** The repository id. */
    private String repositoryId;

    /** The Patient Id. */
    private String patientId;

    /** The document title. */
    private String documentTitle;

    /** The creation date. */
    private String creationDate;

    /** The service start time. */
    private String serviceStartTime;

    /** The service stop time. */
    private String serviceStopTime;

    /** The availability status. */
    private String availabilityStatus;

    /** The document type code. */
    private String documentTypeCode;

    /** The document class code. */
    private String documentClassCode;

    /** The document format code. */
    private String documentFormatCode;

    /** The author person. */
    private String authorPerson;

    /** The author institution. */
    private String authorInstitution;

    /** The event codes. */
    private String eventCodes;

    /**
     * Gets the patient id.
     * 
     * @return the patient id
     */
    public String getPatientId() {
        return patientId;
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
     * Gets the document id.
     * 
     * @return the documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     * 
     * @param documentId the documentId to set
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the repository id.
     * 
     * @return the repositoryId
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * Sets the repository id.
     * 
     * @param repositoryId the repositoryId to set
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * Gets the document title.
     * 
     * @return the documentTitle
     */
    public String getDocumentTitle() {
        return documentTitle;
    }

    /**
     * Sets the document title.
     * 
     * @param documentTitle the documentTitle to set
     */
    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * 
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the service start time.
     * 
     * @return the serviceStartTime
     */
    public String getServiceStartTime() {
        return serviceStartTime;
    }

    /**
     * Sets the service start time.
     * 
     * @param serviceStartTime the serviceStartTime to set
     */
    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    /**
     * Gets the service stop time.
     * 
     * @return the serviceStopTime
     */
    public String getServiceStopTime() {
        return serviceStopTime;
    }

    /**
     * Sets the service stop time.
     * 
     * @param serviceStopTime the serviceStopTime to set
     */
    public void setServiceStopTime(String serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    /**
     * Gets the availability status.
     * 
     * @return the availabilityStatus
     */
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * Sets the availability status.
     * 
     * @param availabilityStatus the availabilityStatus to set
     */
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     * Gets the document type code.
     * 
     * @return the documentTypeCode
     */
    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    /**
     * Sets the document type code.
     * 
     * @param documentTypeCode the documentTypeCode to set
     */
    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    /**
     * Gets the document class code.
     * 
     * @return the documentClassCode
     */
    public String getDocumentClassCode() {
        return documentClassCode;
    }

    /**
     * Sets the document class code.
     * 
     * @param documentClassCode the documentClassCode to set
     */
    public void setDocumentClassCode(String documentClassCode) {
        this.documentClassCode = documentClassCode;
    }

    /**
     * @return the documentFormatCode
     */
    public String getDocumentFormatCode() {
        return documentFormatCode;
    }

    /**
     * @param documentFormatCode the documentFormatCode to set
     */
    public void setDocumentFormatCode(String documentFormatCode) {
        this.documentFormatCode = documentFormatCode;
    }

    /**
     * Gets the author person.
     * 
     * @return the authorPerson
     */
    public String getAuthorPerson() {
        return authorPerson;
    }

    /**
     * Sets the author person.
     * 
     * @param authorPerson the authorPerson to set
     */
    public void setAuthorPerson(String authorPerson) {
        this.authorPerson = authorPerson;
    }

    /**
     * Gets the author institution.
     * 
     * @return the authorInstitution
     */
    public String getAuthorInstitution() {
        return authorInstitution;
    }

    /**
     * Sets the author institution.
     * 
     * @param authorInstitution the authorInstitution to set
     */
    public void setAuthorInstitution(String authorInstitution) {
        this.authorInstitution = authorInstitution;
    }

    /**
     * Gets the event codes.
     * 
     * @return the eventCodes
     */
    public String getEventCodes() {
        return eventCodes;
    }

    /**
     * Sets the event codes.
     * 
     * @param eventCodes the eventCodes to set
     */
    public void setEventCodes(String eventCodes) {
        this.eventCodes = eventCodes;
    }

    /**
     * Gets the care summary.
     * 
     * @return the care summary
     */
    public String getCareSummary() {
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isBlank(eventCodes)) {
            builder.append(eventCodes);
        } else {
            // I know this isn't really defensive programming but each of these fields are required..
            builder.append(documentClassCode);
            builder.append(" - ");
            builder.append(documentTypeCode);
        }

        if (!StringUtils.isBlank(serviceStartTime) && !StringUtils.isBlank(serviceStopTime)) {
            builder.append(" from ");
            builder.append(serviceStartTime);
            builder.append(" to ");
            builder.append(serviceStopTime);
        }
        return builder.toString();
    }

}
