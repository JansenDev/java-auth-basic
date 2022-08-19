package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.models.DataResponse;
import com.example.demo.models.Login;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class demoController {
    @Value("${SECRET_JWT}")
    private String SECRET_JWT;

    @GetMapping()
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello World", HttpStatus.CREATED);
    }

    @GetMapping("/otro")
    public ResponseEntity<DataResponse<String>> test2() {
        DataResponse<String> dataresponse = new  DataResponse<>();
        dataresponse.setData("segunda ruta");
        return new ResponseEntity<>(dataresponse,HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity<DataResponse<String>> login(@RequestBody Login login) {
        Map<String, String> payload = new HashMap<>();
        payload.put("user", login.getUsername());
        payload.put("role", "usuario");
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345");
            String token = JWT.create()
                    .withClaim("createAt", new Date())
                    .withPayload(payload)
                    .sign(algorithm);

            DataResponse<String> dataResponse = new DataResponse<>(200, "Token generated!", token);

            return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);

        } catch (JWTVerificationException exception) {
            DataResponse<String> dataResponse = new DataResponse<>(500, "Error to generate token", "");

            return new ResponseEntity<>(dataResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
