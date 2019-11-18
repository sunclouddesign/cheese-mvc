package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /menu
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCategoryForm(@ModelAttribute @Valid Menu newMenu,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId) {
        Menu menu = menuDao.findById(menuId).orElse(null);
        model.addAttribute("menu",menu);
        model.addAttribute("title",menu.getName());

        return "menu/view";

    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveMenuForm(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Remove Menus");
        return "menu/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] menuIds) {

        for (int menuId : menuIds) {
            menuDao.deleteById(menuId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {

        Menu menu = menuDao.findById(menuId).orElse(null);
        String name = menu.getName();
        Iterable<Cheese> cheeses = cheeseDao.findAll();
        model.addAttribute("title", "Add item to menu: " + name);
        AddMenuItemForm form = new AddMenuItemForm(menu,cheeses);
        model.addAttribute("form", form);
        return "menu/add-item";

    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm form,
                          Errors errors, Model model, @RequestParam int menuId, @RequestParam int cheeseId) {

        if (errors.hasErrors()) {
            return "menu/add-item";
        }

        Cheese theCheese = cheeseDao.findById(cheeseId).orElse(null);
        Menu theMenu = menuDao.findById(menuId).orElse(null);
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:view/" + theMenu.getId();
        /*return "redirect:";*/
    }


}
