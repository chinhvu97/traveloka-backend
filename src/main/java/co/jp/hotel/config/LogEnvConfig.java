package co.jp.hotel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class LogEnvConfig implements ApplicationListener<ContextRefreshedEvent> {

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    final Environment environment = event.getApplicationContext().getEnvironment();
    log.info("====== Environment and configuration ======");
    log.info("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
    final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
    StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
        .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
        .forEach(prop -> log.info("{}", prop));// environment.getProperty(prop)
    log.info("===========================================");
  }
}
