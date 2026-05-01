/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resource;

import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.model.SensorReading;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import com.smartcampus.api.exception.SensorUnavailableException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SensorReadingResource {

    private String sensorId;

    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getAllReadings() {
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = SensorResource.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }
        if (sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            throw new SensorUnavailableException("Sensor under maintenance");
        }

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

        // IMPORTANT SIDE EFFECT (SPEC REQUIREMENT)
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
