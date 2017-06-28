package com.github.vlastikcz.springrestexample.api.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.vlastikcz.springrestexample.api.v1.resource.NewUserFeedback;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResource;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResourceAssembler;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.service.UserFeedbackService;

@RestController
@ExposesResourceFor(UserFeedbackResource.class)
@RequestMapping("/feedback/v1")
public class UserFeedbackController {

    private final UserFeedbackService userFeedbackService;
    private final UserFeedbackResourceAssembler userFeedbackResourceAssembler;

    @Autowired
    public UserFeedbackController(UserFeedbackService userFeedbackService, UserFeedbackResourceAssembler userFeedbackResourceAssembler) {
        this.userFeedbackService = userFeedbackService;
        this.userFeedbackResourceAssembler = userFeedbackResourceAssembler;
    }

    @PostMapping
    public ResponseEntity<UserFeedbackResource> saveNewFeedback(@RequestBody NewUserFeedback newUserFeedback) {
        final UserFeedback userFeedback = userFeedbackService.create(newUserFeedback.toUserFeedback());
        final UserFeedbackResource userFeedbackResource = userFeedbackResourceAssembler.toResource(userFeedback);
        return ResponseEntity.ok(userFeedbackResource);
    }

    @GetMapping
    public ResponseEntity<List<UserFeedbackResource>> listAllFeedback() {
        final Iterable<UserFeedback> userFeedbackItems = userFeedbackService.findAll();
        final List<UserFeedbackResource> userFeedbackResources = userFeedbackResourceAssembler.toResources(userFeedbackItems);
        return ResponseEntity.ok(userFeedbackResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFeedbackResource> showFeedbackById(@PathVariable UUID id) {
        return userFeedbackService.findById(id)
                .map(f -> userFeedbackResourceAssembler.toResource(f))
                .map(r -> ResponseEntity.ok(r))
                .orElse(ResponseEntity.notFound().build());
    }
}
