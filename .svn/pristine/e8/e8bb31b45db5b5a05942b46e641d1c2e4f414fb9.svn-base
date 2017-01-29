package Entity.Manager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 * log标识码
 */
public class LogTag {

    private int tagId; //标签序号
    private String tagDesc; //标签说明

    public LogTag(int tagId, String tagDesc) {
        this.tagId = tagId;
        this.tagDesc = tagDesc;
    }

    private static ArrayList<LogTag> tags = new ArrayList<LogTag>();
    static {
        tags.add(new LogTag(0,"接单"));
        tags.add(new LogTag(1,"取消订单"));
        tags.add(new LogTag(2,"取件"));
        tags.add(new LogTag(3,"取件异常"));
        tags.add(new LogTag(4,"配送"));
        tags.add(new LogTag(5,"配送异常"));
        tags.add(new LogTag(6,"楼长交接-交出订单"));
        tags.add(new LogTag(7,"楼长交接-接收订单"));
        //tags.add(new LogTag(8,""));
    }

    public static LogTag getTagWithStatus(int tid){
        for (int i = 0; i < tags.size(); i++) {
            if(tags.get(i).tagId == tid){

            }
                return tags.get(i);
        }
        return new LogTag(-1,"[日志标签解释错误]");
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public static ArrayList<LogTag> getTags() {
        return tags;
    }

    public static void setTags(ArrayList<LogTag> tags) {
        LogTag.tags = tags;
    }
}
