package com.github.msemitkin.chat.server.rest.model.response;

import java.util.List;

@SuppressWarnings("unused")
public class GetContactsResponse {
    private final List<String> contacts;

    public GetContactsResponse(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }
}
