package entity;

import java.util.List;

public class FbPost {
    private String post_id;
    private String id_str;
    private String url;
    private String full_text;
    private Integer created_at_stamp;
    private String publish_at;
    private Integer retweet_count;
    private Integer favorite_count;
    private Integer comment_count;
    private String task_name;
    private String crawl_time;
    private Integer crawl_timestamp;
    private String publish_at_page;
    private String retweet_count_page;
    private String favorite_count_page;
    private String comment_count_page;
    private List<String> video_urls;
    private List<String> images_urls;
    private List<String> other_urls;
    private String retweeted_status_full_text;
    private String retweeted_status_id_str;
    private String retweeted_status_user_name;
    private String retweeted_status_user_id_str;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public Integer getCreated_at_stamp() {
        return created_at_stamp;
    }

    public void setCreated_at_stamp(Integer created_at_stamp) {
        this.created_at_stamp = created_at_stamp;
    }

    public String getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(String publish_at) {
        this.publish_at = publish_at;
    }

    public Integer getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(Integer retweet_count) {
        this.retweet_count = retweet_count;
    }

    public Integer getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(Integer favorite_count) {
        this.favorite_count = favorite_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getCrawl_time() {
        return crawl_time;
    }

    public void setCrawl_time(String crawl_time) {
        this.crawl_time = crawl_time;
    }

    public Integer getCrawl_timestamp() {
        return crawl_timestamp;
    }

    public void setCrawl_timestamp(Integer crawl_timestamp) {
        this.crawl_timestamp = crawl_timestamp;
    }

    public String getPublish_at_page() {
        return publish_at_page;
    }

    public void setPublish_at_page(String publish_at_page) {
        this.publish_at_page = publish_at_page;
    }

    public String getRetweet_count_page() {
        return retweet_count_page;
    }

    public void setRetweet_count_page(String retweet_count_page) {
        this.retweet_count_page = retweet_count_page;
    }

    public String getFavorite_count_page() {
        return favorite_count_page;
    }

    public void setFavorite_count_page(String favorite_count_page) {
        this.favorite_count_page = favorite_count_page;
    }

    public String getComment_count_page() {
        return comment_count_page;
    }

    public void setComment_count_page(String comment_count_page) {
        this.comment_count_page = comment_count_page;
    }

    public List<String> getVideo_urls() {
        return video_urls;
    }

    public void setVideo_urls(List<String> video_urls) {
        this.video_urls = video_urls;
    }

    public List<String> getImages_urls() {
        return images_urls;
    }

    public void setImages_urls(List<String> images_urls) {
        this.images_urls = images_urls;
    }

    public List<String> getOther_urls() {
        return other_urls;
    }

    public void setOther_urls(List<String> other_urls) {
        this.other_urls = other_urls;
    }

    public String getRetweeted_status_full_text() {
        return retweeted_status_full_text;
    }

    public void setRetweeted_status_full_text(String retweeted_status_full_text) {
        this.retweeted_status_full_text = retweeted_status_full_text;
    }

    public String getRetweeted_status_id_str() {
        return retweeted_status_id_str;
    }

    public void setRetweeted_status_id_str(String retweeted_status_id_str) {
        this.retweeted_status_id_str = retweeted_status_id_str;
    }

    public String getRetweeted_status_user_name() {
        return retweeted_status_user_name;
    }

    public void setRetweeted_status_user_name(String retweeted_status_user_name) {
        this.retweeted_status_user_name = retweeted_status_user_name;
    }

    public String getRetweeted_status_user_id_str() {
        return retweeted_status_user_id_str;
    }

    public void setRetweeted_status_user_id_str(String retweeted_status_user_id_str) {
        this.retweeted_status_user_id_str = retweeted_status_user_id_str;
    }

    public FbPostUser getUser() {
        return user;
    }

    public void setUser(FbPostUser user) {
        this.user = user;
    }

    private FbPostUser user;

    @Override
    public String toString() {
        return post_id +
                "\u0001" +
                id_str +
                "\u0001" +
                url +
                "\u0001" +
                full_text +
                "\u0001" +
                created_at_stamp +
                "\u0001" +
                publish_at +
                "\u0001" +
                retweet_count +
                "\u0001" +
                favorite_count +
                "\u0001" +
                comment_count +
                "\u0001" +
                task_name +
                "\u0001" +
                crawl_time +
                "\u0001" +
                crawl_timestamp +
                "\u0001" +
                publish_at_page +
                "\u0001" +
                retweet_count_page +
                "\u0001" +
                favorite_count_page +
                "\u0001" +
                comment_count_page +
                "\u0001" +
                video_urls +
                "\u0001" +
                images_urls +
                "\u0001" +
                other_urls +
                "\u0001" +
                retweeted_status_full_text +
                "\u0001" +
                retweeted_status_id_str +
                "\u0001" +
                retweeted_status_user_name +
                "\u0001" +
                retweeted_status_user_id_str +
                "\u0001" +
                user +
                "";
    }
}
