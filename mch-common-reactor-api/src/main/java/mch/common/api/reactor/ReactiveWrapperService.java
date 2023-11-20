package mch.common.api.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * Used to wrap blocking as non-blocking one
 */
public interface ReactiveWrapperService {
    <T> Flux<T> asyncFlux(Supplier<Iterable<T>> iterableSupplier);

    <T> Mono<T> asyncMono(Supplier<T> publisherSupplier);
}
