package com.example.demo.pojo.BigScreenes;

import com.example.demo.pojo.BigScreenes.Style;
import lombok.Data;

import java.util.List;

@Data
public class NumberInfo {
    private List<Integer> number;
    private String content;
    private String textAlign;
    private Style style;
}
