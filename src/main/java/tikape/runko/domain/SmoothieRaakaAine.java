package tikape.runko.domain;

public class SmoothieRaakaAine {
    private Integer raaka_aine_id;
    private Integer smoothie_id;
    private String jarjestys;
    private String maara;
    private String ohje;

    public SmoothieRaakaAine(Integer raid, Integer sid, String jarjestys, String maara, String ohje) {
        this.raaka_aine_id = raid;
        this.smoothie_id = sid;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getRaakaAineId() {
        return raaka_aine_id;
    }
    
    public Integer getSmoothieId() {
        return smoothie_id;
    }

    public String getJarjestys() {
        return jarjestys;
    }

    
    public String getMaara() {
        return maara;
    }
    
    public String getOhje() {
        return ohje;
    }

    
//    Tänne ehkä nettisivun ulkonäköön soveltuva toString-metodi

}
