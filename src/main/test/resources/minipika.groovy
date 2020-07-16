properties  {
  database = 'test'
}

configuration {
  opencache true
}


datasource {
  master {
    url ("jdbc:mysql://127.0.0.1:3306/${database}") {
      useUnicode = true
      characterEncoding = "utf-8"
      useSSL = false
      serverTimezone = "GMT"
      allowPublicKeyRetrieval = true
    }
    driver "com.mysql.cj.jdbc.Driver"
    username "root"
    password "root"
  }
}