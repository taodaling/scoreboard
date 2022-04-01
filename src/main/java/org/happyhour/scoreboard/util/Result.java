package org.happyhour.scoreboard.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private T data;
    private boolean success;

    public static <T> Result<T> ofSuccess(T data) {
        return new Result<>(data, true);
    }

    public static Result<Void> ofSuccess() {
        return ofSuccess(null);
    }

    public static <T> Result<T> ofFail(T data) {
        return new Result<>(data, false);
    }

    public static Result<Void> ofFail() {
        return new Result<>(null, false);
    }

}
