package udacity.nanodegree.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yehia on 19/12/16.
 */

public class Trailer {

    @SerializedName("site")
    private String site;

    @SerializedName("id")
    private String id;

    @SerializedName("iso_639_1")
    private String language;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("key")
    private String key;

    @SerializedName("iso_3166_1")
    private String country;

    @SerializedName("size")
    private String size;

    public String getSite ()
    {
        return site;
    }

    public void setSite (String site)
    {
        this.site = site;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }
}
