package self.frame.common.broadcast;

import self.frame.common.channel.Channel;

/**
 * @Author: YuanChangShuai
 * @Date: 2022/1/26 14:41
 * @Description:
 **/
public class LocalBroadcast implements Broadcast {

    @Override
    public Channel open(String channelName) {
        new Channel(channelName, Channel.Range.Local);
        return null;
    }



}
