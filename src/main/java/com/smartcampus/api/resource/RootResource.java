/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resource;

/**
 *
 * @author devnakawickramasinghe
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    @GET
    public Response getApiInfo() {

        Map<String, Object> response = new HashMap<>();

        response.put("version", "v1");
        response.put("developer", "Smart Campus API");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("rooms", "/api/v1/rooms");
        endpoints.put("sensors", "/api/v1/sensors");

        response.put("endpoints", endpoints);

        return Response.ok(response).build();
    }
}
