package com.example.automotiveapp.domain.User;

import com.example.automotiveapp.domain.*;
import com.example.automotiveapp.domain.article.Article;
import com.example.automotiveapp.domain.forum.Forum;

import java.util.Date;
import java.util.Set;

// L1 Builder - first impl
public class UserBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String password;
    private Date dateOfBirth;
    private boolean publicProfile;
    private Set<Role> roles;
    private Set<Forum> forums;
    private Set<Post> posts;
    private Set<Like> likes;
    private Set<Article> articles;
    private Set<Comment> comments;
    private File file;

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserBuilder publicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
        return this;
    }

    public UserBuilder roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder forums(Set<Forum> forums) {
        this.forums = forums;
        return this;
    }

    public UserBuilder posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public UserBuilder likes(Set<Like> likes) {
        this.likes = likes;
        return this;
    }

    public UserBuilder articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public UserBuilder comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public UserBuilder file(File file) {
        this.file = file;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setDateOfBirth(dateOfBirth);
        user.setPublicProfile(publicProfile);
        user.setRoles(roles);
        user.setForums(forums);
        user.setPosts(posts);
        user.setLikes(likes);
        user.setArticles(articles);
        user.setComments(comments);
        user.setFile(file);
        return user;
    }
}