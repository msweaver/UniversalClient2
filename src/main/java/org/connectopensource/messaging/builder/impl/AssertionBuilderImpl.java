/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.messaging.builder.impl;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlIssuerType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;

import org.apache.commons.lang3.StringUtils;
import org.connectopensource.ehealthexchange.AuthorizationFrameworkConstants.AuthenticationMethod;
import org.connectopensource.ehealthexchange.AuthorizationFrameworkConstants.NameIdFormat;
import org.connectopensource.messaging.builder.AssertionBuilder;

/**
 * The Class AssertionBuilderImpl. This class is only designed to construct a bare bones (read: only required elements)
 * assertion.
 * 
 * @author msw
 */
public class AssertionBuilderImpl implements AssertionBuilder {

    /** The assertion type. */
    private AssertionType assertionType = null;

    /** The issuer name id. */
    private String issuerNameId = null;

    /** The issuer name id format. */
    private NameIdFormat issuerNameIdFormat = NameIdFormat.X509;

    /** The subject name id. */
    private String subjectNameId = null;

    /** The auth instant. */
    private String authInstant = null;

    /** The class ref. */
    private AuthenticationMethod classRef = null;

    /** The subject id. */
    private String subjectId = null;

    /** The subject organization. */
    private String subjectOrganization = null;

    /** The subject role. */
    private String subjectRole = null;

    /** The subject role code system. */
    private String subjectRoleCodeSystem = null;

    /** The subject role code system name. */
    private String subjectRoleCodeSystemName = null;

    /** The subject role display name. */
    private String subjectRoleDisplayName = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.CONNECTMessageBuilder#build()
     */
    @Override
    public void build() {
        assertionType = new AssertionType();

        // issuer
        if (StringUtils.isBlank(issuerNameId) || issuerNameIdFormat == null) {
            throw new IllegalArgumentException("Issuer NameID and NameID format are required.");
        }
        SamlIssuerType samlIssuer = new SamlIssuerType();
        samlIssuer.setIssuer(issuerNameId);
        samlIssuer.setIssuerFormat(issuerNameIdFormat.toString());
        assertionType.setSamlIssuer(samlIssuer);

        // subject
        if (StringUtils.isBlank(subjectNameId)) {
            throw new IllegalArgumentException("Subject NameID is required.");
        }
        UserType userInfo = new UserType();
        userInfo.setUserName(subjectNameId);

        // subject organization attribute statement
        if (StringUtils.isBlank(subjectOrganization)) {
            throw new IllegalArgumentException("Subject Organization attribute statement is required.");
        }
        HomeCommunityType homeCommunity = new HomeCommunityType();
        homeCommunity.setName(subjectOrganization);
        userInfo.setOrg(homeCommunity);

        // subject role
        if (StringUtils.isBlank(subjectRole) || StringUtils.isBlank(subjectRoleCodeSystem)
                || StringUtils.isBlank(subjectRoleCodeSystemName) || StringUtils.isBlank(subjectRoleDisplayName)) {
            CeType role = new CeType();
            role.setCode(subjectRole);
            role.setCodeSystem(subjectRoleCodeSystem);
            role.setCodeSystemName(subjectRoleCodeSystemName);
            role.setDisplayName(subjectRoleDisplayName);
            userInfo.setRoleCoded(role);
        }
        assertionType.setUserInfo(userInfo);

        // authentication instant
        if (StringUtils.isBlank(authInstant) || classRef == null) {
            throw new IllegalArgumentException(
                    "Authentication instant and Authentication Context Class Ref are required.");
        }
        SamlAuthnStatementType authnStatement = new SamlAuthnStatementType();
        authnStatement.setAuthInstant(authInstant);
        authnStatement.setAuthContextClassRef(classRef.toString());
        assertionType.setSamlAuthnStatement(authnStatement);

        // subject id attribute statement
        if (StringUtils.isBlank(subjectId)) {
            throw new IllegalArgumentException("Subject Id attribute statement is required.");
        }
        PersonNameType personName = new PersonNameType();
        // TODO Im not sure if setFullName will work... consider yourself warned...
        personName.setFullName(subjectId);
        assertionType.setPersonName(personName);

        // TODO etc...
    }

    /**
     * Gets the assertion.
     * 
     * @return the assertion
     */
    public AssertionType getAssertion() {
        return assertionType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setIssuerNameId(java.lang.String)
     */
    @Override
    public void setIssuerNameId(String issuerNameId) {
        this.issuerNameId = issuerNameId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setIssuerNameIdFormat(org.connectopensource.ehealthexchange.
     * AuthorizationFrameworkConstants.NameIdFormat)
     */
    @Override
    public void setIssuerNameIdFormat(NameIdFormat issuerNameIdFormat) {
        this.issuerNameIdFormat = issuerNameIdFormat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectNameId(java.lang.String)
     */
    @Override
    public void setSubjectNameId(String subjectNameId) {
        this.subjectNameId = subjectNameId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setAuthenticationInstant(java.lang.String)
     */
    @Override
    public void setAuthenticationInstant(String authenticationInstant) {
        this.authInstant = authenticationInstant;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setAuthenticationContextRefClass(org.connectopensource.ehealthexchange.
     * AuthorizationFrameworkConstants.AuthenticationMethod)
     */
    @Override
    public void setAuthenticationContextRefClass(AuthenticationMethod classRef) {
        this.classRef = classRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectId(java.lang.String)
     */
    @Override
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectOrganization(java.lang.String)
     */
    @Override
    public void setSubjectOrganization(String subjectOrganization) {
        this.subjectOrganization = subjectOrganization;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectRole(java.lang.String)
     */
    @Override
    public void setSubjectRole(String subjectRole) {
        this.subjectRole = subjectRole;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectRoleCodeSystem(java.lang.String)
     */
    @Override
    public void setSubjectRoleCodeSystem(String codeSystem) {
        this.subjectRoleCodeSystem = codeSystem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectRoleCodeSystemName(java.lang.String)
     */
    @Override
    public void setSubjectRoleCodeSystemName(String codeSystemName) {
        this.subjectRoleCodeSystemName = codeSystemName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setSubjectRoleDisplayName(java.lang.String)
     */
    @Override
    public void setSubjectRoleDisplayName(String displayName) {
        this.subjectRoleDisplayName = displayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setPurpose(java.lang.String)
     */
    @Override
    public void setPurpose(String purpose) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setPurposeCodeSystem(java.lang.String)
     */
    @Override
    public void setPurposeCodeSystem(String codeSystem) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setPurposeCodeSystemName(java.lang.String)
     */
    @Override
    public void setPurposeCodeSystemName(String codeSystemName) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setPurposeDisplayName(java.lang.String)
     */
    @Override
    public void setPurposeDisplayName(String displayName) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setHomeCommunityId(java.lang.String)
     */
    @Override
    public void setHomeCommunityId(String hcid) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setOrganizationId(java.lang.String)
     */
    @Override
    public void setOrganizationId(String orgId) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setResourceId(java.lang.String)
     */
    @Override
    public void setResourceId(String resourceId) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.connectopensource.messaging.builder.AssertionBuilder#setNPI(java.lang.String)
     */
    @Override
    public void setNPI(String npi) {
        // TODO Auto-generated method stub

    }

}
