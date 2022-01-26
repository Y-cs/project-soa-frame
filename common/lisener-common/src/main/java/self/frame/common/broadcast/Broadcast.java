package self.frame.common.broadcast;

import self.frame.common.channel.Channel;
import self.frame.common.event.AbstractEvent;

/**
 * @Author: YuanChangShuai
 * @Date: 2022/1/25 10:25
 * @Description:
 **/
public interface Broadcast {

    Channel open(String channelName);

}
