package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloLombok2 {
	private final String hello;
	private final int lombok;
	
	public static void main(String[] args) {
		HelloLombok2 helloLombok = new HelloLombok2("헬로", 5);
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}

	// 어노테이션으로 대체.
//	public HelloLombok(String hello, int lombok) {
//		this.hello = hello;
//		this.lombok = lombok;
//	}
}
