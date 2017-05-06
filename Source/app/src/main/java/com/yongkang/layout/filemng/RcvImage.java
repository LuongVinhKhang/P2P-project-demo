package com.yongkang.layout.filemng;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongkang.layout.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong Kang on 06-May-17.
 */

public class RcvImage extends RecyclerView.Adapter<RcvImage.ImageViewHolder> {

    Context context;
    List<ImageModel> list;

    public RcvImage(Context context, List<ImageModel> list) {
        this.context = context;
        this.list = list;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_rcv_image, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        ImageModel model = list.get(position);
        File imgFile = new File(model.getPath());

        int h = DeviceHelper.getHeightScreen(context);
        int w = DeviceHelper.getWithScreen(context);

        if (imgFile.exists()) {

            Bitmap bitmap = decodeSampledBitmapFromFile(imgFile.getAbsolutePath(), w / 2 - 10, w / 2 - 10);
            holder.image.setImageBitmap(bitmap);
            holder.textSize.setText(model.getSize());
            holder.checkBox.setChecked(model.getCheck());
        } else {
            holder.textSize.setText("Image error");
            holder.checkBox.setChecked(false);
        }
    }

    public List<String> getSelectedItem(){
        List<String> result = new ArrayList<>();

        for (ImageModel model:list){
            if (model.getCheck()){
                result.add(model.getPath());
            }
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView textSize;
        CheckBox checkBox;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.item_rcv_image_image);
            textSize = (TextView) itemView.findViewById(R.id.item_rcv_image_text_size);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_rcv_image_check);

            // int w = DeviceHelper.getWithScreen(context);
            // checkBox.setLayoutParams(new RelativeLayout.LayoutParams(w / 5, w / 5));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }

                    ImageModel model = list.get(pos);
                    model.setCheck(checkBox.isChecked());
                    list.set(pos, model);
                    notifyItemChanged(pos);
                }
            });

        }
    }

}
