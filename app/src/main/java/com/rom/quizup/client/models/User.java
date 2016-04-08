package com.rom.quizup.client.models;


/**
 * Created by rom on 30/03/2016.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String idToken;
    private String systemId;
    private String nickname;

    public User(String idToken) {
        this.idToken = idToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--------------------------------------------\n");
        sb.append("User:\n");
        sb.append("username:    ").append(username).append("\n");
        sb.append("email:    ").append(email).append("\n");
        sb.append("firstName:    ").append(firstName).append("\n");
        sb.append("lastName:    ").append(lastName).append("\n");
        sb.append("idToken:    ").append(idToken).append("\n");
        sb.append("systemId:    ").append(systemId).append("\n");
        sb.append("nickName:    ").append(nickname).append("\n");
        sb.append("--------------------------------------------\n");

        return sb.toString();
    }

}
