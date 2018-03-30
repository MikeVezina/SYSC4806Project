package com.sysc4806.project.models.social;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "UserConnection")
@Table(name = "userconnection",
        uniqueConstraints = { @UniqueConstraint(name = "UserConnectionRank", columnNames = { "userid", "providerid", "rank" })})
public class UserConnection {

    @EmbeddedId
    private UserConnectionKey userConnectionKey;

    @NotNull
    @Column(name = "rank")
    private Integer rank;

    @Size(max = 255)
    @Column(name = "displayname")
    private String displayName;

    @Size(max = 512)
    @Column(name = "profileurl")
    private String profileUrl;

    @Size(max = 512)
    @Column(name = "imageurl")
    private String imageUrl;

    @NotNull
    @Size(max = 255)
    @Column(name = "accesstoken")
    private String accessToken;

    @Size(max = 255)
    @Column(name = "secret")
    private String secret;

    @Size(max = 255)
    @Column(name = "refreshtoken")
    private String refreshToken;

    @Column(name = "expiretime")
    private Long expireTime;

    public UserConnection(UserConnectionKey userConnectionKey, Integer rank, String accessToken) {
        this.userConnectionKey = userConnectionKey;
        this.rank = rank;
        this.accessToken = accessToken;
    }

    private UserConnection() {
        // Default empty constructor
    }


    public UserConnectionKey getUserConnectionKey() {
        return userConnectionKey;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserConnection that = (UserConnection) o;

        return userConnectionKey != null ? userConnectionKey.equals(that.userConnectionKey) : that.userConnectionKey == null;
    }

    @Override
    public int hashCode() {
        return userConnectionKey.hashCode();
    }
}
