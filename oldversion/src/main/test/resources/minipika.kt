import org.raniaia.minipika.components.config.MinipikaConfig
import org.raniaia.minipika.components.jdbc.datasource.unpooled.Dsi
import org.raniaia.minipika.framework.provide.Configuration

class minipika: MinipikaConfig()
{
    @Configuration
    fun configuration()
    {
        /*
         * 配置数据源
         */
        val d1 = Dsi(
                "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT",
                "com.mysql.cj.jdbc.Driver",
                "root",
                "root"
        )
        datasource(d1)
        cache(false)
        transaction(false)
        pool(10,50)
        entity("org.jiakesiws.minipika.experiment")
        mapper("org.jiakesiws.minipika.builder")
    }
}