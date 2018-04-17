package com.wisdom.framework.help.data;

import com.blankj.utilcode.utils.CloseUtils;
import com.google.gson.reflect.TypeToken;
import com.wisdom.framework.manager.ThreadManager;
import com.wisdom.framework.utils.ArrayUtils;
import com.wisdom.framework.utils.FileUtils;
import com.wisdom.framework.utils.GsonUtils;
import com.wisdom.framework.utils.ReflectionUtils;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chejiangwei on 2017/11/27.
 * Describe:litepal快捷存取数据（中间加入缓存）
 * 要么存取该类的单个对象，要么存取该类的集合。初衷只是为基础本地数据服务
 */

public abstract class DataHelper {

    private Map<Object, SoftReference> softReferenceMap = new HashMap<>();

    public <Q extends DataSupport> Q getData(Class<Q> clz) {
        Q data;
        SoftReference softReference = softReferenceMap.get(clz);
        if (softReference == null || (data = clz.cast(softReference.get())) == null) {
            data = DataSupport.findFirst(clz);
            if (data == null) return null;
            softReference = new SoftReference<>(data);
            softReferenceMap.put(clz, softReference);
        }
        return data;
    }

    public <Q extends DataSupport> void saveData(Q data) {
        if (data == null) return;
        Class<? extends DataSupport> clz = data.getClass();
        SoftReference<Q> softReference = new SoftReference<>(data);
        softReferenceMap.put(clz, softReference);
        data.saveOrUpdate();
    }

    public <Q extends DataSupport> List<Q> getDatas(Class<Q> clz) {
        List<Q> data;
        SoftReference softReference = softReferenceMap.get(clz);
        if (softReference == null || (data = (List<Q>) softReference.get()) == null) {
            data = DataSupport.findAll(clz);
            if (data == null) return null;
            softReference = new SoftReference<>(data);
            softReferenceMap.put(clz, softReference);
        }
        return data;
    }

    public <Q extends DataSupport> void saveDatas(List<Q> datas) {
        if (ArrayUtils.isEmpty(datas)) return;
        Type type = ReflectionUtils.mygetSuperClassGenricType(datas.getClass(), 0);
        SoftReference softReference = new SoftReference<>(datas);
        softReferenceMap.put(type, softReference);
        DataSupport.saveAll(datas);
    }


    public <T> void saveJson(String fileName, T t) {
        SoftReference<T> softReference = new SoftReference<>(t);
        softReferenceMap.put(fileName, softReference);

        String s = GsonUtils.toJson(t);

        String path = FileUtils.getJsonDir() + fileName;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
//            long time = System.currentTimeMillis() + despectedTime;// 先计算出过期时间，写入第一行
//            writer.write(time + "\r\n");
            writer.write(s.toCharArray());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(writer);
        }
    }

    public <T> T getData4Json(String fileName, TypeToken<T> typeToken) {
        T data = null;
        SoftReference softReference = softReferenceMap.get(fileName);
        if (softReference == null || (data = (T) softReference.get()) == null) {


            String path = FileUtils.getJsonDir() + fileName;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(path));
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                data = GsonUtils.fromJson(sb.toString(), typeToken);
                softReference = new SoftReference<>(data);
                softReferenceMap.put(fileName, softReference);
                return data;
         /*   String line = reader.readLine();// 第一行是时间
            Long time = Long.valueOf(line);
            if (time > System.currentTimeMillis()) {// 如果时间未过期
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                return GsonUtils.fromJson(sb.toString(),typeToken);
            }*/
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseUtils.closeIO(reader);
            }
        }
        return data;
    }

    /**
     * 异步保存数据
     *
     * @param data
     * @param <Q>
     */

    public <Q extends DataSupport> void saveDataAsync(final Q data) {
        ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                saveData(data);
            }
        });
    }


    public void cleanData(Class<? extends DataSupport> clz) {
        if (softReferenceMap.containsKey(clz))
            softReferenceMap.remove(clz);
        DataSupport.deleteAll(clz);
    }

}
