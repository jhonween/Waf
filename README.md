## Waf简介
  Waf是一个开源的Android开发框架,其在网络请求和数据库操作、缓存、图片加载等方面进行了比较好的封装，并提供了大部分常用的 开发工具，Waf结合了当前一些开源框架(Volley和xUtils)中部分好的模块，并对这些模块进行了改进和优化，是一个性能极佳、可扩展性强的开发框架。
  
## Waf特点
  (1)提供封装了其他比较常用的工具，比如日志，图片，SharePreference，流处理工具。    
  (2)请求采用异步方式处理，并使用工作队列处理异步请求。   
  (3)扩展性强。框架大部分基于接口进行设计。   
  (4)提供了ORM框架。简化了对数据库的操作。<br/>
  (5)提供了常用的缓存方式。包含了内存缓存、文件缓存<br/>
## 目前Waf的主要模块
  * Cache模块：  
    * 提供了现成的几个缓存类和工具类，可随意选择所需的缓存，包括内存缓存和文件缓存;  
    * 包含了网络缓存和图片缓存接口，通过实现该接口可自定义自己的缓存类，用作网络请求的缓存类。     
  * NetWork模块：   
    * 请求采用异步方式处理，并使用工作队列处理异步请求；   
    * 使用了CacheDispatcher和NetworkDispatcher分别对缓存请求和网络请求进行分发处理；   
    * 提供了多种请求方式，如StringRequest、JsonRequest、ImageRequest对不同类型的请求进行处理；   
    * 扩展性强。框架大部分基于接口进行设计。   
  * Db模块:   
    * 提供了ORM框架。简化了对数据库的操作；  
    * 提供了注解对表进行定义，自动创建表和数据库；      
    * 支持延时加载；      
    * 支持事务处理；     
    * 支持各数据库操作。        
  * Exception模块：     
    * 提供了各类运行时异常和非运行时异常基类和子类，用户可直接使用该类或扩展该类进行使用。<br/>  

## 使用Waf开发框架需要有以下权限：
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
  ```
## 混淆时注意事项：
 * 添加Android默认混淆配置${sdk.dir}/tools/proguard/proguard-android.txt
 * 不要混淆注解类型，添加混淆配置：-keep class * extends java.lang.annotation.Annotation { *; }
 * 对使用Db模块持久化的实体类不要混淆，或者注解所有表和列名称@Table(name="xxx")，@Id(column="xxx")，@Column(colum       n="xxx"),@Foreign(column="xxx",foreign="xxx")；  
 

## Cache缓存模块
  * 以图片缓存举例，自定义MyBitmapLruCache
 ```java
public class MyBitmapLruCache implements ImageCache {
	/** Get the maximum number of bytes the heap can expand to*/
	private static final int MAX_LRU_MEMORY = (int) Runtime.getRuntime().maxMemory();
	/** The ratio of the MAX_LRU_MEMORY can allocate to the app */
	private static int mRatio=8;
	private DiskBitmapCache mDiskBitmapCache;
	private BitmapLruCache mBitmapLruCache;

	public MyBitmapLruCache(int maxLruSize, File rootDirectory, int maxDiskSize) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory, maxDiskSize);
	}

	public MyBitmapLruCache(int maxLruSize, File rootDirectory) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory);
	}

	public MyBitmapLruCache(int maxLruSize, Context context) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}

	public MyBitmapLruCache(Context context) {
		int mcacheSize = MAX_LRU_MEMORY/mRatio;
		mBitmapLruCache = new BitmapLruCache(mcacheSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}
	...

	@Override
	public Bitmap getBitmap(String url) {
		if (mBitmapLruCache != null && mBitmapLruCache.getBitmap(url) != null) {
			System.out.println("######## BitmapCache GET ######## " + url);
			return mBitmapLruCache.getBitmap(url);
		}
		if (mDiskBitmapCache != null && mDiskBitmapCache.getBitmap(url) != null) {
			Bitmap bitmap = mDiskBitmapCache.getBitmap(url);
			if (mBitmapLruCache != null) {
				mBitmapLruCache.putBitmap(url, bitmap);
			}
			return bitmap;
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		System.out.println("######## BitmapCache PUT ######## " + url);
		if (mBitmapLruCache != null) {
			mBitmapLruCache.putBitmap(url, bitmap);
		}
		if (mDiskBitmapCache != null) {
			mDiskBitmapCache.putBitmap(url, bitmap);
		}
	}
 }
  ```
  *  使用Cache 
     ```java
	//使用自定义Cache   
        mImageLoader = new ImageLoader(mVolleyQueue,
			  new MyBitmapLruCache(this));
	 //使用框架缓存工具类CacheUtil获取Cache
	mImageLoader = new ImageLoader(mVolleyQueue,
				CacheUtil.getBitmapCacheInstance(this));
	//使用框架缓存类
	int mcacheSize = 99999;
	mImageLoader = new ImageLoader(mVolleyQueue,
				new BitmapLruCache(mcacheSize) );
				
       ```
  
  
  
## Network模块使用方法
  *  
     ```java
	public class GSONObjectRequestDemoActivity extends Activity {

	private Button mTrigger;
	private RequestQueue mVolleyQueue;
	private ListView mListView;
	private PicturesAdapter mAdapter;
	private ProgressDialog mProgress;
	private List<ImageLoadModel> mImageList;

	private ImageLoader mImageLoader;

	private final String TAG_REQUEST = "MY_TAG";

	GsonRequest<PicturesResponse> gsonObjRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json_object_layout);

		mVolleyQueue = Volley.newRequestQueue(this);

		// ImageLoader(RequestQueue queue, ImageCache imageCache)
		// queue为请求队列
		// imageCache为图片缓存类，可自定义图片缓存类，该缓存类需实现ImageCache接口
		mImageLoader = new ImageLoader(mVolleyQueue,
				CacheUtil.getBitmapCacheInstance(this));

		mImageList = new ArrayList<ImageLoadModel>();

		mListView = (ListView) findViewById(R.id.image_list);
		mTrigger = (Button) findViewById(R.id.send_http);

		mAdapter = new PicturesAdapter(this);
		mListView.setAdapter(mAdapter);

		mTrigger.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				makeSampleHttpRequest();
			}
		});
	}

	public void onStop() {
		super.onStop();
		if (mProgress != null)
			mProgress.dismiss();
	}

	private void makeSampleHttpRequest() {

		String url = "http://waf.demo.com/services/rest";
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("format", "json");

		gsonObjRequest = new GsonRequest<PicturesResponse>(Request.Method.GET,
				builder.toString(), PicturesResponse.class, null,
				new Request.OnPreListener() {

					@Override
					public void onPreExecute() {
						// TODO Auto-generated method stub
						showProgress();
					}

				}, new Response.Listener<PicturesResponse>() {
					@Override
					public void onResponse(PicturesResponse response) {
						try {
							parsePicturesResponse(response);
							mAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
							showToast("JSON parse error");
						}
						stopProgress();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}

						stopProgress();
						// showToast("error:"+error.getMessage());
					}
				});
		gsonObjRequest.setTag(TAG_REQUEST);
		mVolleyQueue.add(gsonObjRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void showProgress() {
		mProgress = ProgressDialog.show(this, "", "Loading...");
	}

	private void stopProgress() {
		mProgress.cancel();
	}

	private void showToast(String msg) {
		Toast.makeText(GSONObjectRequestDemoActivity.this, msg, Toast.LENGTH_LONG)
				.show();
	}

	private void parsePicturesResponse(PicturesResponse response) {

		mImageList.clear();
		Pictures photos = response.getPictures();
		for (int index = 0; index < photos.getPicturesList().size(); index++) {
			Picture pic = photos.getPicturesList().get(index);
			String imageUrl = "http://waf.demo.com/" + pic.getId() + ".jpg";
			ImageLoadModel model = new ImageLoadModel();
			model.setImageUrl(imageUrl);
			model.setTitle(pic.getTitle());
			LogUtil.d("setTitle", pic.getTitle());
			mImageList.add(model);
		}
	}

	private class PicturesAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public PicturesAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mImageList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(mImageList.get(position).getTitle());
			mImageLoader.get(mImageList.get(position).getImageUrl(),
					ImageLoader.getImageListener(holder.image,
							R.drawable.defaultpic,
							android.R.drawable.ic_dialog_alert), 50, 50);// 指定下载后图片的大小
			return convertView;
		}

		class ViewHolder {
			TextView title;
			ImageView image;
		}

	}
}

				
       ```

## Db模块使用方法
*  
     ```java
	public class DbDemoActivity extends Activity {

	private Button test;
	private TextView mResultView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_fragment);
		test = (Button) findViewById(R.id.db_test_btn);
		mResultView = (TextView) findViewById(R.id.db_test_result);

		test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dbTest();
			}
		});
	}

	private void dbTest() {
		StringBuilder strTemp = new StringBuilder();

		Parent parent = new Parent();
		parent.name = "测试" + System.currentTimeMillis();
		parent.setAdmin(true);
		parent.setEmail("wodekjwozz@163.com");

		try {

			// 可传入不同的参数新建DbUtils
			// DbUtils.create(context, dbName);
			// DbUtils.create(context, dbDir, dbName);
			DbUtils db = DbUtils.create(this);
			db.configAllowTransaction(true);
			db.configDebug(true);

			Child child = new Child();
			child.name = "child' name";
			// db.saveBindingId(parent);
			// child.parent = new ForeignLazyLoader<Parent>(Child.class,
			// "parentId", parent.getId());
			// child.parent = parent;

			Parent test = db.findFirst(Selector.from(Parent.class).where("id",
					"in", new int[] { 1, 3, 6 }));
			// Parent test =
			// db.findFirst(Selector.from(Parent.class).where("id",
			// "between", new String[] { "1", "5" }));
			if (test != null) {
				child.parent = test;
				strTemp.append("first parent:" + test + "\n");
				mResultView.setText(strTemp.toString());
			} else {
				child.parent = parent;
			}

			parent.setTime(new Date());
			parent.setDate(new java.sql.Date(new Date().getTime()));

			db.saveBindingId(child);// 保存实体,将其存入数据库,并获取数据库中该实体的id,设置其id值

			List<Child> children = db.findAll(Selector.from(Child.class));// .where(WhereBuilder.b("name",
																			// "=",
																			// "child' name")));
			strTemp.append("children size:" + children.size() + "\n");
			mResultView.setText(strTemp.toString());
			if (children.size() > 0) {
				strTemp.append("last children:"
						+ children.get(children.size() - 1) + "\n");
				mResultView.setText(strTemp.toString());
			}

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			calendar.add(Calendar.HOUR, 3);

			List<Parent> list = db.findAll(Selector.from(Parent.class)
					.where("id", "<", 54).and("time", ">", calendar.getTime())
					.orderBy("id").limit(10));
			strTemp.append("find parent size:" + list.size() + "\n");
			mResultView.setText(strTemp.toString());
			if (list.size() > 0) {
				strTemp.append("last parent:" + list.get(list.size() - 1)
						+ "\n");
				mResultView.setText(strTemp.toString());
				// list.get(0).children.getAllFromDb();
			}

			// parent.name = "hahaha123";
			// db.update(parent);

			Parent entity = db.findById(Parent.class, child.parent.getId());
			strTemp.append("find by id:" + entity.toString() + "\n");
			mResultView.setText(strTemp.toString());

			List<DbModel> dbModels = db.findDbModelAll(Selector
					.from(Parent.class).groupBy("name")
					.select("name", "count(name) as count"));
			strTemp.append("group by result:" + dbModels.get(0).getDataMap()
					+ "\n");
			mResultView.setText(strTemp.toString());

		} catch (DbException e) {
			strTemp.append("error :" + e.getMessage() + "\n");
			mResultView.setText(strTemp.toString());
		}
	}
}

				
       ```

## 其他工具介绍
   * LogUtil.java  日志工具类    
    ```java   
   	private static final String clazzName=MainActivity.class.getSimpleName();
   	....
   	LogUtil.d(clazzName,"MainActivity");	 
   
      ```
   * SharePreferenceUtil.java  SharePreference工具类
   * BitmapUtil.java 图片处理工具类
   * MathUtil.java 数学运算工具类
   * StreamUtil.java 流处理工具类
   

## 关于 免责声明 感谢 联系方式 q群 
   * Waf是一个Android的开源框架，在写这个框架之前，作者研究了一些目前主流的一些Android框架，包括了其原理和使用方法，发现每个框架都有其好的部分，也有不足的地方，为了能使用这些框架的优势部分，于是作者便有了开发Waf框架的想法，因此Waf框架结合了部分开源框架的部分模块，有volley和xUtils的db模块，选用这两个模块是因为作者觉得volley的网络通信模块写的不错，而xUtils的db模块封装的不错。同时，Waf也根据这两个模块存在的问题进行了修改优化并加入了一些功能。另外除了这两个模块，Waf也包含了Cache模块和Exception模块。
   * 感谢Mani Selvaraj ,wyouflf
   * 由于本人的Android开发水平有限，开发的框架难免会出现一些问题，对于有任何建议或者使用中遇到问题都可以给我发邮件。
   * Email： <273581910@qq.com>, <wodekjwozz@163.com>


