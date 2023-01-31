//
//package com.example.demo.test;
//
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.PongMessage;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.nio.ByteBuffer;
//
//@Component
//@ServerEndpoint(value = "/websocket2/{roomName}/{userId}")
//public class WebSocket2 {
//    @OnOpen
//    void onOpen(Session session, @PathParam("roomName") String roomName, @PathParam("userId") String userId) {
//
//    }
//
//    @OnMessage
//    public void onMessage(Message message, Session session) {
//    }
//
//    @OnMessage
//    public void onBinaryMessage(ByteBuffer message, Session session) {
//    }
//
//    @OnMessage
//    public void onPongMessage(PongMessage message, Session session) {
//    }
//
//}
