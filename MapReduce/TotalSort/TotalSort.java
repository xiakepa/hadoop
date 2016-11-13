package com.xfyan.four;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler.RandomSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

public class TotalSort {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		Path partitionFile = new Path(args[2]);
		int reduceNumber = Integer.parseInt(args[3]);
		
		//RandomSampler��һ��������ʾ��ѡ�еĸ��ʣ��ڶ���������һ��ѡȡ��������������������������ȡ��InputSplit��
		RandomSampler<Text,Text> sampler = new InputSampler.RandomSampler<Text, Text>(0.1, 100,10);
		
		Configuration conf = new Configuration();
		
		//������ҵ�����ļ�·��
		TotalOrderPartitioner.setPartitionFile(conf,partitionFile);
		
		Job job = new Job();
		job.setJobName("TotalSort");
		job.setJarByClass(TotalSort.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setNumReduceTasks(reduceNumber);
		
		//����partition��
		job.setPartitionerClass(TotalOrderPartitioner.class);
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		outputPath.getFileSystem(conf).delete(outputPath,true);
		
		//д������ļ�
		InputSampler.writePartitionFile(job, sampler);
		System.out.println(job.waitForCompletion(true)?0:1);
		
		
	}
}
