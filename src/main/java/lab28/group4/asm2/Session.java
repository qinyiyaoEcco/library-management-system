package lab28.group4.asm2;

import lab28.group4.asm2.models.User;

public class Session {

    private static User user;

    public static void setUser(User user) {
        Session.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static void clear() {
        user = null;
    }

}
