
package com.example.demo.test;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Component
@ServerEndpoint("/websocket2")
public class WebSocket_backup {

    /**
     * 웹소켓 세션을 담는 ArrayList
     */
    // session_id, session
    private static Map<String, Session> sessionMap = Collections.synchronizedMap(new HashMap<>());
    // name, session_id
    private static Map<String, String> sessionName = Collections.synchronizedMap(new HashMap<>());
    // session_id, name
    private static Map<String, String> id2name = Collections.synchronizedMap(new HashMap<>());


//    public Set<MapString,Session> getSessionList() {
//        return sessionMap.entrySet();
//    }

    /**
     * 웹소켓 사용자 연결 성립하는 경우 호출
     */
    @OnOpen
    public void handleOpen(Session session) {
        if (session != null) {
            String sessionId = session.getId();
            sessionMap.put(sessionId, session);

//            System.out.println("client is connected. sessionId == [" + sessionName.get(session.getId()) + "]");
            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
//            sendMessageToAll("***** [USER-" + sessionName.get(session.getId()) + "] is connected. *****");
        }
    }


    /**
     * 웹소켓 메시지(From Client) 수신하는 경우 호출
     */
    @OnMessage
    public String handleMessage(String message, Session session) {
        if (session != null) {

            String[] temp = message.split(":");
            String name = temp[0];
            String msg = temp[1];

            // 처음 접속시.
            if (msg.equals("test")) {
                // 연결 중이였다가 끊어진 후, 다시 연결된 경우.
                if (sessionName.containsKey(name)) {
//                    String origSessionId = sessionName.get(name);
                    String newSessionId = session.getId();
                    sessionName.put(name, newSessionId);
                    id2name.put(newSessionId, name);
//                    sessionMap.put(origSessionId, session);
                    sendMessageToAll(String.format("[%s] 님이 다시 접속하였습니다", name));

                }
                // 처음 연결한 경우.
                else {
                    String newSessionId = session.getId();
                    sessionName.put(name, newSessionId);
                    id2name.put(newSessionId, name);
                    sendMessageToAll(String.format("[%s] 님이 처음 접속하였습니다", name));
                }
            } else {
                String sessionId = session.getId();
                sendMessageToAll("[" + id2name.get(sessionId) + "] " + message);
            }

            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
        }

        return null;
    }


    /**
     * 웹소켓 사용자 연결 해제하는 경우 호출
     */
    @OnClose
    public void handleClose(Session session) {
        if (session != null) {
            String sessionId = session.getId();
            String name = id2name.get(sessionId);
            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
            sendMessageToAll(String.format("[%s] 님이 접속을 종료하였습니다.", id2name.get(session.getId())));
            sessionMap.remove(sessionId);
//            sessionName.remove(name);
            id2name.remove(sessionId);
        }
    }


    /**
     * 웹소켓 에러 발생하는 경우 호출
     */
    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }


    /**
     * 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
     */
    private boolean sendMessageToAll(String message) {
//        if (sessionName == null) {
//            return false;
//        }

        int sessionCount = sessionName.size();
        if (sessionCount < 1) {
            return false;
        }

        Session singleSession = null;

        for (String name : sessionName.keySet()) {

            singleSession = sessionMap.get(sessionName.get(name));
            if (singleSession == null) {
                continue;
            }
            if (!singleSession.isOpen()) {
                continue;
            }
            singleSession.getAsyncRemote().sendText(message);
        }

        return true;
    }

    public List<String> getUserNameList() {
        List<String> result = new ArrayList<>();
        for (String id : id2name.keySet()) {
            result.add(id2name.get(id));
        }
        return result;
    }
}


//import java.util.ArrayList;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
//@Component
//@ServerEndpoint("/websocket”)
//public class Websocket {
//
//    /**
//     * 웹소켓 세션을 담는 ArrayList
//     */
//    private static ArrayList<Session> sessionList = new ArrayList<Session>();
//
//
//    /**
//     * 웹소켓 사용자 연결 성립하는 경우 호출
//     */
//    @OnOpen
//    public void handleOpen(Session session) {
//        if (session != null) {
//            String sessionId = session.getId();
//
//            System.out.println("client is connected. sessionId == [" + sessionId + "]”);
//            sessionList.add(session);
//
//            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
//            sendMessageToAll("***** [USER-" + sessionId + "] is connected. *****”);
//        }
//    }
//
//
//    /**
//     * 웹소켓 메시지(From Client) 수신하는 경우 호출
//     */
//    @OnMessage
//    public String handleMessage(String message, Session session) {
//        if (session != null) {
//            String sessionId = session.getId();
//            System.out.println("message is arrived. sessionId == [" + sessionId + "] / message == [" + message + "]”);
//
//            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
//            sendMessageToAll("[USER-" + sessionId + "] " + message);
//        }
//
//        return null;
//    }
//
//
//    /**
//     * 웹소켓 사용자 연결 해제하는 경우 호출
//     */
//    @OnClose
//    public void handleClose(Session session) {
//        if (session != null) {
//            String sessionId = session.getId();
//            System.out.println("client is disconnected. sessionId == [" + sessionId + "]”);
//
//            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
//            sendMessageToAll("***** [USER-" + sessionId + "] is disconnected. *****”);
//        }
//    }
//
//
//    /**
//     * 웹소켓 에러 발생하는 경우 호출
//     */
//    @OnError
//    public void handleError(Throwable t) {
//        t.printStackTrace();
//    }
//
//
//    /**
//     * 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
//     */
//    private boolean sendMessageToAll(String message) {
//        if (sessionList == null) {
//            return false;
//        }
//
//        int sessionCount = sessionList.size();
//        if (sessionCount < 1) {
//            return false;
//        }
//
//        Session singleSession = null;
//
//        for (int i = 0; i < sessionCount; i++) {
//            singleSession = sessionList.get(i);
//            if (singleSession == null) {
//                continue;
//            }
//
//            if (!singleSession.isOpen()) {
//                continue;
//            }
//
//            sessionList.get(i).getAsyncRemote().sendText(message);
//        }
//
//        return true;
//    }
//}