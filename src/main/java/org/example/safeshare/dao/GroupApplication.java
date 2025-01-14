package org.example.safeshare.dao;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GroupApplication {
    private Long id;                // 申请ID
    private Long user_id;           // 申请用户ID
    private Long group_id;          // 目标组ID
    private String message;         // 申请内容
    private ApplicationStatus status; // 状态
    private Timestamp created_at;    // 申请时间
    private Timestamp processed_at;  // 审批时间
    private Long processed_by;       // 审批管理员ID

    // 枚举类型表示申请状态
    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}
