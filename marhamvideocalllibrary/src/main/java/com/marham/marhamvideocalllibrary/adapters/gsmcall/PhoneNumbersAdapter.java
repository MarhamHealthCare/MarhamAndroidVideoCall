package com.marham.marhamvideocalllibrary.adapters.gsmcall;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.cardview.widget.CardView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.MakeCallListener;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumbersAdapter extends BaseAdapter {

    private MakeCallListener makeCallListener;
    private List<String> phoneNumbers;
    private Context context;
    private static LayoutInflater inflater = null;

    public PhoneNumbersAdapter(Context c, MakeCallListener makeCallListener, List<String> phoneNumbers) {
        super();
        this.phoneNumbers = phoneNumbers;
        context = c;
        this.makeCallListener = makeCallListener;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (phoneNumbers != null) {
            return phoneNumbers.size();
        } else {
            phoneNumbers = new ArrayList<>();
            return phoneNumbers.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        // ImageView img;
        BodyText tvNumber;
        CardView parentLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_row_number_list_item, null);
            holder = new Holder();
            holder.tvNumber = convertView.findViewById(R.id.number_text);
            holder.parentLayout = convertView.findViewById(R.id.number_item_parent_layout);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans_Semibold.ttf");
        holder.tvNumber.setTypeface(tf);
        if (phoneNumbers.get(position) != null && !phoneNumbers.get(position).isEmpty()) {
            holder.tvNumber.setText(phoneNumbers.get(position));
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeCallListener.callPhoneNumber(context, phoneNumbers.get(position), position);

            }
        });
        return convertView;
    }


}
