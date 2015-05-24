package edu.sjsu.cmpe.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.Bootstrap;


import edu.sjsu.cmpe.cache.api.resources.CacheResource;
import edu.sjsu.cmpe.cache.repository.CacheInterface;
import edu.sjsu.cmpe.cache.repository.InMemoryCache;
import edu.sjsu.cmpe.cache.config.CacheServiceConfiguration;
import edu.sjsu.cmpe.cache.domain.Entry;

public class CacheService extends Service<CacheServiceConfiguration> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
        new CacheService().run(args);
    }

    @Override
    public void initialize(Bootstrap<CacheServiceConfiguration> bootstrap) {
        bootstrap.setName("cache-server");
    }

    @Override
    public void run(CacheServiceConfiguration configuration,
            Environment environment) throws Exception {
      
        ConcurrentHashMap<Long, Entry> map = new ConcurrentHashMap<Long, Entry>();
        CacheInterface cache = new InMemoryCache(map);
        environment.addResource(new CacheResource(cache));
        log.info("Loaded resources");

    }
}
