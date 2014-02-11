/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.connectopensource.model.DocumentMetadata;
import org.connectopensource.model.DocumentMetadataResult;
import org.connectopensource.model.DocumentMetadataResults;
import org.connectopensource.services.DocumentMetadataService;
import org.connectopensource.services.exception.DocumentMetadataException;

public class DocumentMetadataServiceNoopImpl implements DocumentMetadataService {

	@Override
	public DocumentMetadataResults queryForDocuments(DocumentMetadata query)
			throws DocumentMetadataException {
		DocumentMetadataResults results = null;
		try{
		if(query != null) {
			if(query.getStartTime() == null && query.getEndTime() == null) {
				results = getAllDocumentsForSelectedPatient(query);
			}
		} else {
			results = getDocumentsForSelectedPatient(query);
		  }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private DocumentMetadataResults getDocumentsForSelectedPatient(DocumentMetadata query) throws Exception {
		DocumentMetadataResults finalResults = new DocumentMetadataResults();
		
		DocumentMetadataResults results = createDocuments(query);
		
		Date startDate = query.getStartTime();
		Date endDate = query.getEndTime();
		
		List<DocumentMetadataResult> allDocuments = results.getResults();
		Iterator<DocumentMetadataResult> allDocumentsIterator = allDocuments.iterator();
		
		if(allDocumentsIterator != null) {
			while(allDocumentsIterator.hasNext()) {
				DocumentMetadataResult document = allDocumentsIterator.next();
				if(document != null) {
					DateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");
					Date documentCreationDate = formatter.parse(document.getCreationDate());
					if(documentCreationDate != null) {
						if(documentCreationDate.after(startDate) && documentCreationDate.before(endDate)) {
							finalResults.addResult(document);
						}
					}
				}
			}
		}
		
		
		return results;
	}
	
	private DocumentMetadataResults getAllDocumentsForSelectedPatient(DocumentMetadata query){
		return createDocuments(query);
	}
	
	// create Documents method will retrieve all the patient documents
	public DocumentMetadataResults createDocuments(DocumentMetadata query) {
		DocumentMetadataResults results = new DocumentMetadataResults();
			
		DocumentMetadataResult result1 = new DocumentMetadataResult();
		DocumentMetadataResult result2 = new DocumentMetadataResult();
		
		String date = null;
		try{
			Date today = new Date();
			DateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");
			date = formatter.format(today);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		result1.setPatientId(query.getPatientId());
		result1.setDocumentId("1234");
		result1.setDocumentTitle("Clinical Document 1");
		result1.setCreationDate(date);
		
		result2.setPatientId(query.getPatientId());
		result2.setDocumentId("5678");
		result2.setDocumentTitle("Clinical Document 2");
		result2.setCreationDate(date);
		
		results.addResult(result1);
		results.addResult(result2);
		
		return results;
	}

}
