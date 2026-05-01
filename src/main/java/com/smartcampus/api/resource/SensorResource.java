/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resource;

import com.smartcampus.api.model.Sensor;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;
import com.smartcampus.api.exception.LinkedResourceNotFoundException;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    public static Map<String, Sensor> sensors = new HashMap<>();

    @POST
    public Response createSensor(Sensor sensor) {

        // validate room exists
        if (!RoomResource.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        sensors.put(sensor.getId(), sensor);

        // link sensor to room
        RoomResource.rooms.get(sensor.getRoomId())
                .getSensorIds()
                .add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensors.values();
        }

        return sensors.values().stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // SUB RESOURCE LOCATOR (VERY IMPORTANT PART 4)
    @Path("/{id}/readings")
    public SensorReadingResource getReadings(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}