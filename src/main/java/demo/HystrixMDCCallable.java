package demo;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class HystrixMDCCallable<K> implements Callable<K> {
	private final Callable<K> actual;
	private final Map<String,String> parentMDC;
	  private static final Logger LOGGER = LoggerFactory
		      .getLogger(HystrixMDCCallable.class);
	  
	public HystrixMDCCallable(Callable<K> actual) {
	    this.actual = actual;
	    this.parentMDC = MDC.getCopyOfContextMap();
	}

	@Override
	public K call() throws Exception {
	    Map<String,String> childMDC = MDC.getCopyOfContextMap();
	    try {
	    	LOGGER.info("******** {}",parentMDC);
	        MDC.setContextMap(parentMDC);
	        return actual.call();
	    } finally {
	        MDC.setContextMap(childMDC);
	    }
	}	

}
