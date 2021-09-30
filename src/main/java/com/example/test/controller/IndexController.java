package com.example.test.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {

    //计算机俱乐部
    private static List<Student> computerClub = Arrays.asList(
            new Student("2015134001", "小明", 15, "1501"),
            new Student("2015134003", "小王", 14, "1503"),
            new Student("2015134006", "小张", 15, "1501"),
            new Student("2015134008", "小梁", 17, "1505")
    );
    //篮球俱乐部
    private static List<Student> basketballClub = Arrays.asList(
            new Student("2015134012", "小c", 13, "1503"),
            new Student("2015134013", "小s", 14, "1503"),
            new Student("2015134015", "小d", 15, "1504"),
            new Student("2015134018", "小y", 16, "1505")
    );
    //乒乓球俱乐部
    private static List<Student> pingpongClub = Arrays.asList(
            new Student("2015134022", "小u", 16, "1502"),
            new Student("2015134021", "小i", 14, "1502"),
            new Student("2015134026", "小m", 17, "1504"),
            new Student("2015134027", "小n", 16, "1504")
    );

    private static List<List<Student>> allClubStu = new ArrayList<>();

    static {
        allClubStu.add(computerClub);
        allClubStu.add(basketballClub);
        allClubStu.add(pingpongClub);
    }


    public static void main(String[] args) {

        computerClub.forEach(student -> {
            System.out.println(JSON.toJSONString(student));
        });
        computerClub.stream().collect(Collectors.toList());
        computerClub.stream().collect(Collectors.toSet());
//        computerClub.stream().collect(Collectors.toCollection());
        Integer a = computerClub.stream().map(Student::getAge).max(Integer::compareTo).get();
        Integer b = computerClub.stream().map(Student::getAge).min(Integer::compareTo).get();
        long c = computerClub.stream().map(Student::getAge).count();
        System.out.println(a + "--" + b + "--" + c);

        Map<String, Object> stringStudentMap = computerClub.stream().collect(Collectors.toMap(Student::getName, Student::getCode));
        Map<String, Object> stringStudentMap2 = computerClub.stream().collect(Collectors.toMap(p -> p.getName(), p -> p.getCode()));
        System.out.println(JSON.toJSONString(stringStudentMap));
        System.out.println(JSON.toJSONString(stringStudentMap2));

        List<Student> studentList = computerClub.stream().filter(p -> p.getAge() > 15).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(studentList));


        List<String> stringList = computerClub.stream().map(Student::getCode).distinct().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(stringList));

        // 根据name去重
        List<Student> unique = computerClub.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Student::getName))), ArrayList::new)
        );

//        List<Student> unique3 = computerClub.stream().collect(
//                Collectors.collectingAndThen(
//                        Collectors.toCollection(() -> new Student("aa", "bb", 10, "1111")), ArrayList::new Student)
//        );

        System.out.println(JSON.toJSONString(unique));
        // 根据name,sex两个属性去重
        List<Student> unique2 = computerClub.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getAge()))), ArrayList::new)
        );
        System.out.println(JSON.toJSONString(unique2));

        Map<String, List<Student>> stringListMap = computerClub.stream().collect(Collectors.groupingBy(Student::getCode));
        System.out.println(JSON.toJSONString(stringListMap));

        String collect = computerClub.stream().map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(collect);
    }
}
