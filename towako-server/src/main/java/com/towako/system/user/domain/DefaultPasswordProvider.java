package com.towako.system.user.domain;

/**
 * 默认密码提供器
 * @author colin
 */
public interface DefaultPasswordProvider {
    /**
     * 生成默认密码
     * @return 默认密码
     */
    String generate();
}
