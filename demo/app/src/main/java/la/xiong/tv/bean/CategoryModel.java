package la.xiong.tv.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/16.
 */
@Entity
public class CategoryModel {

    /**
     * id : 1
     * name : 英雄联盟
     * slug : lol
     * first_letter : L
     * status : 0
     * prompt : 1
     * image : http://image.quanmin.tv/4518e1f7c347c3e78fc5fd9bba6cb6b2png
     * thumb : http://image.quanmin.tv/ca3d8b85f3b19ef7171f4435ce03ebcapng
     * priority : 0
     * screen : 0
     */
    @Id(autoincrement = true)
    private Long _id;
    private int id;
    private String name;
    private String slug;
    private String first_letter;
    private int status;
    private int prompt;
    private String image;
    private String thumb;
    private int priority;
    private int screen;

    private String nickname;
    private String video_url;
    private String head_image;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXinimg() {
        return xinimg;
    }

    public void setXinimg(String xinimg) {
        this.xinimg = xinimg;
    }

    private String xinimg;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrompt() {
        return prompt;
    }

    public void setPrompt(int prompt) {
        this.prompt = prompt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getScreen() {
        return screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }


    private String title;
    //private String name;
    private String img;

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    private String play_url;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    @Generated(hash = 279569687)
    public CategoryModel(Long _id, int id, String name, String slug,
            String first_letter, int status, int prompt, String image, String thumb,
            int priority, int screen, String nickname, String video_url,
            String head_image, String url, String xinimg, String title, String img,
            String play_url, String address) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.first_letter = first_letter;
        this.status = status;
        this.prompt = prompt;
        this.image = image;
        this.thumb = thumb;
        this.priority = priority;
        this.screen = screen;
        this.nickname = nickname;
        this.video_url = video_url;
        this.head_image = head_image;
        this.url = url;
        this.xinimg = xinimg;
        this.title = title;
        this.img = img;
        this.play_url = play_url;
        this.address = address;
    }

    @Generated(hash = 1421288718)
    public CategoryModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
