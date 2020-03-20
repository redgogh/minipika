package org.raniaia.poseidon.framework.monitor;

import org.raniaia.poseidon.extension.ConnectionPool;
import org.raniaia.poseidon.framework.sql.xml.SqlExecute;

/**
 *
 * Poseidon Framework系统监控器, 负责监控系统运行时的执行数据，它可以帮助开发者
 * 快速定位和数据库以及sql有关的信息和优化。
 *
 * 主要的监控对象有:
 *      {@link ConnectionPool} 连接池，监控连接池的信息，比如创建链接耗时，当前存在多少个
 *      链接，以及对链接的最大链接和最小链接的重置等功能。
 *
 *      {@link SqlExecute} sql执行器，监控sql的执行时间，执行错误等信息。
 *
 * 开发者可以对监控器对象进行程序扩展来达到自己的目的，但是需要引入Poseidon的扩展开发包。
 *
 * 监控器页面的默认URL路径: http://127.0.0.1:8080/poseidon/monitor
 *
 * 这个监控器是面向所有对象而开发的，如果你想在你自己的对象中添加监控，你只需要将对象继承{@link Monitor}
 * 即可。然后调用{@link Monitor#createMonitorPage(String)}创建一个新的监控页面即可。
 *
 * 默认是将对象中的成员转换成表字段进行显示，如果你需要自己创建字段可以使用{@link
 * Monitor#createMonitorPage(String, String...)}自定义字段。
 *
 * English:
 *
 * System monitor for poseidon framework it can record system
 * runtime execute data.
 *
 * Easy for developers to optimize the system.
 *
 * The main monitoring objects are {@link ConnectionPool} and sql execute info.
 * example: sql execute time or sql execute error. and {@code ConnectionPool} status.
 *
 * This {@link Monitor} can extension but you need import poseidon extension develop package.
 *
 * If you want reset {@code ConnectionPool} the max connection or min connection or else config,
 * you can open <b>Poseidon Monitor Page</b> in browse, default url is "http://127.0.0.1:8080/poseidon/monitor".
 *
 * This {@link Monitor} can use in everywhere, if you the object need use monitor, you can extends
 * {@link Monitor} object then calling {@link #createMonitorPage(String)} method create new monitor page.
 *
 * Default page info is object the all member value, column name is member name. if you want
 * customize table info you can calling {@link #createMonitorPage(String, String...)} create
 * new page second parameter is you customize the column name.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by tiansheng on 2019/12/17 18:29
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Monitor {

    /**
     * 为对象创建一个监控页面, 默认数据为对象的所有成员信息
     *
     * create monitor page for object, default data is
     * object the all member info.
     *
     * @param name 页面名称 | page name
     */
    protected void createMonitorPage(String name) {
    }

    /**
     * 为对象创建一个监控页面, 默认数据为对象的所有成员信息
     *
     * create monitor page for object, default data is
     * object the all member info.
     *
     * @param name 页面名称 | page name
     * @param columns 自定义列名 | customize column name
     */
    protected void createMonitorPage(String name, String... columns) {
    }

}
