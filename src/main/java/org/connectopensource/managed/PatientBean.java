/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.managed;

/**
 * @author sadusumilli
 *
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.connectopensource.di.binding.PatientServiceModule;
import org.connectopensource.model.Patient;
import org.connectopensource.model.PatientSearchResults;
import org.connectopensource.services.PatientService;
import org.connectopensource.services.impl.PatientServiceImpl;
import org.connectopensource.services.impl.PatientServiceNoopImpl;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.ToggleEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;

//import org.connectopensource.services.impl.PatientServiceNoopImpl;

@ManagedBean(name = "patientbean")
@SessionScoped
public class PatientBean {

    private Patient selectedPatient;

    /**
     * Patient metadata
     */
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String pid;
    private String organization;
    private String ssn;
    private String streetname;
    private Date birthDate;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String drivinglicense;
    protected String selectedTabIndex;

    private PatientService patientService = new PatientServiceImpl();


    private static final Logger LOG = Logger.getLogger(PatientBean.class);

    public boolean displayGridView = false;

    public PatientBean() {
        displayGridView = false;
        
        Injector injector = Guice.createInjector(new PatientServiceModule());
        patientService = injector.getInstance(PatientService.class);
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public void showSelectedPatient() {
        if (selectedPatient != null) {

        }
    }

    public List<Patient> getFoundPatients() {
        return foundPatients;
    }

    public void setFoundPatients(List<Patient> foundPatients) {
        this.foundPatients = foundPatients;
    }

    public Boolean isCorrect = false;

    public List<Patient> foundPatients;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isDisplayGridView() {
        return displayGridView;
    }

    public void setDisplayGridView(boolean displayGridView) {
        this.displayGridView = displayGridView;
    }

    public String getPid() {
        return pid;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private Patient setSearchPatient() {
        Patient searchPatient = new Patient();

        searchPatient.setFirstName(this.firstName);
        searchPatient.setLastName(this.lastName);
        searchPatient.setMiddleName(this.middleName);

        searchPatient.setDomain(this.organization);
        searchPatient.setPid(this.pid);

        searchPatient.setStreetAddr(this.streetname);
        searchPatient.setCity(this.city);
        searchPatient.setState(this.state);

        searchPatient.setZip(this.zip);

        if (this.gender != null && !this.gender.equals("")) {
            searchPatient.setGender(this.gender);
        }

        searchPatient.setSsn(this.ssn);
        searchPatient.setdLicense(this.drivinglicense);

        searchPatient.setPhone(this.phone);

        try{
        	if (this.birthDate != null) {
        		DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        		searchPatient.setBirthDate(dateFormatter.format(this.birthDate));
        	}
        }catch(Exception e){
        	LOG.warn(e);
        	searchPatient.setBirthDate(null);
        }
        return searchPatient;
    }

    private void clearPatientInfo() {
        this.lastName = null;
        this.firstName = null;
        this.middleName = null;
        this.gender = null;
        this.birthDate = null;
        this.streetname = null;
        this.city = null;
        this.state = null;
        this.zip = null;
        this.drivinglicense = null;
        this.organization = null;
        this.pid = null;
        this.phone = null;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDrivinglicense() {
        return this.drivinglicense;
    }

    public void setDrivinglicense(String drivinglicense) {
        this.drivinglicense = drivinglicense;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void showPatientDetails(ActionEvent event) {

    }

    public String patientDetails() {
        return "PatientDetails";
    }

    public void selectedTab(AjaxBehaviorEvent abe) {
        AccordionPanel tb = (AccordionPanel) abe.getComponent();
        selectedTabIndex = tb.getActiveIndex();
        if (selectedTabIndex != null) {
            selectedPatient = foundPatients.get(new Integer(selectedTabIndex).intValue());
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("selectedPatient", selectedPatient);
    }

    public String getSelectedTabIndex() {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(String selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }

    public String invokePatientSearch() {
        try {
            Patient searchPatient = setSearchPatient();
            LOG.debug("Searching for patient: " + searchPatient.getLastName() + ", " + searchPatient.getFirstName());
            PatientSearchResults results = patientService.queryPatient(searchPatient);

            if (!results.isPatientListEmpty()) {
                displayGridView = true;
                foundPatients = results.getPatientList();
                selectedPatient = foundPatients.get(0);
            } else {
                LOG.warn("No Matching Patients Found.");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No Matching Patients Found.",
                        "No Matching Patients Found.");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("patient:resultsLabel", msg);
            }

        } catch (Exception e) {
            LOG.error("Exception while querying MPI.", e);
            foundPatients = null;
            clearPatientInfo();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error querying MPI.",
                    "Error querying MPI.");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("patient:resultsLabel", msg);
        }
        return "PatientSearch";
    }

    public String resetPatient() {
        this.displayGridView = false;
        this.selectedPatient = null;
        this.foundPatients = null;
        clearPatientInfo();

        return "PatientSearch";
    }

    public String goBack() {
        return "PatientSearch";
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fieldset Toggled", "Visibility:"
                + event.getVisibility());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

}
