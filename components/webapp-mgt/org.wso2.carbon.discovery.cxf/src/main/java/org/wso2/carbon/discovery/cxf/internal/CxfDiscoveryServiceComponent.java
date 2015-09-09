/*
* Copyright 2004,2013 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.wso2.carbon.discovery.cxf.internal;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.base.api.ServerConfigurationService;
import org.wso2.carbon.utils.ConfigurationContextService;

/**
 * @scr.component name="org.wso2.carbon.discovery.cxf" immediate="true"
 * @scr.reference name="config.context.service"
 * interface="org.wso2.carbon.utils.ConfigurationContextService"
 * cardinality="1..1" policy="dynamic"  bind="setConfigurationContextService"
 * unbind="unsetConfigurationContextService"
 * @scr.reference name="server.configuration.service"
 * interface="org.wso2.carbon.base.api.ServerConfigurationService"
 * cardinality="1..1" policy="dynamic" bind="setServerConfigurationService"
 * unbind="unsetServerConfigurationService"
 */
public class CxfDiscoveryServiceComponent {

    private static final Log log = LogFactory.getLog(CxfDiscoveryServiceComponent.class);

    private ServiceRegistration observerServiceRegistration;
    private CxfDiscoveryDataHolder dataHolder = CxfDiscoveryDataHolder.getInstance();

    protected void activate(ComponentContext ctx) {

        if (log.isDebugEnabled()) {
            log.info("Activating CXF WS-Discovery Startup Publisher Component");
        }
    }

    protected void deactivate(ComponentContext context) {
        if (log.isDebugEnabled()) {
            log.debug("Deactivating CXF WS-Discovery component");
        }

        if (observerServiceRegistration != null) {
            observerServiceRegistration.unregister();
            observerServiceRegistration = null;
        }
    }

    protected void setConfigurationContextService(ConfigurationContextService contextService) {
        dataHolder.setMainServerConfigContext(contextService.getServerConfigContext());
        dataHolder.setClientConfigurationContext(contextService.getClientConfigContext());
    }

    protected void unsetConfigurationContextService(ConfigurationContextService contextService) {
        dataHolder.setMainServerConfigContext(null);
        dataHolder.setClientConfigurationContext(null);
    }
    protected void setServerConfigurationService(ServerConfigurationService serverConfiguration) {
        dataHolder.setServerConfigurationService(serverConfiguration);
    }

    protected void unsetServerConfigurationService(ServerConfigurationService serverConfiguration) {
        dataHolder.setServerConfigurationService(null);
    }
}