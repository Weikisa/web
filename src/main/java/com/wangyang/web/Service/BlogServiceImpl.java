package com.wangyang.web.Service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wangyang.web.Dao.BlogDao;
import com.wangyang.web.Model.Blog;
import com.wangyang.web.Model.Type;
import com.wangyang.web.NotFoundException;
import com.wangyang.web.Util.MarkdownUtils;
import com.wangyang.web.Util.MyBeanUtils;
import com.wangyang.web.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {

        return blogDao.getOne(id);
    }

    //点击一篇blog查看时，调用该方法，将content内容转换为适合当前页面现实的html文本
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.getOne(id);
        if(blog==null){
            throw new NotFoundException("博客不存在");
        }
        // new一个新的blog对象b，是为了防止blog对象在setContent时，
        // 如下代码:b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // session域中将blog的content内容值改变了(hibernate的一种自制特性)
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        //将blog的博客内容转换为html文本
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新浏览次数Views
        blogDao.updateViews(id);
        return b;
    }

    //admin管理页面用来查询blog的方法
    //多条件分页查询(BlogQuery用来做条件查询的工具类，在package：vo下)
    @Override
    public Page<Blog> listBlog(Pageable pageable, final BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();//用来存放条件的容器
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    //用来实现index页面的blog分页查询
    //将方法的运行结果，进行缓存
    //之后再获取相同的数据，就可以从缓存中获取
    @Cacheable(cacheNames = "blog")//将id作为key
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(final Long tagId, Pageable pageable) {

        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query,pageable);
    }

    //抓取推荐的blog
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogDao.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }


    //admin管理页面blog新增
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            //给新增的blog赋初始值
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else{
            //如果是修改(update)
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    //admin管理页面blog修改
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogDao.getOne(id);
        if(b==null){
            throw new NotFoundException("该篇博客不存在");
        }
        //前端接受过来的blog对象，因为没有createTime、updateTime和views的作用域(例：<input type="hidden" name="id" th:value="*{id}">)
        //所以传入后端是blog里面的这些属性是默认为null值，update时会将原有的这些字段更新为'空',为防止这种情况
        //使用MyBeanUtils.getNullPropertyNames(blog)过滤掉blog里面这些为null的属性值，这些属性值不作update操作即可避免。
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogDao.save(b);
    }

    //根据id删除blog
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteById(id);
    }
}
