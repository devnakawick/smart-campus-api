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
public class GlobalException implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Internal Server Error\"}")
                .build();
    }
}
