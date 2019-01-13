package com.wd.tech.baselibrary.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wd.tech.baselibrary.entity.HistoryEneity;
import com.wd.tech.baselibrary.greendao.DaoMaster;
import com.wd.tech.baselibrary.greendao.DaoSession;
import com.wd.tech.baselibrary.greendao.HistoryEneityDao;

import java.util.List;

public class SqlUtil {
    private static SqlUtil sqlUtil;
    private HistoryEneityDao historyEntityDao;


    private SqlUtil() {

    }

    public static SqlUtil getInstens() {
        if (sqlUtil == null) {
            synchronized (SqlUtil.class) {
                sqlUtil = new SqlUtil();
            }
        }
        return sqlUtil;
    }

    public void init(Context context, String dbName) {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();
        historyEntityDao = daoSession.getHistoryEneityDao();
    }

    public void insert(String type, String data) {
        HistoryEneity historyEntity = new HistoryEneity();
        historyEntity.setType(type);
        historyEntity.setHistory(data);
        historyEntityDao.insert(historyEntity);
    }

    /**
     * 删除全部
     */
    public void deleteAll() {
        historyEntityDao.deleteAll();
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<HistoryEneity> queryAll() {
        return historyEntityDao.loadAll();
    }

    /**
     * 根据类型查询
     *
     * @param type
     * @return
     */
    public HistoryEneity queryByType(String type) {
        List<HistoryEneity> historyEntities = historyEntityDao.loadAll();
        for (int i = 0; i < historyEntityDao.loadAll().size(); i++) {
            if (type.equals(historyEntities.get(i).getType())) {
                return historyEntities.get(i);
            }
        }
        return null;
    }

    /**
     * 更加key删除
     *
     * @param key
     */

    public void deleteByKey(Long key) {
        historyEntityDao.deleteByKey(key);
    }

    /**
     * 删除
     *
     * @param type
     */
    public void deleteByType(String type) {
        List<HistoryEneity> historyEntities = historyEntityDao.loadAll();
        for (int i = 0; i < historyEntityDao.loadAll().size(); i++) {
            if (type.equals(historyEntities.get(i).getType())) {
                historyEntityDao.delete(historyEntities.get(i));
            }
        }
    }


    public void upDataByType(String type, String data) {
        List<HistoryEneity> historyEntities = historyEntityDao.loadAll();
        for (int i = 0; i < historyEntityDao.loadAll().size(); i++) {
            if (type.equals(historyEntities.get(i).getType())) {
                HistoryEneity historyEntity = historyEntities.get(i);
                historyEntity.setHistory(data);
                historyEntityDao.update(historyEntity);
            }
        }
    }

    /**
     * 判断是否有相同的类型
     *
     * @param type
     * @return
     */
    public boolean queryIsExist(String type) {
        List<HistoryEneity> historyEntities = historyEntityDao.loadAll();
        for (int i = 0; i < historyEntityDao.loadAll().size(); i++) {
            if (historyEntities.get(i).getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}