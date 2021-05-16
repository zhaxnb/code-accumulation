package com.example.meishi.shiro;

import com.example.meishi.entity.Admininfo;
import com.example.meishi.entity.Power;
import com.example.meishi.repository.AdmininfoRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 实现AuthorizingRealm接口用户用户认证
 *
 * @author Louis
 * @date Jun 20, 2019
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdmininfoRepository admininfoRepository;

    /**
     * 用户认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        Admininfo user = admininfoRepository.findByAdminnameEquals(name);
        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(user,
                    user.getAdminpassword(), getName());

        }
    }

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        Object obj = principalCollection.getPrimaryPrincipal();
        Admininfo a = (Admininfo) obj;
        // 查询用户名称
        Admininfo user = admininfoRepository.findByAdminnameEquals(a.getAdminname());
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加权限
        String[] split = user.getPower().getName().split(",");
        Set<String> getname = new HashSet<String>(Arrays.asList(split));
        simpleAuthorizationInfo.setStringPermissions(getname);
        return simpleAuthorizationInfo;
    }

}