package csie.ase.ro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SubscriptionAdapter extends ArrayAdapter<Subscription> {
    private Context context;
    private List<Subscription> subscriptions;

    public SubscriptionAdapter(Context context, List<Subscription> subscriptions) {
        super(context, R.layout.item_subscription, subscriptions);
        this.context = context;
        this.subscriptions = subscriptions;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_subscription, parent, false);
        }

        Subscription subscription = subscriptions.get(position);

        TextView nameTextView = convertView.findViewById(R.id.textViewSubscriptionName);
        TextView typeTextView = convertView.findViewById(R.id.textViewType);
        TextView durationTextView = convertView.findViewById(R.id.textViewDuration);

        nameTextView.setText(subscription.getName());
        typeTextView.setText(subscription.getType());
        durationTextView.setText(subscription.getDuration());

        return convertView;
    }
}
