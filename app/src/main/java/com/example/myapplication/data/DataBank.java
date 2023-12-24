package com.example.myapplication.data;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.MainActivity;
import com.example.myapplication.ShoppingListFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    final String DATA_FILENAME = "bookitems.data";
    ArrayList<Book> data = new ArrayList<>();
    public ArrayList<Book> LoadBookList(Context context) {
        ArrayList<Book> data = new ArrayList<>();
        try{
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<Book>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization","Dataloaded successfully"+ data.size());
        } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        }
        return data;
    }

    public List<Book> saveBookItems(Context context, ArrayList<Book> bookitem) {
        try{
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bookitem);
            out.close();
            fileOut.close();
            Log.d("Serialization","Data saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
