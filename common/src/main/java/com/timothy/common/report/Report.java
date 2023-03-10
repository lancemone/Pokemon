package com.timothy.common.report;

import android.util.Log;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.report.Report
 * @Author: MoTao
 * @Date: 2023-03-10
 * <p>
 * <p/>
 */
public class Report {
    private static final String TAG = "P_Report";

    private String itemId;
    private String action;
    private String actionParam;
    private String reportType;
    private String refer;
    private String target;
    private long reportTime;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public void upload(){
        Log.e(TAG, toString());
    }

    @Override
    public String toString() {
        return "{" +
                "itemId='" + itemId + '\'' +
                ", action='" + action + '\'' +
                ", actionParam='" + actionParam + '\'' +
                ", reportType='" + reportType + '\'' +
                ", refer='" + refer + '\'' +
                ", target='" + target + '\'' +
                ", reportTime=" + reportTime +
                '}';
    }

    public static class Helper {
        private String itemId;
        private String action;
        private String actionParam;
        private String reportType;
        private String refer;
        private String target;
        private long reportTime = System.currentTimeMillis();

        public Helper setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public Helper setAction(String action) {
            this.action = action;
            return this;
        }

        public Helper setActionParam(String actionParam) {
            this.actionParam = actionParam;
            return this;
        }

        public Helper setReportType(String reportType) {
            this.reportType = reportType;
            return this;
        }

        public Helper setRefer(String refer) {
            this.refer = refer;
            return this;
        }

        public Helper setTarget(String target) {
            this.target = target;
            return this;
        }

        public Helper setReportTime(long reportTime) {
            this.reportTime = reportTime;
            return this;
        }

        public void build(){
            Report report = new Report();
            report.action = this.action;
            report.itemId = itemId;
            report.actionParam = actionParam;
            report.reportType = reportType;
            report.refer = refer;
            report.target = target;
            report.reportTime = reportTime;
            report.upload();
        }

        public static Helper newInstance(){
            return new Helper();
        }
    }
}
