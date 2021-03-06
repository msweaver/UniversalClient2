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

import ihe.iti.xds_b._2007.DocumentRegistryPortType;
import ihe.iti.xds_b._2007.DocumentRegistryService;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.frontend.ClientProxy;
import org.connectopensource.client.AbstractPortalClient;
import org.connectopensource.client.DocumentRegistryClient;

@Named
public class DocumentRegistryClientImpl extends AbstractPortalClient implements DocumentRegistryClient{

	private static DocumentRegistryPortType registryPort;
	private static DocumentRegistryService registryService;
	
	private static final String WSDL_PATH = "wsdl/AdapterComponentDocRegistry.wsdl";
	private static final String ENDPOINT_KEY = "registry.proxy.endpoint";
    private static URL serviceUrl;
    
    static {
        serviceUrl = DocumentRegistryClientImpl.class.getClassLoader().getResource(WSDL_PATH);
    }
	
	@Override
	public AdhocQueryResponse findDocuments(AdhocQueryRequest request) {
		initializeService();
		return registryPort.documentRegistryRegistryStoredQuery(request);
	}
	
	private void initializeService(){
		if(registryService == null){
			registryService = new DocumentRegistryService(serviceUrl);
			registryPort = registryService.getDocumentRegistryPortSoap();
		}
		
		setTimeout(ClientProxy.getClient(registryPort));
		setEndpoint((BindingProvider)registryPort, ENDPOINT_KEY);
	}
}
