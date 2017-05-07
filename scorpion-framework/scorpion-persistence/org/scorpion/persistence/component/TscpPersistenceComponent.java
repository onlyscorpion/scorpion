package org.scorpion.persistence.component;

import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Component;
import org.scorpion.persistence.configuration.PersistenceConfiguration;
import org.scorpion.persistence.handler.C3p0DataSourceAdapter;
import org.scorpion.persistence.handler.DataSourceFactory;
import org.scorpion.persistence.handler.TscpDBJndiContext;
import org.scorpion.persistence.handler.TscpDbcpDataSourceAdater;
import org.scorpion.persistence.session.SessionFactory;

@Component(name="persistenceComponentManager",isPersistComponent=true,params={})
public class TscpPersistenceComponent extends AbsTscpComponent{

	
	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
	
		PlatformLogger.info("Is beginning to start persistence component !");
    
		PersistenceConfiguration.loadConfigurationByTscpDefaultSetting();
     
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.TSCPDBCP, new TscpDbcpDataSourceAdater());
		
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.C3P0, new C3p0DataSourceAdapter());
		
		DataSourceFactory.getInstance().registerDataSourceAdapter(Constant.JNDI, new TscpDBJndiContext());
     
		DataSourceFactory.getInstance().initDataSource();
		
		SessionFactory.getInstance();
	
	}
	
	
}
