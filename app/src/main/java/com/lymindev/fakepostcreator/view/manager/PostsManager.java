package com.lymindev.fakepostcreator.view.manager;

import com.lymindev.fakepostcreator.view.model.PostItem;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class PostsManager {

    public void clearAllData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.deleteAll());
    }

    public void addPostData(PostItem item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(item));
        realm.close();
    }
    public PostItem getPostData(int id) {
        return Realm.getDefaultInstance().where(PostItem.class).equalTo("id",id).findFirst();
    }
}
