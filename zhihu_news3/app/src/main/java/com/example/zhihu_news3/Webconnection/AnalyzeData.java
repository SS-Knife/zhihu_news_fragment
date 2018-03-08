package com.example.zhihu_news3.Webconnection;

/**
 * Created by 郝书逸 on 2018/2/28.
 */

public class AnalyzeData {
    int l;
    public AnalyzeData(int l){
        this.l=l;
    }
    private String[]title = new String[l];
    private String[]images = new String[l];
    private String[]body = new String[l];
    private String[]image_source = new String[l];
    private String[]js = new String[l];
    private String[]share_url = new String[l];
    private String[]thumbnail = new String[l];
    private String[]css = new String[l];
    private String[]editor_name = new String[l];
    private int[]type = new int[l];
    private int[]ga_prefix = new int[l];
    private int[]id = new int[l];
    private int status = 0;
    private int date;
    private String latest = " ";
    private String msg = " ";
    public int[] getType() {
        return type;
    }

    public String[] getBody() {
        return body;
    }

    public String[] getImage_source() {
        return image_source;
    }

    public int[] getGa_prefix() {
        return ga_prefix;
    }

    public String[] getCss() {
        return css;
    }

    public String[] getEditor_name() {
        return editor_name;
    }

    public String[] getImages() {
        return images;
    }

    public String[] getJs() {
        return js;
    }

    public String[] getShare_url() {
        return share_url;
    }

    public String[] getThumbnail() {
        return thumbnail;
    }

    public int[] getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getDate() { return date; }

    public String[] getTitle() {
        return title;
    }

    public String getLatest() {
        return latest;
    }

    public String getMsg() {
        return msg;
    }

    public void setGa_prefix(int[] ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setBody(String[] body) {
        this.body = body;
    }

    public void setCss(String[] css) {
        this.css = css;
    }

    public void setImage_source(String[] image_source) {
        this.image_source = image_source;
    }

    public void setEditor_name(String[] editor_name) {
        this.editor_name = editor_name;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public void setJs(String[] js) {
        this.js = js;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setShare_url(String[] share_url) {
        this.share_url = share_url;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate(int date) { this.date = date; }

    public void setThumbnail(String[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public void setType(int[] type) {
        this.type = type;
    }
}
