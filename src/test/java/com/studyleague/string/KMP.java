package com.studyleague.string;

/**
 * 根据http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html，
 * 未实现next数组，个人理解next数组是一种编程trick
 *
 * @author Vince
 * @since 2021/2/10
 */
public class KMP {
    private String pattern;
    private int[] pmt;

    public KMP(String pattern) {
        initPmt(pattern);
    }

    /**
     * 返回text中首次出现pattern的起始位置
     *
     * @param text
     * @return
     */
    public int match(String text) {
        int i = 0;
        int patternLen = this.pattern.length();
        int textLen = text.length();
        while (i < textLen) {
            // i默认后移一位
            int step = 1;
            for (int j = 0; j < patternLen; j++) {
                // text剩余部分已不足pattern串长，无需比较
                if (i + patternLen > textLen) {
                    return -1;
                }
                // 匹配失败
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    // 非首位匹配失败时，由已部分匹配长度-pmt值，得到后移位数，停止本轮循环
                    // 否则表示首位匹配失败，此时默认后移一位即可
                    if (j != 0) {
                        step = j - this.pmt[j - 1];
                    }
                    break;
                }
                // 最后一个字符匹配成功，返回首次出现pattern的起始位置
                else if (j == patternLen - 1) {
                    return i;
                }
            }
            // 后移step位
            i += step;
        }
        return -1;
    }

    private void initPmt(String pattern) {
        this.pattern = pattern;
        this.pmt = new int[pattern.length()];
        for (int i = 0; i < pattern.length(); i++) {
            this.pmt[i] = 0;
            int value = 0;
            for (int j = 0; j < i; j++) {
                // 下面比较的子串长度都是j，前者从0开始往后j个字符（前缀），后者从i开始往
                // 前j个字符（i是外层循环中，当前子串的最后一个位置，即后缀的起始点）
                // 相等则表示前缀等于后缀，此时取j+1即公共前后缀的长度
                if (pattern.substring(0, j + 1).equals(pattern.substring(i - j, i + 1))) {
                    value = j + 1;
                }
                this.pmt[i] = value;
            }
        }
    }

    public static void main(String[] args) {
        String pattern = "ABCDABD";
        KMP kmp = new KMP(pattern);
        int index = kmp.match("BBC ABCDAB ABCDABCDABDE");
        System.out.println(index);
    }
}
