package com.sysc4806.project.models.social;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserConnectionKey implements Serializable {

    @NotNull
    @Size(max = 255)
    @Column(name = "userid")
    private String userId;

    @NotNull
    @Size(max = 255)
    @Column(name = "providerid")
    private String providerId;

    @Size(max = 255)
    @Column(name = "provideruserid")
    private String providerUserId;

    public UserConnectionKey()
    {

    }

    public UserConnectionKey(String userId, String providerId, String providerUserId) {
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnectionKey)) return false;
        UserConnectionKey that = (UserConnectionKey) o;
        return Objects.equals(getProviderId(), that.getProviderId()) &&
                Objects.equals(getProviderUserId(), that.getProviderUserId()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getProviderId(), getProviderUserId());
    }

    public String getUserId() {
        return userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }
}