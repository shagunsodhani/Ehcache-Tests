package ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

public class Ehcache {
	
	public static void main(String args[]){
		
		PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration()
																.strategy(Strategy.LOCALTEMPSWAP);
		
		CacheConfiguration cacheConfiguration = new CacheConfiguration()
													.name("dummy")
													.persistence(persistenceConfiguration)
													.maxEntriesLocalHeap(100)
													.maxEntriesLocalDisk(200);

		DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration().path("C:\\cache-testing");
		
		Configuration managerConfiguration = new Configuration().diskStore(diskStoreConfigurationParameter).name("dummyManager1");
		
		Cache cache = new Cache(cacheConfiguration);
		
		CacheManager cacheManager = CacheManager.create(managerConfiguration);
		
		cacheManager.addCache(cache);
		
		for(int i=0; i<10000; i++){
			cache.put(new Element(i, i*i));
		}
		
		System.out.println("Insertion Done");
		
		System.out.print("Number of Keys = ");
		System.out.println(cache.getSize());
		System.out.println(cache.getKeys().size());
		
	}
}
