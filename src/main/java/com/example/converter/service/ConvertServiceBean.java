package com.example.converter.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConvertServiceBean implements ConvertService{
    private final String[] wordsInFeminineGender = {"одна","две"};
    private final String[] wordsForUnits =  {"один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
    private final String[] wordsFor10to19 = {"десять","одиннадцать", "двенадцать", "тринадцать","четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private final String[] wordsForDozens = {"двадцать", "тридцать", "сорок", "пятьдесят","шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private final String[] wordsForHundreds = {"сто","двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};
    private final String[][] wordsForThousandsAndBeyond = {
            {"тысяча", "тысячи", "тысяч"},
            {"миллион", "миллиона", "миллионов"},
            {"миллиард", "миллиарда", "миллиардов"}
    };
    private final List<String> wordsForConversionToNumber = List.of(
            "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять",
            "одиннадцать", "двенадцать", "тринадцать","четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать",
            "двадцать", "тридцать", "сорок", "пятьдесят","шестьдесят", "семьдесят", "восемьдесят", "девяносто",
            "сто","двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"
    );
    private final List<Integer> numbersForConversionToNumber = List.of(
           1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,30,40,50, 60,70,80,90,100,200,300,400,500,600,700,800,900);
    private long numberForCheckEndWord;
    private int countAddedInThisIteration = 0;
    private boolean isMinus = false;
    private boolean isZero = false;
    private int countCircle = 0;

    @Override
    public String convert(String dataForConversion,String type) throws NumberFormatException{
        if (Objects.equals(type, "NumberToString")) {
            long longForConversion=0;
            try {
                longForConversion = Long.parseLong(dataForConversion);
            }
            catch (Exception ex){
                throw new NumberFormatException(String.format("Ошибка при попытке преобразовать значение {%s}, убедитесь что выбрали правильный тип",dataForConversion));
            }
            return extractWords(longForConversion);
        }
        else if (Objects.equals(type, "StringToNumber")) return extractNumbers(dataForConversion);
        return null;
    }
    private String extractNumbers(String stringForConversion){
        List<String> listForConversion = Arrays.stream(stringForConversion.split(" ")).toList();
        Long result = 0L;
        if(Objects.equals(listForConversion.get(0), "ноль")) return "0";
        boolean isMinus = false;
        countCircle=0;
        for (String value : listForConversion) {
            countCircle++;
            if(value.equals("минус")) isMinus = true;
            else if(value.equals("одна")) result+=1;
            else if (value.equals("две")) result +=2;
            else if(wordsForConversionToNumber.contains(value)){
                result += numbersForConversionToNumber.get(wordsForConversionToNumber.indexOf(value));
            }
            else result = checkIfNumberMoreThanThousand(value,result);
        }
        if(result == 0L) throw new NumberFormatException(String.format("Ошибка при попытке преобразовать значение {%s}, убедитесь что выбрали правильный тип",stringForConversion));
        if(isMinus) result*=-1;
        return result.toString();
    }
    private long checkIfNumberMoreThanThousand(String value,long result){
        long thousand = 1000;
        long million  = 1000000;
        long milliard = 1000000000;
        if(value.contains("тысяч")) {
            result = calculateResult(result, thousand, million);
        }
        else if (value.contains("миллион"))
            result = calculateResult(result, million, milliard);
        else if (value.contains("миллиард")) {
            if(result == 0) result = milliard;
            else result *= milliard;
        }
        return result;
    }
    private long calculateResult(long result, long x,long xMultipliedBy1000){
        if(result >= xMultipliedBy1000){
            result = (result / xMultipliedBy1000)*xMultipliedBy1000 + result % xMultipliedBy1000 * x;
        }
        else {
            if (result == 0) result=x;
            else result *= x;
        }
        return result;
    }
    public String extractWords(long longForConversion){
        List<String> resultList = new LinkedList<>();
        byte index = -1;
        longForConversion = extractMinus(longForConversion);
        countCircle = 0;
        while (longForConversion != 0){
            countCircle++;
            resetVariables();
            longForConversion = extractDozens(longForConversion, resultList);
            longForConversion = extractHundreds(longForConversion,resultList);
            if(!(countAddedInThisIteration == 0) && index!=-1) {
                addWordsWithCorrectEndingsToResultList(resultList,index);
            }
            index++;
        }
        if (isMinus) resultList.add("минус");
        return createResultString(resultList);
    }
    private void addWordsWithCorrectEndingsToResultList(List<String> resultList,int index){
        if (numberForCheckEndWord <= 1) {
            resultList.add(resultList.size() - countAddedInThisIteration, wordsForThousandsAndBeyond[index][0]);
        } else if (numberForCheckEndWord < 5) {
            resultList.add(resultList.size() - countAddedInThisIteration, wordsForThousandsAndBeyond[index][1]);
        } else {
            resultList.add(resultList.size() - countAddedInThisIteration, wordsForThousandsAndBeyond[index][2]);
        }
    }
    private String createResultString(List<String> resultList){
        StringBuilder stringResult = new StringBuilder();
        for (int j = resultList.size() -1;j>=0; j--) {
            stringResult.append(resultList.get(j)).append(" ");
        }
        return new String(stringResult);
    }
    private void resetVariables(){
        countAddedInThisIteration = 0;
        numberForCheckEndWord = 0;
        isZero = false;
    }
    private long extractHundreds(long longForConversion, List<String> resultList) {
        long temp = longForConversion % 10;
        if (temp==0){
            return longForConversion / 10;
        }
        countAddedInThisIteration++;
        if(isZero)
            numberForCheckEndWord = numbersForConversionToNumber.get(wordsForConversionToNumber.indexOf(wordsForHundreds[(int) temp - 1]));
        resultList.add(wordsForHundreds[(int) temp - 1]);
        return longForConversion / 10;
    }
    private long extractDozens(long longForConversion, List<String> result) {
        long remainder = longForConversion % 100;
        if(remainder==0) {
            isZero = true;
            return longForConversion / 100;
        }
        countAddedInThisIteration++;
        if (remainder < 10) {
            extractUnits(remainder,result);
            numberForCheckEndWord = remainder;
        } else if (remainder < 20) {
            result.add(wordsFor10to19[(int) remainder % 10]);
            numberForCheckEndWord = remainder;
        } else {
            extractDozensMoreThan19(remainder,result);
        }
        return longForConversion / 100;
    }
    private void extractDozensMoreThan19(long remainder,List<String> result){
        if (remainder % 10 != 0) {
            long temp=remainder%10;
            numberForCheckEndWord = temp;
            extractUnits(temp,result);
            result.add(wordsForDozens[(int) remainder / 10 - 2]);
            countAddedInThisIteration++;
        } else {
            result.add(wordsForDozens[(int) remainder / 10 - 2]);
        }
    }
    private void extractUnits(long remainder,List<String> result){
        if(countCircle ==2 && remainder <= 2) result.add(wordsInFeminineGender[(int)remainder -1]);
        else result.add(wordsForUnits[(int) remainder - 1]);
    }
    private long extractMinus(long longForConversion){
        isMinus = false;
        if(longForConversion < 0){
            isMinus = true;
            return Math.abs(longForConversion);
        }
        return longForConversion;
    }

}

