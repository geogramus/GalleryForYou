package geogram.example.galleryforyou.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by geogr on 03.05.2018.
 */

public class Item extends RealmObject {
    @PrimaryKey
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("sha256")
    @Expose
    private String sha256;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("revision")
    @Expose
    private Long revision;
    @SerializedName("resource_id")
    @Expose
    private String resourceId;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("size")
    @Expose
    private Integer size;

    private String itemtype;

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemtype() {
        return itemtype;
    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
