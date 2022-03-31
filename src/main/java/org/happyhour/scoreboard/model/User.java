package org.happyhour.scoreboard.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (User)实体类
 *
 * @author dalingtao
 * @since 2022-03-31 21:27:39
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("User")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * userid
     */
    @TableId
	private Integer userid;
    /**
     * alias
     */
    private String alias;
    /**
     * name
     */
    private String name;
    /**
     * password
     */
    private String password;
    /**
     * avatar
     */
    private String avatar;

}