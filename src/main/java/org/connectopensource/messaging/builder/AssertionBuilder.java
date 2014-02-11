/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.messaging.builder;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;

import org.connectopensource.ehealthexchange.AuthorizationFrameworkConstants.AuthenticationMethod;
import org.connectopensource.ehealthexchange.AuthorizationFrameworkConstants.NameIdFormat;

/**
 * The Interface AssertionBuilder. Provides methods to set all required fields.
 *
 * @author msw
 */
public interface AssertionBuilder extends Builder {
    
    /**
     * Gets the assertion.
     *
     * @return the assertion
     */
    public AssertionType getAssertion();
    
    /**
     * Sets the issuer name id.
     *
     * @param issuerNameId the new issuer name id
     */
    public void setIssuerNameId(String issuerNameId);
    
    /**
     * Sets the issuer name id format.
     *
     * @param issuerNameIdFormat the new issuer name id format
     */
    public void setIssuerNameIdFormat(NameIdFormat issuerNameIdFormat);
    
    /**
     * Sets the subject name id. CONNECT expects this value to be in X509 format.
     *
     * @param subjectNameId the new subject name id
     */
    public void setSubjectNameId(String subjectNameId);
    
    /**
     * Sets the authentication instant.
     *
     * @param authenticationInstant the new authentication instant
     */
    public void setAuthenticationInstant(String authenticationInstant);
    
    /**
     * Sets the authentication context ref class.
     *
     * @param refClass the new authentication context ref class
     */
    public void setAuthenticationContextRefClass(AuthenticationMethod refClass);
    
    /**
     * Sets the subject id.
     *
     * @param subjectId the new subject id
     */
    public void setSubjectId(String subjectId);
    
    /**
     * Sets the subject organization.
     *
     * @param subjectOrganization the new subject organization
     */
    public void setSubjectOrganization(String subjectOrganization);
    
    /**
     * Sets the subject role.
     *
     * @param subjectRole the new subject role
     */
    public void setSubjectRole(String subjectRole);
    
    /**
     * Sets the subject role code system.
     *
     * @param codeSystem the new subject role code system
     */
    public void setSubjectRoleCodeSystem(String codeSystem);
    
    /**
     * Sets the subject role code system name.
     *
     * @param codeSystemName the new subject role code system name
     */
    public void setSubjectRoleCodeSystemName(String codeSystemName);
    
    /**
     * Sets the subject role display name.
     *
     * @param displayName the new subject role display name
     */
    public void setSubjectRoleDisplayName(String displayName);
    
    /**
     * Sets the purpose.
     *
     * @param purpose the new purpose
     */
    public void setPurpose(String purpose);
    
    /**
     * Sets the purpose code system.
     *
     * @param codeSystem the new purpose code system
     */
    public void setPurposeCodeSystem(String codeSystem);
    
    /**
     * Sets the purpose code system name.
     *
     * @param codeSystemName the new purpose code system name
     */
    public void setPurposeCodeSystemName(String codeSystemName);
    
    /**
     * Sets the purpose display name.
     *
     * @param displayName the new purpose display name
     */
    public void setPurposeDisplayName(String displayName);
    
    /**
     * Sets the home community id.
     *
     * @param hcid the new home community id
     */
    public void setHomeCommunityId(String hcid);
    
    /**
     * Sets the organization id.
     *
     * @param orgId the new organization id
     */
    public void setOrganizationId(String orgId);
    
    /**
     * Sets the resource id.
     *
     * @param resourceId the new resource id
     */
    public void setResourceId(String resourceId);
    
    /**
     * Sets the npi.
     *
     * @param npi the new npi
     */
    public void setNPI(String npi);
}
