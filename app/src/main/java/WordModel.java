public class WordModel {
    String English;
    String Bangla;
    int primaryHash;
    int secondaryHash;
    WordModel(String English,String Bangla)
    {
        this.English = English;
        this.Bangla = Bangla;
    }
    public String getEnglish()
    {
        return this.English;
    }
    public String getBangla()
    {
        return this.Bangla;
    }
    public void setEnglish(String English)
    {
        this.English = English;
    }
    public void setBangla(String Bangla)
    {
        this.Bangla = Bangla;
    }
    public int getPrimaryHash()
    {
        return this.primaryHash;
    }
    public int getSecondaryHash()
    {
        return this.secondaryHash;
    }
    public void setPrimaryHash(int primaryHash)
    {
        this.primaryHash = primaryHash;
    }
    public void setSecondaryHash(int secondaryHash)
    {
        this.secondaryHash = secondaryHash;
    }
}
