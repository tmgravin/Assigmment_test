//package com.intern.msp.security.oauth2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/facebook")
//public class FacebookLoginController {
//
//    private final CustomerOauth2Service customerOauth2Service;
//
//    @Autowired
//    public FacebookLoginController(CustomerOauth2Service customerOauth2Service) {
//        this.customerOauth2Service = customerOauth2Service;
//    }
//
//    @PostMapping("/code")
//    public OAuth2User loadUser(@RequestBody OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        return customerOauth2Service.loadUser(userRequest);
//    }
//}
