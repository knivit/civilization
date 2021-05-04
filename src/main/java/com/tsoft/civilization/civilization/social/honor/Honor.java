package com.tsoft.civilization.civilization.social.honor;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

public class Honor extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = null;
}
