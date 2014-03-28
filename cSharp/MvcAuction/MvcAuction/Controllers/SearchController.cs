using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using MvcAuction.Models;


namespace MvcAuction.Controllers
{
    public class SearchController : AsyncController
    {
        public async Task<ActionResult> Auctions(string key)
        {
            var auctions = await Task.Run<IEnumerable<Models.Auction>>(
                () =>
                {
                    var db = new Models.AuctionsDataContext();
                    return db.Auctions.Where(x => x.Title.Contains(key)).ToArray();

                });

            return Json(auctions, JsonRequestBehavior.AllowGet);
        }
    }
}

