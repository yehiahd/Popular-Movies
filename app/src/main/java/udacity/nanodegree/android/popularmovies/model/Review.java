package udacity.nanodegree.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yehia on 19/12/16.
 */

public class Review {

    @SerializedName("content")
    private String content;

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("url")
    private String url;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

}
