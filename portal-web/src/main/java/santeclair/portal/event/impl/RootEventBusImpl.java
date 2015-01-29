package santeclair.portal.event.impl;

import org.springframework.stereotype.Component;

import santeclair.portal.event.RootEventBusService;

import com.google.common.eventbus.EventBus;

@Component
public class RootEventBusImpl extends EventBus implements RootEventBusService {

}
