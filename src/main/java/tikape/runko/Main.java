package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.SmoothieDao;
import tikape.runko.database.SmoothieRaakaAineDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Smoothie;
import tikape.runko.domain.SmoothieRaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothiedata.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);

        SmoothieDao smoothies = new SmoothieDao(database);
        RaakaAineDao raakaAineet = new RaakaAineDao(database);
        SmoothieRaakaAineDao smoothieRaakaAineet = new SmoothieRaakaAineDao(database);

        Spark.get("/raakikset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakikset", raakaAineet.findAll());

            return new ModelAndView(map, "raakikset");
        }, new ThymeleafTemplateEngine());

        Spark.post("/raakikset", (req, res) -> {
            String nimi = req.queryParams("name");
            RaakaAine raakis = new RaakaAine(-1, nimi);
            raakaAineet.saveOrUpdate(raakis);

            res.redirect("/raakikset");
            return "";
        });

        Spark.post("/raakikset/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            raakaAineet.delete(id);
            smoothieRaakaAineet.deleteRaakaAine(id);
            res.redirect("/raakikset");
            return "";
        });

        Spark.get("/paasivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothies.findAll());

            return new ModelAndView(map, "paasivu");
        }, new ThymeleafTemplateEngine());

        Spark.get("/toiminnot", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothies.findAll());
            map.put("raakikset", raakaAineet.findAll());

            return new ModelAndView(map, "toiminnot");
        }, new ThymeleafTemplateEngine());

        Spark.post("/toiminnot", (req, res) -> {
            String nimi = req.queryParams("name");
            Smoothie juoma = new Smoothie(-1, nimi);
            smoothies.saveOrUpdate(juoma);

            res.redirect("/toiminnot");
            return "";
        });

        Spark.post("/toiminnot/data", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.queryParams("smoothieId"));
            Integer raakisId = Integer.parseInt(req.queryParams("raakisId"));
            String jarjestys = req.queryParams("jarjestys");
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            smoothieRaakaAineet.save(new SmoothieRaakaAine(raakisId, smoothieId, jarjestys, maara, ohje));

            res.redirect("/toiminnot");
            return "";
        });

        Spark.get("/smoothie/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params(":id"));
            List<SmoothieRaakaAine> lista = smoothieRaakaAineet.findAll(id);
            List<String> tulostettavat = new ArrayList<>();
            for (SmoothieRaakaAine a : lista) {
                tulostettavat.add(raakaAineet.findOne(a.getRaakaAineId()).getNimi() + ": " + a.getJarjestys() + " " + a.getMaara() + " " + a.getOhje());
            }
            map.put("tulosteet", tulostettavat);
            map.put("smoothie", smoothies.findOne(id));
            return new ModelAndView(map, "smoothie");

        }, new ThymeleafTemplateEngine());

//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
