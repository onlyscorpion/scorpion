package com.scorpion.huakerongtong.cipher.security.componet;

import java.util.Map;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.cipher.security.configuration.AbsScorpionCipherConfiguration;
import com.scorpion.huakerongtong.common.annotation.Component;

@Component
public class ScorpionCipherComponent extends AbsScorpionComponent {
	
	private AbsScorpionCipherConfiguration ScorpionCipherConfiguration = AbsScorpionCipherConfiguration.getInstance();

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		ScorpionCipherConfiguration.loadCipherConfiguration();
	}

}
