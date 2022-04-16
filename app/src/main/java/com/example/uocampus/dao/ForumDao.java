package com.example.uocampus.dao;

import com.example.uocampus.model.PostModel;

import java.util.List;

public interface ForumDao {

    public boolean addPost(PostModel post);

    public List<PostModel> getPosts();
}
