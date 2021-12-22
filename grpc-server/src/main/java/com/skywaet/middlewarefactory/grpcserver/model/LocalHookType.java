package com.skywaet.middlewarefactory.grpcserver.model;

import coprocess.CoprocessCommon;

public enum LocalHookType {
    UNKNOWN(0),
    /**
     * <code>Pre = 1;</code>
     */
    PRE(1),
    /**
     * <code>Post = 2;</code>
     */
    POST(2),
    /**
     * <code>PostKeyAuth = 3;</code>
     */
    POST_KEY_AUTH(3),
    /**
     * <code>CustomKeyCheck = 4;</code>
     */
    CUSTOM_KEY_CHECK(4),
    UNRECOGNIZED(-1);

    private int code;

    LocalHookType(int code) {
        this.code = code;
    }

    public static LocalHookType getLocalHook(CoprocessCommon.HookType input) {
        for (var hook : LocalHookType.values()) {
            if (hook.code == input.getNumber()) {
                return hook;
            }
        }
        return UNRECOGNIZED;
    }

}
