/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.managed;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.connectopensource.di.binding.DocumentMetadataServiceModule;
import org.connectopensource.model.DocumentMetadata;
import org.connectopensource.model.DocumentMetadataResult;
import org.connectopensource.model.DocumentMetadataResults;
import org.connectopensource.model.Patient;
import org.connectopensource.services.DocumentMetadataService;
import org.connectopensource.services.exception.DocumentMetadataException;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Class DocumentQueryBean.
 */
@ManagedBean(name = "documentquerybean")
@SessionScoped
public class DocumentQueryBean {

    /** The document type. */
    private String documentType;

    /** The begin time. */
    private Date beginTime;

    /** The end time. */
    private Date endTime;

    Patient selectedPatient;

    /** The query service. */
    private DocumentMetadataService queryService = null;

    /** The documents. */
    private DocumentMetadataResults documents;

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    /**
     * Instantiates a new document query bean.
     */
    public DocumentQueryBean() {
        Injector injector = Guice.createInjector(new DocumentMetadataServiceModule());
        queryService = injector.getInstance(DocumentMetadataService.class);
    }

    /**
     * Gets the document type.
     * 
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     * 
     * @param documentType the new document type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the begin time.
     * 
     * @return the begin time
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * Sets the begin time.
     * 
     * @param beginTime the new begin time
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Gets the end time.
     * 
     * @return the end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * 
     * @param endTime the new end time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the documents.
     * 
     * @return the documents
     */
    public DocumentMetadataResults getDocuments() {
        return documents;
    }

    public boolean inputValidator() {
        if (this.getBeginTime() != null && this.getEndTime() != null) {
            if (this.getBeginTime().before(this.getEndTime())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public String invokeDocumentDetails() {

        String returnValue = "PatientDetails";

        if (inputValidator()) {

            if (selectedPatient != null) {
                System.out.println("selected patient details : patientID " + selectedPatient.getPid());
                DocumentMetadata query = new DocumentMetadata();

                query.setPatientId(selectedPatient.getPid());
                query.setPatientIdRoot(selectedPatient.getDomain());
                query.setDocumentType(this.documentType);
                query.setStartTime(this.beginTime);
                query.setEndTime(this.endTime);
                try {
                    documents = queryService.queryForDocuments(query);
                    System.out.println("Documents received from Service" + documents);
                    List<DocumentMetadataResult> results = documents.getResults();
                    System.out.println("Documents received from Service .. displaying results" + results);
                    for (DocumentMetadataResult result : results) {
                        System.out.println("Result 1 Document ID" + result.getDocumentId());
                        System.out.println("Result 1 Creation Date" + result.getCreationDate());
                        System.out.println("Result 1 Document Title" + result.getDocumentTitle());
                        System.out.println("Result 1 Patient ID" + result.getPatientId());
                    }
                    System.out.println("Documents received from Service .. finished displaying results");
                } catch (DocumentMetadataException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                returnValue = "DocumentDetails";
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Creation End Date should be less than the Creation Start Date", "");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("patientdetails:dateLbl1", msg);
        }

        return returnValue;
    }

    public String goBack() {
        return "PatientDetails";
    }

}
