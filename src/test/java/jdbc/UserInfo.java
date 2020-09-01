package jdbc;

/*
 * Creates on 2019/11/13.
 */

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
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
  private BigDecimal space;

  /**
   * 剩余空间
   */
  private BigDecimal remSpace;

  /**
   * 账号状态
   */
  private Integer status;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public Date getCreateTime()
  {
    return createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getUpdateTime()
  {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getNickname()
  {
    return nickname;
  }

  public void setNickname(String nickname)
  {
    this.nickname = nickname;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public int getSex()
  {
    return sex;
  }

  public void setSex(int sex)
  {
    this.sex = sex;
  }

  public String getAvatar()
  {
    return avatar;
  }

  public void setAvatar(String avatar)
  {
    this.avatar = avatar;
  }

  public BigDecimal getBalance()
  {
    return balance;
  }

  public void setBalance(BigDecimal balance)
  {
    this.balance = balance;
  }

  public BigDecimal getSpace()
  {
    return space;
  }

  public void setSpace(BigDecimal space)
  {
    this.space = space;
  }

  public BigDecimal getRemSpace()
  {
    return remSpace;
  }

  public void setRemSpace(BigDecimal remSpace)
  {
    this.remSpace = remSpace;
  }

  public Integer getStatus()
  {
    return status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }
}
