package org.happyhour.scoreboard.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (Match)实体类
 *
 * @author dalingtao
 * @since 2022-03-31 21:27:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("Match")
public class Match extends Model<Match> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * matchid
     */
    @TableId
	private Integer matchid;
    /**
     * time
     */
    private Date time;
    /**
     * attendances
     */
    private String attendances;

}