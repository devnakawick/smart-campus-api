


# Smart Campus API

## Overview

This project is a RESTful API for managing rooms, sensors, and sensor readings in a smart campus environment.
It is developed using Java, JAX-RS (Jersey), and Grizzly server.

---

## How to Run

1. Open the project in NetBeans
2. Build the project
3. Run `Main.java`
4. Server will start at:
   http://localhost:8080/api/v1

---
example
## API Endpoints

### Rooms

* `GET /api/v1/rooms` → Get all rooms
* `POST /api/v1/rooms` → Create room
* `GET /api/v1/rooms/{id}` → Get room by ID
* `PUT /api/v1/rooms/{id}` → Update room
* `DELETE /api/v1/rooms/{id}` → Delete room

---

### Sensors

* `GET /api/v1/sensors` → Get all sensors
* `GET /api/v1/sensors?type=CO2` → Filter sensors by type
* `POST /api/v1/sensors` → Create sensor

---

### Sensor Readings

* `GET /api/v1/sensors/{id}/readings` → Get readings
* `POST /api/v1/sensors/{id}/readings` → Add reading

---

## Exception Handling

* `409 Conflict` → Room not empty
* `422 Unprocessable Entity` → Invalid linked resource
* `403 Forbidden` → Sensor in maintenance mode
* `500 Internal Server Error` → Unexpected errors

---

## Features Implemented

* REST API design
* Resource hierarchy (Rooms → Sensors → Readings)
* Query filtering
* Sub-resource locator
* Exception mapping
* Logging filter

---

## Example Request

### Create Room

```bash
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{
"id": "R1",
"name": "Library",
"capacity": 100
}'
```

---

## Developer

Smart Campus API Project

## part 1 JAX-RS Resource Lifecycle

By default, JAX-RS resource classes are instantiated per request, i.e., a new instance of the resource class is created for each HTTP request received. This makes it thread safe as each request is working on its own instance of an object.

But in this project, shared data structures in memory, such as HashMaps, are declared as static variables. This provides all resource instances with access to the same data. These shared structures are exposed to several requests at the same time, so there is a potential for race conditions. In a production system you'd want to use synchronised or concurrent collections but for the purposes of this coursework simple HashMaps are fine.

## part 1 Hateos 

Hypermedia as the engine of application state (HATEOAS) is an important principle of RESTful design, where responses include links to related resources. This enables clients to explore the API dynamically without needing to refer to external documentation.

This approach is good for developers as the API is self-descriptive. Clients can use the links provided in the response instead of hardcoding endpoints, which improves flexibility and reduces tight coupling between client and server.
## part 2 IDS vs Full objects

Returning only IDs helps to save on network bandwidth usage and improves performance especially with large datasets. But it requires clients to make extra requests to get full details.

Returning full objects will increase the size of the response but will provide complete information in a single request, minimising the need for additional API calls. The choice is based on the performance considerations and client requirements.

## part 2 DELETE idempotency
The DELETE operation is idempotent because sending the same request multiple times results in the same final state. If a room is successfully deleted, subsequent DELETE calls for that room will receive a "not found" response, and will not make any additional modifications to the system.

This provides predictable behaviour and aligns with RESTful principles.

## part 3 @Consumes-JSON
The @Consumes(MediaType.The @RequestMapping annotation with produces=APPLICATION_JSON) indicates that the API expects JSON input. If the client sends data in a different format (say text/plain or application/xml), JAX-RS will reject the request with an HTTP 415 Unsupported Media Type error.

This guarantees that the server only processes data in the expected format.



## part 4 subResource Locator 
Query parameters are better suited for filtering and searching, as they are optional criteria. for example /sensors?type=CO2 allows you to filter flexibly.

If you use path parameters like /sensors/type/CO2 it means a strict resource hierarchy and not a filter. Query parameters are a better way to handle dynamic queries, being more intuitive and scalable.

## part 5 HTTP 422 VS 404
In contrast, HTTP 404 indicates that the requested resource itself does not exist. Therefore, 422 provides more accurate semantic meaning for validation errors.

HTTP 422  Unprocessable Entity is more appropriate when the request is syntactically correct but contains invalid data, for example, a valid JSON payload referencing a non-existent room ID.

HTTP 404, on the other hand, means that the requested resource doesn’t exist in the first place. Thus, 422 provides more precise semantic meaning for validation errors.

## part 5 Security risk of Stack Traces.
Publishing internal Java stack traces may expose sensitive information such as class names, file paths and system architecture. Attackers can use this information to locate vulnerabilities and exploit the system.

The API can return generic error messages instead, preventing information leakage and improving security.
## part 5 Logging Filters
Logging is done using JAX-RS filters to centralise cross-cutting concerns such as request and response tracking. This avoids the need to duplicate the logging logic in each resource method.

It makes maintenance easier and ensures uniform logging throughout the API.



