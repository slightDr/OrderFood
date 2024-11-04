package com.example.orderfood.activity.shop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.shop.ManageShopActivity;
import com.example.orderfood.activity.shop.RegisterShopActivity;
import com.example.orderfood.activity.shop.adapter.ShopFoodListAdapter;
import com.example.orderfood.util.FileImgUtil;

import java.util.List;

public class ManageShopAddFragment extends Fragment {
    View rootView;
    private ActivityResultLauncher<String> getContentLauncher;
    Uri selectPicUri = null;

    /**
     * 打开文件选择器
     * @param v
     */
    private void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_manage_shop_add, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        // 默认头像
        Drawable imgDrawableDefault = ContextCompat.getDrawable(getContext(), R.drawable.upload_img);
        Bitmap imgBitmapDefault = ((BitmapDrawable) imgDrawableDefault).getBitmap();
        ImageView imgView = rootView.findViewById(R.id.manage_shop_add_food_img);
        // 初始化文件选择器
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            imgView.setImageURI(uri);
                            selectPicUri = uri;
                        }
                    }
                }
        );
        // 实现选择头像
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
        EditText nameText = rootView.findViewById(R.id.manage_shop_add_food_name);
        EditText priceText = rootView.findViewById(R.id.manage_shop_add_food_price);
        EditText descText = rootView.findViewById(R.id.manage_shop_add_food_desc);
        Button addButton = rootView.findViewById(R.id.manage_shop_add_food_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable img = imgView.getDrawable(); // 获取图片
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                String desc = descText.getText().toString();

                if (!(img instanceof BitmapDrawable)) {
                    Toast.makeText(getActivity(), "图片格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap sImgBitmap = ((BitmapDrawable) img).getBitmap(); // 获取图片二进制格式
                if (sImgBitmap.sameAs(imgBitmapDefault)) { // 判断是否选择其他头像
                    Toast.makeText(getActivity(), "请添加商品图片", Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入商品名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (price.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入商品价格", Toast.LENGTH_SHORT).show();
                    return;
                } else if (desc.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入商品描述", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存到数据库
                String picPath = FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                FileImgUtil.saveImageUriToFile(selectPicUri, getActivity(), picPath); // 保存图片
                String s_id = sharedPreferences.getString("s_id", ""); // 获取登录商家id
                int res = FoodDAO.addFood(s_id, name, price, desc, picPath);
                if (res == 0) {
                    Toast.makeText(getActivity(), "添加商品成功", Toast.LENGTH_SHORT).show();
                    nameText.setText("");
                    priceText.setText("");
                    descText.setText("");
                    imgView.setImageBitmap(imgBitmapDefault);
                } else {
                    Toast.makeText(getActivity(), "添加商品失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
