package org.apache.rocketmq.example.filter;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.filter.FilterContext;

/**
 * @date 2020/9/16 17:22
 * @author chengjiaqing
 * @version : 0.1
 */ 
 
  
public class MessageFilter {

    public boolean match(MessageExt msg, FilterContext arg1) {
        // NO Chinese
        System.out.println("-------------");
        String property = msg.getUserProperty("SequenceId");
        System.out.println("---------" + property);
        if (property != null) {
            int id = Integer.parseInt(property);
            if((id % 2) == 0) {
                //if ((id % 3) == 0 && (id > 10)) {
                return true;
            }
        }
        return false;
    }
}