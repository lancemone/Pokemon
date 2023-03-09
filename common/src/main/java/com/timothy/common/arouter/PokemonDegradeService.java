package com.timothy.common.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.common.arouter.PokemonDegradeService
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 *     自定义全局降级策略,实现DegradeService接口，并加上一个Path内容任意的注解即可
 * <p/>
 */
@Route(path = ARouterPath.PATH_COMMON_DEGRADE_SERVICE, name = "PokemonDegradeService")
public class PokemonDegradeService implements DegradeService {

    @Override
    public void onLost(Context context, Postcard postcard) {
        Log.e(ARouterManager.AROUTER_TAG, "PokemonDegradeService onLost" + postcard.toString());
    }

    @Override
    public void init(Context context) {
        Log.d(ARouterManager.AROUTER_TAG, "PokemonDegradeService init");
    }
}
