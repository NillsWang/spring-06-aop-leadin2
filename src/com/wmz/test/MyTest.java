package com.wmz.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.wmz.service.ISomeService;
import com.wmz.service.SomeServiceImpl;
import com.wmz.utils.SystemService;

public class MyTest {
	@Test
	public void test01() {
		//AOP面向切面编程，是面向对象编程OOP的一种补充。面向对象编程是从静态角度考虑的
		//	而面向切面编程是从动态角度考虑程序运行过程。
		//	面向切面编程，就是将交叉业务封装成切面，利用AOP容器的功能将切面织入到主业务
		//		逻辑中。所谓的交叉业务逻辑是指，通用的，与主业务逻辑无关的代码，如安全
		//		检查，事务，日志等。
		//AOP编程术语:
		//	切面：切面泛指交叉逻辑，常用的切面有通知与顾问，实际上就是对主业务的增强
		//	织入：将切面代码插入到目标对象的过程
		//	连接点：指可以被切面织入的方法，通常业务接口中的方法均为连接点
		//	切入点：指切面具体织入方法，被标记final的方法不能织入切面，方法本身不能被修改
		//	目标对象：指要被增强的对象，即包含主业务逻辑类的对象
		//	通知：通知是切面的一种实现，可以完成简单的织入功能(织入功能就是在这里完成)，通知
		//		定义了增强代码切入到目标代码的时间点，具体在目标方法执行之前还是之后执行，
		//		通知类型不同，切入时间不同(切入时间)
		//	顾问：是切面的另一种实现，能够将通知以更为复杂的方式织入到目标对象中，是将通知包
		//		装为更复杂切面的装配器(切入时间和位置)
		//	
		//AOP底层就是采用动态代理来实现的，采用了两种代理:
		//	jdk的Proxy动态代理与CGLIB的动态代理
		
		//jdk的动态代理Proxy
		ISomeService target = new SomeServiceImpl();
		ISomeService service = (ISomeService) Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						// TODO Auto-generated method stub
						//目标方法
						SystemService.doTx();
						Object result = method.invoke(target, args);
						SystemService.doLog();
						return result;
					}
				});
				
		service.doFirst();
		System.out.println("-------------------");
		service.doSecond();
	}

}
