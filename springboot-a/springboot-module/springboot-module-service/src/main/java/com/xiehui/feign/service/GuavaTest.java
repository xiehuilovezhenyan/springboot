package com.xiehui.feign.service;

import java.util.stream.IntStream;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class GuavaTest {
	
	public static void main(String[] args) {
		// 布隆过滤器
		// BloomFilter<String> strBloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.000001);
		BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 100000000,0.0001);
		IntStream.range(0, 100_000).forEach(filter::put);
		System.out.println("1");
		System.out.println(filter.mightContain(1));
		
	}

}
