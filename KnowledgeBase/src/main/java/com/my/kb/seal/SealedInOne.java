package com.my.kb.seal;

public abstract sealed class SealedInOne {
    static final class ChildClass1 extends SealedInOne {
    }

    static final class ChildClass2 extends SealedInOne {
    }

    static final class ChildClass3 extends SealedInOne {
    }
}
