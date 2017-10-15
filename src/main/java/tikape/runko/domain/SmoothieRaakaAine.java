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

    public void setRaakaAineId(Integer id) {
        this.raaka_aine_id = id;
    }
    
    public Integer getSmoothieId() {
        return smoothie_id;
    }

    public void setSmoothieId(Integer id) {
        this.smoothie_id = id;
    }

    public String getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(String jarjestys) {
        this.jarjestys = jarjestys;
    }
    
    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }
    
    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
//    Tänne ehkä nettisivun ulkonäköön soveltuva toString-metodi

}
