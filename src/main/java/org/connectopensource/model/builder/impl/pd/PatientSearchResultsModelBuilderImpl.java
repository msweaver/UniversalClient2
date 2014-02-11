/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.model.builder.impl.pd;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.connectopensource.model.Patient;
import org.connectopensource.model.PatientSearchResults;
import org.connectopensource.model.builder.AbstractPatientSearchResultsModelBuilder;
import org.connectopensource.model.builder.PatientSearchResultsModelBuilder;
import org.connectopensource.properties.PropertyAccessor;
import org.hl7.v3.AdxpExplicitCity;
import org.hl7.v3.AdxpExplicitPostalCode;
import org.hl7.v3.AdxpExplicitState;
import org.hl7.v3.AdxpExplicitStreetAddressLine;
import org.hl7.v3.EnExplicitFamily;
import org.hl7.v3.EnExplicitGiven;
import org.hl7.v3.II;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.PRPAIN201306UV02MFMIMT700711UV01Subject1;
import org.hl7.v3.PRPAMT201310UV02OtherIDs;
import org.hl7.v3.PRPAMT201310UV02Patient;
import org.hl7.v3.PRPAMT201310UV02Person;

@Named
public class PatientSearchResultsModelBuilderImpl extends
		AbstractPatientSearchResultsModelBuilder implements
		PatientSearchResultsModelBuilder {

	private PatientSearchResults results;
	private PRPAIN201306UV02 message;

	private static final String SSN_ID_ROOT = "2.16.840.1.113883.4.1";
	private static final String DOMAIN_ROOT_KEY = "hih.domain";

	private static final Logger LOG = Logger
			.getLogger(PatientSearchResultsModelBuilderImpl.class);

	@Override
	public void build() {
		results = new PatientSearchResults();
		if (message != null) {
			LOG.debug("Retrieving Patient Results from Response: " + message);
			List<PRPAIN201306UV02MFMIMT700711UV01Subject1> subjects = getSubjects(message);
			LOG.debug("Mpi Search Results Number Subject found: "
					+ subjects.size());
			for (PRPAIN201306UV02MFMIMT700711UV01Subject1 subject : subjects) {
				PRPAMT201310UV02Patient patient = getSubject1Patient(subject);
				if (patient != null) {
					extractAndAddPatient(patient);
				}
			}
		}
	}

	@Override
	public void setMessage(PRPAIN201306UV02 message) {
		this.message = message;
	}

	@Override
	public PatientSearchResults getPatientSearchResultModel() {
		return results;
	}

	private void extractAndAddPatient(PRPAMT201310UV02Patient msgPatient) {

		if (msgPatient != null && msgPatient.getPatientPerson() != null
				&& msgPatient.getPatientPerson().getValue() != null) {

			PRPAMT201310UV02Person person = msgPatient.getPatientPerson()
					.getValue();
			Patient patient = new Patient();
			patient.setEmptyStrings();

			extractNames(person, patient);
			extractPid(msgPatient, patient);
			extractAddress(person, patient);
			extractSsn(person, patient);
			extractTelephone(person, patient);
			extractGender(person, patient);
			extractDob(person, patient);
			extractDriverLicense(person, patient);

			results.addPatient(patient);
		}
	}

	private void extractNames(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getName() != null && person.getName().size() > 0
				&& person.getName().get(0) != null
				&& person.getName().get(0).getContent() != null
				&& person.getName().get(0).getContent().size() > 0) {
			boolean firstNameFound = false;
			boolean secondNameFound = false;
			for (Serializable object : person.getName().get(0).getContent()) {
				if (object instanceof JAXBElement<?>) {
					Object nameValue = ((JAXBElement<?>) object).getValue();

					if (nameValue instanceof EnExplicitFamily) {
						patient.setLastName(((EnExplicitFamily) nameValue)
								.getContent());
					} else if (nameValue instanceof EnExplicitGiven) {
						if (!firstNameFound) {
							patient.setFirstName(((EnExplicitGiven) nameValue)
									.getContent());
							firstNameFound = true;
						} else if (!secondNameFound) {
							patient.setMiddleName(((EnExplicitGiven) nameValue)
									.getContent());
							secondNameFound = true;
						}
					}
				}
			}
		}
	}

	private void extractPid(PRPAMT201310UV02Patient msgPatient, Patient patient) {
		if (msgPatient.getId() != null) {
			String domainId = PropertyAccessor.getInstance().getProperty(
					DOMAIN_ROOT_KEY);
			for (II id : msgPatient.getId()) {
				if (id.getRoot().equals(domainId)) {
					patient.setPid(id.getExtension());
					patient.setDomain(domainId);
					break;
				}
			}
		}

	}

	private void extractAddress(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getAddr() != null && person.getAddr().size() > 0
				&& person.getAddr().get(0) != null
				&& person.getAddr().get(0).getContent() != null
				&& person.getAddr().get(0).getContent().size() > 0) {
			for (Serializable object : person.getAddr().get(0).getContent()) {
				if(object instanceof JAXBElement<?>){
					Object value = ((JAXBElement<?>) object).getValue();
				
					if (value instanceof AdxpExplicitStreetAddressLine) {
						patient.setStreetAddr(((AdxpExplicitStreetAddressLine) value)
							.getContent());
					} else if (value instanceof AdxpExplicitPostalCode) {
						patient.setZip(((AdxpExplicitPostalCode) value).getContent());
					} else if (value instanceof AdxpExplicitState) {
						patient.setState(((AdxpExplicitState) value).getContent());
					} else if (value instanceof AdxpExplicitCity) {
						patient.setCity(((AdxpExplicitCity) value).getContent());
					} else {
						LOG.warn("Address object not identified: " + value);
					}
				}
			}
		}
	}

	private void extractSsn(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getAsOtherIDs() != null) {
			for (PRPAMT201310UV02OtherIDs otherId : person.getAsOtherIDs()) {
				if (otherId.getId() != null) {
					for (II id : otherId.getId()) {
						if (id.getRoot().equals(SSN_ID_ROOT)) {
							patient.setSsn(id.getExtension());
							return;
						}
					}
				}
			}
		}

	}

	private void extractTelephone(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getTelecom() != null && person.getTelecom().size() > 0
				&& person.getTelecom().get(0) != null
				&& person.getTelecom().get(0).getValue() != null
				&& !person.getTelecom().get(0).getValue().isEmpty()) {
			patient.setPhone(person.getTelecom().get(0).getValue());
		}
	}

	private void extractGender(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getAdministrativeGenderCode() != null
				&& person.getAdministrativeGenderCode().getCode() != null
				&& !person.getAdministrativeGenderCode().getCode().isEmpty()) {
			patient.setGender(person.getAdministrativeGenderCode().getCode());
		}
	}

	private void extractDob(PRPAMT201310UV02Person person, Patient patient) {
		if (person.getBirthTime() != null
				&& person.getBirthTime().getValue() != null
				&& !person.getBirthTime().getValue().isEmpty()) {
			patient.setBirthDate(person.getBirthTime().getValue());
		}
	}

	private void extractDriverLicense(PRPAMT201310UV02Person person,
			Patient patient) {
		// TODO Auto-generated method stub

	}

}
