package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.SmoothieDao;
import tikape.runko.database.SmoothieRaakaAineDao;

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
            raakaAineet.save(req.queryParams("name"));

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

            return new ModelAndView(map, "toiminnot");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/toiminnot", (req, res) -> {
            smoothies.save(req.queryParams("name"));

            res.redirect("/toiminnot");
            return "";
        });
        

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
