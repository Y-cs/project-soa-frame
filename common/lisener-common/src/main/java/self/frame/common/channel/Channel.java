package self.frame.common.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YuanChangShuai
 * @Date: 2022/1/25 10:28
 * @Description:
 **/
public class Channel {

    public enum Range {
        /**
         * 全局
         */
        Global,
        /**
         * 本地
         */
        Local
    }

    private String name;

    private Range range;

    private final Map<String, Object> header;

    public Channel(String name, Range range) {
        header = new HashMap<>(2);
        this.name = name;
        this.range = range;
    }

    public void addAttribute(String name, Object attribute) {
        header.put(name, attribute);
    }

    public Object getAttribute(String name) {
        return header.get(name);
    }

}
