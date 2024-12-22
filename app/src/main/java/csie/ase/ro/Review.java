package csie.ase.ro;

public class Review {
    private int id;
    private String reviewText;
    private float rating;
    private String user;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Review(int id, String reviewText, float rating, String user) {
        this.id = id;
        this.reviewText = reviewText;
        this.rating = rating;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", user='" + user + '\'' +
                '}';
    }
}
