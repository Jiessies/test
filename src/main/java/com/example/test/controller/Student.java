package com.example.test.controller;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
public class Student {

    private String id;
    private String name;
    private int age;
    private String code;

    public Student(String id, String name, int age, String code) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.code = code;
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("hello c++", "hello java", "hello python");
        List<String> result = words.stream()
                // 将单词按照空格切合，返回Stream<String[]>类型的数据
                .map(word -> word.split(" "))
                // 将Stream<String[]>转换为Stream<String>
                .flatMap(Arrays::stream)
                // 去重
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result);


        //解决不了问题的写法
        List<String> strings = words.stream().map(a -> a.split(" ")).map(b -> b.toString()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(strings));


        String[] awords = new String[]{"Hello", "World"};
        List<String[]> a = Arrays.stream(awords)
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
        a.forEach(System.out::print);

        System.out.println("--------------");

        List<String> b = Arrays.stream(awords)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        b.forEach(System.out::print);


        System.out.println("--------------");
    }
}
