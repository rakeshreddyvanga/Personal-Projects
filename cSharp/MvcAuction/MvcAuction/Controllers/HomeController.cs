using MvcAuction.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace MvcAuction.Controllers
{
    [AllowAnonymous]
   // [OutputCache(Duration = 5)] to demonstrate how to cache the entire pages.
   // Placing this line caches all the pages that correspond to this Controller.
    public class HomeController : Controller
    {
        [OutputCache(Duration=5)] // caching this particalur entire view for 5 seconds
        public ActionResult Index()
        {
            
            /* var db = new AuctionsDataContext();
            var categories = db.Auctions.Select(x => x.Category).Distinct();
            ViewBag.Categories = categories.ToArray(); */

            ViewBag.Message = "This page is rendered at " + DateTime.Now;

            return View();
        }

      /* this is called creating child actions where you are developing a controller
       * for generating a partial view 
       * */        
        [OutputCache(Duration = 3600)]
        public ActionResult CategoryListItems()
        {
            var db = new AuctionsDataContext();
            var categories = db.Auctions.Select(x => x.Category).Distinct();
            ViewBag.Categories = categories.ToArray();

            //to demostrate the cache concept of just the required data in a html page
            return PartialView();
        }

        public ActionResult About()
        {
            ViewBag.Message = "This page is rendered at " + DateTime.Now;

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "This page is rendered at " + DateTime.Now;

            return View();
        }
    }
}
