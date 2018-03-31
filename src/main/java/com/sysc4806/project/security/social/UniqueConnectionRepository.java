package com.sysc4806.project.security.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class UniqueConnectionRepository implements ConnectionRepository{

    private UsersConnectionRepository usersConnectionRepository;
    private ConnectionRepository repo;

    public UniqueConnectionRepository(UsersConnectionRepository usersConnectionRepository, ConnectionRepository repository)
    {
        this.usersConnectionRepository = usersConnectionRepository;
        this.repo = repository;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        return repo.findAllConnections();
    }

    @Override
    public List<Connection<?>> findConnections(String s) {
        return repo.findConnections(s);
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> aClass) {
        return repo.findConnections(aClass);
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> multiValueMap) {
        return repo.findConnectionsToUsers(multiValueMap);
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        return repo.getConnection(connectionKey);
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> aClass, String s) {
        return repo.getConnection(aClass, s);
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> aClass) {
        return repo.getPrimaryConnection(aClass);
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> aClass) {
        return repo.findPrimaryConnection(aClass);
    }

    @Override
    public void addConnection(Connection<?> connection) {
        if(usersConnectionRepository.findUserIdsWithConnection(connection).size() > 0)
            throw new DuplicateConnectionException(connection.getKey());

        repo.addConnection(connection);
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        repo.updateConnection(connection);
    }

    @Override
    public void removeConnections(String s) {
        repo.removeConnections(s);
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        repo.removeConnection(connectionKey);
    }
}
