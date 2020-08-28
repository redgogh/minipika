package jdbc;

/*
 * Creates on 2019/11/13.
 */

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wyz
 */
@Getter
@Setter
public class UserInfo
{

  private String id;

  private Date createTime;

  private Date updateTime;

  /**
   * 用户名
   */
  private String username;

  /**
   * 用户昵称
   */
  private String nickname;

  /**
   * 用户密码
   */
  private String password;

  /**
   * 手机号
   */
  private String phone;

  /**
   * 性别
   */
  private int sex;

  /**
   * 用户头像
   */
  private String avatar;

  /**
   * 余额
   */
  private BigDecimal balance;

  /**
   * 总空间
   */
  private Long space;

  /**
   * 剩余空间
   */
  private Long remSpace;

  /**
   * 账号状态
   */
  private Integer status;

}
