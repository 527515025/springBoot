package cn.abel.bean;


public class News {
    private Integer id;
    private String title;
    private String content;
    private String imagePath;
    private Integer readSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getReadSum() {
        return readSum;
    }

    public void setReadSum(Integer readSum) {
        this.readSum = readSum;
    }
}