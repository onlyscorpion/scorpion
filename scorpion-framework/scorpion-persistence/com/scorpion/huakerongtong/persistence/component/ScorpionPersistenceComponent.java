package com.scorpion.huakerongtong.persistence.component;

import java.util.Map;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.annotation.Component;
import com.scorpion.huakerongtong.persistence.configuration.PersistenceConfiguration;
import com.scorpion.huakerongtong.persistence.handler.C3p0DataSourceAdapter;
import com.scorpion.huakerongtong.persistence.handler.DataSourceFactory;
import com.scorpion.huakerongtong.persistence.handler.ScorpionDBJndiContext;
import com.scorpion.huakerongtong.persistence.handler.ScorpionDbcpDataSourceAdater;
import com.scorpion.huakerongtong.persistence.session.SessionFactory;

@Component(name="persistenceComponentManager",isPersistComponent=true,params={})
public class ScorpionPersistenceComponent extends AbsScorpionComponent{

	
	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
	
		PlatformLogger.info("Is beginning to start persistence component !");
    
		PersistenceConfiguration.loadConfigurationByScorpionDefaultSetting();
     
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.ScorpionDBCP, new ScorpionDbcpDataSourceAdater());
		
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.C3P0, new C3p0DataSourceAdapter());
		
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.JNDI, new ScorpionDBJndiContext());
     
		DataSourceFactory.getInstance().initDataSource();
		
		SessionFactory.getInstance();
	
	}
	
	
}
