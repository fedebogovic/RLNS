package live.modak.challenge;

class Gateway {
    void send(String userId, String message) {
        System.out.println("Sending message to user " + userId + ": " + message);
    }

    void error(String message) {
        System.out.println(message);
    }
}