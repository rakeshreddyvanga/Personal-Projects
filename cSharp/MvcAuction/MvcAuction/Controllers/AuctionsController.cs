using MvcAuction.Models;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;

namespace MvcAuction.Controllers
{
    public class AuctionsController : Controller
    {
        //
        // GET: /Auctions/

        [AllowAnonymous]
        public ActionResult Index()
        {
            var db = new AuctionsDataContext();
            var auctions = db.Auctions.ToArray();
            /*var auctions = new[] {
            new Models.Auction () {
                Title = "1. Auction",
                StartTime = DateTime.Now,
                StartPrice = 25.43m,
                EndTime = DateTime.Now.AddDays(4),
                Description = " This the demo auction using model directly in view",
                CurrentPrice = null,
            },
            new Models.Auction () {
               Title = "2. Auction",
                StartTime = DateTime.Now,
                StartPrice = 25.43m,
                EndTime = DateTime.Now.AddDays(2),
                Description = " This the demo auction using model directly in view",
                CurrentPrice = 22.34m,
            },
            new Models.Auction () {
               Title = "3. Auction",
                StartTime = DateTime.Now,
                StartPrice = 25.43m,
                EndTime = DateTime.Now.AddDays(1),
                Description = " Third Auction",
                CurrentPrice = 56.98m,
            }
            }; */

            return View(auctions);
        }

        public ActionResult Auction(long id = 1)
        {

            var db = new AuctionsDataContext();
            var auction = db.Auctions.Find(id);
            if(auction == null)
                return HttpNotFound();
           /* var auction = new MvcAuction.Models.Auction()
            {
                Id = id,
                Title = "Example Auction",
                StartTime = DateTime.Now,
                StartPrice = 25.43m,
                EndTime = DateTime.Now.AddDays(4),
                Description = " This the demo auction using model directly in view",
                CurrentPrice = null,
            };

            ViewData["Auction"] = auction;
            */
            return View(auction);
        }

        [HttpGet]
        public ActionResult Create()
        {
            var category = new SelectList(new[] { "Automative", "Electronics", "Home", "Educational" });
            ViewBag.CategoryList = category;
            return View();
        }

        [HttpPost]
        //[Authorize (Roles="Admin")]
        [Authorize(Users="rvanga")]
        public ActionResult Create([Bind(Exclude="CurrentPrice")]Models.Auction auction)
        {
           /* if (string.IsNullOrWhiteSpace(auction.Title))
            {
                ModelState.AddModelError("Title","Title is expected!");
            }
            else if (auction.Title.Length < 5 || auction.Title.Length > 200)
            {
                ModelState.AddModelError("Title", "Title should be between 5 and 200 characters");
            }
            */

            if (ModelState.IsValid)
            {
                //save to data base
                //Response.Write("<script type='text/javascript'> alert('Saved to the database')</script>");
                var db = new AuctionsDataContext();
                db.Auctions.Add(auction);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return Create();
        }

        [HttpPost]
        [Authorize]
        [ValidateAntiForgeryToken]
        public ActionResult Bid(Bid bid)
        {
            var db = new AuctionsDataContext();
            var auction = db.Auctions.Find(bid.AuctionId);
            if (auction == null)
            {
                ModelState.AddModelError("AuctionId", "Auction not found!");
            }
            else if (auction.CurrentPrice >= bid.Amount)
            {
                ModelState.AddModelError("Amount", "New Bid should exceed the current price");
            }
            else
            {
                bid.UserName = User.Identity.Name;
                auction.Bids.Add(bid);
                auction.CurrentPrice = bid.Amount;
                db.SaveChanges();
            }

            //return RedirectToAction("Auction", new { id = bid.AuctionId });

            if (!Request.IsAjaxRequest()) // if not an ajax request, return norml return
                return RedirectToAction("Auction", new { id = bid.AuctionId });

            if (ModelState.IsValid)
            {
                return Json(new
                {
                    CurrentPrice = bid.Amount.ToString("C"),
                    BidCount = auction.BidCount
                });
            }
            else
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);

            /*
            // to return a partial view
            if (ModelState.IsValid)
                return PartialView("_CurrentPricePartial", auction);
            else
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
             */
            
            /* this code is just only for response
           // if ajax request sent an ajax response
            var httpStatus = ModelState.IsValid ? HttpStatusCode.OK : HttpStatusCode.BadRequest;
            return new HttpStatusCodeResult(httpStatus);
             */
        }

    }
}
