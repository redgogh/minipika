# 新增内容

- 在XML中可以多条SQL一起执行，并防止SQL注入。

```sql

-- 付款方 金额减
UPDATE `user`
SET balance = balance - {{balance}}
where username = {{username}};

-- 收款方 金额加 -->
UPDATE `user`
SET balance = balance + {{balance}}
where username = {{payee}};

```

像上面这样，你只需要每条sql后面加上分号即可