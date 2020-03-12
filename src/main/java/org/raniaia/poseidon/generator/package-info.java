/**
 *
 * mapper.xml、mapper.java等模板生成器, 为了避免开发人员去手动写一些重复的功能，降低
 * 开发效率而开发。
 *
 * 这个包下的开发内容最终成品为一个命令行，让开发人员能够通过一个命令就实现代码生成，提高开发效率。
 * 生成的内容会是基本的增删改查，也可以自己去定义它。
 *
 * 使用方式：
 * 如果我们要针对一张表生成一个新的Mapper.xml、Mapper.java以及Vo.java的话我们可以调用以下命令
 * <code>
 *      pg -g --table=kkb_user_info,kkb_product cfg1
 * </code>
 *
 * pg代表我们当前需要调用的命令,
 *      -g          代表生成某些文件
 *      --table     代表我们要根据表去生成文件
 *      最后一个参数代表我们要选择的配置文件, 根据哪个配置文件的规则去生成
 *
 * 如果配置文件调用错了，或者其他执行错了可以使用 -w 撤回：
 * <code>
 *     pg -w
 * </code>
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
package org.raniaia.poseidon.generator;