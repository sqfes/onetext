//package com.example.demo.stream;
//
//import com.example.demo.pojo.User;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.OptionalInt;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
////Stream的用法：
//// 1.创建Stream
//// 2.过滤（filter）
////3.映射（map）
////4.排序（sorted）
////5.聚合（reduce）
////6并行处理（parallel）
//public class StreamDamo {
//    // 1.创建Stream
//    @Test
//    public void Stream(){
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//        Stream<Integer> stream = list.stream();
//        System.out.println(stream);
//    }
//    //过滤
//    @Test
//    public void Streamfilter(){
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//        List<Integer> stream = list.stream()
//                .filter(s -> s%2==0)
//                .collect(Collectors.toList());
//        stream.forEach(System.out::println);
//    }
//    //映射
//    @Test
//    public void Streammap(){
//        List<String> names = Arrays.asList("张三", "李四", "王五");
//        List<Integer> nameLengths = names.stream()
//                .map(String::length)
//                .collect(Collectors.toList());
//        System.out.println(nameLengths); // 输出：[2, 2, 2]
//    }
//    //排序
//    @Test
//    public void Streamsorted(){
//        List<Integer> numbers = Arrays.asList(5, 3, 1, 4, 2);
//        List<Integer> sortedNumbers = numbers.stream()
//                .sorted()
//                .collect(Collectors.toList());
//        System.out.println(sortedNumbers); // 输出：[1, 2, 3, 4, 5]
//    }
//    //聚合
//    @Test
//    public void Streamreduce(){
//        int[] numbers = {1, 2, 3, 4, 5};
//        OptionalInt sum = IntStream.of(numbers).reduce((a, b) -> a + b);
//        System.out.println(sum.orElse(0)); // 输出：15
//    }
//    //并行处理
//    @Test
//    public void Streamparallel(){
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//        List<Integer> squaredNumbers = numbers.parallelStream()
//                .map(n -> n * n)
//                .collect(Collectors.toList());
//        System.out.println(squaredNumbers); // 输出可能为：[1, 4, 9, 16, 25]，具体顺序取决于线程调度
//    }
//}
