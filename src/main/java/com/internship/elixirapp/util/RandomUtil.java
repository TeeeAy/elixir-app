package com.internship.elixirapp.util;

import java.util.Random;

public final class RandomUtil {

    private RandomUtil(){}

    public static int randomInt(int limit){
        Random random = new Random();
        return random.nextInt(limit);
    }
}
