package mch.asset.view;

import mch.asset.entity.MchProperty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PropertyViewMapper {

    public abstract PropertyView fromModel(MchProperty property);
}
