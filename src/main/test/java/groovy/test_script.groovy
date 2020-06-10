package groovy

def findUserByName(name) {
    sql = "select * from user_name where 1=1"
    if(name != null) {
        sql = sql.concat("name = $name")
    }
    return sql;
}