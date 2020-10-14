package com.boot_demo.demo1.test;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BeamSimpleTest {


    @Test
    public void beamTest() {
        List<String> LINES = Arrays.asList(
                "To be, or not to be: that is the question: ",
                "Whether 'tis nobler in the mind to suffer ",
                "The slings and arrows of outrageous fortune, ",
                "Or to take arms against a sea of troubles, ");

        //创建一个管道Pipeline
        PipelineOptions pipe = PipelineOptionsFactory.create();
        //设置Runtime类型，当我们不指定的时候，会默认使用DirectRunner这种类型
//         pipe.setRunner(DirectRunner.class);
        Pipeline pipeline = Pipeline.create(pipe);

        //使用原始数据创建数据集
        //PCollection表示Beam中任何大小的输入和输出数据。pipeLine读取数据输入，生成PCollection作为输出
        //使用Beam提供的Create从内存中的Java集合创建PCollection，Create接受Java Collection和一个Coder对象作为参数，在Coder指定的Collection中的元素如何编码。

        PCollection<String> words = pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());

        //统计每一个字符串的长度。
        //apply方法转化管道中的数据，转换采用PCollection（或多个PCollection）作为输入，在该集合中的每个元素上执行指定的操作，并生成新的输出PCollection
        //转换格式：[Output PCollection] = [Input PCollection].apply([Transform])
        PCollection<Integer> wordLengths = words.apply(
                "ComputeWordLengths", ParDo.of(new ComputeWordLengthFn()));

        System.out.println(wordLengths.getName());
        pipeline.run();
    }

    /**
     * 传递给ParDo的DoFn对象中包含对输入集合中的元素的进行处理,DoFn从输入的PCollection一次处理一个元素
     */
    static class ComputeWordLengthFn extends DoFn<String, Integer> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            // Get the input element from ProcessContext.
            String word = c.element();
            // Use ProcessContext.output to emit the output element.
            System.out.println(word.length());
            c.output(word.length());
        }
    }


    @Test
    public void test() {
        getDataFromFile();
    }

    public static void getDataFromFile() {
        // Create the pipeline.
        PipelineOptions options =
                PipelineOptionsFactory.create();
        Pipeline p = Pipeline.create(options);

        PCollection<String> lines = p.apply(TextIO.read().from("pom.xml"));

        lines.apply(new CountWords()) //返回一个Map<String, Long>
                .apply(MapElements.via(new FormatAsTextFn())) //返回一个PCollection<String>

                .apply(Window.into(FixedWindows.of(Duration.standardSeconds(10))))
                .apply("WriteCounts", TextIO.write().to("test.txt"));

        p.run().waitUntilFinish();
    }


    public static class CountWords extends PTransform<PCollection<String>,
            PCollection<KV<String, Long>>> {
        @Override
        public PCollection<KV<String, Long>> expand(PCollection<String> lines) {

            // 将文本行转换成单个单词
            PCollection<String> words = lines.apply(
                    ParDo.of(new ExtractWordsFn()));

            // 计算每个单词次数
            PCollection<KV<String, Long>> wordCounts =
                    words.apply(Count.<String>perElement());

            return wordCounts;
        }
    }

    /**
     * 1.a.通过Dofn编程Pipeline使得代码很简洁。b.对输入的文本做单词划分，输出。
     */
    static class ExtractWordsFn extends DoFn<String, String> {

        @ProcessElement
        public void processElement(ProcessContext c) {
            if (c.element().trim().isEmpty()) {
                return;
            }
            // 将文本行划分为单词
            String[] words = c.element().split("[^a-zA-Z']+");
            // 输出PCollection中的单词
            for (String word : words) {
                if (!word.isEmpty()) {
                    c.output(word);
                }
            }
        }
    }

    /**
     * 2.格式化输入的文本数据，将转换单词为并计数的打印字符串。
     */
    public static class FormatAsTextFn extends SimpleFunction<KV<String, Long>, String> {
        @Override
        public String apply(KV<String, Long> input) {
            System.out.println(input.getKey() + ": " + input.getValue());
            return input.getKey() + ": " + input.getValue();
        }
    }

}
