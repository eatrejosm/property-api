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
    private static final String TRANSACTION_STATUS = "CLOSED";
    private final MchPropertyRepository mchPropertyRepository;
    private final ReactiveWrapperService reactiveWrapper;

}
