package pl.michal_sobiech.engineering_thesis.data_source;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Aspect
@Component
@Order(0)
public class RouteInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RouteInterceptor.class);
    
    @Around("@annotation(transactional)")
    public Object chooseRoute(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        try {
			if (transactional.readOnly()) {
				RoutingDataSource.setRoute(Route.REPLICA);
                logger.info("Transaction routed to the replica DB!");
			}
            else {
                RoutingDataSource.setRoute(Route.PRIMARY);
                logger.info("Transaction routed to the primary DB!");
            }
			return joinPoint.proceed();
		} 
        finally {
			RoutingDataSource.clear();
		}
    }

}
