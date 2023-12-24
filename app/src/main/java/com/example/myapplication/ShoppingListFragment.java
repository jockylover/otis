package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.DataBank;
import com.example.myapplication.data.Book;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    public ArrayList<Book> books = new ArrayList<>();
    public BookAdapter bookAdapter;
    ActivityResultLauncher<Intent> launcher;
    ActivityResultLauncher<Intent> updateItemLauncher;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list,container);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        books = new DataBank().LoadBookList(requireActivity());
        if(0 == books.size()){
            books.add(new Book(R.drawable.book_1, "第一本书"));
        }
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
                        books.add(new Book(R.drawable.book_no_name, key));
                        DataBank dataBank = new DataBank();
                        dataBank.saveBookItems(requireActivity(),books);
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
                        dataBank.saveBookItems(requireActivity(), books);
                        bookAdapter.notifyItemChanged(position);
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {// 处理取消操作
                    }
                }
        );
        return rootView;
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                // 启动另一个 Activity 来添加新数据
                Intent addIntent = new Intent(requireActivity(), AddDataActivity.class);
                launcher.launch(addIntent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        books.remove(item.getOrder());
                        bookAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().saveBookItems(requireActivity(), books);
                    }
                });
                builder.create().show();
                break;
            case 2:
                Intent intentUpdate = new Intent(requireActivity(), AddDataActivity.class);
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

    public ArrayList<Book> getListBooks() {
        ArrayList<Book> bookList = new ArrayList<>();

        // 添加图书对象到列表
        DataBank dataBank = new DataBank();
        bookList = (ArrayList<Book>) dataBank.LoadBookList(this.requireActivity());
        bookList.add(new Book(R.drawable.book_1, "软件项目管理案例教程（第4版）"));
        bookList.add(new Book(R.drawable.book_2, "创新工程实践"));
        bookList.add(new Book(R.drawable.book_no_name, "信息安全数学基础（第2版）"));

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_list, parent, false);
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
                coverImageView = itemView.findViewById(R.id.image_view_book_cover);
                titleTextView = itemView.findViewById(R.id.text_view_book_title);
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