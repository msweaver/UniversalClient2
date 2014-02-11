/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.model.builder.impl.dr;

import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType.DocumentResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;

import org.connectopensource.model.DocumentResults;
import org.connectopensource.model.builder.DocumentResultsModelBuilder;
import org.connectopensource.xdsb.xdsbRetrieveResponseHelper;
import org.connectopensource.xdsb.xdsbRetrieveResponseHelperImpl;

/**
 * The Class DocumentResultsModelBuilderImpl.
 * 
 * @author achidambaram
 */
public class DocumentResultsModelBuilderImpl implements DocumentResultsModelBuilder {

    /** The results. */
    private DocumentResults results = null;

    /** The response. */
    private RetrieveDocumentSetResponseType response = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.model.builder.ModelBuilder#build()
     */
    @Override
    public void build() {
        results = new DocumentResults();
        xdsbRetrieveResponseHelper helper = new xdsbRetrieveResponseHelperImpl();
        if (response.getDocumentResponse().size() > 0) {
            DocumentResponse resp = response.getDocumentResponse().get(0);
            
            // hcid
            String hcid = helper.getHCID(response.getDocumentResponse().get(0));
            results.setHcid(hcid);
            
            // repo id
            String repoId = helper.getRepoId(resp);
            results.setRepoId(repoId);
            
            // document id
            String documentId = helper.getDocumentId(resp);
            results.setDoucmnetId(documentId);
            
            // document
            DataHandler data = resp.getDocument();
            byte[] rawDocument = null;
            try {
                rawDocument = convertToBytes(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            results.setDocument(rawDocument);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.model.builder.DocumentResultsModelBuilder#setRetrieveDocumentSetRequestType(ihe.iti.xds_b._2007.
     * RetrieveDocumentSetRequestType)
     */
    @Override
    public void setRetrieveDocumentSetResponseType(RetrieveDocumentSetResponseType response) {
        this.response = response;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.model.builder.DocumentResultsModelBuilder#getResults()
     */
    @Override
    public DocumentResults getResults() {
        return results;
    }

    /**
     * Saves the data handler as a byte array. The data handler will be empty at the end of this call.
     * 
     * @param dh - the data handler to convert
     * @return a byte array containing the data from the data handler
     * @throws IOException
     */
    private byte[] convertToBytes(DataHandler dh) throws IOException {
        InputStream is = dh.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                baos.write(bytes, 0, read);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    // LOG.error("Could not close input stream : " + e.getMessage());
                }
            }
        }

        return baos.toByteArray();
    }

}
