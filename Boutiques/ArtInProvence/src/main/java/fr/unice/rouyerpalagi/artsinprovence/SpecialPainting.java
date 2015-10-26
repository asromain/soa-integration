package fr.unice.rouyerpalagi.artsinprovence;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SpecialPainting extends Painting {

    //region PROPERTIES
    @JsonProperty("url")
    private String pictureURL;

    @JsonProperty("progress")
    private Integer progress;
    //endregion

    //region GETTERS / SETTERS

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        if (pictureURL != null) {
            this.pictureURL = pictureURL;
        }
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        if (progress != null) {
            if (progress > 100)
                progress = 100;
            else if (progress < 0)
                progress = 0;

            this.progress = progress;
        }
    }

    //endregion

    //region CONSTRUCTORS

    /**
     * Empty constructor, necessary for Jackson usage
     */
    public SpecialPainting() {
        super();
    }

    public SpecialPainting(String description, Integer price, String pictureURL) {
        super(description, price);
        this.pictureURL = pictureURL;
        this.progress = 0;
    }
    //endregion

    //region METHODS
    //endregion
}
