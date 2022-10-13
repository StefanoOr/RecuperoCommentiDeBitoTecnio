package org.classificatore;

public class CsvClassificazione {

    private String commento;
    private String classificazione;
    private String nomeFile;


    public CsvClassificazione(String commento , String classificazione , String nomeFile){

        this.commento=commento;
        this.classificazione=classificazione;
        this.nomeFile=nomeFile;

    }

    public  CsvClassificazione (){
        
    }

    public String getCommento() {
        return commento;
    }

    public String getClassificazione() {
        return classificazione;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public void setClassificazione(String classificazione) {
        this.classificazione = classificazione;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile =nomeFile.replace("-","\\");

    }
}
