package org.scorpion.cipher.security.componet;

import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.cipher.security.configuration.AbsTscpCipherConfiguration;
import org.scorpion.common.annotation.Component;

@Component
public class TscpCipherComponent extends AbsTscpComponent {
	
	private AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
		tscpCipherConfiguration.loadCipherConfiguration();
	}

}
