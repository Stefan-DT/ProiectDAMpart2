package csie.ase.ro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewParser {
    public static List<Review> parseReviews(String json) {
        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray reviewsArray = jsonObject.getJSONArray("reviews");

            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject reviewObj = reviewsArray.getJSONObject(i);
                Review review = new Review();
                review.setReviewText(reviewObj.getString("review"));
                review.setRating((float) reviewObj.getDouble("rating"));
                review.setUser(reviewObj.getString("user"));
                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}
