package self.soa.support.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

/**
 * @Author: YuanChangShuai
 * @Date: 2022/1/7 16:27
 * @Description:
 **/
public class RedissonSpringRegister implements EnvironmentAware {
    
    private static final String PREFIX = "redis://";
    private static final String HOST_KEY = "spring.redis.host";
    private static final String PORT_KEY = "spring.redis.port";
    private static final String CLUSTER_KEY = "spring.redis.cluster.nodes";
    private static final String SENTINEL_KEY = "spring.redis.sentinel.nodes";
    private static final String SENTINEL_MASTER = "spring.redis.sentinel.master";
    private static final String PASSWORD_KEY = "spring.redis.password";

    private Environment environment;

    @Bean
    public RedissonClient getRedisson() {
        String host = environment.getProperty(HOST_KEY);
        String cluster = environment.getProperty(CLUSTER_KEY);
        String sentinel = environment.getProperty(SENTINEL_KEY);
        String password = environment.getProperty(PASSWORD_KEY);
        Config config = new Config();

        if (StringUtils.hasText(host)) {
            //单节点模式
            String port = environment.getProperty(PORT_KEY);
            if (StringUtils.hasText(port)) {
                throw new IllegalArgumentException("[getRedisson] port is null.");
            }
            SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(PREFIX + host + ":" + port);
            if (StringUtils.hasText(password)) {
                singleServerConfig.setPassword(password);
            }
        } else if (StringUtils.hasText(cluster)) {
            //集群模式
            String[] nodes = cluster.split(",");
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = PREFIX + nodes[i];
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers().setScanInterval(2000).addNodeAddress(nodes);
            if (StringUtils.hasText(password)) {
                clusterServersConfig.setPassword(password);
            }
        } else if (StringUtils.hasText(sentinel)) {
            //哨兵模式
            String masterName = environment.getProperty(SENTINEL_MASTER);
            String[] nodes = sentinel.split(",");
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = PREFIX + nodes[i];
            }
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers().setMasterName(masterName).addSentinelAddress(nodes);
            if (StringUtils.hasText(password)) {
                sentinelServersConfig.setPassword(password);
            }
        }

        return Redisson.create(config);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
