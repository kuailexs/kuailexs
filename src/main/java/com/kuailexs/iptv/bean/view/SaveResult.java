package com.kuailexs.iptv.bean.view;

import lombok.Data;

/**
 * 提交结果
 */
@Data
public class SaveResult {

    Integer status;
    Integer success;
    Integer duplicate;
    Integer error;

    public SaveResult() {
        this.status = 0;
        this.success = 0;
        this.duplicate = 0;
        this.error = 0;
    }

    public void addSaveResult(SaveResult saveResult1) {
        error += saveResult1.getError();
        success += saveResult1.getSuccess();
        duplicate += saveResult1.getDuplicate();
    }
}
