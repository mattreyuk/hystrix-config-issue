package demo;

import java.util.concurrent.Callable;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

public class HystrixMDCConcurrencyStrategy extends HystrixConcurrencyStrategy{
	@Override
	public Callable wrapCallable(Callable callable) {
		return new HystrixMDCCallable(callable);
	}

}
