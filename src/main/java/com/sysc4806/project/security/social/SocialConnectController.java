package com.sysc4806.project.security.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


@Controller
@Component
public class SocialConnectController extends ConnectController {

    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, new UniqueConnectionRepository(usersConnectionRepository, connectionRepository));
    }

    @Override
    protected String connectView(String providerId) {
        return "redirect:/";
    }

    @Override
    protected String connectedView(String providerId) {
        return "redirect:/";
    }
}
