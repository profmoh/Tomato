package com.datazord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.model.TomatoCategories;
import com.datazord.service.TomatoCategoriesService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping({"/api/tomatocategories"})
public class TomatoCategoriesController {

	private static final Sort SORT_BY_CREATED_DATE = new Sort(Direction.DESC, "createdDate");

	@Autowired
	private TomatoCategoriesService service;

	@GetMapping(value = "/findAll")
	public Flux<TomatoCategories> findAll() {
		return service.findAll(SORT_BY_CREATED_DATE);
	}

	@GetMapping
	public Flux<TomatoCategories> findCategories(Model model) {
		return service.findAll(SORT_BY_CREATED_DATE);
	}

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
//	  }
//
//
//	  @GetMapping("posts/{postId}/comments")
//	  Flux<Comment> getComments(@PathVariable(name = "postId") String postId,
//	      @RequestParam(name = "page", defaultValue = "0", required = false) int page) {
//	    return commentRepository.findAllCommentsByPostId(postId,
//	        PageRequest.of(page, RESULTS_PER_PAGE, SORT_BY_CREATED_DATE))
//	        .skip(((page + 1) * RESULTS_PER_PAGE) - RESULTS_PER_PAGE).take(RESULTS_PER_PAGE);
//	  }
//
//	  @GetMapping(value = "posts/{postId}/comments/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	  Flux<Comment> streamAllComments(@PathVariable(name = "postId") String postId) {
//	    return commentRepository.findAllCommentsByPostId(postId);
//	  }
//
//	  @PostMapping("posts/{postId}/comments")
//	  Mono<ResponseEntity<Comment>> createComment(@PathVariable(name = "postId") String postId,
//	      @RequestBody @Valid Comment comment) {
//	    return postRepository.findById(postId)
//	        .flatMap(post -> {
//	          comment.setPostId(post.getId());
//	          comment.setCreatedDate(Instant.now());
//	          return commentRepository.save(comment);
//	        })
//	        .map(createdComment -> ResponseEntity.status(HttpStatus.CREATED).body(createdComment))
//	        .defaultIfEmpty(ResponseEntity.notFound().build());
//	  }
//
//	  @DeleteMapping("posts/{postId}/comments/{commentId}")
//	  Mono<ResponseEntity<Void>> deleteComment(@PathVariable(name = "commentId") String commentId) {
//	    return commentRepository.findById(commentId)
//	        .flatMap(comment ->
//	            commentRepository.delete(comment)
//	                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
//	        )
//	        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//	  }
//
//	  private Mono<Post> fillCommentsCount(Post post) {
//	    return commentRepository.countCommentsByPostId(post.getId()).map(count -> {
//	      post.setCommentsCount(count);
//	      return post;
//	    });
//	  }
//
//	  private Post fillCommentsCount(Post post, Long commentsCount) {
//	    post.setCommentsCount(commentsCount);
//	    return post;
//	  }

}
