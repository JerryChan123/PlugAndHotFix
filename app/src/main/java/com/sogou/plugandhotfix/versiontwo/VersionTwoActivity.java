package com.sogou.plugandhotfix.versiontwo;

import com.sogou.plugandhotfix.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 第一种方式可以加载一些本地APP不存在的Java类，然而并不能解决动态加载Activity和resource的问题，由于Activity执行需要在Manifest中注册，也就是
 * 需要Android的上下文环境，因此提出的解决方案是：
 * - 在项目中实现注册一个Activity作为插件的代理Activity实现
 */
public class VersionTwoActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.load_apk == id) {
            Intent intent = new Intent(this, ProxyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_two);
        findViewById(R.id.load_apk).setOnClickListener(this);

    }


}
