package org.scorpion.persistence.component;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Component;
import org.scorpion.persistence.configuration.PersistenceConfiguration;
import org.scorpion.persistence.handler.C3p0DataSourceAdapter;
import org.scorpion.persistence.handler.DataSourceFactory;
import org.scorpion.persistence.handler.ScorpionDBJndiContext;
import org.scorpion.persistence.handler.ScorpionDbcpDataSourceAdater;
import org.scorpion.persistence.session.SessionFactory;

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
