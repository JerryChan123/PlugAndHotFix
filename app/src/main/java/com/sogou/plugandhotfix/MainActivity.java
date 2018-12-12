
package com.sogou.plugandhotfix;

import com.sogou.plugandhotfix.versionone.VersionOneActivity;
import com.sogou.plugandhotfix.versiontwo.VersionTwoActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 记得打开存储权限，这里没有动态申请
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.version_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VersionOneActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.version_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VersionTwoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.version_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
