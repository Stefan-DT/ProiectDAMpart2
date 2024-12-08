package csie.ase.ro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private EditText editTextReview;
    private Button btnSubmitReview;
    private RatingBar ratingBar;
    private ListView listViewReviews;
    private ArrayList<String> reviewList;
    private ArrayAdapter<String> reviewAdapter;

    private static final String URL_REVIEW = "https://www.jsonkeeper.com/b/NK0G";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        editTextReview = findViewById(R.id.editTextReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        ratingBar = findViewById(R.id.ratingBar);
        listViewReviews = findViewById(R.id.listViewReviews);

        reviewList = new ArrayList<>();
        reviewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviewList);
        listViewReviews.setAdapter(reviewAdapter);

        loadReviewsFromServer();

        btnSubmitReview.setOnClickListener(v -> {
            String review = editTextReview.getText().toString();
            float rating = ratingBar.getRating();

            if (!review.isEmpty()) {
                String reviewText = "Review: " + review + "\nRating: " + rating + " stele";
                reviewList.add(reviewText);
                reviewAdapter.notifyDataSetChanged();
                editTextReview.setText("");
                ratingBar.setRating(0);
                Toast.makeText(ReviewActivity.this, "Review trimis!", Toast.LENGTH_SHORT).show();
                saveReviewToServer(review, rating);
            } else {
                Toast.makeText(ReviewActivity.this, "Te rugăm să adaugi un review.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReviewsFromServer() {
        Thread thread = new Thread(() -> {
            HttpsReviews httpsReviews = new HttpsReviews(URL_REVIEW);
            String result = httpsReviews.procesareReviews();

            runOnUiThread(() -> {
                if (result != null) {
                    List<Review> reviews = ReviewParser.parseReviews(result);
                    updateReviewList(reviews);
                }
            });
        });
        thread.start();
    }

    private void updateReviewList(List<Review> reviews) {
        reviewList.clear();
        for (Review review : reviews) {
            String reviewText = "Review: " + review.getReviewText() + "\nRating: " + review.getRating() + " stele";
            reviewList.add(reviewText);
        }
        reviewAdapter.notifyDataSetChanged();
    }

    private void saveReviewToServer(String review, float rating) {
        Thread thread = new Thread(() -> {
            HttpsSaveReview httpsSaveReview = new HttpsSaveReview(URL_REVIEW, review, rating);
            String result = httpsSaveReview.procesareSaveReview();

            runOnUiThread(() -> {
                if (result != null && result.equals("Success")) {
                    Toast.makeText(ReviewActivity.this, "Review salvat pe server!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReviewActivity.this, "Eroare la salvarea review-ului.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        thread.start();
    }


}
