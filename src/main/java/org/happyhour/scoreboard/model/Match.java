package org.happyhour.scoreboard.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (Match)实体类
 *
 * @author dalingtao
 * @description
 * @since 2022-03-31 21:27:39
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("Matchs")
public class Match extends Model<Match> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * matchid
     */
    @TableId
    @TableField("matchId")
    private Integer matchId;
    /**
     * matchTime
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("matchTime")
    private Date matchTime;
    /**
     * attendances
     */
    private String attendances;

}