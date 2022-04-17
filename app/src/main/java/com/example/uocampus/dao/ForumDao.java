package com.example.uocampus.dao;

import com.example.uocampus.model.PostModel;

import java.util.List;

public interface ForumDao {

    boolean addPost(PostModel post);

    List<PostModel> getPosts();
}
