package com.example.ubathletics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    private InputStream inputStream;

    public DataHelper(InputStream is) {
        inputStream = is;
    }

    public ArrayList<String[]> read() {
        ArrayList<String[]> result = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                result.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Go home DataHelper, you're drunk");
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
