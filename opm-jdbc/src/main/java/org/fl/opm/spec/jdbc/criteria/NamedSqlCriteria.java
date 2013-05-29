package org.fl.opm.spec.jdbc.criteria;

import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.spec.criteria.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-29 10:31
 */
public class NamedSqlCriteria extends Criteria {
    private String namedSql;
    private String sql;
    private List<String> paramNames = new ArrayList<String>();
    private List<ParamPair> paramPairs;

    public NamedSqlCriteria(String sql) {
        this.namedSql = sql;
        init();
    }

    private void init() {
        Pattern pattern = Pattern.compile("(:\\w+)");
        Matcher matcher = pattern.matcher(namedSql);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            paramNames.add(matcher.group().substring(1));
            matcher.appendReplacement(sb, "?");
        }
        matcher.appendTail(sb);
        sql = sb.toString();
        paramPairs = new ArrayList<ParamPair>(paramNames.size());
        for(int i = 0; i < paramNames.size(); i++){
            paramPairs.add(null);
        }
    }

    public void addParam(String name, Object value) throws Exception {
        int idx = getIndex(name);
        if(idx < 0){
            throw new Exception("Can not find parameter \"" + name + "\".");
        }
        paramPairs.set(idx, new ParamPair(value, JdbcUtils.javaTypeToJdbcType(value.getClass())));
    }

    private int getIndex(String name) {
        return paramNames.indexOf(name);
    }

    public String getSql() {
        return sql;
    }

    public List<ParamPair> getParamPairs() {
        return paramPairs;
    }

}
