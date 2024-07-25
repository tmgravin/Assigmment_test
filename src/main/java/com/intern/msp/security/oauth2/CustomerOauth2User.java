//package com.intern.msp.security.oauth2;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Map;
//
//public class CustomerOauth2User implements OAuth2User {
//    //OAuth2User representation of user princleple(email,id..etc)
//    private final OAuth2User oAuth2User;
//
//    public CustomerOauth2User(OAuth2User oAuth2User) {
//        this.oAuth2User = oAuth2User;
//    }
//    @Override
//    public Map<String, Object> getAttributes() {
//        return oAuth2User.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return oAuth2User.getAuthorities();
//    }
//
//    @Override
//    public String getName() {
//        return oAuth2User.getName();
//    }
//}
