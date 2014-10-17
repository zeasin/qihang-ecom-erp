using Nancy;
using Nancy.Responses;
using Nancy.Authentication.Forms;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace LPOA.Web.Module
{
    public class HomeModule : NancyModule
    {
        public HomeModule()
        {
            //Before += ctx =>
            //{
            //    return (this.Context.CurrentUser == null) ? new HtmlResponse(HttpStatusCode.Unauthorized) : null;
            //};

            //this.RequiresHttps();
            //this.RequiresAuthentication();
            //this.RequiresClaims(new[] { "Admin" });
            
            Get["/"] = par =>
            {
                return View["index"];
            };


        }


    }
}