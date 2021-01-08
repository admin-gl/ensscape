package com.mygdx.escapefromlannioncity.score;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.Writer;

/**
 * Exemple de json qui marche (inutile pour l'instant).
 */
public class Book implements Jsonable {

    private String title;
    private String isbn;
    private long year;
    private String[] authors;

    public Book() {
    }

    public Book(String title, String isbn, long year, String[] authors) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.authors = authors;
    }

    // getters and setters, equals(), toString() .... (omitted for brevity)

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("title", this.title);
        json.put("isbn", this.isbn);
        json.put("year", this.year);
        json.put("authors", this.authors);
        return json.toJson();
    }

    @Override
    public void toJson(Writer writable) throws IOException {
        try {
            writable.write(this.toJson());
        } catch (Exception ignored) {
        }
    }
}
