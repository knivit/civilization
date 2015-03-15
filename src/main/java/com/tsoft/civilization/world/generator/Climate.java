package com.tsoft.civilization.world.generator;

public enum Climate {
    COLD(0), NORMAL(1), HOT(2);

    private int no;

    Climate(int no) {
        this.no = no;
    }

    public static Climate getClimateByNo(int no) {
        for (Climate climate : values()) {
            if (climate.no == no) {
                return  climate;
            }
        }

        throw new IllegalArgumentException("Value " + no + " is invalid");
    }
}
