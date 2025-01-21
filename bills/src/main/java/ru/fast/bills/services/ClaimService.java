package ru.fast.bills.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.ClaimEntity;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.data.models.UserEntity;
import ru.fast.bills.data.repository.ClaimRepository;
import ru.fast.bills.processing.exception.ClaimException;
import ru.fast.bills.processing.mappers.ClaimMapper;
import ru.fast.bills.processing.validators.ClaimValidator;
import ru.fast.bills.web.dto.Claim;

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
        UserEntity userEntity = this.userService.findUserEntity(userId);
        claimEntity.setUser(userEntity);
        claimEntity = this.claimRepository.save(claimEntity);
        return this.claimMapper.toDto(claimEntity);
    }

    public List<Claim> getAll() {
        return this.claimMapper.toDtoList(this.claimRepository.findAll());
    }

    public Claim patchClaim(UUID claimId, JsonPatch patch) {
        this.claimValidator.patchValidate(patch);

        ClaimEntity claimEntity = this.findClaimEntity(claimId);

        Claim claim = this.claimMapper.toDto(claimEntity);
        claim = this.applyPatchToCustomer(patch, claim);

        this.claimMapper.updateEntity(claimEntity, claim);
        return this.claimMapper.toDto(claimEntity);
    }

    private ClaimEntity findClaimEntity(UUID claimId) {
        return this.claimRepository.findById(claimId).orElseThrow(() -> ClaimException.claimNotFound(claimId));
    }

    @SneakyThrows
    private Claim applyPatchToCustomer(
            JsonPatch patch, Claim targetCustomer) {
        JsonNode patched = patch.apply(this.objectMapper.convertValue(targetCustomer, JsonNode.class));
        return this.objectMapper.treeToValue(patched, Claim.class);
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

    public List<Claim> claimsByUser(UUID userId) {
        List<ClaimEntity> claimsByUserId = this.claimRepository.findAllByUser_Id(userId);
        return this.claimMapper.toDtoList(claimsByUserId);
    }
}
