package edu.hm.weidacher.softarch.shareit.util.di;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

/**
 * Mapping Guice as DI framwork.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DIGuiceHk2BridgeApplication extends ResourceConfig {

    @Inject
    public DIGuiceHk2BridgeApplication(ServiceLocator serviceLocator) {
	System.out.println("Initializing DI Bridge");
	GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

	GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);

	guiceBridge.bridgeGuiceInjector(DIUtil.getInjectorStatic());

    }

}
