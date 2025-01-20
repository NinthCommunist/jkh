package ru.fast.bills.web.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fast.bills.services.ClaimService;
import ru.fast.bills.web.dto.Claim;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<Claim> createClaim(@RequestBody @Valid Claim claim, @RequestHeader("user_id") UUID userId) {
        Claim newClaim = this.claimService.createClaimForUser(userId, claim);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClaim);
    }


    @GetMapping
    public ResponseEntity<List<Claim>> getAll() {
        return ResponseEntity.ok(this.claimService.getAll());
    }

    /*
    req body [{
    "op":"replace",
    "path":"/definition",
    "value":"new definition"
}]*/
    @PatchMapping(path = "{claimId}")
    public ResponseEntity<Claim> patchClaim(@PathVariable("claimId") UUID claimId,
                                            @RequestBody JsonPatch patch) {
        Claim patchedClaim = this.claimService.patchClaim(claimId, patch);
        return ResponseEntity.ok(patchedClaim);
    }

    @DeleteMapping(path = "{claimId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchClaim(@PathVariable("claimId") UUID claimId) {
        this.claimService.delete(claimId);
    }

    @PostMapping("{claimId}/executor")
    public ResponseEntity<Claim> addExecutor(@PathVariable("claimId") UUID claimId, @RequestParam("executorId") long executorId) {
        Claim claim = this.claimService.addExecutorForClaim(claimId, executorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(claim);
    }
}
