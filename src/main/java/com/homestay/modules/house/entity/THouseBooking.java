package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 房源预订表
 * @TableName t_house_booking
 */
@TableName(value ="t_house_booking")
@Data
public class THouseBooking implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应房源ID
     */
    @TableField(value = "house_id")
    private Long house_id;

    /**
     * 预订状态: 0 未预订, 1 已预订
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 预订开始时间
     */
    @TableField(value = "booking_start")
    private Date booking_start;

    /**
     * 预订结束时间
     */
    @TableField(value = "booking_end")
    private Date booking_end;

    /**
     * 预订用户姓名
     */
    @TableField(value = "user_name")
    private String user_name;

    /**
     * 预订人手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 预订人邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 记录创建时间
     */
    @TableField(value = "created_at")
    private Date created_at;

    /**
     * 记录最后更新时间
     */
    @TableField(value = "updated_at")
    private Date updated_at;

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
        THouseBooking other = (THouseBooking) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHouse_id() == null ? other.getHouse_id() == null : this.getHouse_id().equals(other.getHouse_id()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBooking_start() == null ? other.getBooking_start() == null : this.getBooking_start().equals(other.getBooking_start()))
            && (this.getBooking_end() == null ? other.getBooking_end() == null : this.getBooking_end().equals(other.getBooking_end()))
            && (this.getUser_name() == null ? other.getUser_name() == null : this.getUser_name().equals(other.getUser_name()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getCreated_at() == null ? other.getCreated_at() == null : this.getCreated_at().equals(other.getCreated_at()))
            && (this.getUpdated_at() == null ? other.getUpdated_at() == null : this.getUpdated_at().equals(other.getUpdated_at()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHouse_id() == null) ? 0 : getHouse_id().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBooking_start() == null) ? 0 : getBooking_start().hashCode());
        result = prime * result + ((getBooking_end() == null) ? 0 : getBooking_end().hashCode());
        result = prime * result + ((getUser_name() == null) ? 0 : getUser_name().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getCreated_at() == null) ? 0 : getCreated_at().hashCode());
        result = prime * result + ((getUpdated_at() == null) ? 0 : getUpdated_at().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", house_id=").append(house_id);
        sb.append(", status=").append(status);
        sb.append(", booking_start=").append(booking_start);
        sb.append(", booking_end=").append(booking_end);
        sb.append(", user_name=").append(user_name);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", created_at=").append(created_at);
        sb.append(", updated_at=").append(updated_at);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}