package com.limix.hello.lucene.analysis;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public class AnalysisDemo {

	public static void main(String[] args) {
		// 1. 构建分析器
		Analyzer analyzer = new StandardAnalyzer();
		// 2. 得到分析流
		TokenStream ts = analyzer.tokenStream("myfield", new StringReader("There is always a better way"));
		// 3. 添加需要分析和关注的属性
		OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
		try {
			// 4. 流初始化
			ts.reset();
			// 5. 遍历消费
			while (ts.incrementToken()) {
				System.out.println("token: " + ts.reflectAsString(true));
				System.out.println("token start offset: " + offsetAtt.startOffset());
				System.out.println("token end offset: " + offsetAtt.endOffset());
			}
			// 6. 结束流
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7. 关闭流
				ts.close();
				analyzer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
