package org.happyhour.scoreboard.util;

import lombok.Data;
import org.happyhour.scoreboard.model.User;

import java.util.Objects;

@Data
public class Context {
    @Data
    public static class Holder {
        private Integer userId;
        private long entranceTime;
    }

    static ThreadLocal<Holder> tl = ThreadLocal.withInitial(Holder::new);

    public static Integer getNotNullUser() {
        Integer user = tl.get().userId;
        BizException.assertTrue(user != null, "auth required");
        return user;
    }

    public static void assertIsAdmin() {
        Integer user = tl.get().userId;
        BizException.assertTrue(Objects.equals(user, 0), "only admin is allowed to operate");
    }

    public static Holder get() {
        return tl.get();
    }


    public static void clear() {
        tl.remove();
    }

}
