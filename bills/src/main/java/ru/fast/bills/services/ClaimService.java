package ru.fast.bills.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.ClaimEntity;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.data.models.UserEntity;
import ru.fast.bills.data.repository.ClaimRepository;
import ru.fast.bills.processing.exception.ClaimException;
import ru.fast.bills.processing.mappers.ClaimMapper;
import ru.fast.bills.processing.validators.ClaimValidator;
import ru.fast.bills.utils.PatchUtils;
import ru.fast.bills.web.dto.Claim;
import ru.fast.bills.web.dto.ClaimInfo;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ExecutorService executorService;
    private final UserService userService;

    private final ClaimValidator claimValidator;
    private final ClaimMapper claimMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public Claim createClaimForUser(UUID userId, Claim claim) {
        ClaimEntity claimEntity = this.claimMapper.toEntity(claim);
        UserEntity userEntity = this.userService.findUserEntityCacheable(userId);
        claimEntity.setUser(userEntity);
        claimEntity = this.claimRepository.save(claimEntity);
        return this.claimMapper.toDto(claimEntity);
    }

    public List<Claim> getAll() {
        List<ClaimEntity> allClaims = this.claimRepository.findAll();
        return this.claimMapper.toDtoList(allClaims);
    }

    @Transactional
    public Claim patchClaim(UUID claimId, JsonPatch patch) {
        this.claimValidator.patchValidate(patch);

        ClaimEntity claimEntity = this.findClaimEntity(claimId);

        Claim claim = this.claimMapper.toDto(claimEntity);
        claim = PatchUtils.applyPatchToCustomer(patch, claim);

        this.claimMapper.updateEntity(claimEntity, claim);
        return this.claimMapper.toDto(claimEntity);
    }

    private ClaimEntity findClaimEntity(UUID claimId) {
        return this.claimRepository.findById(claimId).orElseThrow(() -> ClaimException.claimNotFound(claimId));
    }

    public void delete(UUID claimId) {
        this.claimRepository.deleteById(claimId);
    }

    @Transactional
    public Claim addExecutorForClaim(UUID claimId, long executorId) {
        ExecutorEntity executorEntity = this.executorService.findExecutorEntity(executorId);
        ClaimEntity claimEntity = this.findClaimEntity(claimId);

        claimEntity.setExecutor(executorEntity);
        return this.claimMapper.toDto(claimEntity);
    }

    public List<ClaimInfo> claimsInfoByUser(UUID userId) {
        List<ClaimEntity> claimsByUserId = this.claimRepository.findLazyAllByUser_Id(userId);
        return this.claimMapper.toInfoList(claimsByUserId);
    }

    public Claim getClaim(UUID claimId) {
        ClaimEntity claimEntity = this.findClaimEntity(claimId);
        return this.claimMapper.toDto(claimEntity);
    }

    public Page<Claim> getAllPageable(Pageable pageable) {
        Page<ClaimEntity> claimEntities = this.claimRepository.findAll(pageable);
        return claimEntities.map(this.claimMapper::toDto);
    }
}
