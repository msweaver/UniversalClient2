/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
/**
 * 
 */
package org.connectopensource.ehealthexchange;

/**
 * The Class ConnectConstants.
 * 
 * @author msw
 */
public class AuthorizationFrameworkConstants {

    /**
     * The Enum NameIdFormat.
     */
    public enum NameIdFormat {

        /** The Unspecified. */
        Unspecified("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified "), /** The Email_ address. */
        Email_Address("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"),
        /** The X509. */
        X509("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName"),
        /** The Windows. */
        Windows("urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName"),
        /** The Kerberos. */
        Kerberos("urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos"),
        /** The Entity. */
        Entity("urn:oasis:names:tc:SAML:2.0:nameid-format:entity"),
        /** The Persistent. */
        Persistent("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"),
        /** The Transient. */
        Transient("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");

        /** The value. */
        private String value = null;

        /**
         * Instantiates a new xDS query status.
         * 
         * @param value the value
         */
        NameIdFormat(String value) {
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return value;
        }

        /**
         * From string.
         * 
         * @param valueString the value string
         * @return the xDS query status
         */
        public static NameIdFormat fromString(String valueString) {
            if (valueString != null) {
                for (NameIdFormat enumValue : NameIdFormat.values()) {
                    if (valueString.equals(enumValue.toString())) {
                        return enumValue;
                    }
                }
            }
            throw new IllegalArgumentException("No enum constant " + valueString);
        }
    };

    /**
     * The Enum AuthenticationMethod.
     */
    public enum AuthenticationMethod {

        /** The Internet protocol. */
        InternetProtocol,
        /** The Internet protocol password. */
        InternetProtocolPassword,
        /** The Password. */
        Password,
        /** The Password protected transport. */
        PasswordProtectedTransport,
        /** The Kerberos. */
        Kerberos,
        /** The Previous session. */
        PreviousSession,
        /** The Secure remote password. */
        SecureRemotePassword,
        /** The TLS client. */
        TLSClient,
        /** The X509. */
        X509,
        /** The pgp. */
        PGP,
        /** The spki. */
        SPKI,
        /** The XMLD sig. */
        XMLDSig,
        /** The unspecified. */
        unspecified;

        /** The prefix. */
        private String prefix = "urn:oasis:names:tc:SAML:2.0:ac:classes:";

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return prefix + super.toString();
        }

    };

}
