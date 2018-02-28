package com.rocoinfo.enumeration;

/**
 * 描述：日程创建数据类型
 *
 * @author tony
 * @创建时间 2017-06-15 16:46
 */
@SuppressWarnings("all")
public enum ScheduleSourceType {
    INVITATIONSTORE("邀约到店"),ROOMRESERVATIONS("预约量房"),CUSTOMERCARE("客户关怀"),OTHER("其它");
    String label;

    ScheduleSourceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 根据name取得label的值
     * @param name
     * @return
     */
    public static String getLabelByName(String name) {
        for (ScheduleSourceType scheduleSourceType : ScheduleSourceType.values()) {
            if(name.equals(scheduleSourceType.name())) {
                return scheduleSourceType.getLabel();
            }
        }

        return "";
    }
}
