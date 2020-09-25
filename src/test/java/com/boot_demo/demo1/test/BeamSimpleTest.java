package com.boot_demo.demo1.test;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
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
        PipelineOptions pipe= PipelineOptionsFactory.create();
        //设置Runtime类型，当我们不指定的时候，会默认使用DirectRunner这种类型
//         pipe.setRunner(DirectRunner.class);
        Pipeline pipeline= Pipeline.create(pipe);

        //使用原始数据创建数据集
        //PCollection表示Beam中任何大小的输入和输出数据。pipeLine读取数据输入，生成PCollection作为输出
        //使用Beam提供的Create从内存中的Java集合创建PCollection，Create接受Java Collection和一个Coder对象作为参数，在Coder指定的Collection中的元素如何编码。

        PCollection<String> words = pipeline.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());

        //统计每一个字符串的长度。
        //apply方法转化管道中的数据，转换采用PCollection（或多个PCollection）作为输入，在该集合中的每个元素上执行指定的操作，并生成新的输出PCollection
        //转换格式：[Output PCollection] = [Input PCollection].apply([Transform])
        PCollection<Integer> wordLengths = words.apply(
                "ComputeWordLengths",  ParDo.of(new ComputeWordLengthFn()));

        pipeline.run().waitUntilFinish();

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
}
