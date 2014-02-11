/*******************************************************************************
 * Copyright © 2013 The California Health and Human Services Agency (CHHS). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"), you may not use this file except in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0.
 * Unless required by applicable law or agreed to in writing, content (including but not limited to software, documentation, information, and all other works distributed under the License) is distributed on an "AS IS" BASIS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE CONTENT OR THE USE OR OTHER DEALINGS IN THE CONTENT. IN NO EVENT SHALL CHHS HAVE ANY OBLIGATION TO PROVIDE SUPPORT, UPDATES, MODIFICATIONS, AND/OR UPGRADES FOR CONTENT. See the License for the specific language governing permissions and limitations under the License.
 * This publication/product was made possible by Award Number 90HT0029 from Office of the National Coordinator for Health Information Technology (ONC), U.S. Department of Health and Human Services. Its contents are solely the responsibility of the authors and do not necessarily represent the official views of ONC or the State of California.
 ******************************************************************************/
package org.connectopensource.messaging.builder.impl.pd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBElement;

import org.connectopensource.messaging.builder.PRPAIN201305UV02Builder;
import org.connectopensource.properties.PropertyAccessor;
import org.hl7.v3.ActClassControlAct;
import org.hl7.v3.COCTMT090300UV01AssignedDevice;
import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000100UV01Agent;
import org.hl7.v3.MCCIMT000100UV01Device;
import org.hl7.v3.MCCIMT000100UV01Organization;
import org.hl7.v3.MCCIMT000100UV01Receiver;
import org.hl7.v3.MCCIMT000100UV01Sender;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201305UV02QUQIMT021001UV01ControlActProcess;
import org.hl7.v3.QUQIMT021001UV01AuthorOrPerformer;
import org.hl7.v3.TSExplicit;
import org.hl7.v3.XActMoodIntentEvent;

public abstract class AbstractPRPAIN201305UV02Builder implements PRPAIN201305UV02Builder {

	private PRPAIN201305UV02 request = null;
	
	private static final String LOCAL_HCID_KEY = "local.hcid";
	
	@Override
	public void build(){
		request = new PRPAIN201305UV02();
		buildSenderReceiver();
		buildCreationTime();
		buildControlActProcess();
	}
	
	@Override
	public PRPAIN201305UV02 getMessage(){
		return request;
	}
	
	protected void buildSenderReceiver(){
		MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();		
		MCCIMT000100UV01Receiver receiver = new MCCIMT000100UV01Receiver();
		
		MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
		MCCIMT000100UV01Agent asAgent = new MCCIMT000100UV01Agent();
		MCCIMT000100UV01Organization representedOrg = new MCCIMT000100UV01Organization();
		II id = new II();
		id.setRoot(getLocalHcid());
		representedOrg.getId().add(id);
		JAXBElement<MCCIMT000100UV01Organization> representedOrgJax = 
				new org.hl7.v3.ObjectFactory().createMCCIMT000100UV01AgentRepresentedOrganization(representedOrg);
		asAgent.setRepresentedOrganization(representedOrgJax);
		JAXBElement<MCCIMT000100UV01Agent> asAgentJax = 
				new org.hl7.v3.ObjectFactory().createMCCIMT000100UV01DeviceAsAgent(asAgent);
		device.setAsAgent(asAgentJax);
		
		sender.setDevice(device);
		receiver.setDevice(device);
		
		request.getReceiver().add(receiver);
		request.setSender(sender);		
	}
	
	protected void buildCreationTime(){
		TSExplicit creationTime = new TSExplicit();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		creationTime.setValue(format.format(new Date()));
		request.setCreationTime(creationTime);
	}
	
	protected void buildControlActProcess(){
		PRPAIN201305UV02QUQIMT021001UV01ControlActProcess cap = 
				new PRPAIN201305UV02QUQIMT021001UV01ControlActProcess();
		cap.setMoodCode(XActMoodIntentEvent.EVN);
        cap.setClassCode(ActClassControlAct.CACT);
		QUQIMT021001UV01AuthorOrPerformer author = new QUQIMT021001UV01AuthorOrPerformer();
		COCTMT090300UV01AssignedDevice device = new COCTMT090300UV01AssignedDevice();
		II id = new II();
		id.setRoot(getLocalHcid());
		device.getId().add(id);
		JAXBElement<COCTMT090300UV01AssignedDevice> deviceJAX =
				new org.hl7.v3.ObjectFactory().createMFMIMT700701UV01AuthorOrPerformerAssignedDevice(device);
		author.setAssignedDevice(deviceJAX);
		cap.getAuthorOrPerformer().add(author);
		request.setControlActProcess(cap);
	}
		
	protected String getLocalHcid(){
		return PropertyAccessor.getInstance().getProperty(LOCAL_HCID_KEY);
	}
}
