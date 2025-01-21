package ru.fast.bills.processing.mappers;

import org.mapstruct.*;
import ru.fast.bills.data.models.ClaimEntity;
import ru.fast.bills.web.dto.Address;
import ru.fast.bills.web.dto.Claim;
import ru.fast.bills.web.dto.ClaimInfo;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ClaimMapper {
    public abstract ClaimEntity toEntity(Claim claim);

    public abstract Claim toDto(ClaimEntity claimEntity);

    public abstract ClaimInfo toInfo(ClaimEntity claimEntity);

    public abstract List<Claim> toDtoList(List<ClaimEntity> claimEntities);

    public abstract List<ClaimInfo> toInfoList(List<ClaimEntity> claimEntities);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(@MappingTarget ClaimEntity claimEntity, Claim claim);

    public Address toAddressDto(ru.fast.bills.data.models.Address address) {
        return new Address(address.getCity(), address.getStreet(), address.getHouse());
    }

    public ru.fast.bills.data.models.Address toAddressEntity(Address address) {
        return new ru.fast.bills.data.models.Address(address.city(), address.street(), address.house());
    }
}