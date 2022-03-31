package org.happyhour.scoreboard.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (UserMatch)实体类
 *
 * @author dalingtao
 * @since 2022-03-31 21:27:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("UserMatch")
public class Usermatch extends Model<Usermatch> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private Integer id;
    /**
     * userid
     */
    private Integer userid;
    /**
     * matchid
     */
    private Integer matchid;
    /**
     * role
     */
    private Integer role;
    /**
     * score
     */
    private Integer score;

}