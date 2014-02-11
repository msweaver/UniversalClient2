/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.managed;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.model.DocumentResults;
import org.connectopensource.model.DocumentSearch;
import org.connectopensource.services.DocumentService;
import org.connectopensource.services.exception.DocumentException;
import org.connectopensource.services.impl.DocumentServiceImpl;

/**
 * @author sadusumilli
 * 
 */
@ManagedBean(name = "documentbean")
@SessionScoped
public class DocumentBean {

    private DocumentService documentService = null;

    private DocumentResults documents;

    private HtmlInputHidden documentId;

    private HtmlInputHidden repositoryId;

    private HtmlInputHidden formatCode;

    private HtmlInputHidden typeCode;

    public DocumentBean() {
        documentService = new DocumentServiceImpl();
    }

    /**
     * @return the documents
     */
    public DocumentResults getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(DocumentResults documents) {
        this.documents = documents;
    }

    /**
     * @return the documentId
     */
    public HtmlInputHidden getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(HtmlInputHidden documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the repositoryId
     */
    public HtmlInputHidden getRepositoryId() {
        return repositoryId;
    }

    /**
     * @param repositoryId the repositoryId to set
     */
    public void setRepositoryId(HtmlInputHidden repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * @return the formatCode
     */
    public HtmlInputHidden getFormatCode() {
        return formatCode;
    }

    /**
     * @param formatCode the formatCode to set
     */
    public void setFormatCode(HtmlInputHidden formatCode) {
        this.formatCode = formatCode;
    }

    /**
     * @return the typeCode
     */
    public HtmlInputHidden getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(HtmlInputHidden typeCode) {
        this.typeCode = typeCode;
    }

    public String retrieveDocument() throws DocumentException, IOException {
        DocumentSearch documentSearch = new DocumentSearch();
        documentSearch.setDocumentId(documentId.getValue().toString());
        documentSearch.setRepoId(repositoryId.getValue().toString());
        documents = documentService.retrieveDocument(documentSearch);

        InputStream xsl = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/CDA.xsl");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (typeCode != null && StringUtils.equals(typeCode.getValue().toString(), "HIH-ERROR")) {
            xsl = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/ErrorReportStylesheet.xsl");
        }
        if (formatCode != null && StringUtils.startsWith(formatCode.getValue().toString(), "PDF")) {
            xsl = null;
            context.addResponseHeader("Content-Type", "application/pdf");
            context.addResponseHeader("Content-Length", String.valueOf(documents.getDocument().length));
        }
        InputStream xml = new ByteArrayInputStream(documents.getDocument());

        byte[] html = null;
        if (xsl != null && xml != null) {
            html = convertXMLToHTML(xml, xsl);

            xsl.close();
            xml.close();
        }

        OutputStream os = context.getResponseOutputStream();
        if (html != null) {
            os.write(html);
        } else {
            os.write(documents.getDocument());
        }

        os.flush();
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    private byte[] convertXMLToHTML(InputStream xml, InputStream xsl) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xsl));
            transformer.transform(new javax.xml.transform.stream.StreamSource(xml),
                    new javax.xml.transform.stream.StreamResult(output));

        } catch (Exception e) {
            e.printStackTrace();
            // LOG.error("Exception in transforming xml to html", e);
        }

        return output.toByteArray();
    }

    public String goBack() {
        return "PatientDetails";
    }
}
