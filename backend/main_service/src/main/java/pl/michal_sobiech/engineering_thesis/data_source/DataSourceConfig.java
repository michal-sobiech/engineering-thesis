package pl.michal_sobiech.engineering_thesis.data_source;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(
            @Value("${spring.datasource.primary.url}") String PRIMARY_DATASOURCE_URL,
            @Value("${spring.datasource.primary.username}") String PRIMARY_DATASOURCE_USERNAME,
            @Value("${spring.datasource.primary.password}") String PRIMARY_DATASOURCE_PASSWORD,
            @Value("${spring.datasource.primary.driver-class-name}") String PRIMARY_DATASOURCE_DRIVER,
            @Value("${spring.datasource.replica.url}") String REPLICA_DATASOURCE_URL,
            @Value("${spring.datasource.replica.username}") String REPLICA_DATASOURCE_USERNAME,
            @Value("${spring.datasource.replica.password}") String REPLICA_DATASOURCE_PASSWORD,
            @Value("${spring.datasource.replica.driver-class-name}") String REPLICA_DATASOURCE_DRIVER) {

        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println(PRIMARY_DATASOURCE_URL);
        System.out.println(PRIMARY_DATASOURCE_USERNAME);
        System.out.println(PRIMARY_DATASOURCE_PASSWORD);
        System.out.println(PRIMARY_DATASOURCE_DRIVER);
        System.out.println(REPLICA_DATASOURCE_URL);
        System.out.println(REPLICA_DATASOURCE_USERNAME);
        System.out.println(REPLICA_DATASOURCE_PASSWORD);

        final RoutingDataSource routingDataSource = new RoutingDataSource();

        final DataSource primaryDataSource = makeDataSource(
                "PrimaryHikariPool",
                PRIMARY_DATASOURCE_URL,
                PRIMARY_DATASOURCE_USERNAME,
                PRIMARY_DATASOURCE_PASSWORD,
                PRIMARY_DATASOURCE_DRIVER);
        final DataSource replicaDataSource = makeDataSource(
                "ReplicaHikariPool",
                REPLICA_DATASOURCE_URL,
                REPLICA_DATASOURCE_USERNAME,
                REPLICA_DATASOURCE_PASSWORD,
                REPLICA_DATASOURCE_DRIVER);

        System.out.println("=====================================================================");
        try {
            System.out.println(primaryDataSource.getConnection().getMetaData().getURL());
            System.out.println(replicaDataSource.getConnection().getMetaData().getURL());
        } catch (Exception exception) {
            System.out.println("Errrorrrrr");
        }

        final Map<Object, Object> targetDataSources = Map.of(
                Route.PRIMARY, primaryDataSource,
                Route.REPLICA, replicaDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(primaryDataSource);

        return routingDataSource;
    }

    private DataSource makeDataSource(
            String poolName,
            String url,
            String username,
            String password,
            String driver) {
        final HikariConfig config = new HikariConfig();

        config.setPoolName(poolName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        return new HikariDataSource(config);
    }
}