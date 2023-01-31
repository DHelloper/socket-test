package com.example.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MainRestController {
    @Autowired
    WebSocket webSocket;

    @GetMapping(value = "session/list")
    public ResponseEntity<?> getSessionList() {
        return new ResponseEntity<Set<String>>(webSocket.getSessionSet(), HttpStatus.OK);
    }
//
//    @GetMapping(value = "customer/list/{seller_id}")
//    public ResponseEntity<?> getCustomerList(@PathVariable("seller_id") String seller_id) {
//        return new ResponseEntity<Set<Map<String, String>>>(webSocket.getCustomerSet(seller_id), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "seller/list")
//    public ResponseEntity<?> getSellerList() {
//        return new ResponseEntity<Set<String>>(webSocket.getSellerSet(), HttpStatus.OK);
//    }

}