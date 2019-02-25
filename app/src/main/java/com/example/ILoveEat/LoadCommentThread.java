package com.example.ILoveEat;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class LoadCommentThread extends Thread {
    private String[] urls = null;
    private String[] ids = null;
    //UI线程中传递的参数用于向UI线程传递消息和数据
    private Handler mHandler;
    private File cachedir;
    private final int numbers;
    private static int howmany = 0;

    public LoadCommentThread(String[] urls, String[] ids, Handler mHandler, File cachedir) {
        this.urls = urls;
        this.mHandler = mHandler;
        this.cachedir = cachedir;
        this.ids = ids;
        this.numbers = urls.length;
    }

    @Override
    public void run() {
        for (int i = 0; i < numbers; i++)
            loadoneimage(i);
    }

    private void loadoneimage(int index) {

        File localFile = null;

        localFile = new File(cachedir, "food" + ids[index] + "image");
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(urls[index]);
        File finalLocalFile = localFile;
        gsReference.getFile(localFile).

                addOnSuccessListener(taskSnapshot -> {
                    howmany++;
                    if (howmany >= numbers)


                        mHandler.obtainMessage(0).sendToTarget();

                }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        mHandler.obtainMessage(1, index).sendToTarget();
                    }
                });
    }
}
