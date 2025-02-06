package org.qafellas.utils;

import com.microsoft.playwright.Page;

public class ElementUtils {
    public Page page;

    public ElementUtils(Page page){
        this.page = page;
    }

    public String mergeStrings(String str1, String str2){
        return str1 + str2;
    }

}
