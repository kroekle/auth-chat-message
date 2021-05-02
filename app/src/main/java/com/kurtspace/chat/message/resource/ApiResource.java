package com.kurtspace.chat.message.resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.kurtspace.chat.message.clients.UsersClient;
import com.kurtspace.chat.message.clients.UsersClient.User;

import lombok.Data;


@Path("/messages")
public class ApiResource {

    private static final List<Message> messages = new ArrayList<>();
    
    @GET
    @Path("/ping")
    public String ping() {
        return "hello";
    }

    @GET
    @Produces("application/json")
    public List<Message> getMessages(@QueryParam("sub") int sub) {

       return messages.stream()
            .filter(m -> m.from == sub || m.to == null || m.to.size() == 0 || m.to.stream().anyMatch(s -> s == sub))
            .sorted(Comparator.comparing(Message::getSent))
            .collect(Collectors.toList());
    }
    
    @POST
    @Consumes("application/json")
    public void sendMessage(Message message) {
        message.setSent(LocalDateTime.now());
        messages.add(message);
    }

    @Data
    private static final class Message {

        private int from;
        private List<Integer> to;
        private String message;
        private LocalDateTime sent;
    }
}

