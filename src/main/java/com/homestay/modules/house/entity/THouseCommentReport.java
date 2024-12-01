package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论举报表
 * @TableName t_house_comment_report
 */
@TableName(value ="t_house_comment_report")
@Data
public class THouseCommentReport implements Serializable {
    /**
     * 举报ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论ID
     */
    @TableField(value = "comment_id")
    private Long comment_id;

    /**
     * 举报用户ID
     */
    @TableField(value = "user_id")
    private Long user_id;

    /**
     * 举报原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 详细说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 处理状态(0:待处理 1:已处理 2:已驳回)
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 处理说明
     */
    @TableField(value = "handle_note")
    private String handle_note;

    /**
     * 处理时间
     */
    @TableField(value = "handle_time")
    private Date handle_time;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date create_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        THouseCommentReport other = (THouseCommentReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getComment_id() == null ? other.getComment_id() == null : this.getComment_id().equals(other.getComment_id()))
            && (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getHandle_note() == null ? other.getHandle_note() == null : this.getHandle_note().equals(other.getHandle_note()))
            && (this.getHandle_time() == null ? other.getHandle_time() == null : this.getHandle_time().equals(other.getHandle_time()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getComment_id() == null) ? 0 : getComment_id().hashCode());
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getHandle_note() == null) ? 0 : getHandle_note().hashCode());
        result = prime * result + ((getHandle_time() == null) ? 0 : getHandle_time().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", comment_id=").append(comment_id);
        sb.append(", user_id=").append(user_id);
        sb.append(", reason=").append(reason);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", handle_note=").append(handle_note);
        sb.append(", handle_time=").append(handle_time);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}