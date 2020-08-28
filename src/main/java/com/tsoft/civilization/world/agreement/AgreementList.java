package com.tsoft.civilization.world.agreement;

import java.util.HashMap;

public class AgreementList {
    public static final AgreementList EMPTY_AGREEMENTS = new AgreementList();

    private HashMap<Class <? extends AbstractAgreement>, AbstractAgreement> agreements = new HashMap<>();

    public <A extends AbstractAgreement> A get(Class<A> agreementClass) {
        return (A)agreements.get(agreementClass);
    }

    public void add(AbstractAgreement agreement) {
        agreements.put(agreement.getClass(), agreement);
    }
}
