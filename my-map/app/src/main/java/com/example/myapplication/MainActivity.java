package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private String[] tabHeaderStrings = {"Shopping items", "tencent maps", "news"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(id.tab_layout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position)->tab.setText(tabHeaderStrings[position])
        ).attach();
    }
    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 3;

        public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager,lifecycle);
        }
        public Fragment createFragment(int position){
            switch(position){
                case 0:
                    return new ShoppingListFragment();
                case 2:
                    return new WebViewFragment();
                case 1:
                    return new MapFragment();
                default:
                    return null;

            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }

}
    /*
    public ArrayList<Book> books = new ArrayList<>();
    public MainActivity.BookAdapter bookAdapter;
    ActivityResultLauncher<Intent> launcher;
    ActivityResultLauncher<Intent> updateItemLauncher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        RecyclerView recyclerView = findViewById(id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        books = (ArrayList<Book>) new DataBank().LoadBookList(MainActivity.this);
//        if(0 == books.size()){
//            books.add(new Book(drawable.book_1, "第一本书"));
//        }
        books.add(new Book(drawable.book_1, "软件项目管理案例教程（第4版）"));
        books.add(new Book(drawable.book_2, "创新工程实践"));
        books.add(new Book(drawable.book_no_name, "信息安全数学基础（第2版）"));
        bookAdapter = new BookAdapter(books);
        // 获取图书列表
        recyclerView.setAdapter(bookAdapter);
        registerForContextMenu(recyclerView);
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String key = data.getStringExtra( "key"); // 获款返回的数据
                        books.add(new Book(drawable.book_no_name, key));
                        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                        DataBank dataBank = new DataBank();
                        dataBank.saveBookItems(MainActivity.this,books);
                        bookAdapter.notifyItemRemoved(books.size());
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {// 处理取消操作
                    }
                }
        );
        updateItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position", 0);
                        String key = data.getStringExtra( "key"); // 获款返回的数据
                        Book book = books.get(position);
                        book.setKey(key);
                        DataBank dataBank = new DataBank();
                        dataBank.saveBookItems(MainActivity.this, books);
                        bookAdapter.notifyItemChanged(position);
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {// 处理取消操作
                    }
                }
        );
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                // 启动另一个 Activity 来添加新数据
                Intent addIntent = new Intent(MainActivity.this, AddDataActivity.class);
                launcher.launch(addIntent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        books.remove(item.getOrder());
                        bookAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().saveBookItems( MainActivity.this, books);
                    }
                });
                builder.create().show();
                break;
            case 2:
                Intent intentUpdate = new Intent(MainActivity.this, AddDataActivity.class);
                Book book = books.get(item.getOrder());
                intentUpdate.putExtra("key",books.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("position", item.getOrder());
                updateItemLauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class Book implements Serializable {
        private int coverResourceId;
        private String title;

        public Book(int coverResourceId, String title) {
            this.coverResourceId = coverResourceId;
            this.title = title;
        }

        public int getCoverResourceId() {
            return coverResourceId;
        }

        public String getTitle() {
            return title;
        }

        public void setKey(String key) {
            this.title = key;
        }
    }

    public ArrayList<Book> getListBooks() {
        ArrayList<Book> bookList = new ArrayList<>();

        // 添加图书对象到列表
        DataBank dataBank = new DataBank();
        bookList = (ArrayList<Book>) dataBank.LoadBookList(this.getApplicationContext());
        bookList.add(new Book(drawable.book_1, "软件项目管理案例教程（第4版）"));
        bookList.add(new Book(drawable.book_2, "创新工程实践"));
        bookList.add(new Book(drawable.book_no_name, "信息安全数学基础（第2版）"));

        return bookList;
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
        // 在这里定义数据集合和ViewHolder
        private ArrayList<Book> bookitem;
        public BookAdapter(ArrayList<Book> book) {
            bookitem = book;
        }

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 创建并返回ViewHolder
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.book_item_list, parent, false);
            return new BookAdapter.BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
            holder.getCoverImageView().setImageResource(bookitem.get(position).getCoverResourceId());
            holder.getTitleTextView().setText(bookitem.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            // 返回数据集合的大小
            return books.size();
        }

        public class BookViewHolder extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener {

            // 在ViewHolder中定义视图元素

            private ImageView coverImageView;
            private TextView titleTextView;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);
                // 在构造函数中找到布局中的视图元素
                coverImageView = itemView.findViewById(id.image_view_book_cover);
                titleTextView = itemView.findViewById(id.text_view_book_title);
                itemView.setOnCreateContextMenuListener(this);
            }

            // 提供一个方法来绑定数据到ViewHolder中的视图元素
            public void bind(Book book) {
                coverImageView.setImageResource(book.getCoverResourceId());
                titleTextView.setText(book.getTitle());
            }
            @Override

            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                // 注册上下文菜单
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "增加"+this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除"+this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改"+this.getAdapterPosition());
            }

            public ImageView getCoverImageView() {
                return coverImageView;
            }

            public TextView getTitleTextView() {
                return titleTextView;
            }
        }
    }
}

     */