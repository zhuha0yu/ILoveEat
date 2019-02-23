package com.example.ILoveEat;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadThread extends Thread {
    private String[] urls = null;
    private String[] ids=null;
    //UI线程中传递的参数用于向UI线程传递消息和数据
    private Handler mHandler;
    private File cachedir;
    private final int numbers;
    private static int howmany=0;
    public LoadThread(String[] urls,String[] ids, Handler mHandler, File cachedir) {
        this.urls = urls;
        this.mHandler = mHandler;
        this.cachedir = cachedir;
        this.ids=ids;
        this.numbers=urls.length;
    }

    @Override
    public void run()
    {
        loadoneimage(0);
     }
private void loadoneimage(int index)
{

    File localFile = null;

    localFile = new File(cachedir, ids[index]);
    StorageReference gsReference= FirebaseStorage.getInstance().getReferenceFromUrl(urls[index]);
    File finalLocalFile = localFile;
    gsReference.getFile(localFile).

            addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess (FileDownloadTask.TaskSnapshot taskSnapshot){
                    if(index<numbers-1)
                    loadoneimage(index+1);
                    else{
                        mHandler.obtainMessage(0).sendToTarget();
                    }
                }
            }).

            addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure (@NonNull Exception exception){
                    mHandler.obtainMessage(1,index).sendToTarget();
                }
            });
}
}
