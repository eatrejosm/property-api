
package mch.common.impl.reactor;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mch.common.api.reactor.ReactiveWrapperService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.function.Supplier;

@ConfigurationProperties(prefix = "mch.api.reactor")
@Configuration
@Slf4j
public class ReactorAdapterConfig {

    @Setter
    private int adapterPoolSize = 10;


    @Bean
    public Scheduler adapterScheduler() {
        log.info("Creates a scheduler with poolSize {}", adapterPoolSize);
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(adapterPoolSize));
    }

    @Bean
    public ReactiveWrapperService reactiveWrapperService() {
        return new ReactiveWrapperImpl(adapterScheduler());
    }

    @RequiredArgsConstructor
    private static class ReactiveWrapperImpl implements ReactiveWrapperService {
        private final Scheduler scheduler;

        @Override
        public <T> Flux<T> asyncFlux(Supplier<Iterable<T>> iterableSupplier) {
            log.debug("Flux differing starts...");
            return Flux.defer(() -> Flux.fromIterable(iterableSupplier.get())).subscribeOn(scheduler);
        }

        @Override
        public <T> Mono<T> asyncMono(Supplier<T> publisherSupplier) {
            log.debug("Mono differing starts...");
            return Mono.defer(() -> Mono.fromSupplier(publisherSupplier)).subscribeOn(scheduler);
        }
    }
}
