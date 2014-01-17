using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace MvcApplication1.Controllers
{
    public class HelloWorldController : Controller
    {
        //
        // GET: /HelloWorld/
        
        public ActionResult Index()
        {
            return View();
        }

        public String Welcome(String name,int id)
        {
            return HttpUtility.HtmlEncode("Hello "+name+", Welcome to the class your id is:"+id);
        }
    }
}
