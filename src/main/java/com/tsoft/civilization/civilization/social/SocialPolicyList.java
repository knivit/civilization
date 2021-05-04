package com.tsoft.civilization.civilization.social;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SocialPolicyList implements Iterable<AbstractSocialPolicy> {

    private final List<AbstractSocialPolicy> policies = new ArrayList<>();
    private boolean isUnmodifiable;

    public SocialPolicyList() { }

    public SocialPolicyList(List<AbstractSocialPolicy> policies) {
        Objects.requireNonNull(policies);
        this.policies.addAll(policies);
    }

    public SocialPolicyList unmodifiableList() {
        SocialPolicyList list = new SocialPolicyList();
        list.policies.addAll(policies);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<AbstractSocialPolicy> iterator() {
        return policies.listIterator();
    }

    public Stream<AbstractSocialPolicy> stream() {
        return policies.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public AbstractSocialPolicy get(int index) {
        return policies.get(index);
    }

    public SocialPolicyList add(AbstractSocialPolicy policy) {
        checkIsUnmodifiable();
        policies.add(policy);
        return this;
    }

    public boolean isEmpty() {
        return policies.isEmpty();
    }

    public int size() {
        return policies.size();
    }

}
