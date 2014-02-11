/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.client.impl;

import java.net.URL;

import javax.inject.Named;
import javax.xml.ws.BindingProvider;

import ihe.iti.xds_b._2007.DocumentRepositoryPortType;
import ihe.iti.xds_b._2007.DocumentRepositoryService;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;

import org.apache.cxf.frontend.ClientProxy;
import org.connectopensource.client.AbstractPortalClient;
import org.connectopensource.client.DocumentRepositoryClient;

@Named
public class DocumentRepositoryClientImpl extends AbstractPortalClient implements DocumentRepositoryClient {

	private static DocumentRepositoryPortType repoPort;
	private static DocumentRepositoryService repoService;
	
	private static final String WSDL_PATH = "wsdl/AdapterComponentDocRepository.wsdl";
	private static final String ENDPOINT_KEY = "repository.proxy.endpoint";
    private static URL serviceUrl;
    
    static {
        serviceUrl = DocumentRepositoryClientImpl.class.getClassLoader().getResource(WSDL_PATH);
    }
	
	@Override
	public RetrieveDocumentSetResponseType retrieveDocument(
			RetrieveDocumentSetRequestType request) {
		intializeService();
		return repoPort.documentRepositoryRetrieveDocumentSet(request);
	}

	private void intializeService() {
		if(repoService == null){
			repoService = new DocumentRepositoryService(serviceUrl);
			repoPort = repoService.getDocumentRepositoryPortSoap();
		}
		
		setTimeout(ClientProxy.getClient(repoPort));
		setEndpoint((BindingProvider)repoPort, ENDPOINT_KEY);		
	}	

}
