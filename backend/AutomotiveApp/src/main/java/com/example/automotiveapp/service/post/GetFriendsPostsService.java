package com.example.automotiveapp.service.post;

import com.example.automotiveapp.reponse.PostResponse;
import org.springframework.data.domain.Pageable;

public interface GetFriendsPostsService {

    PostResponse getFriendsPosts(Pageable pageable);
}
