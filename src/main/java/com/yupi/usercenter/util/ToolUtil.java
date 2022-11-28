package com.yupi.usercenter.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : HP
 * @date : 2022/11/17
 */
public class ToolUtil {
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);

    }
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return "".equals(o.toString().trim());
        } else if (o instanceof List) {
            return ((List<?>) o).size() == 0;
        } else if (o instanceof Map) {
            return ((Map<?, ?>) o).size() == 0;
        } else if (o instanceof Set) {
            return ((Set<?>) o).size() == 0;
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else if (o instanceof int[]) {
            return ((int[]) o).length == 0;
        } else if (o instanceof long[]) {
            return ((long[]) o).length == 0;
        }
        return false;
    }
}
