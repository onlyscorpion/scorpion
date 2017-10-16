package org.scorpion.cipher.security.componet;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.cipher.security.configuration.AbsScorpionCipherConfiguration;
import org.scorpion.common.annotation.Component;

@Component
public class ScorpionCipherComponent extends AbsScorpionComponent {
	
	private AbsScorpionCipherConfiguration ScorpionCipherConfiguration = AbsScorpionCipherConfiguration.getInstance();

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		ScorpionCipherConfiguration.loadCipherConfiguration();
	}

}
