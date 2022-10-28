package com.work.weekplan;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.work.dao.DaoMaster;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public ActivityListener mlistener;

    public static interface ActivityListener{
        void PickerResult(String Path);
    }

    private NoScrollViewPager mViewPager;
    private RadioGroup mTabRadioGroup;
    String path;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        DaoMaster.dropAllTables(new DaoMaster.DevOpenHelper(MainActivity.this, "week_plan.db").getWritableDb(), true);
//        DaoMaster.createAllTables(new DaoMaster.DevOpenHelper(MainActivity.this, "week_plan.db").getWritableDb(), true);

//
//        //添加初始数据
//        Collect col = new Collect();
//        col.ID = null;
//        col.setContents("一个人知道自己为什么而活，就可以忍受任何一种生活。\n");
//        col.setName("弗里德里希·威廉·尼采");
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("人生就是一团欲望，欲望得到满足，就会无聊，得不到就会痛苦。\n");
//        col.setName("亚瑟·叔本华");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("自律即自由。\n");
//        col.setName("伊曼努尔·康德");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("我去死，你们去活，究竟谁过得更幸福，唯有神知道。\n");
//        col.setName("苏格拉底");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("要么庸俗，要么孤独。\n");
//        col.setName("亚瑟·叔本华");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("存在即合理。\n");
//        col.setName("黑格尔");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("每个不曾起舞的日子，都是对生命的辜负。\n");
//        col.setName("弗里德里希·威廉·尼采");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//        col = new Collect();
//        col.setContents("眼睛为她下着雨，心却为她打着伞，这就是爱情。\n");
//        col.setName("拉宾德拉纳特 · 泰戈尔");
//        col.ID = null;
//        col.Status = Params.Enable;
//        col.CreateTime = new Date();
//        col.UpdateTime = new Date();
//        ExCollectDao.getInstance().InsertCollect(MainActivity.this, col);
//
//        Events ev = new Events();
//        ev.ID = null;
//        ev.UpdateTime = new Date();
//        ev.CreateTime = new Date();
//        ev.Type = Params.Enable;
//        ev.Data = "学习GreenDao技术";
//        ev.Status = Params.Enable;
//        ExEventsDao.getInstance().InsertEvents(MainActivity.this, ev);
//        Events ev2 = new Events();
//        ev2.ID = null;
//        ev2.setData("做一次可乐鸡翅");
//        ev2.UpdateTime = new Date();
//        ev2.CreateTime = new Date();
//        ev2.setType(Params.Disable);
//        ev2.Status = Params.Enable;
//        ExEventsDao.getInstance().InsertEvents(MainActivity.this, ev2);
//        Events ev3 = new Events();
//        ev3.ID = null;
//        ev3.setData("跟番剧 一人之下");
//        ev3.UpdateTime = new Date();
//        ev3.CreateTime = new Date();
//        ev3.setType(Params.Disable);
//        ev3.Status = Params.Enable;
//        ExEventsDao.getInstance().InsertEvents(MainActivity.this, ev3);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        // init fragment
        mFragments = new ArrayList<>(5);
        mFragments.add(ToDayFragment.newInstance("今日", "1"));
        mFragments.add(EventsFragment.newInstance("事件", "2"));
        mFragments.add(ProjectsFragment.newInstance("项目", "3"));
        mFragments.add(CollectFragment.newInstance("收藏", "4"));
        mFragments.add(SettingsFragment.newInstance("设置", "5"));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode ==114) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(this, path + "11111", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
            }
            mlistener = SettingsFragment.getInstance();
            mlistener.PickerResult(path);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                 String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    158 + "", null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}