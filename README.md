
[![Github contributors][contributors-shield]][contributors-url]
[![Github forks][forks-shield]][forks-url]
[![Github stars][stars-shield]][stars-url]
[![Github license][license-shield]][license-url]

# REST Lite

A Lite REST client project using pure java programming language.

This library was built with the intent of facilitating the process of calling REST APIs.

Similar to Java Persistence API (JPA), everything can be settled using annotations.

## Usage/Examples

Check basic usage below:

```java
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.manager.RestManager;
import io.github.gabrielgp0811.restlite.service.RestService;

public class MainClass {

    private static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) {
        LogManager.getLogManager().readConfiguration();

        RestManager manager = RestManager.getRestManager();

        try {
            RestService service = manager.createService();
    
            service.setUrl("https://api.example.com/");
            service.setMethod("GET");
            service.setContentType("application/json");
            service.setHeader("User-Agent", "Some Example User Agent");

            // You can inform parameters
            service.setParameter("param", "Some parameter value");
    
            LOGGER.info("Result:" + service.getStringResult());
        } catch (RestException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

}
```

## Advanced Features

It's possible to map a REST Service API like **User** class below:

```java
package com.myproject.model;

import java.util.Date;

import io.github.gabrielgp0811.restlite.annotation.RestDateTimeFormat;
import io.github.gabrielgp0811.restlite.annotation.RestService;
import io.github.gabrielgp0811.restlite.annotation.RestServiceParameter;

@RestService(
    name = "User.findAll",

    url = "https://api.example.com/users",

    // OR

    protocol = "https",
    host = "api.example.com",
    // port = 80, // Inform port if needed
    // app = "appname", // Inform appname if needed
    path = "users",

    method = "GET",
    contentType = "application/json", // or application/x-www-form-urlencoded
    headers = {
        @RequestHeader(name = "Accept", value = "application/json"),
        // More headers here
    }
)
@RestService(
    name = "User.findById",

    url = "https://api.example.com/users",

    // OR

    protocol = "https",
    host = "api.example.com",
    // port = 80, // Inform port if needed
    // app = "appname", // Inform appname if needed
    path = "users",

    method = "GET",
    contentType = "application/application/x-www-form-urlencoded",
    parameters = {
        @RestServiceParameter(name = "id", type = Integer.class) // Expected type is Integer
    },
    headers = {
        @RequestHeader(name = "Accept", value = "application/json"),
        // More headers here
    }
)
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id = null;

    private String username = null;

    private String password = null;

    @RestDateTimeFormat("yyyy-MM-dd")
    private Date birthDate = null;

    // ...getters and setters...

}
```

Now, let's create a service consumer like this:

```java
package com.myproject.consumer;

import java.util.List;

import com.myproject.model.User;

import io.github.gabrielgp0811.restlite.consumer.ServiceConsumer;
import io.github.gabrielgp0811.restlite.exception.RestException;
import io.github.gabrielgp0811.restlite.service.RestService;
import io.github.gabrielgp0811.restlite.util.Constants;

public class UserConsumer extends ServiceConsumer<User> {

    static {
        // It's possible to use system property below
        // to inform the location of classes
        System.setProperty("restlite.packagename", "com.myproject.model");
        // or
        System.setProperty(Constants.PACKAGE_NAME_PROPERTY, "com.myproject.model");

        // You can inform more than one package by separating them with semicolon
        System.setProperty(Constants.PACKAGE_NAME_PROPERTY, "com.myproject.model;com.myotherproject.model");
    }

    public UserConsumer() {
        // In case the static constructor above is not used,
        // it's possible to inform package name where classes
        // containing @RestService annotations are located
        super("com.myproject.model");
    }
    
    public List<User> findAll() throws RestException {
        List<User> users = getTypedResultList("User.findAll");

        // If package name in constructor above was not informed,
        // a class, which contains the @RestService annotation, must be provided
        users = getTypedResultList("User.findAll", User.class);
        
        return users;
    }

    public User findById(Integer id) throws RestException {
        return getTypedSingleResult("User.findById", "id", id);
    }

}
```

It's possible to simplify **User** with **@RestServices** annotation like below:

```java
package com.myproject.model;

import java.util.Date;

import io.github.gabrielgp0811.restlite.annotation.RestDateTimeFormat;
import io.github.gabrielgp0811.restlite.annotation.RestService;
import io.github.gabrielgp0811.restlite.annotation.RestServiceParameter;
import io.github.gabrielgp0811.restlite.annotation.RestServices;

@RestServices(
    url = "https://api.example.com/users",

    // OR

    protocol = "https",
    host = "api.example.com",
    // port = 80, // Inform port if needed
    // app = "appname", // Inform appname if needed
    path = "users",

    method = "GET",
    headers = {
        @RequestHeader(name = "Accept", value = "application/json"),
    },
    value = {
        @RestService(name = "User.findAll"),
        @RestService(
            name = "User.findById",
            method = "GET", // You can overwrite HTTP method
            contentType = "application/x-www-form-urlencoded",
            parameters = {
                @RestServiceParameter(name = "id", type = Integer.class)
            }
        ),
    }
)
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id = null;

    private String username = null;

    private String password = null;

    @RestDateTimeFormat("yyyy-MM-dd")
    private Date birthDate = null;

    // ...getters and setters...

}
```

## Demo

Check [UserConsumerTest][demo] class for more information.

## Feedback

If you have any feedback, please reach out to me at gabrielgp0811@gmail.com.

[contributors-shield]: https://img.shields.io/github/contributors/gabrielgp0811/rest-lite.svg?style=for-the-badge
[contributors-url]: https://github.com/gabrielgp0811/rest-lite/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/gabrielgp0811/rest-lite.svg?style=for-the-badge
[forks-url]: https://github.com/gabrielgp0811/rest-lite/network/members
[stars-shield]: https://img.shields.io/github/stars/gabrielgp0811/rest-lite.svg?style=for-the-badge
[stars-url]: https://github.com/gabrielgp0811/rest-lite/stargazers
[issues-shield]: https://img.shields.io/github/issues/gabrielgp0811/rest-lite.svg?style=for-the-badge
[issues-url]: https://github.com/gabrielgp0811/rest-lite/issues
[license-shield]: https://img.shields.io/github/license/gabrielgp0811/rest-lite?style=for-the-badge
[license-url]: https://github.com/gabrielgp0811/rest-lite/blob/master/LICENSE
[demo]: https://github.com/gabrielgp0811/rest-lite/blob/master/src/test/java/io/github/gabrielgp0811/restlite/UserConsumerTest.java