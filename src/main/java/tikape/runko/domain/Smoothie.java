
package tikape.runko.domain;

public class Smoothie {
    private Integer id;
    private String nimi;

    public Smoothie(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

}
