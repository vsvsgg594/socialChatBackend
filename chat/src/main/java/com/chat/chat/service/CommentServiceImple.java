package com.chat.chat.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.chat.model.Comment;
import com.chat.chat.model.Post;
import com.chat.chat.model.User;
import com.chat.chat.repository.commentsRepository;
import com.chat.chat.repository.postRepository;


@Service
public class CommentServiceImple implements CommentService{
    @Autowired
    private commentsRepository commentRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private postRepository postRepository;


    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        User user=userService.getUserById(userId);

        Post post=postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCraetedAt(LocalDate.now());
        Comment savComment=commentRepo.save(comment);

        post.getComments().add(savComment);
        postRepository.save(post);

        return savComment;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment=findCommentById(commentId);
        User user=userService.getUserById(userId);
        if(!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        }else{
            comment.getLiked().remove(user);
        }

        return commentRepo.save(comment);

        
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> opt=commentRepo.findById(commentId);
        if(opt.isEmpty()){
            throw new Exception("Comments are not exits");
        }
        return opt.get();
        
    }

  
    
}
