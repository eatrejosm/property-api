package mch.asset.entity.mapper;

import mch.asset.entity.UserEntity;
import mch.asset.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "password", ignore = true)
    User toView(UserEntity userEntity);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    UserEntity toEntity(User user);

}
