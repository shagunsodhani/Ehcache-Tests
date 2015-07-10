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
	
	public static void main(String args[]) throws InterruptedException{
		
		PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration()
																.strategy(Strategy.LOCALTEMPSWAP);
		
		CacheConfiguration cacheConfiguration1 = new CacheConfiguration()
													.name("dummy1")
													.persistence(persistenceConfiguration)
													.maxBytesLocalHeap(10, MemoryUnit.KILOBYTES)
													.maxBytesLocalDisk(100, MemoryUnit.KILOBYTES);
		
		CacheConfiguration cacheConfiguration2 = new CacheConfiguration()
														.name("dummy2")
														.persistence(persistenceConfiguration)
														.maxEntriesLocalHeap(1000)
														.maxEntriesLocalDisk(4000);

		DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration().path("C:\\cache-testing");
		
		Configuration managerConfiguration = new Configuration().diskStore(diskStoreConfigurationParameter).name("dummyManager1");
		
		Cache cache1 = new Cache(cacheConfiguration1);
		Cache cache2 = new Cache(cacheConfiguration2);
		
		CacheManager cacheManager = CacheManager.create(managerConfiguration);
		
		cacheManager.addCache(cache1);
		cacheManager.addCache(cache2);
		
		for(int i=0; i<10000; i++){	
			cache1.put(new Element(i, i));
		}
		
		System.out.println("Integer Insertion Done in Cache1");
		
		for(int i=0; i<10000; i++){
			cache2.put(new Element(i, i));
		}
		
		System.out.println("Integer Insertion Done in Cache2");
		
		System.out.println("Sleeping for 30 seconds...");
		Thread.sleep(30000);
		
		System.out.print("Total Number of Keys in Cache1 = ");
		System.out.println(cache1.getSize());
		
		System.out.println("Size of Heap for cache1");
		System.out.println((float)cache1.calculateInMemorySize()/1024);
		
		System.out.println("Size of Disk for cache1");
		System.out.println((float)cache1.calculateOnDiskSize()/1024);
		
		
		System.out.print("Total Number of Keys in Cache2 = ");
		System.out.println(cache2.getSize(		));
		System.out.println(cache2.getKeys().size());
		
		System.out.println("Size of Heap for cache2");
		System.out.println((float)cache2.calculateInMemorySize()/1024);
		
		System.out.println("Size of Disk for cache2");
		System.out.println((float)cache2.calculateOnDiskSize()/1024);
		
	}
}
	