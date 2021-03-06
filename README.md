  
UniversalAdapterLib
===================
 通过封装`BaseAdapter`和`RecyclerView.Adapter`以及`PagerAdapter`,`BaseAExpandableListAdapter`得到一个通用简便的`Adapter`

  
特点 
---
   * 提升item的独立性，完美支持item被多处复用
   * item会根据type来做自动复用
   * 支持多种类型的item
   * 仅仅会在item创建完毕后调用一次配置item的操作，不会避免重复建立监听器
   * 一个item仅会触发一次绑定视图的操作
   * 提供了`getView()`方法来代替findViewById
   * 支持通过item的构造方法来传入Activity对象
   * 支持通过item的构造方法来传入item中事件的回调
   * 提供了`getConvertedData(data, type)`方法来对item传入的数据做转换，方便拆包和提升item的复用性
   * 支持viewpager的正常加载模式和懒加载
   * 支持快速将listview的适配器切换为recyclerView的适配器
   * viewpager的`notifyDataSetChanged`可以正常更新所有数据
   * 支持recyclerView的添加头部和底部
   * 支持适配器的数据自动绑定，只用操作数据便可自动notify界面
  
### 示例
  ![github](https://github.com/heavenxue/UniversalAdapter/raw/master/docs/demo.png "github")

### 一、ListView+GridView的通用适配器——UniversalAdapter

只需继承UniversalAdapter便可实现适配器</br>

```java
listView.setAdapter(new UniversalAdapter<DemoMode>(data) {
public AdapterItem<DemoMode> createItem(Object type) {
    return new UserItem();
}
});
```
     

### 二、RecyclerView的通用适配器——UniversalRcvAdapter

```java
private UniversalRcvAdapter<DemoMode> getAdapter(List<DemoMode> data) {
    return new UniversalRcvAdapter<DemoMode>(data) {

        @Override
        public Object getItemType(DemoMode demoModel) {
            return demoModel.type;
        }

        @NonNull
        @Override
        public AdapterItem createItem(Object type) {
            return new UserItem();
        }
    };
}
```
        
### 三、ViewPager的通用适配器——UniversalPagerAdapter

```java
private UniversalPagerAdapter<DemoMode> test01(List<DemoMode> data) {
    return new UniversalPagerAdapter<DemoMode>(data) {

        @Override
        public Object getItemType(DemoMode demoModel) {
            return demoModel.type;
        }

        @NonNull
        @Override
        public AdapterItem createItem(Object type) {
           return new UserItem();
        }
    };
}
```

### 四、ExpandableListView的通用适配器——UniversalExpandableAdapter

```java
private UniversalExpandaleAdapter<DemoMode> test01(List<DemoMode> groups,List<List<DemoMode>> childs){
    universalExpandaleAdapter = new UniversalExpandaleAdapter<DemoMode>(groups,childs){

       @NonNull
       @Override
       public AdapterItem createItem(Object type) {
           return null;
       }

       @Override
       public ExpandableApdaterItem createItems(Object type) {
           return new ExpandUserItem();
       }
   };
    return universalExpandaleAdapter;
}
```