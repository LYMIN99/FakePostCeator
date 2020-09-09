package com.lymindev.fakepostcreator.view.manager;

import com.lymindev.fakepostcreator.view.model.PostItem;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class PostsManager {

    public void clearAllData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void addPostData(PostItem item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(item);
            }
        });
        realm.close();

    }

    public PostItem getPostData(int id) {
        return Realm.getDefaultInstance().where(PostItem.class).equalTo("id",id).findFirst();
    }
}
