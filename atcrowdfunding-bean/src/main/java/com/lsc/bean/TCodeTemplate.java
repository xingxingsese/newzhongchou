package com.lsc.bean;

import java.util.Date;

public class TCodeTemplate {
    private Integer id;

    private String templateName;

    private String templateText;

    private String outPath;

    private String metaDataJsonText;

    private Integer type;

    private Date createDate;

    private String author;

    private Date updateDate;

    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText == null ? null : templateText.trim();
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath == null ? null : outPath.trim();
    }

    public String getMetaDataJsonText() {
        return metaDataJsonText;
    }

    public void setMetaDataJsonText(String metaDataJsonText) {
        this.metaDataJsonText = metaDataJsonText == null ? null : metaDataJsonText.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}