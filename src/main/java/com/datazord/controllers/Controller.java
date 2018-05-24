package com.datazord.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class Controller {

//	@GetMapping(path = "posts")
//	  Flux<Post> getPosts(
//	      @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
//	    return postRepository
//	        .findAllBy(PageRequest.of(page, RESULTS_PER_PAGE, SORT_BY_CREATED_DATE))
//	        .flatMap(this::fillCommentsCount);
//	  }
//
//	  @GetMapping("posts/{id}")
//	  Mono<ResponseEntity<Post>> getPost(@PathVariable String id) {
//	    Mono<Post> postMono = postRepository.findById(id);
//	    Mono<Long> commentsCountMono = commentRepository.countCommentsByPostId(id);
//	    return postMono
//	        .zipWith(commentsCountMono,
//	            (post, commentsCount) -> ResponseEntity.ok(fillCommentsCount(post, commentsCount)))
//	        .defaultIfEmpty(ResponseEntity.notFound().build());
//	  }
//
//	  @GetMapping(value = "/posts/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	  public Flux<Post> streamAllPosts() {
//	    return postRepository.findAll(SORT_BY_CREATED_DATE).flatMap(this::fillCommentsCount);
//	  }
//
//	  @PostMapping("posts")
//	  Mono<ResponseEntity<Post>> createPost(@Valid @RequestBody Post post) {
//	    post.setCreatedDate(Instant.now());
//	    post.setUpdatedDate(Instant.now());
//	    Mono<Long> commentsCountMono = commentRepository.countCommentsByPostId(post.getId());
//	    return postRepository
//	        .save(post)
//	        .zipWith(commentsCountMono, this::fillCommentsCount)
//	        .map(createdPost -> ResponseEntity.status(HttpStatus.CREATED).body(createdPost));
//	  }
//
//	  @PutMapping("posts/{id}")
//	  Mono<ResponseEntity<Post>> updatePost(@PathVariable String id,
//	      @Valid @RequestBody Post requestPost) {
//	    Mono<Long> commentsCountMono = commentRepository.countCommentsByPostId(id);
//	    return postRepository
//	        .findById(id)
//	        .flatMap(post -> {
//	          requestPost.setId(post.getId());
//	          requestPost.setUpdatedDate(Instant.now());
//	          return postRepository.save(requestPost);
//	        })
//	        .zipWith(commentsCountMono, this::fillCommentsCount)
//	        .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//	  }
//
//	  @DeleteMapping("posts/{id}")
//	  Mono<ResponseEntity<Void>> deletePost(@PathVariable String id) {
//	    return postRepository.findById(id)
//	        .flatMap(post ->
//	            postRepository.delete(post)
//	                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
//	        )
//	        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//	}
}
