/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.exception;

/**
 *
 * @author devnakawickramasinghe
 */
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

@Provider
public class LinkedResourceMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        return Response.status(422)
                .entity("{\"error\":\"" + ex.getMessage() + "\"}")
                .build();
    }
}
