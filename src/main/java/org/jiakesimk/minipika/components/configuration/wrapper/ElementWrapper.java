package org.jiakesimk.minipika.components.configuration.wrapper;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2020/7/6.
 */

import org.jdom2.*;
import org.jdom2.filter.Filter;
import org.jdom2.util.IteratorIterable;
import org.jiakesimk.minipika.framework.utils.Lists;
import org.jiakesimk.minipika.framework.utils.Matches;
import org.jiakesimk.minipika.framework.utils.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class ElementWrapper {

  Element e;

  public ElementWrapper() {
  }

  public ElementWrapper(Element e) {
    this.e = e;
  }

  public Element getE() {
    return e;
  }

  public String getName() {
    return e.getName();
  }

  public ElementWrapper setName(String name) {
    return newQuoteElement(e.setName(name));
  }

  public Namespace getNamespace() {
    return e.getNamespace();
  }

  public ElementWrapper setNamespace(Namespace namespace) {
    return newQuoteElement(e.setNamespace(namespace));
  }

  public String getNamespacePrefix() {
    return e.getNamespacePrefix();
  }

  public String getNamespaceURI() {
    return e.getNamespaceURI();
  }

  public Namespace getNamespace(String prefix) {
    return e.getNamespace(prefix);
  }

  public String getQualifiedName() {
    return e.getQualifiedName();
  }

  public boolean addNamespaceDeclaration(Namespace additionalNamespace) {
    return e.addNamespaceDeclaration(additionalNamespace);
  }

  public void removeNamespaceDeclaration(Namespace additionalNamespace) {
    e.removeNamespaceDeclaration(additionalNamespace);
  }

  public List<Namespace> getAdditionalNamespaces() {
    return e.getAdditionalNamespaces();
  }

  public String getValue() {
    return configuration(e.getValue());
  }

  public boolean isRootQuoteElement() {
    return e.isRootElement();
  }

  public int getContentSize() {
    return e.getContentSize();
  }

  public int indexOf(Content child) {
    return e.indexOf(child);
  }

  public ElementWrapper getParent() {
    return newQuoteElement((Element) e.getParent());
  }

  public String getText() {
    return configuration(e.getText());
  }

  public String getTextTrim() {
    return configuration(e.getTextTrim());
  }

  public String getTextNormalize() {
    return configuration(e.getTextNormalize());
  }

  public String getChildText(String cname) {
    return configuration(e.getChildText(cname));
  }

  public String getChildTextTrim(String cname) {
    return configuration(e.getChildTextTrim(cname));
  }

  public String getChildTextNormalize(String cname) {
    return configuration(e.getChildTextNormalize(cname));
  }

  public String getChildText(String cname, Namespace ns) {
    return configuration(e.getChildText(cname, ns));
  }

  public String getChildTextTrim(String cname, Namespace ns) {
    return configuration(e.getChildTextTrim(cname, ns));
  }

  public String getChildTextNormalize(String cname, Namespace ns) {
    return configuration(e.getChildTextNormalize(cname, ns));
  }

  public ElementWrapper setText(String text) {
    return newQuoteElement(e.setText(text));
  }

  public boolean coalesceText(boolean recursively) {
    return e.coalesceText(recursively);
  }

  public List<Content> getContent() {
    return e.getContent();
  }

  public <E extends Content> List<E> getContent(Filter<E> filter) {
    return e.getContent(filter);
  }

  public List<Content> removeContent() {
    return e.removeContent();
  }

  public <F extends Content> List<F> removeContent(Filter<F> filter) {
    return e.removeContent(filter);
  }

  public ElementWrapper setContent(Collection<? extends Content> newContent) {
    return newQuoteElement(e.setContent(newContent));
  }

  public ElementWrapper setContent(int index, Content child) {
    return newQuoteElement(e.setContent(index, child));
  }

  public Parent setContent(int index, Collection<? extends Content> newContent) {
    return e.setContent(index, newContent);
  }

  public ElementWrapper addContent(String str) {
    return newQuoteElement(e.addContent(str));
  }

  public ElementWrapper addContent(Content child) {
    return newQuoteElement(e.addContent(child));
  }

  public ElementWrapper addContent(Collection<? extends Content> newContent) {
    return newQuoteElement(e.addContent(newContent));
  }

  public ElementWrapper addContent(int index, Content child) {
    return newQuoteElement(e.addContent(index, child));
  }

  public ElementWrapper addContent(int index, Collection<? extends Content> newContent) {
    return newQuoteElement(e.addContent(index, newContent));
  }

  public List<Content> cloneContent() {
    return e.cloneContent();
  }

  public Content getContent(int index) {
    return e.getContent(index);
  }

  public boolean removeContent(Content child) {
    return e.removeContent(child);
  }

  public Content removeContent(int index) {
    return e.removeContent(index);
  }

  public ElementWrapper setContent(Content child) {
    return newQuoteElement(e.setContent(child));
  }

  public boolean isAncestor(Element element) {
    return e.isAncestor(element);
  }

  public boolean hasAttributes() {
    return e.hasAttributes();
  }

  public boolean hasAdditionalNamespaces() {
    return e.hasAdditionalNamespaces();
  }

  public List<Attribute> getAttributes() {
    List<Attribute> attributes = e.getAttributes();
    for (Attribute attribute : attributes) {
      attributeSetValue(attribute);
    }
    return attributes;
  }

  public Attribute getAttribute(String attname) {
    Attribute attribute = e.getAttribute(attname);
    attributeSetValue(attribute);
    return attribute;
  }

  public Attribute getAttribute(String attname, Namespace ns) {
    Attribute attribute = e.getAttribute(attname, ns);
    attributeSetValue(attribute);
    return attribute;
  }

  public String getAttributeValue(String attname) {
    return configuration(e.getAttributeValue(attname));
  }

  public String getAttributeValue(String attname, String def) {
    return configuration(e.getAttributeValue(attname, def));
  }

  public String getAttributeValue(String attname, Namespace ns) {
    return configuration(e.getAttributeValue(attname, ns));
  }

  public String getAttributeValue(String attname, Namespace ns, String def) {
    return configuration(e.getAttributeValue(attname, ns, def));
  }

  public ElementWrapper setAttributes(Collection<? extends Attribute> newAttributes) {
    return newQuoteElement(e.setAttributes(newAttributes));
  }

  public ElementWrapper setAttribute(String name, String value) {
    return newQuoteElement(e.setAttribute(name, value));
  }

  public ElementWrapper setAttribute(String name, String value, Namespace ns) {
    return newQuoteElement(e.setAttribute(name, value, ns));
  }

  public ElementWrapper setAttribute(Attribute attribute) {
    return newQuoteElement(e.setAttribute(attribute));
  }

  public boolean removeAttribute(String attname) {
    return e.removeAttribute(attname);
  }

  public boolean removeAttribute(String attname, Namespace ns) {
    return e.removeAttribute(attname, ns);
  }

  public boolean removeAttribute(Attribute attribute) {
    return e.removeAttribute(attribute);
  }

  public String toString() {
    return e.toString();
  }

  public ElementWrapper clone() {
    return newQuoteElement(e.clone());
  }

  public IteratorIterable<Content> getDescendants() {
    return e.getDescendants();
  }

  public <F extends Content> IteratorIterable<F> getDescendants(Filter<F> filter) {
    return e.getDescendants(filter);
  }

  public List<ElementWrapper> getChildren() {
    List<ElementWrapper> qe = Lists.newArrayList();
    for (Element element : e.getChildren()) {
      qe.add(newQuoteElement(element));
    }
    return qe;
  }

  public List<ElementWrapper> getChildren(String cname) {
    List<ElementWrapper> qe = Lists.newArrayList();
    for (Element element : e.getChildren(cname)) {
      qe.add(newQuoteElement(element));
    }
    return qe;
  }

  public List<ElementWrapper> getChildren(String cname, Namespace ns) {
    List<ElementWrapper> qe = Lists.newArrayList();
    for (Element element : e.getChildren(cname, ns)) {
      qe.add(newQuoteElement(element));
    }
    return qe;
  }

  public ElementWrapper getChild(String cname, Namespace ns) {
    return newQuoteElement(e.getChild(cname, ns));
  }

  public ElementWrapper getChild(String cname) {
    return newQuoteElement(e.getChild(cname));
  }

  public boolean removeChild(String cname) {
    return e.removeChild(cname);
  }

  public boolean removeChild(String cname, Namespace ns) {
    return e.removeChild(cname, ns);
  }

  public boolean removeChildren(String cname) {
    return e.removeChildren(cname);
  }

  public boolean removeChildren(String cname, Namespace ns) {
    return e.removeChildren(cname, ns);
  }

  public List<Namespace> getNamespacesInScope() {
    return e.getNamespacesInScope();
  }

  public List<Namespace> getNamespacesInherited() {
    return e.getNamespacesInherited();
  }

  public List<Namespace> getNamespacesIntroduced() {
    return e.getNamespacesIntroduced();
  }

  public ElementWrapper detach() {
    return newQuoteElement(e.detach());
  }

  public void canContainContent(Content child, int index, boolean replace) throws IllegalAddException {
    e.canContainContent(child, index, replace);
  }

  public void sortContent(Comparator<? super Content> comparator) {
    e.sortContent(comparator);
  }

  public void sortChildren(Comparator<? super Element> comparator) {
    e.sortChildren(comparator);
  }

  public void sortAttributes(Comparator<? super Attribute> comparator) {
    e.sortAttributes(comparator);
  }

  public <E extends Content> void sortContent(Filter<E> filter, Comparator<? super E> comparator) {
    e.sortContent(filter, comparator);
  }

  public URI getXMLBaseURI() throws URISyntaxException {
    return e.getXMLBaseURI();
  }

  ElementWrapper newQuoteElement(Element e) {
    return new ElementWrapper(e);
  }

  void attributeSetValue(Attribute attribute) {
    if (attribute != null) {
      attribute.setValue(configuration(attribute.getValue()));
    }
  }

  /**
   * 将元素内容中含有${xxx}的字符串转换成属性配置信息
   *
   * @param input 元素内容
   * @return 处理后的内容字符串
   */
  private String configuration(String input) {
    if (StringUtils.isNotEmpty(input)) {
      String[] args = Matches.find(input, "\\$\\{(.*?)}");
      for (String arg : args) {
        String value = System.getProperty(arg);
        if(StringUtils.isEmpty(value)) {
          value = "${".concat(arg).concat("}");
        }
        try {
          input = input.replaceAll("\\$\\{".concat(arg).concat("}"), value);
        }catch (Throwable ignore) {
          // ignored
        }
      }
    }
    return input;
  }

}
