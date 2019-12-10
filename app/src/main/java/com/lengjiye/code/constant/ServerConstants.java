package com.lengjiye.code.constant;

public interface ServerConstants {
    /**
     * 首页列表
     */
    String ARTICLE = "article/list/{page}/json";
    String ARTICLE_TOP = "article/top/json";
    String BANNER = "banner/json";


    /**
     * 体系
     */
    String TREE = "tree/json";
    String TREE_ARTICLE = "article/list/{page}/json";


    /**
     * 项目
     */
    String PROJECT_TREE = "project/tree/json";
    String PROJECT_TREE_ARTICLE = "project/list/{page}/json";

    /**
     * 登录
     */
    String USER_LOGIN = "user/login";
    String USER_REGISTER = "user/register";
    String USER_LOGOUT = "user/logout/json";


    /**
     * 积分
     */
    String COIN_RANK = "coin/rank/{page}/json";
    String COIN_USERINFO = "lg/coin/userinfo/json";
    String COIN_USERINFO_LIST = "lg/coin/list/{page}/json";
}
