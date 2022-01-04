package uz.qiroldev.namozvaqtlari.services;

import org.springframework.stereotype.Service;

@Service
public class DateUtils {

    public String getNameIslamicMonth(Integer month){
        String moth = "";

        switch (month){
            case 1: {
                moth = "МУҲАРРАМ";
                break;
            }
            case 2: {
                moth = "САФАР";
                break;
            }
            case 3: {
                moth = "РАБИъ-УЛ-АВВАЛ";
                break;
            }
            case 4: {
                moth = "РАБИъ-УС-СОНИЙ";
                break;
            }
            case 5: {
                moth = "ЖУМОДУЛ-АВВАЛ";
                break;
            }
            case 6: {
                moth = "ЖУМОДУС-СОНИЙ";
                break;
            }
            case 7: {
                moth = "РАЖАБ";
                break;
            }
            case 8: {
                moth = "ШАъБОН";
                break;
            }
            case 9: {
                moth = "РАМАЗОН";
                break;
            }
            case 10: {
                moth = "ШАВВОЛ";
                break;
            }
            case 11: {
                moth = "ЗУЛҚАъДА";
                break;
            }
            case 12: {
                moth = "ZULHIJJA";
                break;
            }
            default:{
                break;
            }
        }
        return moth;
    }

    public String getNameMonth(Integer m){
        String month = "";

        switch (m){
            case 1:{
                month = "ЯНВАРЬ";
                break;
            }
            case 2: {
                month = "ФEВРАЛЬ";
                break;
            }
            case 3: {
                month = "МАРТ";
                break;
            }
            case 4: {
                month = "АПРEЛЬ";
                break;
            }
            case 5: {
                month = "МАЙ";
                break;
            }
            case 6:{
                month = "ИЮНЬ";
                break;
            }
            case 7:{
                month = "ИЮЛЬ";
                break;
            }
            case 8:{
                month = "АВГУСТ";
                break;
            }
            case 9:{
                month = "СEНТЯБРЬ";
                break;
            }
            case 10:{
                month = "ОКТЯБРЬ";
                break;
            }
            case 11:{
                month = "НОЯБРЬ";
                break;
            }
            case  12:{
                month = "ДEКАБРЬ";
                break;
            }
            default:{
                break;
            }
        }

        return month;
    }
}
