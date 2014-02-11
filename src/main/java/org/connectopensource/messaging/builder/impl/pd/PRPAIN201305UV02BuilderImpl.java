/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.messaging.builder.impl.pd;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.connectopensource.model.Patient;
import org.hl7.v3.CE;
import org.hl7.v3.CS;
import org.hl7.v3.ENExplicit;
import org.hl7.v3.ENXPExplicit;
import org.hl7.v3.EnExplicitFamily;
import org.hl7.v3.EnExplicitGiven;
import org.hl7.v3.II;
import org.hl7.v3.IVLTSExplicit;
import org.hl7.v3.ObjectFactory;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectAdministrativeGender;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectBirthTime;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectId;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectName;
import org.hl7.v3.PRPAMT201306UV02ParameterList;
import org.hl7.v3.PRPAMT201306UV02QueryByParameter;

public class PRPAIN201305UV02BuilderImpl extends AbstractPRPAIN201305UV02Builder {

	private Patient patient;
	
	private PRPAIN201305UV02 request;
	
	private static final Logger LOG = Logger.getLogger(PRPAIN201305UV02BuilderImpl.class);
	

	@Override
	public void build() {
		if(patient == null){
			throw new IllegalArgumentException("Patient value is required.");
		}
		super.build();
		request = super.getMessage();
		buildQueryParams();
	}

	@Override
	public PRPAIN201305UV02 getMessage(){
		return request;
	}

	public void setPatient(Patient patient){
		this.patient = patient;
	}
	
	private void buildQueryParams() {
		PRPAMT201306UV02QueryByParameter query = new PRPAMT201306UV02QueryByParameter();
		CS statusCode = new CS();
		statusCode.setCode("new");
		query.setStatusCode(statusCode);
		CS respModal = new CS();
		respModal.setCode("R");
		query.setResponseModalityCode(respModal);
		CS priority = new CS();
		priority.setCode("I");
		query.setResponsePriorityCode(priority);
		
		query.setParameterList(buildParameterList());
		JAXBElement<PRPAMT201306UV02QueryByParameter> queryJAX = 
				new org.hl7.v3.ObjectFactory().createPRPAIN201305UV02QUQIMT021001UV01ControlActProcessQueryByParameter(query);
		request.getControlActProcess().setQueryByParameter(queryJAX);
		LOG.debug("ControlActProcess set for request: " + request);
	}

	private PRPAMT201306UV02ParameterList buildParameterList() {
		org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();
		
		PRPAMT201306UV02ParameterList parameterList = new PRPAMT201306UV02ParameterList();
		
		if(patient.getLastName() != null){
			setNames(parameterList, factory);
		}
		
		if(patient.getBirthDate() != null){
			setBirthTime(parameterList);
		}
		
		if(patient.getGender() != null){
			setGender(parameterList);
		}
		
		if(patient.getStreetAddr() != null || patient.getCity() != null || patient.getState() != null
				|| patient.getZip() != null){
			setAddress(parameterList, factory);
		}
		
		if(patient.getPhone() != null){
			setPhone(parameterList);
		}
		
		if(patient.getPid() != null && patient.getDomain() != null){
			setPid(parameterList);
		}
		
		if(patient.getSsn() != null){
			setSsn(parameterList);
		}
		
		if(patient.getdLicense() != null){
			setDLicense(parameterList);
		}
		
		return parameterList;
	}

	private void setNames(PRPAMT201306UV02ParameterList parameterList, org.hl7.v3.ObjectFactory factory){
		PRPAMT201306UV02LivingSubjectName name = new PRPAMT201306UV02LivingSubjectName();
		EnExplicitFamily familyName = new EnExplicitFamily();
		familyName.setContent(patient.getLastName());
		familyName.setPartType("FAM");
		
		ENExplicit namesWrapper = new ENExplicit();
		namesWrapper.getContent().add(factory.createENExplicitFamily(familyName));
					
		if(patient.getFirstName() != null && !patient.getFirstName().equals("")){
			EnExplicitGiven givenName = new EnExplicitGiven();
			givenName.setContent(patient.getFirstName());
			givenName.setPartType("GIV");
			
			namesWrapper.getContent().add(factory.createENExplicitGiven(givenName));
			
			if(patient.getMiddleName() != null && !patient.getMiddleName().equals("")){
				EnExplicitGiven middleName = new EnExplicitGiven();
				middleName.setContent(patient.getMiddleName());
				middleName.setPartType("GIV");
				namesWrapper.getContent().add(factory.createENExplicitGiven(middleName));
			}
		}
		
		name.getValue().add(namesWrapper);
	
		parameterList.getLivingSubjectName().add(name);
	}
	
	
	private void setBirthTime(PRPAMT201306UV02ParameterList parameterList){
		PRPAMT201306UV02LivingSubjectBirthTime birthTime = new PRPAMT201306UV02LivingSubjectBirthTime();
		IVLTSExplicit birthValue = new IVLTSExplicit();
		birthValue.setValue(patient.getBirthDate());
		birthTime.getValue().add(birthValue);
		parameterList.getLivingSubjectBirthTime().add(birthTime);
	}
	
	private void setGender(PRPAMT201306UV02ParameterList parameterList) {
		PRPAMT201306UV02LivingSubjectAdministrativeGender adminGender = 
				new PRPAMT201306UV02LivingSubjectAdministrativeGender();
		CE code = new CE();
		code.setCode(patient.getGender());
		adminGender.getValue().add(code);
		parameterList.getLivingSubjectAdministrativeGender().add(adminGender);		
	}
	
	private void setAddress(PRPAMT201306UV02ParameterList parameterList,
			ObjectFactory factory) {
		// TODO Auto-generated method stub
		
	}
	
	private void setPhone(PRPAMT201306UV02ParameterList parameterList) {
		// TODO Auto-generated method stub
		
	}
	
	private void setPid(PRPAMT201306UV02ParameterList parameterList) {
		PRPAMT201306UV02LivingSubjectId subjectId = new PRPAMT201306UV02LivingSubjectId();
		II id = new II();
		id.setExtension(patient.getPid());
		id.setRoot(patient.getDomain());
		subjectId.getValue().add(id);
		parameterList.getLivingSubjectId().add(subjectId);		
	}

	private void setSsn(PRPAMT201306UV02ParameterList parameterList) {
		// TODO Auto-generated method stub
		
	}
	
	private void setDLicense(PRPAMT201306UV02ParameterList parameterList) {
		// TODO Auto-generated method stub
		
	}
}
