package org.raniaia.poseidon.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Generator {

    Configuration cfg;

    public Generator() {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            // 指定模板文件路径
            String file = Objects.requireNonNull(this.getClass().getClassLoader().getResource("tfl")).getFile();
            cfg.setDirectoryForTemplateLoading(new File(file));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate() {
        try {
            Template template = cfg.getTemplate("bean.java.ftl");
            Writer out = new OutputStreamWriter(System.out);
            template.process(null, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
