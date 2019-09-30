package com.cmiov.tsop.oscar.mall.dbutool.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cmiov.tsop.oscar.mall.dbutool.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengKai
 * @data 2019-09-28 18:51
 */
@RestController
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DBController {
    @Value("${spring.datasource.host}")
    private String host;

    private String url;

    @GetMapping("/query")
    public String query(@RequestParam("database") String database,
                        @RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("sql") String sql,
                        String callback) throws Exception {
        log.debug("DBController query start url = [{}], sql=[{}], callback=[{}]", url, database, callback);
        url="jdbc:mysql://"+host+"/"+database+"?characterEncoding=utf-8&useUnicode=true";
        String result = callback + "(" + JSON.toJSONString(DBUtil.query(sql, url, userName, password),
                SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat) + ")";
        log.debug("DBController query end, result = [{}]", result);
        return result;
    }

    @GetMapping("/update")
    public String update(@RequestParam("url") String url,
                         @RequestParam("userName") String userName,
                         @RequestParam("password") String password,
                         @RequestParam("sql") String sql,
                         String callback) throws Exception {
        log.debug("DBController update start ,url = [{}], sql=[{}], callback=[{}]", url, sql, callback);
        String result = callback+"("+JSON.toJSONString(DBUtil.update(sql, url, userName, password))+")";
        log.debug("DBController update end, result = [{}]", result);
        return result;
    }
}
