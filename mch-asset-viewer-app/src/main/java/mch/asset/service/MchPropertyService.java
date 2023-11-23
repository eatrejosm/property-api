package mch.asset.service;

import mch.asset.entity.MchProperty;
import mch.asset.repository.MchPropertyRepository;
import lombok.AllArgsConstructor;
import mch.common.api.reactor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MchPropertyService {
    private final MchPropertyRepository mchPropertyRepository;
    private final ReactiveWrapperService reactiveWrapper;

    public Mono<Page<MchProperty>> findAll(Pageable paging){
        return reactiveWrapper.asyncMono(() -> mchPropertyRepository.findAll(paging));
    }
    public Mono<Page<MchProperty>> findByProfile(String profile, Pageable paging){
        return reactiveWrapper.asyncMono(() -> mchPropertyRepository.findByProfileContaining(profile, paging));
    }
    public Mono<Page<MchProperty>> findByApplication(String application, Pageable paging){
        return  reactiveWrapper.asyncMono(() -> mchPropertyRepository.findByApplicationContaining(application, paging));
    }
}
