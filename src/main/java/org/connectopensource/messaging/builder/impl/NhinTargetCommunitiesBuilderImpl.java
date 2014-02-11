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

import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunityType;

import java.util.Collections;
import java.util.List;

import org.connectopensource.messaging.builder.NhinTargetCommunitiesBuilder;

/**
 * The Class AbstractCONNECTOutboundMessageBuilder.
 *
 * @author msw
 */
public class NhinTargetCommunitiesBuilderImpl implements NhinTargetCommunitiesBuilder {

    /** The targets. */
    private NhinTargetCommunitiesType targets = null;
    
    /** The HCI ds. */
    private List<String> HCIDs = null;
    
    /* (non-Javadoc)
     * @see org.connectopensource.messaging.builder.CONNECTOutboundMessageBuilder#setTarget(java.lang.String)
     */
    @Override
    public void setTarget(String targetHCID) {
        HCIDs = Collections.singletonList(targetHCID);
    }

    /* (non-Javadoc)
     * @see org.connectopensource.messaging.builder.CONNECTOutboundMessageBuilder#setTargets(java.util.List)
     */
    @Override
    public void setTargets(List<String> targetHCIDs) {
        HCIDs = targetHCIDs;
    }
    
    /* (non-Javadoc)
     * @see org.connectopensource.messaging.builder.impl.AbstractCONNECTMessageBuilder#build()
     */
    @Override
    public void build() {
        targets = new NhinTargetCommunitiesType();
        for (String s : HCIDs) {
            NhinTargetCommunityType target = new NhinTargetCommunityType();
            HomeCommunityType HCID = new HomeCommunityType();
            HCID.setHomeCommunityId(s);
            target.setHomeCommunity(HCID);
            targets.getNhinTargetCommunity().add(target);
        }
    }

    /* (non-Javadoc)
     * @see org.connectopensource.messaging.builder.NhinTargetCommunitiesBuilder#getNhinTargetCommunities()
     */
    @Override
    public NhinTargetCommunitiesType getNhinTargetCommunities() {
        return targets;
    }

}
