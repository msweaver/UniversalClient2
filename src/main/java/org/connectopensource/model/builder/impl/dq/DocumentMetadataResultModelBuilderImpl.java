/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.model.builder.impl.dq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectType;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.model.DocumentMetadataResult;
import org.connectopensource.model.builder.DocumentMetadataResultModelBuilder;
import org.connectopensource.xdsb.XDSbAdhocQueryResponseHelper;
import org.connectopensource.xdsb.XDSbAdhocQueryResponseHelperImpl;
import org.connectopensource.xdsb.XDSbConstants.ClassificationScheme;
import org.connectopensource.xdsb.XDSbConstants.IdentificationScheme;
import org.connectopensource.xdsb.XDSbConstants.ResponseSlotName;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentMetadataResultModelBuilderImpl. This is intentionally scoped package because there shouldn't be any
 * need to create these single objects by themselves outside of the scope of the composite results.
 * 
 * @author msw
 */
class DocumentMetadataResultModelBuilderImpl implements DocumentMetadataResultModelBuilder {

    /** The result. */
    private DocumentMetadataResult result = null;

    /** The extrinsic object. */
    private ExtrinsicObjectType extrinsicObject = null;

    /**
     * Instantiates a new document metadata result model builder impl.
     */
    DocumentMetadataResultModelBuilderImpl() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.model.builder.ModelBuilder#build()
     */
    @Override
    public void build() {
        result = new DocumentMetadataResult();
        XDSbAdhocQueryResponseHelper helper = new XDSbAdhocQueryResponseHelperImpl();

        // slot values
        String creationDate = helper.getSingleSlotValue(ResponseSlotName.creationTime, extrinsicObject);
        String serviceStartTime = helper.getSingleSlotValue(ResponseSlotName.serviceStartTime, extrinsicObject);
        String serviceStopTime = helper.getSingleSlotValue(ResponseSlotName.serviceStopTime, extrinsicObject);
        try {
            result.setCreationDate(formatDatePretty(creationDate));
            result.setServiceStartTime(formatDateTimePretty(serviceStartTime));
            result.setServiceStopTime(formatDateTimePretty(serviceStopTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String repositoryId = helper.getSingleSlotValue(ResponseSlotName.repositoryUniqueId, extrinsicObject);
        result.setRepositoryId(repositoryId);

        // classifications
        String documentTypeCode = helper.getClassificationValue(ClassificationScheme.typeCode, extrinsicObject);
        result.setDocumentTypeCode(documentTypeCode);
        String documentClassCode = helper.getClassificationValue(ClassificationScheme.classCode, extrinsicObject);
        result.setDocumentClassCode(documentClassCode);
        String documentFormatCode = helper.getClassificationValue(ClassificationScheme.formatCode, extrinsicObject);
        result.setDocumentFormatCode(documentFormatCode);

        // author is a bit of a different classification
        RegistryObjectType author = helper.getClassification(ClassificationScheme.Author, extrinsicObject);
        String authorPerson = helper.getSingleSlotValue("authorPerson", author);
        result.setAuthorPerson(authorPerson);
        String authorInstitution = helper.getSingleSlotValue("authorInstitution", author);
        result.setAuthorInstitution(authorInstitution);

        // external ids
        String documentId = helper.getExternalIdentifierValue(IdentificationScheme.uniqueId, extrinsicObject);
        result.setDocumentId(documentId);

        // extrinsic object
        String availabilityStatus = helper.getStatus(extrinsicObject);
        result.setAvailabilityStatus(availabilityStatus);
        String documentTitle = helper.getTitle(extrinsicObject);
        result.setDocumentTitle(documentTitle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.model.builder.DocumentMetadataResultModelBuilder#getDocumentMetadataResult()
     */
    @Override
    public DocumentMetadataResult getDocumentMetadataResult() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.connectopensource.model.builder.DocumentMetadataResultModelBuilder#setRegistryObjectType(oasis.names.tc.ebxml_regrep.
     * xsd.rim._3.ExtrinsicObjectType)
     */
    @Override
    public void setRegistryObjectType(ExtrinsicObjectType extrinsicObject) {
        this.extrinsicObject = extrinsicObject;
    }

    /**
     * Format date time pretty.
     * 
     * @param date the date
     * @return the string
     * @throws ParseException the parse exception
     */
    private String formatDateTimePretty(String date) throws ParseException {
        String prettyDateTime = null;
        if (!StringUtils.isBlank(date)) {
            Date dDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
            prettyDateTime = new SimpleDateFormat("M dd, yyyy, HH:mm Z").format(dDate);
        }
        return prettyDateTime;
    }

    /**
     * Format date pretty.
     * 
     * @param date the date
     * @return the string
     * @throws ParseException the parse exception
     */
    private String formatDatePretty(String date) throws ParseException {
        String prettyDate = null;
        if (!StringUtils.isBlank(date)) {
            Date dDate = new SimpleDateFormat("yyyyMMdd").parse(date);
            prettyDate = new SimpleDateFormat("dd/MMMM/yyyy").format(dDate);
        }
        return prettyDate;
    }

}
