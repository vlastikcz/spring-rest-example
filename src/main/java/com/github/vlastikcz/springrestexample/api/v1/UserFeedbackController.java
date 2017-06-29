package com.github.vlastikcz.springrestexample.api.v1;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vlastikcz.springrestexample.api.v1.resource.NewUserFeedback;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResource;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResourceAssembler;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.service.UserFeedbackService;

import static com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController.USER_FEEDBACK_CONTROLLER_PATH;

@RestController
@ExposesResourceFor(UserFeedbackResource.class)
@RequestMapping(USER_FEEDBACK_CONTROLLER_PATH)
public class UserFeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(UserFeedbackController.class);

    static final String USER_FEEDBACK_CONTROLLER_PATH = "/feedback/v1";
    static final String LIST_FEEDBACK_BY_NAME_PARAM = "name";

    private final UserFeedbackService userFeedbackService;
    private final UserFeedbackResourceAssembler userFeedbackResourceAssembler;

    @Autowired
    public UserFeedbackController(UserFeedbackService userFeedbackService, UserFeedbackResourceAssembler userFeedbackResourceAssembler) {
        this.userFeedbackService = userFeedbackService;
        this.userFeedbackResourceAssembler = userFeedbackResourceAssembler;
    }

    @PostMapping
    public ResponseEntity<UserFeedbackResource> createNewFeedback(@Valid @RequestBody NewUserFeedback newUserFeedback) {
        logger.info("new feedback submitted [feedback={}]", newUserFeedback);
        final UserFeedback userFeedback = userFeedbackService.save(newUserFeedback.toUserFeedback());
        final UserFeedbackResource userFeedbackResource = userFeedbackResourceAssembler.toResource(userFeedback);
        final URI uri = URI.create(userFeedbackResource.getLink(Link.REL_SELF).getHref());
        return ResponseEntity.created(uri).body(userFeedbackResource);
    }

    @GetMapping
    public ResponseEntity<List<UserFeedbackResource>> listAllFeedback() {
        logger.info("listing all feedback items");
        final Iterable<UserFeedback> userFeedbackItems = userFeedbackService.findAll();
        final List<UserFeedbackResource> userFeedbackResources = userFeedbackResourceAssembler.toResources(userFeedbackItems);
        return ResponseEntity.ok(userFeedbackResources);
    }

    @GetMapping(params = LIST_FEEDBACK_BY_NAME_PARAM)
    public ResponseEntity<List<UserFeedbackResource>> listFeedbackByName(@RequestParam(name = LIST_FEEDBACK_BY_NAME_PARAM) String name) {
        logger.info("listing feedback items by [name={}]", name);
        final Iterable<UserFeedback> userFeedbackItems = userFeedbackService.findByName(name);
        final List<UserFeedbackResource> userFeedbackResources = userFeedbackResourceAssembler.toResources(userFeedbackItems);
        return ResponseEntity.ok(userFeedbackResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFeedbackResource> findFeedbackById(@PathVariable UUID id) {
        logger.info("getting feedback item by [id={}]", id);
        return userFeedbackService.findById(id)
                .map(f -> userFeedbackResourceAssembler.toResource(f))
                .map(r -> ResponseEntity.ok(r))
                .orElse(ResponseEntity.notFound().build());
    }
}
