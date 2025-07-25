package com.library_app.utilities;

import java.util.List;

public class SearchService <T extends Searchable> {

    private List<T> items;


    public SearchService(List<T> items){
        this.items = items;
    }

    
    public List<T> getItems() {
        return items;
    }
    
    public T search_by_name(String name){

        for (T item : getItems()) {
            if ( item.getName().equalsIgnoreCase(name)){
                return item;
            }
           
        }

        return null;
    }


    public T search_by_id(String id){

        for (T item : getItems()) {
            if ( item.getId().equalsIgnoreCase(id)){
                return item;
            }
           
        }

        return null;

    }
    
}
