package woody.upgrade;

import com.google.gson.annotations.Expose;


public class Upgrade {

    @Expose
    private Integer version;
    @Expose
    private String updatetitle;
    @Expose
    private String updatecontent;
    @Expose
    private Integer filelen;
    @Expose
    private String apkurl;

    /**
     * 
     * @return
     *     The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The updatetitle
     */
    public String getUpdatetitle() {
        return updatetitle;
    }

    /**
     * 
     * @param updatetitle
     *     The updatetitle
     */
    public void setUpdatetitle(String updatetitle) {
        this.updatetitle = updatetitle;
    }

    /**
     * 
     * @return
     *     The updatecontent
     */
    public String getUpdatecontent() {
        return updatecontent;
    }

    /**
     * 
     * @param updatecontent
     *     The updatecontent
     */
    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent;
    }

    /**
     * 
     * @return
     *     The filelen
     */
    public Integer getFilelen() {
        return filelen;
    }

    /**
     * 
     * @param filelen
     *     The filelen
     */
    public void setFilelen(Integer filelen) {
        this.filelen = filelen;
    }

    /**
     * 
     * @return
     *     The apkurl
     */
    public String getApkurl() {
        return apkurl;
    }

    /**
     * 
     * @param apkurl
     *     The apkurl
     */
    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }

}
