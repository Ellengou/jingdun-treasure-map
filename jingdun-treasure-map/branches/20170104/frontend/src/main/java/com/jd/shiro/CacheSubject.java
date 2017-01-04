//package com.jd.shiro;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authz.Permission;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.ExecutionException;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.TimeUnit;
//
///**
// * 有缓存的subject，对shiro原生的subject进行包装
// *
// * @author xiaqi
// */
//public class CacheSubject implements Subject {
//
//    private static final Logger logger = LoggerFactory.getLogger(CacheSubject.class);
//
//    private static final Cache<Object, CacheSubject> subjectCache = CacheBuilder.newBuilder()
//            .maximumSize(500)
//            .expireAfterAccess(30, TimeUnit.MINUTES)
//            .build();
//
//    public static void clearCache(){
//        subjectCache.invalidateAll();
//    }
//
//    public static CacheSubject getInstance(){
//        Subject shiroSubject = SecurityUtils.getSubject();
//        Object sessionId = shiroSubject.getSession().getId();
//        CacheSubject cacheSubject = subjectCache.getIfPresent(sessionId);
//        if(cacheSubject == null){
//            cacheSubject = new CacheSubject(shiroSubject);
//            subjectCache.put(sessionId, cacheSubject);
//            return cacheSubject;
//        }
//        return cacheSubject;
//    }
//
//    private CacheSubject(Subject shiroSubject) {
//        super();
//        this.shiroSubject = shiroSubject;
//    }
//
//    private Subject shiroSubject;
//
//
//    private LoadingCache<String, Boolean> roleCache = CacheBuilder.newBuilder()
//            .maximumSize(10)
//            .expireAfterAccess(30, TimeUnit.MINUTES)
//            .build(
//                    new CacheLoader<String, Boolean>(){
//                        @Override
//                        public Boolean load(String key) throws Exception {
//                            boolean result = shiroSubject.hasRole(key);
//                            return result;
//                        }
//
//                    }
//            );
//
//    public Object getPrincipal() {
//        return shiroSubject.getPrincipal();
//    }
//
//    public PrincipalCollection getPrincipals() {
//        return shiroSubject.getPrincipals();
//    }
//
//    public boolean isPermitted(String permission) {
//        return shiroSubject.isPermitted(permission);
//    }
//
//    public boolean isPermitted(Permission permission) {
//        return shiroSubject.isPermitted(permission);
//    }
//
//    public boolean[] isPermitted(String... permissions) {
//        return shiroSubject.isPermitted(permissions);
//    }
//
//    public boolean[] isPermitted(List<Permission> permissions) {
//        return shiroSubject.isPermitted(permissions);
//    }
//
//    public boolean isPermittedAll(String... permissions) {
//        return shiroSubject.isPermittedAll(permissions);
//    }
//
//    public boolean isPermittedAll(Collection<Permission> permissions) {
//        return shiroSubject.isPermittedAll(permissions);
//    }
//
//    public void checkPermission(String permission) throws AuthorizationException {
//        shiroSubject.checkPermission(permission);
//    }
//
//    public void checkPermission(Permission permission) throws AuthorizationException {
//        shiroSubject.checkPermission(permission);
//    }
//
//    public void checkPermissions(String... permissions) throws AuthorizationException {
//        shiroSubject.checkPermissions(permissions);
//    }
//
//    public void checkPermissions(Collection<Permission> permissions) throws AuthorizationException {
//        shiroSubject.checkPermissions(permissions);
//    }
//
//    public boolean hasRole(String roleIdentifier) {
//        try {
//            return roleCache.get(roleIdentifier);
//        } catch (Exception e) {
//            logger.error("query role from cache error", e);
//            return shiroSubject.hasRole(roleIdentifier);
//        }
//    }
//
//    public boolean[] hasRoles(List<String> roleIdentifiers) {
//        return shiroSubject.hasRoles(roleIdentifiers);
//    }
//
//    public boolean hasAllRoles(Collection<String> roleIdentifiers) {
//        return shiroSubject.hasAllRoles(roleIdentifiers);
//    }
//
//    public void checkRole(String roleIdentifier) throws AuthorizationException {
//        shiroSubject.checkRole(roleIdentifier);
//    }
//
//    public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
//        shiroSubject.checkRoles(roleIdentifiers);
//    }
//
//    public void checkRoles(String... roleIdentifiers) throws AuthorizationException {
//        shiroSubject.checkRoles(roleIdentifiers);
//    }
//
//    public void login(AuthenticationToken token) throws AuthenticationException {
//        shiroSubject.login(token);
//    }
//
//    public boolean isAuthenticated() {
//        return shiroSubject.isAuthenticated();
//    }
//
//    public boolean isRemembered() {
//        return shiroSubject.isRemembered();
//    }
//
//    public Session getSession() {
//        return shiroSubject.getSession();
//    }
//
//    public Session getSession(boolean create) {
//        return shiroSubject.getSession(create);
//    }
//
//    public void logout() {
//        shiroSubject.logout();
//    }
//
//    public <V> V execute(Callable<V> callable) throws ExecutionException {
//        return shiroSubject.execute(callable);
//    }
//
//    public void execute(Runnable runnable) {
//        shiroSubject.execute(runnable);
//    }
//
//    public <V> Callable<V> associateWith(Callable<V> callable) {
//        return shiroSubject.associateWith(callable);
//    }
//
//    public Runnable associateWith(Runnable runnable) {
//        return shiroSubject.associateWith(runnable);
//    }
//
//    public void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException {
//        shiroSubject.runAs(principals);
//    }
//
//    public boolean isRunAs() {
//        return shiroSubject.isRunAs();
//    }
//
//    public PrincipalCollection getPreviousPrincipals() {
//        return shiroSubject.getPreviousPrincipals();
//    }
//
//    public PrincipalCollection releaseRunAs() {
//        return shiroSubject.releaseRunAs();
//    }
//
//    public Subject getShiroSubject() {
//        return shiroSubject;
//    }
//
//    public void setShiroSubject(Subject shiroSubject) {
//        this.shiroSubject = shiroSubject;
//    }
//
//}
