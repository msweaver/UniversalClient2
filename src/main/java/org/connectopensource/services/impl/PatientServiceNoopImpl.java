/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.connectopensource.model.Patient;
import org.connectopensource.model.PatientSearchResults;
import org.connectopensource.services.PatientService;
import org.connectopensource.services.exception.PatientSearchException;

@Named
public class PatientServiceNoopImpl implements PatientService{

	private static List<Patient> mockDatabase = new ArrayList<>();
	
	static {
		initializeMockDatabase();
	}
	
	@Override
	public PatientSearchResults queryPatient(Patient query)
			throws PatientSearchException {
		PatientSearchResults results = new PatientSearchResults();
		for(Patient patient : mockDatabase){
			if(patient.getFirstName().equalsIgnoreCase(query.getFirstName())
					&& patient.getLastName().equalsIgnoreCase(query.getLastName())){
				results.addPatient(patient);
			}
		}
		return results;
	}

	private static void initializeMockDatabase() {		
			Patient p1 = new Patient();
			p1.setPid("DGR456^^^&1.3.6.1.4.1.21367.2010.1.2.315&ISO");
			p1.setFirstName("mark");
			p1.setLastName("burton");
			p1.setCity("MD");
			p1.setSsn("333333333");

			Patient p2 = new Patient();
			p2.setPid("2");
			p2.setFirstName("ruth");
			p2.setLastName("ruth");
			p2.setCity("NY");
			p2.setSsn("000000000");

			Patient p3 = new Patient();
			p3.setPid("3");
			p3.setFirstName("mark");
			p3.setLastName("burton");
			p3.setCity("DC");
			p3.setSsn("111111111");

			mockDatabase.add(p1);
			mockDatabase.add(p2);
			mockDatabase.add(p3);		
	}

}
