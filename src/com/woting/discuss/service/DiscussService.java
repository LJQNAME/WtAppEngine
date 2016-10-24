package com.woting.discuss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.spiritdata.framework.core.cache.CacheEle;
import com.spiritdata.framework.core.cache.SystemCache;
import com.spiritdata.framework.core.dao.mybatis.MybatisDAO;
import com.spiritdata.framework.core.model.Page;
import com.spiritdata.framework.core.model.tree.TreeNode;
import com.spiritdata.framework.util.SequenceUUID;
import com.spiritdata.framework.util.StringUtils;
import com.woting.WtAppEngineConstants;
import com.woting.cm.core.channel.mem._CacheChannel;
import com.woting.cm.core.channel.model.Channel;
import com.woting.cm.core.channel.persis.po.ChannelAssetPo;
import com.woting.cm.core.media.MediaType;
import com.woting.cm.core.media.model.MediaAsset;
import com.woting.cm.core.media.persis.po.MediaAssetPo;
import com.woting.cm.core.utils.ContentUtils;
import com.woting.discuss.model.Discuss;
import com.woting.discuss.persis.po.DiscussPo;
import com.woting.favorite.persis.po.UserFavoritePo;
import com.woting.favorite.service.FavoriteService;

public class DiscussService {
    @Resource(name="defaultDAO")
    private MybatisDAO<DiscussPo> discussDao;
    @Resource(name="defaultDAO")
    private MybatisDAO<MediaAssetPo> mediaAssetDao;
    @Resource(name="defaultDAO")
    private MybatisDAO<ChannelAssetPo> channelAssetDao;
    @Resource(name="defaultDAO")
    private MybatisDAO<UserFavoritePo> favoriteDao;

    @PostConstruct
    public void initParam() {
        discussDao.setNamespace("WT_DISCUSS");
        mediaAssetDao.setNamespace("A_MEDIA");
        channelAssetDao.setNamespace("A_CHANNELASSET");
        favoriteDao.setNamespace("DA_USERFAVORITE");
    }

    /**
     * 得到重复意见
     * @param opinion 意见信息
     * @return 创建用户成功返回1，否则返回0
     */
    public List<Discuss> getDuplicates(Discuss discuss) {
        try {
            List<Discuss> ret=new ArrayList<Discuss>();
            List<DiscussPo> _ret=discussDao.queryForList("getDuplicates", discuss.toHashMapAsBean());
            if (_ret!=null&&!_ret.isEmpty()) {
                for (DiscussPo dpo: _ret) {
                    Discuss ele=new Discuss();
                    ele.buildFromPo(dpo);
                    ret.add(ele);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户所提意见
     * @param opinion 意信息
     * @return 创建用户成功返回1，否则返回0
     */
    public int insertDiscuss(Discuss discuss) {
        int i=0;
        try {
            discuss.setId(SequenceUUID.getUUIDSubSegment(4));
            discussDao.insert(discuss.convert2Po());
            i=1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 删除评论
     * @param discuss 要删除的评论信息
     * @return 0-删除失败;1删除成功;-1无对应的评论无法删除;-2无权删除
     */
    public int delDiscuss(Discuss discuss) {
        try {
            Map<String, Object> param=((DiscussPo)discuss.convert2Po()).toHashMapAsBean();
            DiscussPo dpo=discussDao.getInfoObject(param);
            if (dpo==null) return -1;
            if (discuss.getUserId().equals(dpo.getUserId())) {
                discussDao.delete(param);
                return 1;
            } else return -2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据文章Id获得文章的评论列表
     * @param mt 内容分类
     * @param contentId 内容Id
     * @param isPub 是否发布
     * @param page 页数
     * @param pageSize 每页条数
     * @return 文章评论列表
     */
    public Map<String, Object> getArticleDiscusses(MediaType mt, String contentId, int isPub, int page, int pageSize) {
        try {
            Map<String, Object> param=new HashMap<String, Object>();
            List<DiscussPo> ol=null;
            long allCount=0;
            if (isPub==1) {
                param.put("a.resTableName", mt.getTabName());
                param.put("a.resId", contentId);
                param.put("sortByClause", " a.cTime desc");
                if (page>=0) { //分页
                    if (page==0) page=1;
                    if (pageSize<0) pageSize=10;
                    Page<DiscussPo> p=this.discussDao.pageQuery("getPubList", param, page, pageSize);
                    if (!p.getResult().isEmpty()) {
                        ol=new ArrayList<DiscussPo>();
                        ol.addAll(p.getResult());
                    }
                    allCount=p.getDataCount();
                } else { //获得所有
                    ol=this.discussDao.queryForList("getPubList", param);
                }
            } else {
                param.put("resTableName", mt.getTabName());
                param.put("resId", contentId);
                param.put("sortByClause", " cTime desc");
                if (page>=0) { //分页
                    if (page==0) page=1;
                    if (pageSize<0) pageSize=10;
                    Page<DiscussPo> p=this.discussDao.pageQuery(param, page, pageSize);
                    if (!p.getResult().isEmpty()) {
                        ol=new ArrayList<DiscussPo>();
                        ol.addAll(p.getResult());
                    }
                    allCount=p.getDataCount();
                } else { //获得所有
                    ol=this.discussDao.queryForList(param);
                }
            }
            if (ol!=null&&ol.size()>0) {
                List<Discuss> ret=new ArrayList<Discuss>();
                if (ol!=null&&!ol.isEmpty()) {
                    for (DiscussPo dpo: ol) {
                        Discuss ele=new Discuss();
                        ele.buildFromPo(dpo);
                        ret.add(ele);
                    }
                }
                param.clear();
                param.put("AllCount", allCount);
                param.put("List", ret);
                return param;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户Id,获得用户评论过的文章列表
     * @param userId 用户Id
     * @param ml 内容分类列表
     * @param isPub 是否发布
     * @param page 页数
     * @param pageSize 每页条数
     * @param rType 返回类型
     * @return 文章列表
     */
    public Map<String, Object> getUserDiscusses(String userId, List<MediaType> ml, int isPub, int page, int pageSize, int rType) {
        if (StringUtils.isNullOrEmptyOrSpace(userId)) return null;

        if (page==0) page=1;
        if (pageSize<0) pageSize=10;

        try {
            Map<String, Object> param=new HashMap<String, Object>();
            List<DiscussPo> dl=null;
            long allCount=0;

            if (rType==1||(ml==null||ml.isEmpty())) { //按一个列表进行返回
                if (isPub==1) { //发布的内容
                    param.put("a.userId", userId);
                    param.put("sortByClause", " a.cTime desc");
                    if (page>=0) { //分页
                        if (page==0) page=1;
                        if (pageSize<0) pageSize=10;
                        Page<DiscussPo> p=this.discussDao.pageQuery("getPubList", param, page, pageSize);
                        if (!p.getResult().isEmpty()) {
                            dl=new ArrayList<DiscussPo>();
                            dl.addAll(p.getResult());
                        }
                        allCount=p.getDataCount();
                    } else { //获得所有
                        dl=this.discussDao.queryForList("getPubList", param);
                    }
                } else {
                    param.put("userId", userId);
                    param.put("sortByClause", " cTime desc");
                    if (page>=0) { //分页
                        if (page==0) page=1;
                        if (pageSize<0) pageSize=10;
                        Page<DiscussPo> p=this.discussDao.pageQuery(param, page, pageSize);
                        if (!p.getResult().isEmpty()) {
                            dl=new ArrayList<DiscussPo>();
                            dl.addAll(p.getResult());
                        }
                        allCount=p.getDataCount();
                    } else { //获得所有
                        dl=this.discussDao.queryForList(param);
                    }
                }
                if (dl==null||dl.isEmpty()) return null;
                //处理内容
                param.clear();
                String s="", f="";
                int i=0;
                for (DiscussPo dPo: dl) {
                    s+=" or (assetType='"+dPo.getResTableName()+"' and assetId='"+dPo.getResId()+"')";
                    f+=" or (resTableName='"+dPo.getResTableName()+"' and resId='"+dPo.getResId()+"')";
                }
                //相关栏目信息
                param.put("whereByClause", s.substring(4));
                List<ChannelAssetPo> chas=channelAssetDao.queryForList("getListByWhere", param);
                List<Map<String, Object>> chasm=new ArrayList<Map<String, Object>>();
                if (chas!=null&&!chas.isEmpty()) {
                    _CacheChannel _cc=(SystemCache.getCache(WtAppEngineConstants.CACHE_CHANNEL)==null?null:((CacheEle<_CacheChannel>)SystemCache.getCache(WtAppEngineConstants.CACHE_CHANNEL)).getContent());
                    for (ChannelAssetPo caPo: chas) {
                        Map<String, Object> one=caPo.toHashMap();
                        if (_cc!=null) {
                            TreeNode<Channel> _c=(TreeNode<Channel>)_cc.channelTree.findNode(caPo.getChannelId());
                            if (_c!=null) one.put("channelName", _c.getNodeName());
                        }
                        chasm.add(one);
                    }
                }
                if (chasm.isEmpty()) chasm=null;
                //获得喜欢列表
                param.clear();
                param.put("whereByClause", f.substring(4));
                List<UserFavoritePo> fl=favoriteDao.queryForList("getListByWhere", param);
                List<Map<String, Object>> fsml=new ArrayList<Map<String, Object>>();
                if (fl!=null&&!fl.isEmpty()) {
                    for (UserFavoritePo ufPo: fl) {
                        fsml.add(ufPo.toHashMap());
                    }
                }
                if (fsml.isEmpty()) fsml=null;
                //分类型获得列表

                //组织返回值
//                List<Map<String, Object>> rl=new ArrayList<>();
//                for (MediaAssetPo maPo : mas) {
//                    MediaAsset mediaAsset = new MediaAsset();
//                    mediaAsset.buildFromPo(maPo);
//                    Map<String, Object> mam=ContentUtils.convert2Ma(mediaAsset.toHashMap(), null, null, chasm, fsm);
//                    rl.add(mam);
//                }
//                param.clear();
//                param.put("AllCount", allCount);
//                param.put("List", rl);
                return param;
            } else { //按分类列表进行返回
                
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}