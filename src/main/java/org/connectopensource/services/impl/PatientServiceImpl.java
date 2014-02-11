/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.services.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.connectopensource.client.MpiClient;
import org.connectopensource.messaging.builder.PRPAIN201305UV02Builder;
import org.connectopensource.messaging.builder.impl.pd.PRPAIN201305UV02BuilderImpl;
import org.connectopensource.messaging.director.PatientDiscoveryMessageDirector;
import org.connectopensource.model.Patient;
import org.connectopensource.model.PatientSearchResults;
import org.connectopensource.model.builder.PatientSearchResultsModelBuilder;
import org.connectopensource.services.PatientService;
import org.connectopensource.services.exception.PatientSearchException;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;

public class PatientServiceImpl implements PatientService {

    @Inject
    private MpiClient mpiClient;

    @Inject
    private PatientSearchResultsModelBuilder resultsBuilder;

    @Inject
    private PatientDiscoveryMessageDirector pdMessageDirector;

    private static Logger LOG = Logger.getLogger(PatientServiceImpl.class);

    @Override
    public PatientSearchResults queryPatient(Patient query) throws PatientSearchException {

        LOG.trace("Entering PatientServiceImpl.queryPatient()");
        setPRPAINBuilder(query);
        pdMessageDirector.build();
        RespondingGatewayPRPAIN201305UV02RequestType request = pdMessageDirector.getMessage();
        PRPAIN201306UV02 response = mpiClient.findIdentifier(request);
        resultsBuilder.setMessage(response);
        resultsBuilder.build();

        return resultsBuilder.getPatientSearchResultModel();
    }

    protected void setPRPAINBuilder(Patient query) {
        PRPAIN201305UV02Builder builder = new PRPAIN201305UV02BuilderImpl();
        ((PRPAIN201305UV02BuilderImpl) builder).setPatient(query);
        pdMessageDirector.setPRPAIN201305UV02Builder(builder);
    }

}
