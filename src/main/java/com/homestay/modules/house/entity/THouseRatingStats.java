package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 房源评分统计表
 * @TableName t_house_rating_stats
 */
@TableName(value ="t_house_rating_stats")
@Data
public class THouseRatingStats implements Serializable {
    /**
     * 房源ID
     */
    @TableId(value = "house_id")
    private Long house_id;

    /**
     * 总评分数
     */
    @TableField(value = "total_rating")
    private Integer total_rating;

    /**
     * 平均评分
     */
    @TableField(value = "average_rating")
    private BigDecimal average_rating;

    /**
     * 1星评价数
     */
    @TableField(value = "rating_1_count")
    private Integer rating_1_count;

    /**
     * 2星评价数
     */
    @TableField(value = "rating_2_count")
    private Integer rating_2_count;

    /**
     * 3星评价数
     */
    @TableField(value = "rating_3_count")
    private Integer rating_3_count;

    /**
     * 4星评价数
     */
    @TableField(value = "rating_4_count")
    private Integer rating_4_count;

    /**
     * 5星评价数
     */
    @TableField(value = "rating_5_count")
    private Integer rating_5_count;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date update_time;

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
        THouseRatingStats other = (THouseRatingStats) that;
        return (this.getHouse_id() == null ? other.getHouse_id() == null : this.getHouse_id().equals(other.getHouse_id()))
            && (this.getTotal_rating() == null ? other.getTotal_rating() == null : this.getTotal_rating().equals(other.getTotal_rating()))
            && (this.getAverage_rating() == null ? other.getAverage_rating() == null : this.getAverage_rating().equals(other.getAverage_rating()))
            && (this.getRating_1_count() == null ? other.getRating_1_count() == null : this.getRating_1_count().equals(other.getRating_1_count()))
            && (this.getRating_2_count() == null ? other.getRating_2_count() == null : this.getRating_2_count().equals(other.getRating_2_count()))
            && (this.getRating_3_count() == null ? other.getRating_3_count() == null : this.getRating_3_count().equals(other.getRating_3_count()))
            && (this.getRating_4_count() == null ? other.getRating_4_count() == null : this.getRating_4_count().equals(other.getRating_4_count()))
            && (this.getRating_5_count() == null ? other.getRating_5_count() == null : this.getRating_5_count().equals(other.getRating_5_count()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHouse_id() == null) ? 0 : getHouse_id().hashCode());
        result = prime * result + ((getTotal_rating() == null) ? 0 : getTotal_rating().hashCode());
        result = prime * result + ((getAverage_rating() == null) ? 0 : getAverage_rating().hashCode());
        result = prime * result + ((getRating_1_count() == null) ? 0 : getRating_1_count().hashCode());
        result = prime * result + ((getRating_2_count() == null) ? 0 : getRating_2_count().hashCode());
        result = prime * result + ((getRating_3_count() == null) ? 0 : getRating_3_count().hashCode());
        result = prime * result + ((getRating_4_count() == null) ? 0 : getRating_4_count().hashCode());
        result = prime * result + ((getRating_5_count() == null) ? 0 : getRating_5_count().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", house_id=").append(house_id);
        sb.append(", total_rating=").append(total_rating);
        sb.append(", average_rating=").append(average_rating);
        sb.append(", rating_1_count=").append(rating_1_count);
        sb.append(", rating_2_count=").append(rating_2_count);
        sb.append(", rating_3_count=").append(rating_3_count);
        sb.append(", rating_4_count=").append(rating_4_count);
        sb.append(", rating_5_count=").append(rating_5_count);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}