/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resource;

import com.smartcampus.api.model.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import com.smartcampus.api.exception.RoomNotEmptyException;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    public static Map<String, Room> rooms = new HashMap<>();

    // GET all rooms
    @GET
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    // POST create new room
    @POST
    public Response createRoom(Room room) {
        rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{id}")
    public Response getRoomById(@PathParam("id") String id) {
        Room room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();

    }

    @PUT
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {

        Room existing = rooms.get(id);

        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        // update values
        existing.setName(updatedRoom.getName());
        existing.setCapacity(updatedRoom.getCapacity());
        existing.setSensorIds(updatedRoom.getSensorIds());

        return Response.ok(existing).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        // business rule: cannot delete if sensors exist
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room has active sensors");
        }

        rooms.remove(id);

        return Response.ok("Room deleted successfully").build();
    }
}
