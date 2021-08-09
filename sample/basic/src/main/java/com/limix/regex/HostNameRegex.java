package com.limix.regex;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * 通过字符串,提取主机信息
 * node25,node26,node27
 * node[25-27] == node25,node26,node27
 * node[25,26,27] == node25,node26,node27
 * 192.168.1.[1-5] == 192.168.1.1,192.168.1.2,192.168.1.3,192.168.1.4,192.168.1.5
 * 192.168.1.[1,2,3,4,5] == 192.168.1.1,192.168.1.2,192.168.1.3,192.168.1.4,192.168.1.5
 * <p>
 * 三个元素 , [1-5] [1,2,3,4,5]
 * <p>
 * node[1-5,26],iflytek99 预期应该得到 node1,node2,node3,node4,node5,node26,iflytek99
 * <p>
 * 第一步: [1-5,26]替换解析程具体数字 [1;2;3;4;5;26]
 * 第二步: 按照逗号分割 node[1;2;3;4;5;26]  iflytek99
 * 第三步: 拼接 node1 node2 node3 node4 node5 node26 和 iflytek99
 * <p>
 * 改进逻辑
 * <p>
 * 第一步: 分词
 * 第二步: 分词流式处理
 * normal状态:不停读取字符,直到碰到了[或者,
 * more状态:当碰到了[
 *
 * 关键点:
 * 有几个状态 NORMAL MORE
 * 改变处理状态
 *
 * 从这里的抽象语法解析,我最终在想SQL是怎样解析的,然后想,calcite到底是通过什么来解决问题的,有没有可以复用的模式
 *
 * <table>
 * <th><tr><td>职责</td><td>输入</td><td>处理</td><td>输出</td></tr></th>
 * <tr><td>第一步</td><td>字符流</td><td>处理</td><td>token流</td></tr>
 * <tr><td>第二步</td><td>token流</td><td>处理</td><td>term流</td></tr>
 * </table>
 * 改进逻辑
 * 返回集合对象,返回迭代器
 */
public class HostNameRegex {

    private static HostNameRegex instance;

    private HostNameRegex() {
    }

    public static HostNameRegex getInstance() {
        if (instance == null) {
            instance = new HostNameRegex();
        }
        return instance;
    }

    public static void main(String[] args) {
        HostNameRegex instance = new HostNameRegex();
        instance.parseHosts("node[1-5,26],iflytek99,iflytek98,node[001-100],192.168.1.[1-100]");
    }

    public Set<String> parseHosts(String hostStr) {
        Set<String> hosts = new HashSet<String>();
        Reader reader = new StringReader(hostStr);
        HostNameAnalyzer analyzer = new HostNameAnalyzer();
        try {
            hosts.addAll(analyzer.process(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hosts;
    }

    enum TokenEnum {
        INIT, NORMAL, MORE;
    }

    class StringToken {

        private String item;

        private TokenEnum type;

        public StringToken() {
        }

        public StringToken(String item, TokenEnum type) {
            this.item = item;
            this.type = type;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public TokenEnum getType() {
            return type;
        }

        public void setType(TokenEnum type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "StringToken{" +
                    "item='" + item + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    class HostNameAnalyzer {

        public List<String> process(Reader reader) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(reader);
            List<String> hosts = new ArrayList<String>();
            StringToken prefixToken = null;
            StringToken currentToken = null;
            while (tokenizer.hasNext()) {
                prefixToken = currentToken;
                currentToken = tokenizer.next();
                System.out.println(String.format("token type[%s],value[%s]", currentToken.getType(), currentToken.getItem()));
                if (currentToken.getType() == TokenEnum.NORMAL){
                    if(prefixToken != null && prefixToken.getType() == TokenEnum.NORMAL){
                        hosts.add(prefixToken.getItem());
                    }
                } else if (currentToken.getType() == TokenEnum.MORE){
                    if (prefixToken != null){
                        // 根据前一个token和当前token来生成host
                        List<String> suffixs = parseCurrentMore(currentToken.getItem());
                        for (String suffix : suffixs){
                            hosts.add(prefixToken.getItem() + suffix);
                        }
                    } else {
                        throw new IOException(String.format("wrong token, prefix is null, suffix[%s]", currentToken.toString()));
                    }
                }
            }
            if(currentToken.getType() == TokenEnum.NORMAL){
                hosts.add(currentToken.getItem());
            }
            System.out.println(hosts);
            return hosts;
        }

        private List<String> parseCurrentMore(String input) throws IOException {
            String[] items = input.split(",");
            List<String> results = new ArrayList<String>();
            for (String item : items){
                if (item.contains("-")){
                    String[] beginEnds = item.split("-");
                    if (beginEnds.length == 2){
                        int begin = Integer.parseInt(beginEnds[0]);
                        int end = Integer.parseInt(beginEnds[1]);
                        for (int i=0; begin + i<= end; i++){
                            results.add(begin + i+"");
                        }
                    } else {
                        throw new IOException(String.format("wrong begin end[%s]", item));
                    }
                } else {
                    results.add(item);
                }
            }
            System.out.println(results);
            return results;
        }
    }


    /**
     * 将字符按照特定规则解析为 StringToken
     */
    class StringTokenizer implements Iterator<StringToken> {

        private Reader reader;
        private boolean inMoreBracket = false;
        private String current = "";
        StringToken currentToken = null;
        private Queue<StringToken> bufferTokens = new ArrayDeque<StringToken>();

        private char LEFT_BRACKET = '[';
        private char RIGHT_BRACKET = ']';
        private char COMMA = ',';

        public StringTokenizer(Reader r) {
            if (r == null) {
                throw new NullPointerException();
            }
            this.reader = r;
        }

        public boolean hasNext() {
            readToken();
            if (currentToken.getType() != TokenEnum.INIT) {
                return true;
            } else {
                return false;
            }
        }

        private void reset() {
            currentToken = new StringToken();
            currentToken.setType(TokenEnum.INIT);
            current = "";
        }

        public StringToken readToken() {
            reset();
            try {
                int currentChar = -1;
                while ((currentChar = reader.read()) != -1) {
                    if (inMoreBracket) {
                        if (currentChar != RIGHT_BRACKET) {
                            current += (char) currentChar;
                        } else {
                            currentToken.setItem(current);
                            currentToken.setType(TokenEnum.MORE);
                            inMoreBracket = false;
                            return currentToken;
                        }
                    } else {
                        if (currentChar != LEFT_BRACKET && currentChar != RIGHT_BRACKET && currentChar != COMMA) {
                            current += (char) currentChar;
                        } else if (currentChar == COMMA || currentChar == LEFT_BRACKET) {
                            if (currentChar == LEFT_BRACKET) {
                                inMoreBracket = true;
                            }
                            if (current.length() > 0) {
                                currentToken.setItem(current);
                                currentToken.setType(TokenEnum.NORMAL);
                                return currentToken;
                            }
                        } else {
                            throw new IOException("wrong bracket pairs");
                        }
                    }
                }

                if (inMoreBracket) {
                    throw new IOException("wrong end bracket pairs");
                } else {
                    if (current.length() > 0) {
                        currentToken.setItem(current);
                        currentToken.setType(TokenEnum.NORMAL);
                        return currentToken;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return currentToken;
        }

        public StringToken next() {
            return currentToken;
        }
    }
}
