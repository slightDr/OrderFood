package com.example.orderfood.activity.shop.fragment;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.DAO.FoodDAO;
import com.example.orderfood.R;
import com.example.orderfood.util.FileImgUtil;

public class ManageShopUpdateFoodFragment extends Fragment {
    View rootView;
    private ActivityResultLauncher<String> getContentLauncher;
    Uri selectPicUri = null;
    String f_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * 打开文件选择器
     * @param v
     */
    private void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }

    /**
     * 处理fragment返回事件
     */
    private void handleBackPress() {
        // 使用 FragmentManager 处理回退操作
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.manage_shop_frame, new ManageShopHomeFragment());
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_manage_shop_update, container, false);

        // 实现返回功能
        Toolbar toolbar = rootView.findViewById(R.id.manage_shop_update_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 返回有两种，跳转和关闭
                handleBackPress();
            }
        });

        // 获取当前商品信息
        Bundle bundle = getArguments();
        String f_id = bundle.getString("f_id");
        this.f_id = f_id;
        String f_img = bundle.getString("f_img");
        String f_name = bundle.getString("f_name");
        String f_price = bundle.getString("f_price");
        String f_desc = bundle.getString("f_desc");

        // 默认头像
        ImageView imgView = rootView.findViewById(R.id.manage_shop_update_food_img);
        imgView.setImageBitmap(BitmapFactory.decodeFile(f_img));
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
        EditText nameText = rootView.findViewById(R.id.manage_shop_update_food_name);
        nameText.setText(f_name);
        EditText priceText = rootView.findViewById(R.id.manage_shop_update_food_price);
        priceText.setText(f_price);
        EditText descText = rootView.findViewById(R.id.manage_shop_update_food_desc);
        descText.setText(f_desc);
        Button updateButton = rootView.findViewById(R.id.manage_shop_update_food_button);

        // 更新按钮
        updateButton.setOnClickListener(new View.OnClickListener() {
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

                if (name.isEmpty()) {
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
                String picPath = selectPicUri == null ? f_img : FileImgUtil.getPicAbsPath(); // 获取保存图片的绝对路径
                if (selectPicUri != null) {
                    FileImgUtil.saveImageUriToFile(selectPicUri, getActivity(), picPath); // 保存图片
                }
                int res = FoodDAO.updateFood(f_id, name, price, desc, picPath);
                if (res == 0) {
                    Toast.makeText(getActivity(), "修改商品成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "修改商品失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.manage_shop_del_food, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.manage_shop_del_food_icon) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("删除商品");
            builder.setMessage("您确定删除该商品吗?");
            builder.setCancelable(false);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int res = FoodDAO.delFood(f_id);
                    if (res == 0) {
                        Toast.makeText(getActivity(), "删除商品成功", Toast.LENGTH_SHORT).show();
                        handleBackPress();
                    } else {
                        Toast.makeText(getActivity(), "删除商品失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
