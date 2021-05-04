package com.tsoft.civilization.civilization.social;

import com.tsoft.civilization.civilization.Civilization;

public class CivilizationSocialPolicyService {

    private final Civilization civilization;

    private final SocialPolicyList policies = new SocialPolicyList();

    public CivilizationSocialPolicyService(Civilization civilization) {
        this.civilization = civilization;
    }
}
